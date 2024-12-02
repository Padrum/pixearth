package com.pixearth.idle.game;

import com.pixearth.idle.models.IdleGameModel;
import com.sun.istack.Nullable;
import org.bukkit.entity.Player;
import java.util.HashMap;

/**
 * Manager qui contient la partie de chaque joueur
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class IdleGameManager {

    /**
     * Contient pour chaque joueur la partie qui est lui est associée
     */
    private HashMap<String, IdleGameModel> gameByPlayer = new HashMap<>();

    /**
     * Instance du manager
     */
    private static IdleGameManager instance;

    /**
     * Instance
     */
    private IdleGameManager() {

        instance = this;
    }

    /**
     * @return  Retourne l'instance du manager
     */
    public static IdleGameManager getInstance() {

        if(instance == null) {
            instance = new IdleGameManager();
        }

        return instance;
    }

    /**
     * Permet de savoir si un joueur à une partie sauvegardé
     *
     * @param player    Joueur
     * @return  Retourne true si le manager contient la partie du joueur sinon false
     */
    public boolean playerHaveGame(Player player) {

        return this.gameByPlayer.containsKey(player.getName());
    }

    /**
     * Permet de récuperer la partie d'un joueur
     *
     * @param player    Joueur
     * @return  Retouren la partie du jour si elle existe dans le manager sinon null
     */
    @Nullable
    public IdleGameModel getGame(Player player) {

        if(this.playerHaveGame(player)) {
            return this.gameByPlayer.get(player.getName());
        }

        return null;
    }

    /**
     * Permet d'ajouter une partie au manager
     *
     * @param game  Partie à ajouter
     * @return  Retourne true si la partie a été ajouté au manager ou false dans le cas contraire
     */
    public boolean addGame(IdleGameModel game) {

        Player player = game.getPlayer();
        if(!this.playerHaveGame(player)) {

            this.gameByPlayer.put(player.getName(), game);
            return true;
        }

        return false;
    }

    /**
     * Permet de mettre à jour une partie dans le manager
     *
     * @param game  Partie à mettre à jour
     * @return  Retourne true si l'update est ok
     */
    public boolean updateGame(IdleGameModel game) {

        if(this.playerHaveGame(game.getPlayer())) {
            this.gameByPlayer.replace(game.getPlayer().getName(), game);
            return true;
        }

        return false;
    }

}
