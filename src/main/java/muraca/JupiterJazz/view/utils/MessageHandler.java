package muraca.JupiterJazz.view.utils;

import javax.swing.*;
import java.awt.*;

public class MessageHandler {
    private Component parent;

    private static MessageHandler instance = new MessageHandler();

    public static void setParent(Component parent) {
        instance.parent = parent;
    }

    public static void showXMLImportErrorMessage(String filename) {
        JOptionPane.showMessageDialog(instance.parent,
                "Error while importing XML file " + filename,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void showXMLExportErrorMessage(String filename) {
        JOptionPane.showMessageDialog(instance.parent,
                "Error while exporting XML to " + filename,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void showNoFileSelectedErrorMessage() {
        JOptionPane.showMessageDialog(instance.parent,
                "No file selected!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void showNoNoteEnabledErrorMessage() {
        JOptionPane.showMessageDialog(instance.parent,
                "At least a note in the tonality is required to generate an IEEE1599 file.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void showNoInstrumentEnabledErrorMessage() {
        JOptionPane.showMessageDialog(instance.parent,
                "At least an active instrument is required to generate an IEEE1599 file.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void showAboutMessage() {
        JOptionPane.showMessageDialog(instance.parent,
                "JupiterJazz is a project made by Matteo Muraca id 984277\nfor \"Programmazione per la musica\" course at UniMi, AA 2021/2022",
                "About JupiterJazz",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
