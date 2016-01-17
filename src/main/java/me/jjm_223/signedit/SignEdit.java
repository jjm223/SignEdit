package me.jjm_223.signedit;

import me.jjm_223.signedit.command.CommandSignEdit;
import me.jjm_223.signedit.command.CommandSignRedo;
import me.jjm_223.signedit.command.CommandSignUndo;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class SignEdit extends JavaPlugin implements Listener {

    private static Map<Player, SignEditPlayer> players = new HashMap<Player, SignEditPlayer>();

    public void onEnable() {
        getCommand("signedit").setExecutor(new CommandSignEdit());
        getCommand("signundo").setExecutor(new CommandSignUndo());
        getCommand("signredo").setExecutor(new CommandSignRedo());
        getServer().getPluginManager().registerEvents(this, this);
    }

    public static SignEditPlayer getSignEditPlayer(Player player) {
        if (!players.containsKey(player)) {
            players.put(player, new SignEditPlayer(player));
        }

        return players.get(player);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        players.remove(e.getPlayer());
    }

    @EventHandler
    public void onSignText(SignChangeEvent e) {
        if (e.getPlayer().hasPermission("signedit.autocolor")) {
            for (int x = 0; x < e.getLines().length; x++) {
                e.getLines()[x] = ChatColor.translateAlternateColorCodes('&', e.getLines()[x]);
            }
        }
    }
}
