package com.pixearth.core.database.connection.repositories;

import com.pixearth.core.Logger;
import com.pixearth.core.PixEarthCore;
import com.pixearth.core.database.connection.DatabaseManager;
import com.pixearth.core.database.connection.repositories.exceptions.InvalidRepositoryClass;
import com.pixearth.core.database.connection.repositories.exceptions.ObjectModelNotDefined;
import com.pixearth.core.utils.QueryJoiner;
import com.sun.istack.Nullable;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite pour les repositories
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractRepository {

    /**
     * Permet de vérifier que l'objet passé en paramètre est valide
     *
     * @param object    Objet à vérifier
     * @return  Retourne true si l'objet est valide et false dans le cas contraire
     */
    private boolean checkObject(Object object) {

        Logger log = PixEarthCore.log();
        if(this.getObjectModel() == null) {

            log.fatal("", new ObjectModelNotDefined(this.getClass()));
            return false;
        }

        if(!(object.getClass() == this.getObjectModel())) {
            log.fatal("", new InvalidRepositoryClass(this.getObjectModel(), object.getClass()));
            return false;
        }

        return true;
    }

    /**
     * Retourne la liste de tous les objets
     *
     * @param <T>   Type d'objet retourné
     * @return      Retourne la liste des tous les objets T
     */
    public <T> List<T> findAll() {

        Session session = this.getSession();
        String queryStr = QueryJoiner.create().add("FROM").add(this.getObjectModel().getName()).join();
        Query query = session.createQuery(queryStr);

        List<T> data = new ArrayList<>();

        for(Object obj : ((org.hibernate.query.Query) query).list()) {
            data.add(((T) obj));
        }

        return data;
    }

    /**
     * Permet d'insérer un objet
     *
     * @param object    Object à insérer
     * @param <T>       Type de l'objet
     * @return  Retourne l'objet inséré ou null si le type de l'objet est invalid
     */
    @Nullable
    public <T> T insert(T object) {

        if(!this.checkObject(object)) {
            return null;
        }

        Session session = this.getSession();
        Transaction tx = session.beginTransaction();
        session.save(object);
        tx.commit();

        return object;
    }

    /**
     * Permet de mettre à jour un objet
     *
     * @param object    Objet à mettre à jour
     * @param <T>       Type de l'objet
     * @return  Retourne l'objet inséré ou null si le type de l'objet est invalid
     */
    @Nullable
    public <T> T update(T object) {

        if(!this.checkObject(object)) {
            return null;
        }

        Session session = this.getSession();
        Transaction tx = session.beginTransaction();
        session.update(object);
        tx.commit();

        return object;
    }

    /**
     * Permet de supprimer un objet
     *
     * @param object    Objet à supprimer
     */
    public void delete(Object object) {

        if(!this.checkObject(object)) {
            return;
        }

        Session session = this.getSession();
        Transaction tx = session.beginTransaction();
        session.delete(object);
        tx.commit();

    }

    /**
     * @return Retourne la session hibernate qui permet de faire des requêtes
     */
    protected Session getSession() {

        return DatabaseManager.getInstance().getHibernateSession();
    }

    /**
     * @return  Class qui est mappé avec le repository
     */
    public abstract Class getObjectModel();

}
