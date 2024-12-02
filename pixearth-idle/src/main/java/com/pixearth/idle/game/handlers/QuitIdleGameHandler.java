package com.pixearth.idle.game.handlers;

import com.pixearth.idle.PixEarthIdle;
import com.pixearth.idle.game.events.QuitIdleGameEvent;
import com.pixearth.idle.models.IdleGameModel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Classe qui contient la logique d'exécution lorsqu'un joueur quitte sa partie d'idle
 *
 * @author 1.0
 * @version 1.0
 * @since 1.0
 */
public class QuitIdleGameHandler {

    /**
     * Permet de lancer le handler
     *
     * @param player    Joueur qui vient de quitter la partie d'idle
     */
    public static void execute(IdleGameModel game, Location lastLocation) {

        // Le joueur a bien une partie et il est en train d'y jouer
        if(game != null && game.isActive()) {

            PixEarthIdle.log().info("Game found in QuitIdleGameHandler");

            Bukkit.getPluginManager().callEvent(new QuitIdleGameEvent(game, lastLocation));
        }
    }

}
