package me.jjm_223.signedit;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SignEditPlayer {
    private Player handle;
    private SignChangeList changeList = new SignChangeList();

    public SignEditPlayer(Player handle) {
        this.handle = handle;
    }

    public void addEdit(Location location, String[] oldText, String[] newText) {
        changeList.addChange(new SignEditChange(oldText, newText, location));
    }

    public boolean undo() {
        return changeList.undo();
    }

    public boolean redo() {
        return changeList.redo();
    }
}
