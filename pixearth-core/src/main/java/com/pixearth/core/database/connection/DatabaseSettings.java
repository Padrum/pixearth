package com.pixearth.core.database.connection;

import org.hibernate.cfg.Environment;
import java.util.HashMap;
import java.util.Properties;

/**
 * Classe qui permet de créer une configuration pour la connexion à une base de données hibernate
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class DatabaseSettings extends Properties {

    /**
     * HashMap qui contient pour chaque driver le dialect associé
     * https://www.tutorialspoint.com/hibernate/hibernate_configuration.htm
     *
     * @see #setDriver(String)
     */
    private HashMap<String, String> dialectByDrivers = new HashMap<String, String>() {{
        put("com.mysql.jdbc.Driver", "org.hibernate.dialect.MySQLDialect");
    }};

    /**
     * Set l'utilisateur de la base de données
     *
     * @param user  Utilisateur
     * @return  Retourne l'instance de la classe
     */
    public DatabaseSettings setUser(String user) {

        this.put(Environment.USER, user);
        return this;
    }

    /**
     * Set le mot de passe de la base de données
     *
     * @param password  Mot de passe
     * @return  Retourne l'instance de la base de données
     */
    public DatabaseSettings setPassword(String password) {

        this.put(Environment.PASS, password);
        return this;
    }

    /**
     * Set le driver
     *
     * @param driver Driver
     * @return  Retourne l'instance de la classe
     */
    public DatabaseSettings setDriver(String driver) {

        this.put(Environment.DRIVER, driver);

        // Ajoute automatiquement le dialect qui va avec le driver
        if(this.get(Environment.DIALECT) == null && this.dialectByDrivers.containsKey(driver)) {
            this.setDialect(this.dialectByDrivers.get(driver));
        }

        return this;
    }

    /**
     * Set l'url de la base de données
     *
     * @param url   Url
     * @return  Retourne l'instance de la classe
     */
    public DatabaseSettings setUrl(String url) {

        this.put(Environment.URL, url);
        return this;
    }

    /**
     * Set le dialect de la base de données
     * https://www.tutorialspoint.com/hibernate/hibernate_configuration.htm
     *
     * @param dialect Dialect
     * @return  Retourne l'instance de la classe
     */
    public DatabaseSettings setDialect(String dialect) {

        this.put(Environment.DIALECT, dialect);
        return this;
    }

    /**
     * Set l'affichage des requêtes Sql
     * https://www.mkyong.com/hibernate/hibernate-display-generated-sql-to-console-show_sql-format_sql-and-use_sql_comments/
     *
     * @param show  Si true affiche les requêtes
     * @return  Retourne l'instance de la classe
     */
    public DatabaseSettings setShowSql(boolean show) {

        this.put(Environment.SHOW_SQL, show);
        return this;
    }

}
