package muraca.JupiterJazz.view.components;

import muraca.JupiterJazz.model.Notes;
import muraca.JupiterJazz.model.session.Instrument;
import muraca.JupiterJazz.view.utils.RangeSlider;

import javax.swing.*;
import java.awt.*;
import java.util.stream.IntStream;

public class InstrumentPanel extends JPanel {
    private Instrument instrument;

    private JButton enableButton;
    private RangeSlider instrumentPitchSlider;
    private JLabel instrumentPitchLabel;

    public InstrumentPanel(Instrument i) {
        super(null);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        instrument = i;

        JPanel topPanel = new JPanel(new FlowLayout());

        JComboBox<String> chooseInstrumentComboBox = new JComboBox<>(Instrument.INSTRUMENTS);
        chooseInstrumentComboBox.setSelectedIndex(instrument.getId());
        chooseInstrumentComboBox.addActionListener(l -> {
            int idx = chooseInstrumentComboBox.getSelectedIndex();
            instrument.setId(idx);
            if (getParent() instanceof JTabbedPane) {
                JTabbedPane parent = (JTabbedPane) getParent();
                parent.setTitleAt(parent.getSelectedIndex(), instrument.getName());
            }
        });
        topPanel.add(chooseInstrumentComboBox);
        add(topPanel);

        JPanel bottomPanel = new JPanel(new FlowLayout());

        enableButton = new JButton();
        setEnableButtonText();
        enableButton.addActionListener(l -> {
            toggleEnabled();
            setEnableButtonText();
        });
        bottomPanel.add(enableButton);
        enableButton.setPreferredSize(new Dimension(128, 64));
        enableButton.setAlignmentY(CENTER_ALIGNMENT);

        JPanel centerPanel = new JPanel(null);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        instrumentPitchSlider = new RangeSlider(0, 127);
        instrumentPitchSlider.setValues(instrument.getMinPitch(), instrument.getMaxPitch());
        instrumentPitchSlider.addChangeListener(l -> {
            instrument.setMinPitch(instrumentPitchSlider.getValue());
            instrument.setMaxPitch(instrumentPitchSlider.getUpperValue());
            setInstrumentPitchLabelText();
        });
        centerPanel.add(instrumentPitchSlider);

        instrumentPitchLabel = new JLabel();
        setInstrumentPitchLabelText();
        centerPanel.add(instrumentPitchLabel);

        Dimension d = new Dimension(160, 32);
        centerPanel.setPreferredSize(d);
        centerPanel.setMinimumSize(d);
        centerPanel.setMaximumSize(d);
        bottomPanel.add(centerPanel);


        JPanel rightPanel = new JPanel(null);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JComboBox<String> clefShapeComboBox = new JComboBox<>(Instrument.CLEF_SHAPES);
        clefShapeComboBox.setSelectedIndex(
                IntStream.range(0, Instrument.CLEF_SHAPES.length)
                        .filter(idx -> Instrument.CLEF_SHAPES[idx].equals(instrument.getClefShape()))
                        .findFirst().orElse(0));
        clefShapeComboBox.addActionListener(l ->
                instrument.setClefShape(Instrument.CLEF_SHAPES[clefShapeComboBox.getSelectedIndex()]));
        rightPanel.add(clefShapeComboBox);

        JComboBox<String> clefStaffStepComboBox = new JComboBox<>(Instrument.CLEF_STAFF_STEPS);
        clefStaffStepComboBox.setSelectedIndex(
            IntStream.range(0, Instrument.CLEF_STAFF_STEPS.length)
                    .filter(idx -> Instrument.CLEF_STAFF_STEPS[idx].equals(instrument.getClefStaffStep()))
                    .findFirst().orElse(0));
        clefStaffStepComboBox.addActionListener(l ->
                instrument.setClefStaffStep(Instrument.CLEF_STAFF_STEPS[clefStaffStepComboBox.getSelectedIndex()]));
        rightPanel.add(clefStaffStepComboBox);

        bottomPanel.add(rightPanel);

        add(bottomPanel);

    }

    private void toggleEnabled() {
        instrument.setEnabled(!instrument.isEnabled());
    }

    private void setEnableButtonText() {
        enableButton.setText(instrument.isEnabled() ? "Disable" : "Enable");
    }

    private void setInstrumentPitchLabelText() {
        instrumentPitchLabel.setText(
                "min: " + Notes.midiToEnglish(instrument.getMinPitch()) + ", " +
                "max: " + Notes.midiToEnglish(instrument.getMaxPitch()));
    }
}
