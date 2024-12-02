package com.pixearth.idle.utils;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import org.bukkit.Location;
import org.bukkit.World;
import org.json.simple.JSONObject;

/**
 * Classe utilitaire pour les localisations
 */
public class LocationUtils {

    /**
     * Retourne la location centrale entre deux Vector3
     *
     * @param world         Monde
     * @param vectorMin     Vector minimal
     * @param vectorMax     Vector maximal
     * @return              Retourne la location centrale entre les deux vectors
     */
    public static Location getCenter(World world, Vector3 vectorMin, Vector3 vectorMax) {

        return new Location(world,
                (vectorMin.getX() + vectorMax.getX()) / 2,
                (vectorMin.getY() + vectorMax.getY()) / 2,
                (vectorMin.getZ() + vectorMax.getZ()) / 2
        );
    }

    /**
     * Retourne la location centrale entre deux BlocVector3
     * @param world         Monde
     * @param vectorMin     Vector minimal
     * @param vectorMax     Vector maximul
     * @return              Retourne la location centre entre les deux vectors
     */
    public static Location getCenter(World world, BlockVector3 vectorMin, BlockVector3 vectorMax) {

        return LocationUtils.getCenter(world, vectorMin.toVector3(), vectorMax.toVector3());
    }

}
