package com.pixearth.idle.schematic;

/**
 * Classe qui permet de charger une schematic de l'idle de la version 1
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class IdleV1Schematic extends AbstractSchematicLoader {

    /**
     * @return Retourne le nom du sous dossier qui contient les schematics pour l'idle
     */
    public String getFolderName() {

        return "idle-v1";
    }

}
