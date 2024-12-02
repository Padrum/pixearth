package com.pixearth.idle.game.plot;

import com.pixearth.core.options.OptionManager;
import com.pixearth.idle.PixEarthIdle;
import com.pixearth.idle.game.IdleSettings;
import com.pixearth.idle.game.plot.exceptions.IdlePlotManagerPreSetupFailed;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 * Classe qui permet de gerer l'attribution des parcelles pour les parties idles.
 * L'objectif de cette classe est de trouver une parcelle disponible
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class IdlePlotManager {

    /**
     * Nom de l'option qui stocke l'identifiant de la dernière parcelle créée
     */
    private static final String OPT_LAST_CREATED_PLOT = "last_created_plot_id";

    /**
     * Permet de gerer les plots d'un monde (WorldGuard)
     * @see #createIdPlot(Player, RegionManager)
     */
    private RegionManager regionManager;

    /**
     * Monde dans lequel se trouve l'idle
     * Dans la premiere version, il n'y a qu'un seul monde
     * Dans le futur prevoir plusieurs monde dans le cas ou un monde est plein
     */
    private World worldIdle;

    /**
     * Instance du manager
     */
    private static IdlePlotManager instance;

    /**
     * Contient le dernier plot cree, cela permet de gerer la position du plot suivant
     */
    private ProtectedRegion lastCreatedPlot;

    /**
     * Constructor prive (singleton)
     */
    private IdlePlotManager() { }

    /**
     * Permet de pré-initialiser le manager en vérifiant que le manager puis fonctionner correctement
     *
     * @throws IdlePlotManagerPreSetupFailed    Exception si l'initialisation du manager pose problème
     */
    public static void preSetup() throws IdlePlotManagerPreSetupFailed {

        instance = new IdlePlotManager();

        FileConfiguration configuration = PixEarthIdle.getInstance().getConfig();
        String worldName = configuration.getString("idle.world", null);

        if(worldName == null || worldName.isEmpty()) {
            throw new IdlePlotManagerPreSetupFailed("Impossible d'initialiser le manager d'attribution des plots, la clef [idle.world] est invalide ou n'existe pas dans le fichier de configuration du plugin.");
        }

        World world = Bukkit.getServer().getWorld(worldName);
        if(world == null) {
            throw new IdlePlotManagerPreSetupFailed("Impossible d'initialiser le manager d'attribution des plots, le monde " + worldName + " n'existe pas.");
        }

        RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regionManager = regionContainer.get(BukkitAdapter.adapt(world));
        if(regionManager == null) {
            throw new IdlePlotManagerPreSetupFailed("Impossible d'initialiser le manager d'attribution des plots, le regionManager du monde " + worldName + " est égale à null.");
        }

        // On récupère la dernière région afin de pouvoir placer les régions suivantes
        String lastCreatedPlotId = PixEarthIdle.getOptions().getString(IdlePlotManager.OPT_LAST_CREATED_PLOT, null);
        if(lastCreatedPlotId != null) {
            try {
                instance.lastCreatedPlot = regionManager.getRegion(lastCreatedPlotId);
            } catch (Exception e) {
                // Erreur non important ...
            }
        }

        instance.regionManager = regionManager;
        instance.worldIdle = world;
    }

    /**
     * Permet de créer automatiquement une nouvelle parcelle
     *
     * @param player    Joueur à qui va appartenir la parcelle
     * @return          Retourne un object IdlePlot qui contient les informations sur la parcelle
     */
    public IdlePlot createNewPlot(Player player) {

        BlockVector3 minVector = this.getNextMinVector();

        this.lastCreatedPlot = new ProtectedCuboidRegion(
                this.createIdPlot(player, this.regionManager),
                minVector,
                this.getMaxVector(minVector));

        // Sauvegarde l'identifiant du dernier plot
        OptionManager.getInstance().save(IdlePlotManager.OPT_LAST_CREATED_PLOT, this.lastCreatedPlot.getId());
        this.regionManager.addRegion(this.lastCreatedPlot);

        return new IdlePlot(this.lastCreatedPlot, this.worldIdle);
    }

    /**
     * @return Retourne l'instance du manager
     */
    public static IdlePlotManager getInstance() { return instance; }

    /**
     * Permet de créer le vecteur max par rapport à un vecteur minimum
     *
     * @param minVector     Vector minimum
     * @return              Retourne le vector maximum
     */
    private BlockVector3 getMaxVector(BlockVector3 minVector) {

        return Vector3.at(minVector.getX() + IdleSettings.PLOT_SIZE,
                255,
                minVector.getZ() + IdleSettings.PLOT_SIZE).toBlockPoint();
    }

    /**
     * @return Retourne la position du vecteur min pour la création du plot suivant par rapport au dernier plot créé
     */
    public BlockVector3 getNextMinVector() {

        if(this.lastCreatedPlot == null) {
            return Vector3.at(0, 4, 0).toBlockPoint();
        }

        BlockVector3 lastVectorMin = this.lastCreatedPlot.getMinimumPoint();
        BlockVector3 lastVectorMax = this.lastCreatedPlot.getMaximumPoint();

        return Vector3.at(lastVectorMax.getX() + IdleSettings.PLOT_SEPARATOR,
                0,
                lastVectorMin.getZ()).toBlockPoint();
    }

    /**
     * Permet de generer l'identifiant d'un plot
     * @param player            Joueur
     * @param regionManager     Region manager
     * @return                  Retourne l'identifiant du plot genere
     */
    private String createIdPlot(Player player, RegionManager regionManager) {

        // L'objectif est d'avoir un identifiant unique pour cela on recherche si un plot existe deja avec l'identifiant
        String baseId = "idle_plot_" + player.getName() + "_";
        int id = 0;

        // idle_plot_JOUEUR_0
        // idle_plot_JOUEUR_1
        // idle_plot_JOUEUR_2
        // ...
        while(regionManager.getRegion(baseId + id) != null) {
            id++;
        }

        return baseId + id;
    }

}
