package me.jjm_223.signedit.command;

import me.jjm_223.signedit.SignEdit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Set;

public class CommandSignEdit implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("signedit.edit")) {
            commandSender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "You must be a player in order to use this command.");
            return true;
        }

        if (strings.length < 1) {
            commandSender.sendMessage(ChatColor.RED + "You must include the line you want to change and what you want to change it to.");
            return false;
        }

        Player player = ((Player) commandSender);
        Block block = player.getTargetBlock(((Set<Material>) null), 20);

        if (block == null || (block.getType() != Material.SIGN_POST && block.getType() != Material.WALL_SIGN)) {
            commandSender.sendMessage(ChatColor.RED + "You must look at the sign you wish to perform that action on!");
            return true;
        }

        int lineNumber;

        try {
            lineNumber = Integer.parseInt(strings[0]);
        } catch (NumberFormatException e) {
            commandSender.sendMessage(ChatColor.RED + "That line number isn't a number at all!");
            return false;
        }

        if (lineNumber < 1 || lineNumber > 4) {
            commandSender.sendMessage(ChatColor.RED + "Your line number is out of bounds!");
            return false;
        }

        StringBuilder messageBuilder = new StringBuilder();
        for (String string : Arrays.asList(strings).subList(1, strings.length)) {
            messageBuilder.append(string).append(" ");
        }
        if (messageBuilder.length() > 0) {
            messageBuilder.deleteCharAt(messageBuilder.length() - 1);
        }

        Sign sign = ((Sign) block.getState());
        String[] oldStrings = sign.getLines().clone();
        String[] newStrings = sign.getLines();
        newStrings[lineNumber - 1] = ChatColor.translateAlternateColorCodes('&', messageBuilder.toString());
        SignEdit.getSignEditPlayer(player).addEdit(sign.getLocation(), oldStrings, newStrings);
        sign.update();

        commandSender.sendMessage(ChatColor.GREEN + "Sign updated.");
        return true;
    }
}
