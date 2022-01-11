package muraca.JupiterJazz.view.components;

import muraca.JupiterJazz.model.Constants;
import muraca.JupiterJazz.model.Fraction;
import muraca.JupiterJazz.model.HasSession;
import muraca.JupiterJazz.model.session.Session;
import muraca.JupiterJazz.view.utils.SimpleDocumentListener;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class TempoPanel extends JPanel implements HasSession {

    private Session session;

    private JSlider bpmSlider;
    private JComboBox<String> timeSignatureComboBox;
    private JTextField durationInMeasuresField;
    private JLabel durationInSecondsField;

    public TempoPanel() {
        super(new FlowLayout());

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
        add(bpmLabel);
        add(bpmSlider);
        add(bpmValueLabel);

        timeSignatureComboBox = new JComboBox<>();
        for (String t: Constants.TIME_SIGNATURES_FRAC) {
            timeSignatureComboBox.addItem(t);
        }
        timeSignatureComboBox.addActionListener(l -> {
            session.setTimeSignature(Fraction.parseString(timeSignatureComboBox.getSelectedItem().toString()));
            durationInSecondsField.setText(String.valueOf(session.getDurationInSeconds()));
        });
        JLabel timeSignatureLabel = new JLabel("Time signature");
        timeSignatureLabel.setLabelFor(timeSignatureComboBox);
        add(timeSignatureLabel);
        add(timeSignatureComboBox);

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
        add(durationInMeasuresLabel);
        add(durationInMeasuresField);

        durationInSecondsField = new JLabel("");
        durationInSecondsField.setMinimumSize(new Dimension(40, 16));
        durationInSecondsField.setMaximumSize(new Dimension(40, 16));
        durationInSecondsField.setPreferredSize(new Dimension(40, 16));
        JLabel durationInSecondsLabel = new JLabel("Duration (seconds):");
        durationInSecondsLabel.setLabelFor(durationInSecondsField);
        add(durationInSecondsLabel);
        add(durationInSecondsField);

        setAlignmentX(CENTER_ALIGNMENT);
    }

    @Override
    public void setSession(Session s) {
        session = s;

        bpmSlider.setValue(session.getBpm());
        timeSignatureComboBox.setSelectedItem(session.getTimeSignature().toString());
        durationInMeasuresField.setText(String.valueOf(session.getDurationInMeasures()));
        durationInSecondsField.setText(String.valueOf(session.getDurationInSeconds()));
    }
}
