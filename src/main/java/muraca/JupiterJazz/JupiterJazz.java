package muraca.JupiterJazz;

import muraca.JupiterJazz.model.Constants;
import muraca.JupiterJazz.model.session.Session;
import muraca.JupiterJazz.view.components.SessionPanel;
import muraca.JupiterJazz.view.utils.MessageHandler;
import muraca.JupiterJazz.view.utils.FileHandler;

import javax.swing.*;
import java.awt.*;

public class JupiterJazz extends JFrame {
    private SessionPanel sessionPanel = new SessionPanel();

    public JupiterJazz() {
        super("Jupiter Jazz");

        FileHandler.setParent(this);
        MessageHandler.setParent(this);

        setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - Constants.WIDTH/2, dim.height/2 - Constants.HEIGHT/2);

        JMenuBar menuBar = initMenuBar();
        setJMenuBar(menuBar);

        add(sessionPanel);

        setVisible(true);
        setResizable(false);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JMenuBar initMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        /* File */

        JMenu file = new JMenu("File");

        JMenuItem newSession = new JMenuItem("New Session");
        newSession.addActionListener(l -> sessionPanel.setSession(new Session()));
        file.add(newSession);

        JMenuItem openSession = new JMenuItem("Open existing Session");
        openSession.addActionListener(l -> sessionPanel.setSession(Session.loadFromFile()));
        file.add(openSession);

        JMenuItem saveCurrentSession = new JMenuItem("Save current Session");
        saveCurrentSession.addActionListener(l -> sessionPanel.saveSessionAsFile());
        file.add(saveCurrentSession);

        file.addSeparator();

        JMenuItem exportIEEE1599 = new JMenuItem("Generate IEEE1599 XML file");
        exportIEEE1599.addActionListener(l -> sessionPanel.generateIEEE1599());
        file.add(exportIEEE1599);

        menuBar.add(file);

        /* Help */

        JMenu help = new JMenu("Help");

        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.addActionListener(l -> MessageHandler.showHelpMessage());
        help.add(helpItem);

        JMenuItem about = new JMenuItem("About");
        about.addActionListener(l -> MessageHandler.showAboutMessage());
        help.add(about);

        menuBar.add(help);

        return menuBar;
    }

    public static void main(String[] args) { new JupiterJazz(); }
}
