package me.jjm_223.signedit;

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

    public boolean undo() {
        if (history.size() == 0 || history.size() <= position) {
            return false;
        }
        history.get(position).undo();
        position++;
        return true;
    }

    public boolean redo() {
        if (position <= 0) {
            return false;
        }
        position--;
        history.get(position).redo();
        return true;
    }
}
