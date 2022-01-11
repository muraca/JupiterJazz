package muraca.JupiterJazz.view.components;

import muraca.JupiterJazz.model.Notes;
import muraca.JupiterJazz.model.session.Instrument;
import muraca.JupiterJazz.view.utils.RangeSlider;

import javax.swing.*;
import java.awt.*;

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

        JComboBox<String> chooseInstrument = new JComboBox<>(Instrument.INSTRUMENTS);
        chooseInstrument.setSelectedIndex(instrument.getId());
        chooseInstrument.addActionListener(l -> {
            int idx = chooseInstrument.getSelectedIndex();
            instrument.setId(idx);
            if (getParent() instanceof JTabbedPane) {
                JTabbedPane parent = (JTabbedPane) getParent();
                parent.setTitleAt(parent.getSelectedIndex(), Instrument.INSTRUMENTS[idx]);
            }
        });
        topPanel.add(chooseInstrument);
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

        JPanel rightPanel = new JPanel(null);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        instrumentPitchSlider = new RangeSlider(0, 127);
        instrumentPitchSlider.setValues(instrument.getMinPitch(), instrument.getMaxPitch());
        instrumentPitchSlider.addChangeListener(l -> {
            instrument.setMinPitch(instrumentPitchSlider.getValue());
            instrument.setMaxPitch(instrumentPitchSlider.getUpperValue());
            setInstrumentPitchLabelText();
        });
        rightPanel.add(instrumentPitchSlider);

        instrumentPitchLabel = new JLabel();
        setInstrumentPitchLabelText();
        rightPanel.add(instrumentPitchLabel);

        Dimension d = new Dimension(160, 32);
        rightPanel.setPreferredSize(d);
        rightPanel.setMinimumSize(d);
        rightPanel.setMaximumSize(d);
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
