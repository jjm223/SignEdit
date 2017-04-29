package me.jjm_223.signedit;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SignChangeList {
    private List<SignEditChange> history = new ArrayList<SignEditChange>();
    private int position = 0;

    public void addChange(SignEditChange change) {
        history = history.subList(position, history.size());
        position = 0;
        history.add(0, change);
    }

    public boolean undo(Player player) {
        if (history.size() == 0 || history.size() <= position) {
            return false;
        }

        if (history.get(position).undo(player)) {
            ++position;
            return true;
        } else {
            return false;
        }
    }

    public boolean redo(Player player) {
        if (position <= 0) {
            return false;
        }

        if (history.get(position - 1).redo(player)) {
            --position;
            return true;
        } else {
            return false;
        }
    }
}
