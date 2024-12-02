package com.pixearth.core.database.connection;

import com.pixearth.core.PixEarthCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet de gérer les connexions vers les différentes bases de données
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class DatabaseManager {

    /**
     * Instance de la classe
     * @see #DatabaseManager()
     * @see #getInstance()
     */
    private static DatabaseManager instance;

    /**
     * Session hibernate
     * @see #setup()
     */
    private Session hibernateSession;

    /**
     * Configuration de la base de données (url, utilisateur, mot de passe, ...)
     * @see #DatabaseManager()
     */
    private DatabaseSettings baseDatabaseSettings;

    /**
     * Liste des classes qui doivent être enregistrées dans
     * @see #setup()
     * @see #registerAnnotatedClass(Class)
     */
    private List<Class> annotatedClass = new ArrayList<>();

    /**
     * Constructor
     */
    public DatabaseManager() {

        instance = this;

        FileConfiguration config = PixEarthCore.getInstance().getConfig();

        // Configuration pour l'accès à la base de données
        DatabaseSettings settings = this.createProperties(
                config.getString("database.idle.driver"),
                config.getString("database.idle.url"),
                config.getString("database.idle.user"),
                config.getBoolean("database.idle.debug", false)
        );

        String password = config.getString("database.idle.password");
        if(password != null && ! password.isEmpty()){
            settings.setPassword(password);
        }

        this.baseDatabaseSettings = settings;
    }

    /**
     * @return DatabaseManager Retourne l'instance du manager
     */
    public static DatabaseManager getInstance() {

        return instance;
    }

    /**
     * @return Retourne la session hibernate pour effectuer les requêtes
     */
    public Session getHibernateSession() { return this.hibernateSession; }

    /**
     * Permet de créer une instance de la configuration de connexion
     *
     * @param driver    Driver
     * @param url       Url
     * @param user      Utilisateur
     * @return  Retourne une instance DatabaseSettings
     */
    private DatabaseSettings createProperties(String driver, String url, String user, boolean showSql) {

        return (new DatabaseSettings())
                .setDriver(driver)
                .setUrl(url)
                .setShowSql(showSql)
                .setUser(user);
    }

    /**
     * Enregistre une classe pour hibernate
     *
     * @param className Nom de la classe
     * @return  Retourne l'instance de la classe
     */
    public DatabaseManager registerAnnotatedClass(Class className) {

        this.annotatedClass.add(className);
        return this;
    }

    /**
     * Configure la session hibernate après une modification de la configuration
     */
    public void setup() {

        // Ferme la connexion avant d'en ouvrir une nouvelle
        if(this.hibernateSession != null) {
            this.hibernateSession.close();
        }

        Configuration configuration = new Configuration();
        configuration.setProperties(this.baseDatabaseSettings);

        // Enregistre les classes
        for(Class cls : this.annotatedClass) {
            configuration.addAnnotatedClass(cls);
        }

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        this.hibernateSession = sessionFactory.openSession();
    }

}