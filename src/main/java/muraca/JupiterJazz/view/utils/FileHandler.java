package muraca.JupiterJazz.view.utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class FileHandler {
    private static FileNameExtensionFilter xmlExtensionFilter = new FileNameExtensionFilter("XML file", "xml");

    public static int OPEN_FILE = 1;
    public static int SAVE_FILE = 2;

    public static File chooseXMLFile(Component parent, int option) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setFileFilter(xmlExtensionFilter);
        if ((option == OPEN_FILE && fc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) ||
            (option == SAVE_FILE && fc.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION)) {
            if (!xmlExtensionFilter.accept(fc.getSelectedFile())) {
                return new File(fc.getSelectedFile().getAbsolutePath() + ".xml");
            }
            return fc.getSelectedFile();
        }
        return null;
    }
}
