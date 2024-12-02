package com.pixearth.idle.game.events;

import com.pixearth.idle.models.IdleGameModel;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Evénement qui est appelé lorsuqu'un joueur quitte une partie d'idle
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class QuitIdleGameEvent extends Event {

    /**
     * Handlers
     * @see #getHandlers()
     * @see #getHandlerList()
     */
    private static final HandlerList handlers = new HandlerList();

    /**
     * Partie qui vient d'être quittée
     */
    private IdleGameModel game;

    /**
     * Dernière position du joueur dans la partie
     */
    private Location lastLocation;

    /**
     * Constructor
     *
     * @param game          Partie qui vient d'être quittée
     * @param lastLocation  Dernière position du joueur dans la partie
     */
    public QuitIdleGameEvent(IdleGameModel game, Location lastLocation) {

        this.game = game;
        this.lastLocation = lastLocation;
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
     * @return  Retourne la partie qui vient d'être quittée
     */
    public IdleGameModel getGame() { return this.game; }

    /**
     * @return  Retourne la dernière position du joueur dans la partie
     */
    public Location getLastLocation() { return this.lastLocation; }

}
