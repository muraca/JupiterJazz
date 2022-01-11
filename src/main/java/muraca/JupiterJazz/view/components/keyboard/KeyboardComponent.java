package muraca.JupiterJazz.view.components.keyboard;

import muraca.JupiterJazz.model.HasSession;
import muraca.JupiterJazz.model.session.Session;
import muraca.JupiterJazz.model.session.Tonality;

import javax.swing.*;
import java.awt.*;

public class KeyboardComponent extends JPanel implements HasSession {
    private WhiteKey[] whites;
    private BlackKey[] blacks;

    private Tonality tonality;

    public KeyboardComponent() {
        super(null);

        whites = new WhiteKey[7];
        blacks = new BlackKey[5];

        for (int i = 0; i < blacks.length; i++) {
            blacks[i] = new BlackKey(i, this);
            add(blacks[i]);
        }

        for (int i = 0; i < whites.length; i++) {
            whites[i] = new WhiteKey(i, this);
            add(whites[i]);
        }

        int count = getComponentCount();
        Component last = getComponent(count - 1);
        Rectangle bounds = last.getBounds();
        int width = 10 + bounds.x + bounds.width,
            height = 10 + bounds.y + bounds.height;
        Dimension d = new Dimension(width, height);
        setMinimumSize(d);
        setMaximumSize(d);
        setPreferredSize(d);

        setAlignmentX(CENTER_ALIGNMENT);
        repaint();
    }

    public void setToggled(int noteId, boolean toggled) {
        if (tonality == null)
            return;
        if (toggled)
            tonality.enableNote(noteId);
        else
            tonality.disableNote(noteId);
    }

    public void setNoteName(int noteId, int position) {
        tonality.setNoteName(noteId, position);
    }

    @Override
    public void setSession(Session s) {
        tonality = s.getTonality();
        for (BlackKey b : blacks) {
            b.setToggled(tonality.isNoteEnabled(b.getNote()));
            b.setSelectedName(tonality.getNoteNamePos(b.getNote()));
        }
        for (WhiteKey w : whites) {
            w.setToggled(tonality.isNoteEnabled(w.getNote()));
            w.setSelectedName(tonality.getNoteNamePos(w.getNote()));
        }
    }

}