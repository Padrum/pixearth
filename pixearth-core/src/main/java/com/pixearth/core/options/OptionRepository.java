package com.pixearth.core.options;

import com.pixearth.core.database.connection.repositories.AbstractRepository;

/**
 * Gestion de base de données pour les options
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class OptionRepository extends AbstractRepository {

    /**
     * @return  Retourne objet qui est mappé avec le repository
     */
    @Override
    public Class getObjectModel() { return Option.class; }

}
