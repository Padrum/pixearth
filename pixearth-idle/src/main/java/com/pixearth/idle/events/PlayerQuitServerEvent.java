package com.pixearth.idle.events;

import com.pixearth.idle.game.IdleGameManager;
import com.pixearth.idle.game.handlers.QuitIdleGameHandler;
import com.pixearth.idle.models.IdleGameModel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Evénement lorsque le joueur quitte le serveur
 *
 * @author 1.0
 * @version 1.0
 * @since 1.0
 */
public class PlayerQuitServerEvent implements Listener {

    /**
     * @param event Evénement
     */
    @EventHandler
    public void event(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        IdleGameModel game = IdleGameManager.getInstance().getGame(player);

        // Si l'utilisateur quitte le serveur alors qu'il est sur l'idle
        if(event.getPlayer().getWorld() == game.getWorld()) {
            QuitIdleGameHandler.execute(game, player.getLocation());
        }
    }

}
