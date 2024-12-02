package com.pixearth.core;

import com.pixearth.core.database.connection.DatabaseManager;
import com.pixearth.core.options.Option;
import com.pixearth.core.options.OptionManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

/**
 * Plugin racine pour l'ensemble des plugins du serveur PixEarth
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class PixEarthCore extends JavaPlugin {

    /**
     * Instances
     */
    private DatabaseManager databaseManager;
    private ConfigLoader configLoader;
    private FileConfiguration config;
    private static PixEarthCore instance;
    private OptionManager optionManager;
    private Logger logger;

    /**
     * Lors de l'activation du plugin
     */
    @Override
    public void onEnable() {

        instance = this;

        try {
            this.preSetup();
        } catch (Exception e) {

            this.logger.fatal("Une erreur s'est produite durant l'initialisation du plugin " + getName() +
                    ", impossible de demarrer le serveur. Veuillez regarder le fichier de log " +  this.logger.getLogFile() + " pour corriger le probleme.", e);

            getServer().shutdown();
            return;
        }

    }

    /**
     * Lors de la désactivation du plugin
     */
    @Override
    public void onDisable() {

        if(this.logger != null) {
            this.logger.closeFileWriter();
        }
    }

    /**
     * Initialise le plugin avant de l'activer
     *
     * @throws Exception Exception s'il y a une erreur lors de l'initialisation du plugin
     */
    private void preSetup() throws Exception {

        this.createDataFolder();
        this.logger = new Logger(getLogger(), this);
        this.loadConfig();
        this.initDatabase();
        this.loadOptions();
    }

    /**
     * Permet de créer le dossier data du plugin dans le dossier /plugins
     */
    private void createDataFolder() throws Exception {

        File dir = getDataFolder();
        if(!dir.exists()) {
            if(!dir.mkdir()) {
                throw new Exception("Impossible de creer le dossier plugins/"+ dir.getName());
            }
        }
    }

    /**
     * Initialise la base de données
     */
    private void initDatabase() {

        DatabaseManager databaseManager = new DatabaseManager();

        databaseManager.registerAnnotatedClass(Option.class);
        databaseManager.setup();

        this.databaseManager = databaseManager;
    }

    /**
     * Charge le fichier de configuration du plugin
     */
    private void loadConfig() {

        if(this.configLoader == null) {
            this.configLoader = new ConfigLoader(this);
        }

        this.config = this.configLoader.load();
    }

    /**
     * Initialise le manager d'option
     */
    private void loadOptions() {

        OptionManager manager = new OptionManager();
        manager.loadOptions();
        this.optionManager = manager;
    }

    /**
     * @return Retourne le fichier de configuration
     */
    @Override
    public FileConfiguration getConfig() { return this.config; }

    /**
     * @return Retourne l'instance du plugin
     */
    public static PixEarthCore getInstance() { return instance; }

    /**
     * @return Retourne le manager
     */
    public OptionManager getOptions() { return this.optionManager; }

    /**
     * @return Retourne le databaseManager
     */
    public DatabaseManager getDatabaseManager() { return this.databaseManager; }

    /**
     * @return Retourne le logger du plugin
     */
    public static Logger log() { return instance.logger; }

}