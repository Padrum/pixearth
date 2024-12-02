package com.pixearth.idle.game;

import com.pixearth.idle.PixEarthIdle;
import com.pixearth.idle.database.repositories.IdleGameRepository;
import com.pixearth.idle.game.plot.IdlePlot;
import com.pixearth.idle.game.plot.IdlePlotManager;
import com.pixearth.idle.models.IdleGameModel;
import com.pixearth.idle.schematic.IdleV1Schematic;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.entity.Player;

/**
 * Classe qui permet de créer une nouvelle partie
 *
 * @author PixEarth
 * @version 1.0
 * @since 1.0
 */
public class IdleGameCreator {

    /**
     * Permet de créer une nouvelle partie pour un joueur
     *
     * @param player    Joueur à qui doit appartenir la partie
     * @return  Retourne la partie si elle a été correctement créé ou null s'il y a une erreur
     */
    public static IdleGameModel createNewGame(Player player) {

        // Récupération de la région de la partie
        IdlePlot regionPlot = IdlePlotManager.getInstance().createNewPlot(player);

        IdleGameModel game = (new IdleGameModel())
                .setWorld(regionPlot.getWorld())
                .setRegion(regionPlot.getRegion())
                .setPlayer(player)
                .setIsActive(true);

        // Sauvegarde la partie en bdd
        IdleGameRepository.insert(game);

        // La partie a été sauvegardée
        if(game.getId() > 0) {

            try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(BukkitAdapter.adapt(regionPlot.getWorld()), -1)) {

                BlockVector3 minV = regionPlot.getRegion().getMinimumPoint();

                // Paste le premier niveau de la partie
                Operation operation = new ClipboardHolder(IdleV1Schematic.loadFile("idle_level_1.schem"))
                        .createPaste(editSession)
                        .to(minV)
                        .ignoreAirBlocks(true)
                        .build();

                Operations.complete(operation);

                return game;
            } catch (Exception e) {
                PixEarthIdle.log().warning("Error", e);
            }
        }

        return null;
    }

}
