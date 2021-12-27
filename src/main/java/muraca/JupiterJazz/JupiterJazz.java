package muraca.JupiterJazz;

import muraca.JupiterJazz.model.Constants;
import muraca.JupiterJazz.model.session.Session;
import muraca.JupiterJazz.view.components.*;
import muraca.JupiterJazz.view.utils.MessageHandler;
import muraca.JupiterJazz.view.utils.FileHandler;

import javax.swing.*;
import java.awt.*;

public class JupiterJazz extends JFrame {

    private CardLayout layout;
    private Container container;

    private SessionPanel sessionPanel;

    public JupiterJazz() {
        super("Jupiter Jazz");

        FileHandler.setParent(this);
        MessageHandler.setParent(this);

        layout = new CardLayout();
        container = getContentPane();
        container.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        container.setLayout(layout);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - Constants.WIDTH/2, dim.height/2 - Constants.HEIGHT/2);

        JMenuBar menuBar = initMenuBar();
        setJMenuBar(menuBar);

        initPanels();

        setVisible(true);
        setResizable(false);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JMenuBar initMenuBar() { //TODO implement listeners
        JMenuBar menuBar = new JMenuBar();

        /* File */

        JMenu file = new JMenu("File");

        JMenuItem newSession = new JMenuItem("New Session");
        newSession.addActionListener(l -> {
            sessionPanel.setSession(new Session());
            layout.show(container, "session");
        });
        file.add(newSession);

        JMenuItem openSession = new JMenuItem("Open existing Session");
        openSession.addActionListener(l -> sessionPanel.setSession(Session.loadFromFile()));
        file.add(openSession);

        JMenuItem saveCurrentSession = new JMenuItem("Save current Session");
        saveCurrentSession.addActionListener(l -> sessionPanel.getSession().saveSessionAsFile());
        file.add(saveCurrentSession);

        file.addSeparator();

        JMenuItem importIEEE1599 = new JMenuItem("Import IEEE1599 XML file");
        importIEEE1599.addActionListener(l -> {});
        file.add(importIEEE1599);

        JMenuItem exportIEEE1599 = new JMenuItem("Export IEEE1599 XML file");
        exportIEEE1599.addActionListener(l -> sessionPanel.getSession().generateIEEE1599());
        file.add(exportIEEE1599);

        file.addSeparator();

        JMenuItem exportAudio = new JMenuItem("Export as audio file");
        exportAudio.addActionListener(l -> {});
        file.add(exportAudio);

        menuBar.add(file);

        /* Help */

        JMenu help = new JMenu("Help");

        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.addActionListener(l -> {});
        help.add(helpItem);

        JMenuItem about = new JMenuItem("About");
        about.addActionListener(l -> MessageHandler.showAboutMessage());
        help.add(about);

        menuBar.add(help);

        return menuBar;
    }

    private void initPanels() {
        sessionPanel = new SessionPanel();
        container.add("session", sessionPanel);

        layout.show(container, "session");
    }

    public void setPanel(String name) { layout.show(container, name); }

    public JPanel getPanel(String name) {
        for (Component c: container.getComponents()) {
            if (c.getName().equalsIgnoreCase(name) && c instanceof JPanel) {
                return (JPanel) c;
            }
        }
        return null;
    }

    public static void main(String[] args) { JupiterJazz j = new JupiterJazz(); }
}
