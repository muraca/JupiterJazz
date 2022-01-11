package muraca.JupiterJazz.view.components;

import muraca.JupiterJazz.model.HasSession;
import muraca.JupiterJazz.model.session.Session;
import muraca.JupiterJazz.view.components.keyboard.KeyboardComponent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SessionPanel extends JPanel implements HasSession {

    private Session session;

    private ArrayList<HasSession> hasSessionArrayList = new ArrayList<>();

    public SessionPanel() {
        this(new Session());
    }
    public SessionPanel(Session session) {
        super(null);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(new MetadataPanel());
        add(new TempoPanel());

        NotePausePanel notePanel = new NotePausePanel(NotePausePanel.NOTE);
        NotePausePanel pausePanel = new NotePausePanel(NotePausePanel.PAUSE);
        notePanel.setAntiPanel(pausePanel);
        add(notePanel);
        add(pausePanel);

        add(new KeyboardComponent());

        add(new InstrumentsWrapperPanel());
        add(new SavePanel());

        setSession(session);
    }

    @Override
    public Component add(Component comp) {
        if (comp instanceof HasSession) {
            hasSessionArrayList.add((HasSession) comp);
        }
        super.add(comp);
        return comp;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;

        for (HasSession h: hasSessionArrayList)
            h.setSession(session);

        revalidate();
        repaint();
    }

    public void saveSessionAsFile() {
        session.saveSessionAsFile();
    }

    public void generateIEEE1599() {
        session.generateIEEE1599();
    }
}
