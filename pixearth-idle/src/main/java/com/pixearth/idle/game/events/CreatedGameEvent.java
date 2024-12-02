package com.pixearth.idle.game.events;

import com.pixearth.idle.models.IdleGameModel;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Evénement qui est appelé lors de la création d'une nouvelle partie d'un joueur
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class CreatedGameEvent extends Event {

    /**
     * Handlers
     */
    private static final HandlerList handlers = new HandlerList();

    /**
     * Joueur à qui appartient la partie
     */
    private Player player;

    /**
     * Partie qui vient d'être créée
     */
    private IdleGameModel game;

    /**
     * Constructor
     *
     * @param player    Joueur à qui appartient la partie
     * @param game      Partie
     */
    public CreatedGameEvent(Player player, IdleGameModel game) {

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
    public Player getPlayer() { return player; }

    /**
     * @return  Retourne le joueur
     */
    public IdleGameModel getGame() { return game; }

}
