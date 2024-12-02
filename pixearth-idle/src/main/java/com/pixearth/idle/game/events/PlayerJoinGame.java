package com.pixearth.idle.game.events;

import com.pixearth.idle.models.IdleGameModel;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Événement qui est appelé lorsqu'un joueur souhaite rejoindre une partie
 * Dans le cas c'est une nouvelle partie, l'événement CreatedGameEvent est appelé
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class PlayerJoinGame extends Event {

    /**
     * Handlers
     */
    private static final HandlerList handlers = new HandlerList();

    /**
     * Joueur à qui appartient la partie
     */
    private Player player;

    /**
     * Partie
     */
    private IdleGameModel game;

    /**
     * Constructor
     *
     * @param player    Joueur à qui appartient la partie
     * @param game      Partie
     */
    public PlayerJoinGame(Player player, IdleGameModel game) {

        this.player = player;
        this.game = game;
    }

    /**
     * @return Retourne l'instance du handler
     */
    @Override
    public HandlerList getHandlers() { return handlers; }

    /**
     * @return Retourne l'instance du handler
     */
    public static HandlerList getHandlerList() { return handlers; }

    /**
     * @return  Retourne le joueur
     */
    public Player getPlayer() { return this.player; }

    /**
     * @return  Retourne la partie
     */
    public IdleGameModel getGame() { return this.game; }

}
