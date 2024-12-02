package com.pixearth.idle.schematic;

import com.pixearth.idle.PixEarthIdle;
import com.pixearth.idle.schematic.exceptions.SchematicLoaderExceptionFileNotExist;
import com.pixearth.idle.schematic.exceptions.SchematicLoaderInitFailed;
import com.pixearth.idle.utils.JarResourcesUtils;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.jar.JarFile;

/**
 * Classe de base pour le chargement d'une schematic
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractSchematicLoader {

    /**
     * Nom du dossier dans le plugin qui contient les schematics
     */
    private static final String FOLDER_NAME = "schematics";

    /**
     * Instance du loader
     */
    private static AbstractSchematicLoader instance;

    /**
     * HashMap qui contient pour chaque nom de fichier schematic le fichier charge
     * Systeme de cache
     *
     * test.schem => [le fichier charge]
     */
    private HashMap<String, Clipboard> schematicCache = new HashMap<>();

    /**
     * Instance du plugin
     */
    private PixEarthIdle plugin;

    /**
     * Instance jar du plugin
     */
    private JarFile jarFile;

    /**
     * Charger une schematic
     *
     * @param fileName  Nom du fichier a charger
     * @return          Retourne le fichier charge
     */
    public static Clipboard loadFile(String fileName) {

        Clipboard clipboard = instance.schematicCache.get(fileName);
        if(clipboard == null) {

            String path = AbstractSchematicLoader.FOLDER_NAME + "/" + instance.getFolderName() + "/" + fileName;

            // @todo changer le systeme de sauvegarde dans le cache
            String pathCopy = instance.plugin.getDataFolder() + "/cache";

            try {
                File file = JarResourcesUtils.copyResourcesToDirectory(instance.jarFile,
                        path, pathCopy);

                if(file != null) {

                    ClipboardFormat format = ClipboardFormats.findByFile(file);
                    try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {

                        clipboard = reader.read();
                        instance.schematicCache.put(fileName, clipboard);
                    } catch (Exception e) {
                        PixEarthIdle.log().warning("Error loader", e);
                    }
                }
            } catch (Exception e) {
                PixEarthIdle.log().warning("Error jar", e);
            }
        }

        return clipboard;
    }

    /**
     * @return Retourne le nom du sous dossier qui contient les schematics
     */
    protected abstract String getFolderName();

    /**
     * Initialise le loader
     *
     * @param file      Fichier
     * @param plugin    Instance du plugin
     *
     * @throws SchematicLoaderInitFailed    Exception durant l'initialisation
     */
    public void init(File file, PixEarthIdle plugin) throws SchematicLoaderInitFailed {

        try {
            this.jarFile = new JarFile(file);
        } catch (Exception e) {
            throw new SchematicLoaderInitFailed("Impossible d'initialiser le loader de schematic :( - " + e.getMessage());
        }

        this.plugin = plugin;
        instance = this;
    }

}