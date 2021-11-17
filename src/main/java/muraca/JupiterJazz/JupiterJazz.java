package muraca.JupiterJazz;

import muraca.JupiterJazz.model.Session;
import muraca.JupiterJazz.view.panels.*;
import muraca.JupiterJazz.view.utils.FileHandler;

import javax.swing.*;
import java.awt.*;

public class JupiterJazz extends JFrame {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    private CardLayout layout;
    private Container container;

    private SessionPanel sessionPanel;

    public JupiterJazz() {
        super("Jupiter Jazz");

        FileHandler.setParent(this);

        layout = new CardLayout();
        container = getContentPane();
        container.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        container.setLayout(layout);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - WIDTH/2, dim.height/2 - HEIGHT/2);

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
        newSession.addActionListener(a -> {
            sessionPanel.setSession(new Session());
            layout.show(container, "session");
        });
        file.add(newSession);

        JMenuItem openSession = new JMenuItem("Open existing Session");
        openSession.addActionListener(a -> sessionPanel.setSession(Session.loadFromFile(FileHandler.chooseXMLFile(FileHandler.OPEN_FILE))));
        file.add(openSession);

        JMenuItem saveCurrentSession = new JMenuItem("Save current Session");
        saveCurrentSession.addActionListener(a -> sessionPanel.getSession().saveSessionAsFile(FileHandler.chooseXMLFile(FileHandler.SAVE_FILE)));
        file.add(saveCurrentSession);

        file.addSeparator();

        JMenuItem importIEEE1599 = new JMenuItem("Import IEEE1599 XML file");
        importIEEE1599.addActionListener(a -> {});
        file.add(importIEEE1599);

        JMenuItem exportIEEE1599 = new JMenuItem("Export IEEE1599 XML file");
        exportIEEE1599.addActionListener(a -> sessionPanel.getSession().generateIEEE1599(FileHandler.chooseXMLFile(FileHandler.SAVE_FILE)));
        file.add(exportIEEE1599);

        file.addSeparator();

        JMenuItem exportAudio = new JMenuItem("Export as audio file");
        exportAudio.addActionListener(a -> {});
        file.add(exportAudio);

        menuBar.add(file);

        /* Help */

        JMenu help = new JMenu("Help");

        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.addActionListener(a -> {});
        help.add(helpItem);

        JMenuItem about = new JMenuItem("About");
        about.addActionListener(a -> JOptionPane.showMessageDialog(this,
                "JupiterJazz is a project made by Matteo Muraca id 984277\nfor \"Programmazione per la musica\" course at UniMi, AA 2021/2022",
                "About JupiterJazz",
                JOptionPane.INFORMATION_MESSAGE));
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
        for(Component c: container.getComponents()) {
            if (c.getName().equalsIgnoreCase(name) && c instanceof JPanel) {
                return (JPanel) c;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        JupiterJazz j = new JupiterJazz();
    }
}
