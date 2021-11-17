package muraca.JupiterJazz.view.utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class FileHandler {
    private static FileNameExtensionFilter xmlExtensionFilter = new FileNameExtensionFilter("XML file", "xml");

    public static int OPEN_FILE = 1;
    public static int SAVE_FILE = 2;

    private Component parent;

    private static FileHandler instance = new FileHandler();

    public static void setParent(Component parent) {
        instance.parent = parent;
    }

    public static FileHandler getInstance() {
        return instance;
    }

    public static File chooseXMLFile(int option) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setFileFilter(xmlExtensionFilter);
        if ((option == OPEN_FILE && fc.showOpenDialog(instance.parent) == JFileChooser.APPROVE_OPTION) ||
            (option == SAVE_FILE && fc.showSaveDialog(instance.parent) == JFileChooser.APPROVE_OPTION)) {
            File selectedFile = fc.getSelectedFile();
            if (selectedFile != null && !xmlExtensionFilter.accept(selectedFile)) {
                if (selectedFile.getName().matches(".*\\..*")) {
                    fileIsNotXML(selectedFile.getName());
                    return chooseXMLFile(option);
                }
                return new File(selectedFile.getAbsolutePath() + ".xml");
            }
            return selectedFile;
        }
        return null;
    }

    public static void fileIsNotXML(String filename) {
        JOptionPane.showMessageDialog(instance.parent, "File " + filename + " is not an XML file.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void fileNotFoundExceptionInfo(String filename) {
        JOptionPane.showMessageDialog(instance.parent, "File " + filename + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
