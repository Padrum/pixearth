package com.pixearth.idle.schematic.exceptions;

/**
 * Exception qui est levée dans le cas où il y ait une erreur lors de l'initialisation d'un loader de schematic
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class SchematicLoaderInitFailed extends Exception {

    public SchematicLoaderInitFailed(String error) {
        super(error);
    }
}
