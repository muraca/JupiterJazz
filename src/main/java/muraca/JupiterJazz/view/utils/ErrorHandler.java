package muraca.JupiterJazz.view.utils;

import javax.swing.*;
import java.awt.*;

public class ErrorHandler {
    private Component parent;

    private static ErrorHandler instance = new ErrorHandler();

    public static void setParent(Component parent) {
        instance.parent = parent;
    }

    public static ErrorHandler getInstance() {
        return instance;
    }

    public static void xmlImportException(String filename) {
        JOptionPane.showMessageDialog(instance.parent, "Error while importing XML file " + filename, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void xmlExportException(String filename) {
        JOptionPane.showMessageDialog(instance.parent, "Error while exporting XML to " + filename, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
