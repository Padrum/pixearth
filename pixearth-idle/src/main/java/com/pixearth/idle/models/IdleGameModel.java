package com.pixearth.idle.models;

import com.pixearth.idle.models.types.LocationJsonType;
import com.pixearth.idle.models.types.PlayerBukkitType;
import com.pixearth.idle.models.types.WorldType;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sun.istack.Nullable;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.util.Date;

/**
 * Classe qui représente une partie d'idle
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name="idle_game")
public class IdleGameModel {

    /**
     * Identifiant de la partie (auto_increment)
     */
    @Id
    @Column(name = "id_game", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Date de création de la partie
     */
    @Column(name = "created_at", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    /**
     * Date de la dernière connexion dans la partie
     */
    @Column(name = "last_join", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastJoin;

    /**
     * Identifiant de la region
     */
    @Column(name = "region_id")
    private String regionId;

    /**
     * Permet de savoir si la partie est jouable ou non
     * Si true, la partie est jouable
     */
    @Column(name = "is_enabled")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isEnabled;

    /**
     * Dernière position connue de l'utilisateur dans la partie
     */
    @Column(name = "last_position")
    @Type(type = LocationJsonType.CLASS_NAME)
    private Location lastPosition;

    /**
     * Joueur à qui appartient la partie
     */
    @Column(name = "player_name")
    @Type(type = PlayerBukkitType.CLASS_NAME)
    private Player player;

    /**
     * Monde dans lequel se trouve la partie
     */
    @Column(name = "world_name")
    @Type(type = WorldType.CLASS_NAME)
    private World world;

    /**
     * Region de la partie
     */
    @Transient
    private ProtectedRegion region;

    /**
     * Permet de savoir si la partie est active
     * En gros, si le joueur joue à l'idle
     */
    @Transient
    private boolean isActive;

    /**
     * Constructor
     */
    public IdleGameModel() { }

    /**
     * @return Retourne l'identifiant de la partie
     */
    public long getId() { return id; }

    /**
     * Set l'identifiant de la partie
     *
     * @param id    Identifiant
     * @return  Retourne l'instance de l'objet
     */
    public IdleGameModel setId(long id) {

        this.id = id;
        return this;
    }

    /**
     * @return Retourne la date de création de la partie
     */
    public Date getCreatedAt() { return createdAt; }

    /**
     * Set la date de creation de la partie
     *
     * @param createdAt Date de création
     * @return  Retourne l'instance de l'objet
     */
    public IdleGameModel setCreatedAt(Date createdAt) {

        this.createdAt = createdAt;
        return this;
    }

    /**
     * @return  Retourne l'identifiant de la région WorldGuard
     */
    public String getRegionId() { return regionId; }

    /**
     * Set l'identifiant de la région WorldGuard
     *
     * @param regionId  Identifiant de la région
     * @return  Retourne l'instance de l'objet
     */
    public IdleGameModel setRegionId(String regionId) {

        this.regionId = regionId;
        return this;
    }

    /**
     * @return  Retourne true si la partie est jouable
     */
    public boolean isEnabled() { return isEnabled; }

    /**
     * Set si la partie est jouable
     *
     * @param enabled   Si la partie est jouable
     * @return  Retourne l'instance de l'objet
     */
    public IdleGameModel setEnabled(boolean enabled) {

        this.isEnabled = enabled;
        return this;
    }

    /**
     * @return  Retourne l'identifant du monde
     */
    public World getWorld() { return world; }

    /**
     * Set l'identifant du monde
     *
     * @param world Identifiant du monde
     * @return  Retourne l'instance de l'objet
     */
    public IdleGameModel setWorld(World world) {

        this.world = world;
        return this;
    }

    /**
     * @return  Retourne la region
     */
    public ProtectedRegion getRegion() { return this.region; }

    /**
     * Set la region
     *
     * @param region    Region
     * @return  Retourne l'instance de l'objet
     */
    public IdleGameModel setRegion(ProtectedRegion region) {

        this.region = region;
        this.regionId = region.getId();
        return this;
    }

    /**
     * @return  Retourne le joueur a qui appartient la partie
     */
    public Player getPlayer() { return this.player; }

    /**
     * Set le joueur de la partie
     *
     * @param player    Joueur
     * @return  Retourne l'instance de l'objet
     */
    public IdleGameModel setPlayer(Player player) {

        this.player =  player;
        return this;
    }

    /**
     * @return  Retourne la dernière position du joueur dans la partie
     */
    @Nullable
    public Location getLastPosition() { return this.lastPosition; }

    /**
     * Set la dernière position du joueur dans la partie
     *
     * @param location  Position
     * @return  Retourne l'instance de l'objet
     */
    public IdleGameModel setLastPosition(Location location) {

        this.lastPosition = location;
        return this;
    }

    /**
     * @return  Retourne true si la partie est en train d'être joué par le joueur
     */
    public boolean isActive() { return this.isActive; }

    /**
     * Permet de set si le joueur joue à l'idle ou non
     *
     * @param isActive  IsActive
     * @return  Retourne l'instance de l'objet
     */
    public IdleGameModel setIsActive(boolean isActive) {

        this.isActive = isActive;
        return this;
    }

    /**
     * @return  Retourne la date de la dernier connexion dans la partie
     */
    public Date getLastJoin() { return this.lastJoin; }

    /**
     * Set la date de la dernière connexion
     *
     * @param date  Date
     * @return  Retourne l'instance de l'objet
     */
    public IdleGameModel setLastJoin(Date date) {

        this.lastJoin = date;
        return this;
    }

    /**
     * @return  Retourne les informations de la partie pour l'affichage
     */
    @Override
    public String toString() {

        return "Game: [id : " + this.id + " player : " + this.player.getName() + ", isActive: " + this.isActive + "]";
    }

    /**
     * Initialise des valeurs lors de l'insertion de l'objet en base de données
     */
    @PrePersist
    public void prePersist() {

        this.createdAt = new Date();
        this.lastJoin = new Date();
        this.isEnabled = true;
    }

}
