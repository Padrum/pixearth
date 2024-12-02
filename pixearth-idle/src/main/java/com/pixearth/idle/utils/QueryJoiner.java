package com.pixearth.idle.utils;

import java.util.StringJoiner;

/**
 * Classe qui permet de créer une requête à partir de plusieurs bouts
 * Cette classe est un "alias" de la classe StringJoiner
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class QueryJoiner {

    /**
     * Joiner
     * @see #add(String)
     * @see #join()
     * @see #QueryJoiner()
     */
    private StringJoiner joiner;

    /**
     * Constructor
     */
    private QueryJoiner() {

        this.joiner = new StringJoiner(" ");
    }

    /**
     * @return Retourne une nouvelle instance QueryJoiner
     */
    public static QueryJoiner create() {

        return new QueryJoiner();
    }

    /**
     * Ajoute une nouvelle section à la requête
     *
     * @param txt   Text
     * @return  Retourne l'instance du QueryJoiner
     */
    public QueryJoiner add(String txt) {

        this.joiner.add(txt);
        return this;
    }

    /**
     * Permet de concaténer la requête
     *
     * @return  Retourne la requête entière après concaténation
     */
    public String join() {

        return this.joiner.toString();
    }

}
