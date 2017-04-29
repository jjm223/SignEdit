package me.jjm_223.signedit;

import me.jjm_223.signedit.command.CommandSignEdit;
import me.jjm_223.signedit.command.CommandSignRedo;
import me.jjm_223.signedit.command.CommandSignUndo;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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

    public static boolean updateSign(@Nonnull Player player, @Nonnull Block block, @Nonnull String[] newLines) {
        Validate.isTrue(block.getState() instanceof Sign);

        PluginManager pluginManager = Bukkit.getPluginManager();
        Sign originalState = (Sign) block.getState();
        Sign newState;

        BlockPlaceEvent blockPlaceEvent;
        SignChangeEvent signChangeEvent;

        blockPlaceEvent = new BlockPlaceEvent(block, originalState, getSupportingBlock(originalState),
                new ItemStack(Material.SIGN), player, true, EquipmentSlot.HAND);
        pluginManager.callEvent(blockPlaceEvent);
        if (blockPlaceEvent.isCancelled()) return false;

        signChangeEvent = new SignChangeEvent(block, player, newLines);
        pluginManager.callEvent(signChangeEvent);
        if (signChangeEvent.isCancelled()) return false;

        if (!(block.getState() instanceof Sign)) return false;
        newState = (Sign) block.getState();

        for (int x = 0; x < newLines.length; ++x)
        {
            newState.setLine(x, newLines[x]);
        }

        return newState.update();
    }

    @Nullable
    private static Block getSupportingBlock(Sign sign) {
        if (sign == null) return null;

        Block block = sign.getBlock();

        if (block.getType() == Material.WALL_SIGN) {
            //noinspection deprecation
            switch (sign.getRawData()) {
                case 2:
                    return block.getRelative(BlockFace.SOUTH);
                case 3:
                    return block.getRelative(BlockFace.NORTH);
                case 4:
                    return block.getRelative(BlockFace.EAST);
                case 5:
                    return block.getRelative(BlockFace.WEST);
                default:
                    return null;
            }
        } else {
            return block.getRelative(BlockFace.DOWN);
        }
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
