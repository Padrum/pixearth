package com.pixearth.idle;

import com.pixearth.core.ConfigLoader;
import com.pixearth.core.Logger;
import com.pixearth.core.PixEarthCore;
import com.pixearth.core.database.connection.DatabaseManager;
import com.pixearth.core.options.OptionManager;
import com.pixearth.idle.commands.IdleCommand;
import com.pixearth.idle.events.idle.PlayerJoinIdleGameEvent;
import com.pixearth.idle.events.idle.PlayerJoinNewIdleGameEvent;
import com.pixearth.idle.events.idle.PlayerQuitIdleGameEvent;
import com.pixearth.idle.events.PlayerQuitServerEvent;
import com.pixearth.idle.game.plot.IdlePlotManager;
import com.pixearth.idle.models.IdleGameModel;
import com.pixearth.idle.schematic.IdleV1Schematic;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public class PixEarthIdle extends JavaPlugin {

    /**
     * Instances
     */
    private static PixEarthIdle instance;
    private WorldGuardPlugin worldGuardPlugin;
    private WorldEditPlugin worldEdit;
    private ConfigLoader configLoader;
    private FileConfiguration config;
    private PixEarthCore pluginCore;
    private Logger logger;

    /**
     * Lors du demarrage du plugin
     */
    @Override
    public void onEnable() {

        instance = this;

        // On tente de pre-configurerer le plugin en verifiant par exemple que certains plugins existent, que les managers
        // peuvent etre initiliser
        // Si y a une erreur, le plugin est desactive
        try {
            this.preSetup();
        } catch (Exception e) {
            this.logger.fatal("Impossible de demarrer le plugin", e);
            setEnabled(false);
            return;
        }

        this.registerCommands();
        this.registerEvents();
    }

    /**
     * Lors de la fermeture du plugin
     */
    @Override
    public void onDisable() {

        this.logger.closeFileWriter();
    }

    /**
     * Permet de vérifier l'existence de certains plugiciels, d'initialiser certains manager
     */
    private void preSetup() throws Exception {

        PixEarthCore core = (PixEarthCore)getServer().getPluginManager().getPlugin("PixEarth-Core");
        if(core == null) {
            throw new Exception("Impossible de charger le plugin PixEarthCore, le plugin n'est peut-être pas installe sur le serveur...");
        }

        if(!core.isEnabled()) {
           throw new Exception("Impossible de charger le plugin " + this.getName() + " car le plugin " + core.getName() + " n'est pas activite");
        }

        this.pluginCore = core;
        this.createDataFolder();
        this.logger = new Logger(getLogger(), this);
        this.loadConfig();
        this.initDatabase();

        // Recuperation du plugin WorldGuard
        this.worldGuardPlugin = WorldGuardPlugin.inst();
        if(this.worldGuardPlugin == null) {
            throw new Exception("Impossible de charger le plugin WorldGuard, le plugin n'est peut-être pas installe sur le serveur...");
        }

        // Pas besoin de verifier si le plugin WorldEdit existe car WorldGuard fonctionne uniquement si WorlEdit est
        // sur le serveur
        try {
            this.worldEdit = this.worldGuardPlugin.getWorldEdit();
        } catch (Exception exception) {
            throw new Exception("Impossible de recuperer le plugin WorldEdit : " + exception.getMessage());
        }

        // Initialise le dossier qui contient les schematics pour l'idle
        this.initSchematicLoader();

        // Initialise le système d'attribution de parcelle pour les parties idle
        IdlePlotManager.preSetup();
    }

    /**
     * Initialise le systèm qui permet de charger des schematics
     *
     * @throws Exception    Exception
     */
    private void initSchematicLoader() throws Exception {

        new IdleV1Schematic().init(getFile(), this);
    }

    /**
     * Initialise la base de données
     */
    private void initDatabase() {

        DatabaseManager databaseManager = this.pluginCore.getDatabaseManager();
        databaseManager.registerAnnotatedClass(IdleGameModel.class);

        databaseManager.setup();
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
     * Charge le fichier de configuration
     */
    private void loadConfig() {

        if(this.configLoader == null) {
            this.configLoader = new ConfigLoader(this);
        }

        this.config = this.configLoader.load();
    }

    /**
     * Initialiser les commandes
     */
    private void registerCommands() {

        getCommand("idle").setExecutor(new IdleCommand());
    }

    /**
     * Initialise les événements
     */
    private void registerEvents() {

        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PlayerJoinNewIdleGameEvent(), this);
        pm.registerEvents(new PlayerJoinIdleGameEvent(), this);
        pm.registerEvents(new PlayerQuitServerEvent(), this);
        pm.registerEvents(new PlayerQuitIdleGameEvent(), this);
    }

    /**
     * @return Retourne le fichier de configuration
     */
    @Override
    public FileConfiguration getConfig() { return this.config; }

    /**
     * @return Retourne le plugin WorldGuard
     */
    public WorldGuardPlugin getWorldGuardPlugin() { return this.worldGuardPlugin; }

    /**
     * @return Retourne le plugin WorldEdit
     */
    public WorldEditPlugin getWorldEdit() { return this.worldEdit; }

    /**
     * @return Retourne le manager
     */
    public static OptionManager getOptions() { return instance.pluginCore.getOptions(); }

    /**
     * @return Retourne l'instance du plugin
     */
    public static PixEarthIdle getInstance() { return instance; }

    /**
     * @return  Retourne le logger
     */
    public static Logger log() { return instance.logger; }

}