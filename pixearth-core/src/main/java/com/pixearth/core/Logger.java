package com.pixearth.core;

import com.google.common.base.Throwables;
import com.pixearth.core.utils.ExceptionUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

/**
 * Classe qui permet de gérer les logs
 * Un nouveau fichier de log est créé chaque jour.
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class Logger {

    /**
     * Fichier qui contient les logs
     * @see #Logger(java.util.logging.Logger, Plugin)
     */
    private File logFile;

    /**
     * Logger java
     * @see #Logger(java.util.logging.Logger, Plugin)
     * @see #info(String)
     * @see #info(String)
     * @see #warning(String)
     * @see #warning(String, Throwable)
     */
    private java.util.logging.Logger logger;

    /**
     * Système qui permet d'écrire dans le fichier de log
     * @see #closeFileWriter()
     * @see #writeLog(String)
     */
    private OutputStreamWriter fileWriter;

    /**
     * Contient un saut de ligne
     * @see #writeLog(String)
     */
    private static final String NEW_LINE = System.getProperty("line.separator");

    /**
     * Dossier qui contient les logs
     * @see #Logger(java.util.logging.Logger, Plugin)
     */
    private static final String LOG_DIR = "logs";

    /**
     * Systeme qui permet de formatter la date pour le log
     * @see #writeLog(String)
     */
    private static final DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder()
            .appendLiteral("[")
            .appendPattern("MM-dd HH:mm:ss")
            .appendLiteral("]")
            .toFormatter();

    /**
     * Constructor
     *
     * @param logger    Logger
     * @param plugin    Plugin du logger
     */
    public Logger(java.util.logging.Logger logger, Plugin plugin) {

        this.logger = logger;

        File dataFolder = plugin.getDataFolder();
        if(!dataFolder.exists()) {
            // Erreur le dossier data n'existe pas
        }

        // Dossier qui contient les logs
        File dirLog = new File(dataFolder.getAbsolutePath(), Logger.LOG_DIR);
        if(!dirLog.exists()) {
            if(!dirLog.mkdir()) {
                // Impossible de creer le dossier LOG_DIR
            }
        }

        // Fichier qui contient les logs selon la date du jour
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = formatter.format(new Date(System.currentTimeMillis())) + ".log";

        this.logFile = new File(dirLog.getAbsolutePath(), fileName);

        this.initializeFileWriter();
    }

    /**
     * Log un message du type INFO
     *
     * @param message   Message a logger
     */
    public void info(String message) {

        this.logger.info(message);
        this.writeLog("[INFO] " + message);
    }

    /**
     * Log un message du type WARNING
     *
     * @param message   Message a logger
     */
    public void warning(String message) {

        this.logger.warning(message);
        this.writeLog("[WARNING] " + message);
    }

    /**
     * Log un message du type WARNING avec une exception
     *
     * @param message   Message a logger
     * @param throwable Exception
     */
    public void warning(String message, Throwable throwable) {

        this.logger.warning(message + " - " + ExceptionUtils.formatException(throwable));
        this.writeLog("[WARNING]" + message + "|" + Throwables.getStackTraceAsString(throwable));
    }

    /**
     * Log un message de type FATAL
     *
     * @param message   Message a logger
     */
    public void fatal(String message) {

        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + message);
        this.writeLog("[FATAL] " + message);
    }

    /**
     * Log un message du type FATAL avec une exception
     *
     * @param message   Message a logger
     * @param throwable Exception
     */
    public void fatal(String message, Throwable throwable) {

        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + message + " - " + ExceptionUtils.formatException(throwable));
        this.writeLog("[FATAL]" + message + "|" + Throwables.getStackTraceAsString(throwable));
    }

    /**
     * Permet de fermer le système d'écriture dans le logger
     */
    public void closeFileWriter() {

        if(this.fileWriter != null) {
            try {
                this.fileWriter.close();
            } catch (IOException e) {
                this.closeWriter(this.fileWriter);
                this.fileWriter = null;
            }
        }
    }

    /**
     * @return Retourne le chemin du fichier de log
     */
    public String getLogFile() {

        return this.logFile.getAbsolutePath();
    }

    /**
     * Initialise le système qui permet d'écrire dans le fichier de log
     */
    private void initializeFileWriter() {

        if(fileWriter == null) {

            FileOutputStream stream = null;
            try {
                stream = new FileOutputStream(this.logFile, true);
                fileWriter = new OutputStreamWriter(stream, StandardCharsets.UTF_8);
            } catch (Exception e) {

            }
        }
    }

    /**
     * Permet d'écrire un log dans le fichier de log
     *
     * @param message   Message à ajouter au fichier de log
     */
    private void writeLog(String message) {

        if(this.fileWriter != null) {
            try {
                this.fileWriter.write(DATE_FORMAT.format(LocalDateTime.now()));
                this.fileWriter.write(": ");
                this.fileWriter.write(message);
                this.fileWriter.write(Logger.NEW_LINE);
                this.fileWriter.flush();
            } catch (IOException e) {

            }
        }
    }

    /**
     * Permet de fermer une ressource
     *
     * @param closeable Ressource
     */
    private void closeWriter(Closeable closeable) {

        if(closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {

            }
        }
    }

}