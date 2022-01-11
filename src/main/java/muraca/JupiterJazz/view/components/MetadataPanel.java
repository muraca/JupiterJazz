package muraca.JupiterJazz.view.components;

import muraca.JupiterJazz.model.HasSession;
import muraca.JupiterJazz.model.session.Session;
import muraca.JupiterJazz.view.utils.SimpleDocumentListener;

import javax.swing.*;
import java.awt.*;

public class MetadataPanel extends JPanel implements HasSession {

    private Session session;

    private JTextField authorField;
    private JTextField titleField;

    public MetadataPanel() {
        super(new FlowLayout());

        authorField = new JTextField(25);
        authorField.getDocument().addDocumentListener((SimpleDocumentListener) e ->
                session.setAuthor(authorField.getText()));
        JLabel authorLabel = new JLabel("Author");
        authorLabel.setLabelFor(authorField);
        add(authorLabel);
        add(authorField);

        titleField = new JTextField(25);
        titleField.getDocument().addDocumentListener((SimpleDocumentListener) e ->
                session.setTitle(titleField.getText()));
        JLabel titleLabel = new JLabel("Title");
        titleLabel.setLabelFor(titleField);
        add(titleLabel);
        add(titleField);

        setAlignmentX(CENTER_ALIGNMENT);
    }

    @Override
    public void setSession(Session s) {
        session = s;

        authorField.setText(session.getAuthor());
        titleField.setText(session.getTitle());
    }
}
