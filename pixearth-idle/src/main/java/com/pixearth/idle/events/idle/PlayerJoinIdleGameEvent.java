package com.pixearth.idle.events.idle;

import com.pixearth.idle.database.repositories.IdleGameRepository;
import com.pixearth.idle.game.IdleGameManager;
import com.pixearth.idle.game.events.PlayerJoinGame;
import com.pixearth.idle.models.IdleGameModel;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Date;

/**
 * Événement lorsqu'un joueur rejoint sa partie d'idle
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class PlayerJoinIdleGameEvent implements Listener {

    /**
     * @param event Événement
     */
    @EventHandler
    public void event(PlayerJoinGame event) {

        IdleGameModel game = event.getGame();
        Player player = event.getPlayer();

        if(!game.isActive()) {  // Le joueur ne joue pas déjà à la partie

            Location lastLocation = game.getLastPosition();

            if (lastLocation != null) {

                // Sauvegarde la date de la derniere connexion
                game.setLastJoin(new Date());
                IdleGameRepository.update(game);
                IdleGameManager.getInstance().updateGame(game);

                // Le joueur joue a l'idle
                IdleGameManager.getInstance().updateGame(game.setIsActive(true));

                player.sendMessage("Teleportation dans votre partie...");
                player.teleport(lastLocation);
            } else {
                // @todo Gerer l'erreur si la postion est inconnue
            }
        } else {
            player.sendMessage("Hum O_o, vous etes deja dans votre partie.");
        }
    }

}
