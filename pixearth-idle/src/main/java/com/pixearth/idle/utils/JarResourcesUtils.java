package com.pixearth.idle.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Utilitaire qui permet de manipuler les fichiers qui se trouvent dans le .jar
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class JarResourcesUtils {

    /**
     * Copie une ressource qui se trouve dans le jar, dans un dossier local
     *
     * @param fromJar   JarFile
     * @param jarDir    Chemin vers le fichier à copier
     * @param destDir   Chemin de destination du fichier
     * @return  Retourne une instance du fichier copié
     *
     * @throws IOException  Exception
     */
    public static File copyResourcesToDirectory(JarFile fromJar, String jarDir, String destDir) throws IOException {

        for (Enumeration<JarEntry> entries = fromJar.entries(); entries.hasMoreElements();) {
            JarEntry entry = entries.nextElement();

            if (entry.getName().equals(jarDir) && !entry.isDirectory()) {

                Path p = Paths.get(jarDir);
                String fileName = p.getFileName().toString();

                File dest = new File(destDir + "/" + fileName);
                File parent = dest.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }

                FileOutputStream out = new FileOutputStream(dest);
                InputStream in = fromJar.getInputStream(entry);

                try {
                    byte[] buffer = new byte[8 * 1024];

                    int s = 0;
                    while ((s = in.read(buffer)) > 0) {
                        out.write(buffer, 0, s);
                    }
                } catch (IOException e) {
                    throw new IOException("Could not copy asset from jar file", e);
                } finally {
                    try {
                        in.close();

                        return new File(destDir + "\\" + fileName);
                    } catch (IOException ignored) {}
                    try {
                        out.close();
                    } catch (IOException ignored) {}
                }
            }
        }

        return null;
    }

}

