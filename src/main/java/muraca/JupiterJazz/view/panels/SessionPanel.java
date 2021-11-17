package muraca.JupiterJazz.view.panels;

import muraca.JupiterJazz.model.Constants;
import muraca.JupiterJazz.model.Fraction;
import muraca.JupiterJazz.model.Session;
import muraca.JupiterJazz.view.utils.FileHandler;
import muraca.JupiterJazz.view.utils.RangeSlider;
import muraca.JupiterJazz.view.utils.SimpleDocumentListener;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class SessionPanel extends JPanel {

    private Session session;

    private JTextField authorField;
    private JTextField titleField;

    private JSlider bpmSlider;
    private JComboBox<String> timeSignatureComboBox;
    private JTextField durationInMeasuresField;
    private JLabel durationInSecondsField;

    private JSlider noteProbabilitySlider;
    private RangeSlider noteDurationSlider;

    private JSlider pauseProbabilitySlider;
    private RangeSlider pauseDurationSlider;

    public SessionPanel(Session session) {
        super(null);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(createMetadataPanel());
        add(createTempoPanel());
        add(createNotePanel());
        add(createPausePanel());
        add(createKeyPanel());
        add(createSavePanel());

        setSession(session);
    }

    public SessionPanel() {
        this(new Session());
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;

        authorField.setText(session.getAuthor());
        titleField.setText(session.getTitle());
        bpmSlider.setValue(session.getBpm());
        timeSignatureComboBox.setSelectedItem(session.getTimeSignature().toString());
        durationInMeasuresField.setText(String.valueOf(session.getDurationInMeasures()));
        durationInSecondsField.setText(String.valueOf(session.getDurationInSeconds()));
        noteProbabilitySlider.setValue(100 - session.getPauseProbability());
        noteDurationSlider.setValues(session.getMinNoteDurationIndex(), session.getMaxNoteDurationIndex());
        pauseProbabilitySlider.setValue(session.getPauseProbability());
        pauseDurationSlider.setValues(session.getMinPauseDurationIndex(), session.getMaxPauseDurationIndex());

        revalidate();
        repaint();
    }

    private JPanel createMetadataPanel() {
        JPanel metadataPanel = new JPanel(new FlowLayout());

        authorField = new JTextField(25);
        authorField.getDocument().addDocumentListener((SimpleDocumentListener) e -> session.setAuthor(authorField.getText()));
        JLabel authorLabel = new JLabel("Author");
        authorLabel.setLabelFor(authorField);
        metadataPanel.add(authorLabel);
        metadataPanel.add(authorField);

        titleField = new JTextField(25);
        titleField.getDocument().addDocumentListener((SimpleDocumentListener) e -> {
            session.setTitle(titleField.getText());
        });
        JLabel titleLabel = new JLabel("Title");
        titleLabel.setLabelFor(titleField);
        metadataPanel.add(titleLabel);
        metadataPanel.add(titleField);

        return metadataPanel;
    }

    private JPanel createTempoPanel() {
        JPanel tempoPanel = new JPanel(new FlowLayout());

        JLabel bpmValueLabel = new JLabel("60");
        bpmValueLabel.setMinimumSize(new Dimension(24, 16));
        bpmValueLabel.setMaximumSize(new Dimension(24, 16));
        bpmValueLabel.setPreferredSize(new Dimension(24, 16));
        bpmSlider = new JSlider(30, 210, 60);
        bpmSlider.setSnapToTicks(true);
        bpmSlider.setLabelTable(bpmSlider.createStandardLabels(30));
        bpmSlider.setPaintLabels(true);
        bpmSlider.addChangeListener(l -> {
            session.setBpm(bpmSlider.getValue());
            bpmValueLabel.setText(String.valueOf(bpmSlider.getValue()));
            durationInSecondsField.setText(String.valueOf(session.getDurationInSeconds()));
        });
        JLabel bpmLabel = new JLabel("BPM");
        bpmLabel.setLabelFor(bpmSlider);
        tempoPanel.add(bpmLabel);
        tempoPanel.add(bpmSlider);
        tempoPanel.add(bpmValueLabel);

        timeSignatureComboBox = new JComboBox<>();
        for (String t: Constants.TIME_SIGNATURES_FRAC) {
            timeSignatureComboBox.addItem(t);
        }
        timeSignatureComboBox.addActionListener(e -> {
            session.setTimeSignature(Fraction.parseString(timeSignatureComboBox.getSelectedItem().toString()));
            durationInSecondsField.setText(String.valueOf(session.getDurationInSeconds()));
        });
        JLabel timeSignatureLabel = new JLabel("Time signature");
        timeSignatureLabel.setLabelFor(timeSignatureComboBox);
        tempoPanel.add(timeSignatureLabel);
        tempoPanel.add(timeSignatureComboBox);

        NumberFormatter durationInMeasuresFormatter = new NumberFormatter(NumberFormat.getIntegerInstance());
        durationInMeasuresFormatter.setValueClass(Integer.class);
        durationInMeasuresFormatter.setAllowsInvalid(false);
        durationInMeasuresFormatter.setMinimum(1);
        durationInMeasuresFormatter.setMaximum(10000);
        durationInMeasuresField = new JFormattedTextField(durationInMeasuresFormatter);
        durationInMeasuresField.setColumns(5);
        durationInMeasuresField.getDocument().addDocumentListener((SimpleDocumentListener) e -> {
            session.setDurationInMeasures(Integer.parseInt(durationInMeasuresField.getText().isBlank() ? "0" : durationInMeasuresField.getText()));
            durationInSecondsField.setText(String.valueOf(session.getDurationInSeconds()));
        });
        JLabel durationInMeasuresLabel = new JLabel("Duration (measures):");
        durationInMeasuresLabel.setLabelFor(durationInMeasuresField);
        tempoPanel.add(durationInMeasuresLabel);
        tempoPanel.add(durationInMeasuresField);

        durationInSecondsField = new JLabel("");
        durationInSecondsField.setMinimumSize(new Dimension(40, 16));
        durationInSecondsField.setMaximumSize(new Dimension(40, 16));
        durationInSecondsField.setPreferredSize(new Dimension(40, 16));
        JLabel durationInSecondsLabel = new JLabel("Duration (seconds):");
        durationInSecondsLabel.setLabelFor(durationInSecondsField);
        tempoPanel.add(durationInSecondsLabel);
        tempoPanel.add(durationInSecondsField);

        return tempoPanel;
    }

    private JPanel createNotePanel() { //TODO single reusable method
        JPanel notePanel = new JPanel(new FlowLayout());

        JLabel noteProbabilityValueLabel = new JLabel("50%");
        noteProbabilityValueLabel.setMinimumSize(new Dimension(36, 16));
        noteProbabilityValueLabel.setMaximumSize(new Dimension(36, 16));
        noteProbabilityValueLabel.setPreferredSize(new Dimension(36, 16));
        noteProbabilitySlider = new JSlider();
        noteProbabilitySlider.setSnapToTicks(true);
        noteProbabilitySlider.setLabelTable(noteProbabilitySlider.createStandardLabels(25));
        noteProbabilitySlider.setPaintLabels(true);
        noteProbabilitySlider.addChangeListener(l -> {
            session.setPauseProbability(100 - noteProbabilitySlider.getValue());
            noteProbabilityValueLabel.setText(noteProbabilitySlider.getValue() + "%");
            if (pauseProbabilitySlider.getValue() != session.getPauseProbability())
                pauseProbabilitySlider.setValue(session.getPauseProbability());
        });
        JLabel noteProbabilityLabel = new JLabel("Note probability");
        noteProbabilityLabel.setLabelFor(noteProbabilitySlider);
        notePanel.add(noteProbabilityLabel);
        notePanel.add(noteProbabilitySlider);
        notePanel.add(noteProbabilityValueLabel);

        JLabel noteDurationValuesLabel = new JLabel("");
        noteDurationValuesLabel.setMinimumSize(new Dimension(160, 16));
        noteDurationValuesLabel.setMaximumSize(new Dimension(160, 16));
        noteDurationValuesLabel.setPreferredSize(new Dimension(160, 16));
        noteDurationSlider = new RangeSlider(0, Constants.EVENT_DURATIONS_SIZE-1);
        noteDurationSlider.addChangeListener(e -> {
            session.setMinNoteDurationIndex(noteDurationSlider.getValue());
            session.setMaxNoteDurationIndex(noteDurationSlider.getUpperValue());
            noteDurationValuesLabel.setText("min: " + Constants.EVENT_DURATIONS_FRAC.get(noteDurationSlider.getValue()) + ", max: " + Constants.EVENT_DURATIONS_FRAC.get(noteDurationSlider.getUpperValue()));
        });
        JLabel noteDurationLabel = new JLabel("Note duration");
        noteDurationLabel.setLabelFor(noteDurationSlider);
        notePanel.add(noteDurationLabel);
        notePanel.add(noteDurationSlider);
        notePanel.add(noteDurationValuesLabel);

        return notePanel;
    }

    private JPanel createPausePanel() { //TODO single reusable method
        JPanel pausePanel = new JPanel(new FlowLayout());

        JLabel pauseProbabilityValueLabel = new JLabel("50%");
        pauseProbabilityValueLabel.setMinimumSize(new Dimension(36, 16));
        pauseProbabilityValueLabel.setMaximumSize(new Dimension(36, 16));
        pauseProbabilityValueLabel.setPreferredSize(new Dimension(36, 16));
        pauseProbabilitySlider = new JSlider();
        pauseProbabilitySlider.setSnapToTicks(true);
        pauseProbabilitySlider.setLabelTable(pauseProbabilitySlider.createStandardLabels(25));
        pauseProbabilitySlider.setPaintLabels(true);
        pauseProbabilitySlider.addChangeListener(l -> {
            session.setPauseProbability(pauseProbabilitySlider.getValue());
            pauseProbabilityValueLabel.setText(pauseProbabilitySlider.getValue() + "%");
            if (noteProbabilitySlider.getValue() != (100 - session.getPauseProbability()))
                noteProbabilitySlider.setValue(100 - session.getPauseProbability());
        });
        JLabel pauseProbabilityLabel = new JLabel("Pause probability");
        pauseProbabilityLabel.setLabelFor(pauseProbabilitySlider);
        pausePanel.add(pauseProbabilityLabel);
        pausePanel.add(pauseProbabilitySlider);
        pausePanel.add(pauseProbabilityValueLabel);

        JLabel pauseDurationValuesLabel = new JLabel("");
        pauseDurationValuesLabel.setMinimumSize(new Dimension(160, 16));
        pauseDurationValuesLabel.setMaximumSize(new Dimension(160, 16));
        pauseDurationValuesLabel.setPreferredSize(new Dimension(160, 16));
        pauseDurationSlider = new RangeSlider(0, Constants.EVENT_DURATIONS_SIZE-1);
        pauseDurationSlider.addChangeListener(e -> {
            session.setMinPauseDurationIndex(pauseDurationSlider.getValue());
            session.setMaxPauseDurationIndex(pauseDurationSlider.getUpperValue());
            pauseDurationValuesLabel.setText("min: " + Constants.EVENT_DURATIONS_FRAC.get(pauseDurationSlider.getValue()) + ", max: " + Constants.EVENT_DURATIONS_FRAC.get(pauseDurationSlider.getUpperValue()));
        });
        JLabel pauseDurationLabel = new JLabel("Pause duration");
        pauseDurationLabel.setLabelFor(pauseDurationSlider);
        pausePanel.add(pauseDurationLabel);
        pausePanel.add(pauseDurationSlider);
        pausePanel.add(pauseDurationValuesLabel);

        return pausePanel;
    }

    private JPanel createKeyPanel() {
        JPanel keyPanel = new JPanel(new FlowLayout());
        // TODO
        return keyPanel;
    }

    private JPanel createSavePanel() {
        JPanel savePanel = new JPanel(new FlowLayout());

        JButton saveSessionButton = new JButton("Save current Session");
        saveSessionButton.setMinimumSize(new Dimension(256, 64));
        saveSessionButton.setMaximumSize(new Dimension(256, 64));
        saveSessionButton.setPreferredSize(new Dimension(256, 64));
        saveSessionButton.addActionListener(e -> session.saveSessionAsFile(FileHandler.chooseXMLFile(FileHandler.SAVE_FILE)));
        savePanel.add(saveSessionButton);

        JButton generateIEEE1599Button = new JButton("Generate IEEE1599");
        generateIEEE1599Button.setMinimumSize(new Dimension(256, 64));
        generateIEEE1599Button.setMaximumSize(new Dimension(256, 64));
        generateIEEE1599Button.setPreferredSize(new Dimension(256, 64));
        generateIEEE1599Button.addActionListener(e -> session.generateIEEE1599(FileHandler.chooseXMLFile(FileHandler.SAVE_FILE)));
        savePanel.add(generateIEEE1599Button);

        return savePanel;
    }
}
