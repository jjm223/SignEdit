package me.jjm_223.signedit.command;

import me.jjm_223.signedit.SignEdit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSignUndo implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("signedit.undo")) {
            commandSender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "You must be a player in order to use this command.");
            return true;
        }

        if (SignEdit.getSignEditPlayer(((Player) commandSender)).undo()) {
            commandSender.sendMessage(ChatColor.GREEN + "Undone.");
        } else {
            commandSender.sendMessage(ChatColor.RED + "There's nothing to undo!");
        }

        return true;
    }
}
