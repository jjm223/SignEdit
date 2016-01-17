package me.jjm_223.signedit;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;

public class SignEditChange {
    private String[] oldLines;
    private String[] newLines;
    private Location signLocation;

    public SignEditChange(String[] oldLines, String[] newLines, Location sign) {
        this.oldLines = oldLines;
        this.newLines = newLines;
        this.signLocation = sign;
    }

    public boolean redo() {
        if (signLocation.getBlock().getType() == Material.WALL_SIGN || signLocation.getBlock().getType() == Material.SIGN_POST) {
            Sign sign = ((Sign) signLocation.getBlock().getState());
            for (int x = 0; x < newLines.length; x++) {
                sign.setLine(x, newLines[x]);
            }

            sign.update();
            return true;
        }

        return false;
    }

    public boolean undo() {
        if (signLocation.getBlock().getType() == Material.WALL_SIGN || signLocation.getBlock().getType() == Material.SIGN_POST) {
            Sign sign = ((Sign) signLocation.getBlock().getState());
            for (int x = 0; x < oldLines.length; x++) {
                sign.setLine(x, oldLines[x]);
            }

            sign.update();
            return true;
        }

        return false;
    }
}
