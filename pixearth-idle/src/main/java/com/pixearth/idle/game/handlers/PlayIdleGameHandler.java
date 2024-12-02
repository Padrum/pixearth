package com.pixearth.idle.game.handlers;

import com.pixearth.idle.database.repositories.IdleGameRepository;
import com.pixearth.idle.game.IdleGameCreator;
import com.pixearth.idle.game.IdleGameManager;
import com.pixearth.idle.game.events.CreatedGameEvent;
import com.pixearth.idle.game.events.PlayerJoinGame;
import com.pixearth.idle.models.IdleGameModel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Classe qui contient la logique d'exécution pour lancer une partie idle
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class PlayIdleGameHandler {

    /**
     * Permet de lancer la partie idle d'un joueur
     *
     * @param player    Joueur
     */
    public static void execute(Player player) {

        IdleGameModel game = IdleGameManager.getInstance().getGame(player);

        // Le joueur n'a pas de partie stocké dans le manager
        // Selection de la partie du joueur
        if(game == null) {

            // Recherche la partie en base de données
            // Si la partie est null alors le joueur n'a pas encore de partie ou alors la partie n'est plus jouable
            // Donc création d'une partie
            game = IdleGameRepository.getGame(player);
            if(game == null) {

                // Sauvegarde la partie dans le manager
                game = IdleGameCreator.createNewGame(player);

                if(game != null) {

                    // Sauvegarde la partie dans le manager
                    IdleGameManager.getInstance().addGame(game);

                    // Appel l'événement lors de la création de la game
                    Bukkit.getPluginManager().callEvent(new CreatedGameEvent(player, game));
                    return;
                }

                // Erreur lors de la creation de la partie
                // @todo Gerer l'erreur ...
                return;
            }

            // Sauvegarde la partie dans le manager
            IdleGameManager.getInstance().addGame(game);
        }

        Bukkit.getPluginManager().callEvent(new PlayerJoinGame(player, game));
    }

}
