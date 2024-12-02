package com.pixearth.idle.game.plot.exceptions;

/**
 * Exception dans le cas ou il y est une erreur lors de l'initialisation du manager IdlePlotManager
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class IdlePlotManagerPreSetupFailed extends Exception {

    public IdlePlotManagerPreSetupFailed(String error) {

        super(error);
    }

}
