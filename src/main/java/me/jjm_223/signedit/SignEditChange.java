package me.jjm_223.signedit;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SignEditChange {
    private String[] oldLines;
    private String[] newLines;
    private Location signLocation;

    public SignEditChange(String[] oldLines, String[] newLines, Location sign) {
        this.oldLines = oldLines;
        this.newLines = newLines;
        this.signLocation = sign;
    }

    public boolean redo(Player player) {
        return (signLocation.getBlock().getType() == Material.WALL_SIGN
                || signLocation.getBlock().getType() == Material.SIGN_POST)
                && SignEdit.updateSign(player, signLocation.getBlock(), newLines);
    }

    public boolean undo(Player player) {
        return (signLocation.getBlock().getType() == Material.WALL_SIGN
                || signLocation.getBlock().getType() == Material.SIGN_POST)
                && SignEdit.updateSign(player, signLocation.getBlock(), oldLines);
    }
}
