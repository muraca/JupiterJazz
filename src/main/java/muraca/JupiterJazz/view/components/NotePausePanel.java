package muraca.JupiterJazz.view.components;

import muraca.JupiterJazz.model.Constants;
import muraca.JupiterJazz.model.HasSession;
import muraca.JupiterJazz.model.session.Session;
import muraca.JupiterJazz.view.utils.RangeSlider;

import javax.swing.*;
import java.awt.*;

public class NotePausePanel extends JPanel implements HasSession {

    private Session session;

    private boolean isNote;

    private JSlider probabilitySlider;
    private RangeSlider durationSlider;

    private JSlider antiProbabilitySlider = null;

    public NotePausePanel(boolean isNote) {
        super(new FlowLayout());

        this.isNote = isNote;
        String type = isNote ? "Note" : "Pause";

        JLabel probabilityValueLabel = new JLabel("50%");
        probabilityValueLabel.setMinimumSize(new Dimension(36, 16));
        probabilityValueLabel.setMaximumSize(new Dimension(36, 16));
        probabilityValueLabel.setPreferredSize(new Dimension(36, 16));
        probabilitySlider = new JSlider();
        probabilitySlider.setSnapToTicks(true);
        probabilitySlider.setLabelTable(probabilitySlider.createStandardLabels(25));
        probabilitySlider.setPaintLabels(true);
        probabilitySlider.addChangeListener(l -> {
            if (session != null)
                session.setPauseProbability(isNote ? 100 - probabilitySlider.getValue() : probabilitySlider.getValue());

            probabilityValueLabel.setText(probabilitySlider.getValue() + "%");

           if (antiProbabilitySlider.getValue() != 100 - probabilitySlider.getValue())
               antiProbabilitySlider.setValue(100 - probabilitySlider.getValue());
        });
        JLabel probabilityLabel = new JLabel(type + " probability");
        probabilityLabel.setLabelFor(probabilitySlider);
        add(probabilityLabel);
        add(probabilitySlider);
        add(probabilityValueLabel);

        JLabel durationValuesLabel = new JLabel("");
        durationValuesLabel.setMinimumSize(new Dimension(160, 16));
        durationValuesLabel.setMaximumSize(new Dimension(160, 16));
        durationValuesLabel.setPreferredSize(new Dimension(160, 16));
        durationSlider = new RangeSlider(0, Constants.EVENT_DURATIONS_SIZE-1);
        durationSlider.addChangeListener(e -> {
            if (isNote) {
                session.setMinNoteDurationIndex(durationSlider.getValue());
                session.setMaxNoteDurationIndex(durationSlider.getUpperValue());
            }
            else {
                session.setMinPauseDurationIndex(durationSlider.getValue());
                session.setMaxPauseDurationIndex(durationSlider.getUpperValue());
            }
            durationValuesLabel.setText(
                    "min: " + Constants.EVENT_DURATIONS_FRAC.get(durationSlider.getValue()) + ", " +
                    "max: " + Constants.EVENT_DURATIONS_FRAC.get(durationSlider.getUpperValue()));
        });
        JLabel durationLabel = new JLabel(type + " duration");
        durationLabel.setLabelFor(durationSlider);
        add(durationLabel);
        add(durationSlider);
        add(durationValuesLabel);

        setAlignmentX(CENTER_ALIGNMENT);
    }

    @Override
    public void setSession(Session s) {
        session = s;

        if (isNote) {
            probabilitySlider.setValue(100 - session.getPauseProbability());
            durationSlider.setValues(session.getMinNoteDurationIndex(), session.getMaxNoteDurationIndex());
        }
        else {
            probabilitySlider.setValue(session.getPauseProbability());
            durationSlider.setValues(session.getMinPauseDurationIndex(), session.getMaxPauseDurationIndex());
        }

    }

    public void setAntiPanel(NotePausePanel antiPanel) {
        if (this.antiProbabilitySlider != antiPanel.probabilitySlider) {
            this.antiProbabilitySlider = antiPanel.probabilitySlider;
            if (antiPanel.antiProbabilitySlider != this.probabilitySlider) {
                antiPanel.antiProbabilitySlider = this.probabilitySlider;
            }
        }
    }

    public static boolean NOTE = true;
    public static boolean PAUSE = false;
}
