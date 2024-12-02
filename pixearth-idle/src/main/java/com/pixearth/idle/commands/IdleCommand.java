package com.pixearth.idle.commands;

import com.pixearth.idle.PixEarthIdle;
import com.pixearth.idle.game.handlers.PlayIdleGameHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IdleCommand implements CommandExecutor {

    /**
     * @param commandSender
     * @param command
     * @param s
     * @param args
     * @return
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(!(commandSender instanceof Player)) {
            PixEarthIdle.log().info("Impossible d'excuter la commande depuis la console...");
            return false;
        }

        // Lance la partie d'idle du joueur
        PlayIdleGameHandler.execute((Player)commandSender);
        return true;
    }

}
