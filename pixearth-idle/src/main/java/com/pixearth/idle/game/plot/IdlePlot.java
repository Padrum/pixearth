package com.pixearth.idle.game.plot;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.World;

/**
 * Classe qui permet de transmettre des informations supplémentaires sur un plot après la création du plot
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class IdlePlot {

    /**
     * Region WorldGuard
     */
    private ProtectedRegion region;

    /**
     * Monde dans lequel se trouve la region
     */
    private World world;

    /**
     * Constructor
     * @param region    Region
     * @param world     Monde
     */
    public IdlePlot(ProtectedRegion region, World world) {

        this.region = region;
        this.world = world;
    }

    /**
     * @return  Retourne la region
     */
    public ProtectedRegion getRegion() { return region; }

    /**
     * @return  Retourne le monde
     */
    public World getWorld() { return world; }

}
