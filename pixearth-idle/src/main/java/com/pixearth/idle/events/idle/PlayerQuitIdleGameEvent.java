package com.pixearth.idle.events.idle;

import com.pixearth.idle.PixEarthIdle;
import com.pixearth.idle.database.repositories.IdleGameRepository;
import com.pixearth.idle.game.IdleGameManager;
import com.pixearth.idle.game.events.QuitIdleGameEvent;
import com.pixearth.idle.models.IdleGameModel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Evénement lorsque le joueur quitte sa partie d'idle
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class PlayerQuitIdleGameEvent implements Listener {

    /**
     * @param event Evénement
     */
    @EventHandler
    public void event(QuitIdleGameEvent event) {

        IdleGameModel game = event.getGame();
        game.setLastPosition(event.getLastLocation())
            .setIsActive(false); // Le joueur n'est plus dans la partie

        IdleGameRepository.update(game);

        if(!IdleGameManager.getInstance().updateGame(game)) {
            PixEarthIdle.log().warning("Erreur de sauvegarde dans PlayerQuitIdleGameEvent.event");
        }
    }

}
