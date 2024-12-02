package com.pixearth.idle.schematic.exceptions;

/**
 * Exception dans le cas ou le fichier qui doit etre charge n'existe pas
 */
public class SchematicLoaderExceptionFileNotExist extends Exception {

    public SchematicLoaderExceptionFileNotExist(String filePath) {

        super("Le fichier " + filePath + " n'existe pas, impossible de le charger.");
    }
}
