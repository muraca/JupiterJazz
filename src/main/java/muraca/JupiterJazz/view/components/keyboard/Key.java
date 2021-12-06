package muraca.JupiterJazz.view.components.keyboard;

import muraca.JupiterJazz.model.Notes;
import muraca.JupiterJazz.view.utils.SimpleMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class Key extends JButton {
    static int WD = 40;
    static int HT = (WD * 9) / 2;

    int note;
    public int getNote () { return note; }

    KeyboardComponent parent;

    String[] names;
    int selectedName = 0;
    void setSelectedName(int pos) {
        selectedName = pos;
        setText(names[selectedName]);
        parent.setNoteName(note, pos);
    }

    public String getNoteName() {return String.valueOf(names[selectedName].charAt(0));};
    public String getNoteAccidental() {return Notes.ACCIDENTALS.get(names[selectedName].substring(1));}

    boolean toggled = false;
    void setToggled(boolean t) {
        toggled = t;
        setForeground(toggled ? Color.GREEN : Color.RED);
        parent.setToggled(note, toggled);
    }
    boolean isToggled() {return toggled;}
    void toggle() {setToggled(!toggled);}

    JPopupMenu menu;

    void showPopupMenu(MouseEvent e) {
        if (menu == null) {
            menu = new JPopupMenu();
            for (int i = 0; i < names.length; ++i) {
                JMenuItem item = new JMenuItem(names[i]);
                int finalI = i;
                item.addActionListener(l -> setSelectedName(finalI));
                menu.add(item);
            }
        }
        menu.show(e.getComponent(), e.getX(), e.getY());
    }

    MouseListener makeMouseListener() {
        return new SimpleMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e))
                    showPopupMenu(e);
            }
        };
    }

}

class WhiteKey extends Key {
    static int WWD = (WD * 3) / 2;
    static int WHT = (HT * 3) / 2;

    public WhiteKey (int pos, KeyboardComponent comp) {
        note =  2 * pos - (pos + 4) / 7 - pos / 7;
        names = Notes.NOTE_COMPACTNAMES[note];

        int left = 10 + WWD * pos;
        setBounds(left, 10, WWD, WHT);

        setFocusPainted(false);
        addActionListener(l -> toggle());
        addMouseListener(makeMouseListener());

        parent = comp;

        setText(names[selectedName]);
        setToggled(toggled);
    }
}

class BlackKey extends Key {
    public BlackKey (int pos, KeyboardComponent comp) {
        note = 1 + 2 * pos + (pos + 3) / 5 + pos / 5;
        names = Notes.NOTE_COMPACTNAMES[note];

        int left = 10 + WD
                + ((WD * 3) / 2) * (pos + (pos / 5)
                + ((pos + 3) / 5));
        setBounds(left, 10, WD, HT);

        setOpaque(false);
        setBorderPainted(true);
        setFocusPainted(false);
        addActionListener(l -> toggle());
        addMouseListener(makeMouseListener());

        parent = comp;

        setText(names[selectedName]);
        setToggled(toggled);
    }
}