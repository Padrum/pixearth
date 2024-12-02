package com.pixearth.idle.events.idle;

import com.pixearth.idle.game.events.CreatedGameEvent;
import com.pixearth.idle.models.IdleGameModel;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Événement lorsqu'il y a une nouvelle partie de créer
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class PlayerJoinNewIdleGameEvent implements Listener {

    /**
     * @param event Événement
     */
    @EventHandler
    public void event(CreatedGameEvent event) {

        event.getPlayer().sendMessage("Téléportation dans votre partie");

        IdleGameModel game = event.getGame();

        // Simulation de la position du spawn lors du premier spawn
        // @todo à Changer !
        final double spawnX = 50;
        final double spawnY = 40;
        final double spawnZ = 50;

        BlockVector3 minV = game.getRegion().getMinimumPoint();

        Location goTo = new Location(game.getWorld(),
                minV.getX() + spawnX,
                minV.getY() + spawnY,
                spawnZ);

        event.getPlayer().teleport(goTo);
    }

}
