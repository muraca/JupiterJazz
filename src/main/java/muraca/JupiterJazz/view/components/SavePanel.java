package muraca.JupiterJazz.view.components;

import muraca.JupiterJazz.model.HasSession;
import muraca.JupiterJazz.model.session.Session;

import javax.swing.*;
import java.awt.*;

public class SavePanel extends JPanel implements HasSession {

    private Session session;

    public SavePanel() {
        JButton saveSessionButton = new JButton("Save current Session");
        saveSessionButton.setMinimumSize(new Dimension(256, 64));
        saveSessionButton.setMaximumSize(new Dimension(256, 64));
        saveSessionButton.setPreferredSize(new Dimension(256, 64));
        saveSessionButton.addActionListener(l -> session.saveSessionAsFile());
        add(saveSessionButton);

        JButton generateIEEE1599Button = new JButton("Generate IEEE1599");
        generateIEEE1599Button.setMinimumSize(new Dimension(256, 64));
        generateIEEE1599Button.setMaximumSize(new Dimension(256, 64));
        generateIEEE1599Button.setPreferredSize(new Dimension(256, 64));
        generateIEEE1599Button.addActionListener(l -> session.generateIEEE1599());
        add(generateIEEE1599Button);

        setAlignmentX(CENTER_ALIGNMENT);
    }


    @Override
    public void setSession(Session s) {
        session = s;
    }
}
