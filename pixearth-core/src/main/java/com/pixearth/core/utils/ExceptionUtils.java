package com.pixearth.core.utils;

/**
 * Classe utilitaire pour la generation des exceptions
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class ExceptionUtils {

    /**
     * Format une exception pour etre afficher dans une console
     *
     * @param throwable Exception
     * @return Retourne une chaine sous le format "[IdlePlugin]: Une erreur"
     */
    public static String formatException(Throwable throwable) {

        return (new StringBuilder())
                .append("[")
                .append(throwable.getClass().getCanonicalName())
                .append("]: ")
                .append(throwable.getMessage()).toString();
    }

}
