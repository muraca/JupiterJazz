package muraca.JupiterJazz.view.components;

import muraca.JupiterJazz.model.Constants;
import muraca.JupiterJazz.model.session.Instrument;
import muraca.JupiterJazz.model.session.Session;
import muraca.JupiterJazz.view.utils.ButtonTabComponent;
import muraca.JupiterJazz.model.HasSession;

import javax.swing.*;
import java.awt.*;

public class InstrumentsWrapperPanel extends JPanel implements HasSession {

    private Session session;

    private JTabbedPane instrumentsPanel;

    public InstrumentsWrapperPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton addInstrumentButton = new JButton("+ Add Instrument");
        add(addInstrumentButton);
        addInstrumentButton.addActionListener(l -> createAndAddInstrumentPanel(session.createNewInstrument()));
        addInstrumentButton.setAlignmentX(CENTER_ALIGNMENT);

        instrumentsPanel = new JTabbedPane();
        add(instrumentsPanel);
        instrumentsPanel.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        instrumentsPanel.setAlignmentX(CENTER_ALIGNMENT);

        Dimension d = new Dimension(Constants.WIDTH / 2, 180);
        setPreferredSize(d);
        setMinimumSize(d);
        setMaximumSize(d);

        repaint();
    }

    @Override
    public void setSession(Session s) {
        session = s;

        instrumentsPanel.removeAll();

        for (Instrument i: session.getInstruments())
            createAndAddInstrumentPanel(i);
    }

    private void createAndAddInstrumentPanel(Instrument i) {
        InstrumentPanel instrumentPanel = new InstrumentPanel(i);
        instrumentsPanel.add(instrumentPanel);
        int idx = instrumentsPanel.indexOfComponent(instrumentPanel);
        instrumentsPanel.setTitleAt(idx, i.getName());
        instrumentsPanel.setTabComponentAt(idx, new ButtonTabComponent(instrumentsPanel, true));
    }
}
