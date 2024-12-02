package com.pixearth.core;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * Classe qui permet de charger le fichier de configuration d'un plugin
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class ConfigLoader {

    /**
     * Nom du fichier de configuration
     */
    private static final String FILE_NAME = "config.yml";

    /**
     * Instance du plugin
     */
    private final Plugin plugin;

    /**
     * Constructor
     */
    public ConfigLoader(Plugin plugin) {

        this.plugin = plugin;
    }

    /**
     * Permet de charger la configuration
     *
     * @return Retourne le fichier de configuration
     */
    public FileConfiguration load() {

        try {
            return this.loadConfig();
        } catch (InvalidConfigurationException | IOException exception) {
            throw new IllegalStateException("Impossible de charger le fichier de configuration du plugin : " + this.plugin.getName(), exception);
        }
    }

    /**
     * Permet de charger le fichier de configuration
     *
     * @return Retourne le fichier de configuration au format yaml
     * @throws IOException                      Exception
     * @throws InvalidConfigurationException    Exception
     */
    private FileConfiguration loadConfig() throws IOException, InvalidConfigurationException {

        File folder = this.getDataFolder();
        File file = new File(folder, ConfigLoader.FILE_NAME);

        if(!file.exists()) {

            // Sauvegarde le fichier de config par défaut si le fichier n'existe pas
            this.plugin.saveDefaultConfig();
        }

        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        yamlConfiguration.load(file);
        return yamlConfiguration;
    }

    /**
     * Permet de recuperer le dossier data
     *
     * @return Retourne le dossier data
     */
    private File getDataFolder() {

        File folder = this.plugin.getDataFolder();

        if(!folder.exists()) {
            if(!folder.mkdir()) {
                throw new IllegalStateException("Impossible de creer le dossier " + folder.getAbsolutePath() + " pour le plugin " + this.plugin.getName());
            }
        }

        return folder;
    }

}
