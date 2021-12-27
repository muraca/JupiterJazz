package muraca.JupiterJazz.view.components.keyboard;

import muraca.JupiterJazz.model.session.Tonality;

import javax.swing.*;
import java.awt.*;

public class KeyboardComponent extends JPanel {
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
        int width = 10 + bounds.x + bounds.width;
        int height = 10 + bounds.y + bounds.height;
        Dimension d = new Dimension(width, height);
        setMinimumSize(d);
        setMaximumSize(d);
        setPreferredSize(d);

        repaint();
    }

    public void setTonality(Tonality tonality) {
        this.tonality = tonality;
        for (BlackKey b : blacks) {
            b.setToggled(tonality.isNoteEnabled(b.getNote()));
            b.setSelectedName(tonality.getNoteNamePos(b.getNote()));
        }
        for (WhiteKey w : whites) {
            w.setToggled(tonality.isNoteEnabled(w.getNote()));
            w.setSelectedName(tonality.getNoteNamePos(w.getNote()));
        }
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


/*
    public void updateTonality() {
        for (BlackKey b: blacks) {
            if (b.isToggled())
                tonality.enableNote(b.getNote());
            else
                tonality.disableNote(b.getNote());
            tonality.setNoteName(b.getNote(), b.getNoteName());
            tonality.setNoteAccidental(b.getNote(), b.getNoteAccidental());
        }
        for (WhiteKey w: whites) {
            if (w.isToggled())
                tonality.enableNote(w.getNote());
            else
                tonality.disableNote(w.getNote());
            tonality.setNoteName(w.getNote(), w.getNoteName());
            tonality.setNoteAccidental(w.getNote(), w.getNoteAccidental());
        }
    }*/

}