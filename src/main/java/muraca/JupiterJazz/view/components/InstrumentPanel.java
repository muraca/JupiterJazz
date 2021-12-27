package muraca.JupiterJazz.view.components;

import muraca.JupiterJazz.model.Notes;
import muraca.JupiterJazz.model.instruments.Instrument;
import muraca.JupiterJazz.model.session.Session;
import muraca.JupiterJazz.view.utils.RangeSlider;

import javax.swing.*;
import java.awt.*;

public class InstrumentPanel extends JPanel {
    private Instrument instrument;

    private JButton enableButton;
    private RangeSlider instrumentPitchSlider;
    private JLabel instrumentPitchLabel;

    public InstrumentPanel(Session s, int i) {
        super(new FlowLayout());

        instrument = s.getInstruments().get(i);

        enableButton = new JButton();
        setEnableButtonText();
        enableButton.addActionListener(l -> {
            toggleEnabled();
            setEnableButtonText();
        });
        add(enableButton);
        enableButton.setPreferredSize(new Dimension(128, 64));

        instrumentPitchSlider = new RangeSlider(instrument.getMinPitch(), instrument.getMaxPitch());
        instrumentPitchSlider.setValues(instrument.getSelectedMinPitch(), instrument.getSelectedMaxPitch());
        instrumentPitchSlider.addChangeListener(l -> {
            instrument.setSelectedMinPitch(instrumentPitchSlider.getValue());
            instrument.setSelectedMaxPitch(instrumentPitchSlider.getUpperValue());
            setInstrumentPitchLabelText();
        });
        add(instrumentPitchSlider);

        instrumentPitchLabel = new JLabel();
        setInstrumentPitchLabelText();
        add(instrumentPitchLabel);
        instrumentPitchLabel.setPreferredSize(new Dimension(160, 16));

    }

    private void toggleEnabled() {
        instrument.setEnabled(!instrument.isEnabled());
    }

    private void setEnableButtonText() {
        enableButton.setText((instrument.isEnabled() ? "Disable " : "Enable ") + instrument.getName());
    }

    private void setInstrumentPitchLabelText() {
        instrumentPitchLabel.setText("min: " + Notes.midiToEnglish(instrument.getSelectedMinPitch()) +
                ", max: " + Notes.midiToEnglish(instrument.getSelectedMaxPitch()));
    }
}
