package com.pixearth.idle.database.repositories;

import com.pixearth.core.database.connection.DatabaseManager;
import com.pixearth.idle.models.IdleGameModel;
import com.pixearth.idle.utils.QueryJoiner;
import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class IdleGameRepository {

    /**
     * Permet d'ajouter une nouvelle partie
     *
     * @param game  Partie
     * @return  Retourne la partie avec l'identifiant si l'insert a réussi
     */
    public static IdleGameModel insert(IdleGameModel game) {

        Session session = IdleGameRepository.getSession();
        Transaction tx = session.beginTransaction();
        session.save(game);
        tx.commit();

        return game;
    }

    public static IdleGameModel getGame(Player player) {

        // On recupere la partie si elle est jouable
        String queryStr = QueryJoiner.create().add("FROM")
                .add(IdleGameModel.class.getName())
                .add("g WHERE g.player = :playerName AND g.isEnabled=true").join();

        Query query = IdleGameRepository.getSession().createQuery(queryStr);
        query.setParameter("playerName", player);
        query.setMaxResults(1);

        Object result = query.uniqueResult();
        if(result != null) {
            return (IdleGameModel)result;
        }

        return null;
    }

    /**
     * Permet de mettre à jour une partie
     *
     * @param game  Partie qui doit être mise à jour
     * @return  Retourne true
     */
    public static boolean update(IdleGameModel game) {

        Session session = IdleGameRepository.getSession();
        Transaction tx = session.beginTransaction();
        session.update(game);
        tx.commit();

        return false;
    }

    /**
     * @return Retourne la session hibernate qui permet de faire des requêtes
     */
    private static Session getSession() {

        return DatabaseManager.getInstance().getHibernateSession();
    }

}
