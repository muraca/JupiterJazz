package muraca.JupiterJazz.model;

import lombok.Data;
import muraca.JupiterJazz.view.utils.ErrorHandler;
import muraca.JupiterJazz.view.utils.FileHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Data
public class Session {
    private String author = "";
    private String title = "";

    private int bpm = 60;
    private Fraction timeSignature = new Fraction(4,4);
    private int durationInMeasures = 60;

    private int pauseProbability = 25; //0 to 99

    private int minNoteDurationIndex = 0;
    private int maxNoteDurationIndex = Constants.EVENT_DURATIONS_SIZE - 1;

    private int minPauseDurationIndex = 4;
    private int maxPauseDurationIndex = Constants.EVENT_DURATIONS_SIZE - 3;

    private int minPitch = 1;
    private int maxPitch = 88;

    private Tonality tonality = new Tonality();

    private List<Instrument> instruments;

    public int getMinNoteDurationVTU() {
        return Constants.EVENT_DURATIONS_VTU.indexOf(minNoteDurationIndex);
    }

    public int getMaxNoteDurationVTU() {
        return Constants.EVENT_DURATIONS_VTU.indexOf(maxNoteDurationIndex);
    }

    public int getMinPauseDurationVTU() {
        return Constants.EVENT_DURATIONS_VTU.indexOf(minNoteDurationIndex);
    }

    public int getMaxPauseDurationVTU() {
        return Constants.EVENT_DURATIONS_VTU.indexOf(maxNoteDurationIndex);
    }

    public float getDurationInSeconds() {
        float beatDurationInSeconds = 60.f / bpm;
        float durationInSeconds = timeSignature.getNum() * beatDurationInSeconds * durationInMeasures;
        return Math.round(durationInSeconds * 10) / 10.f; //only one decimal digit
    }

    public static Session loadFromFile(String pathname) { return loadFromFile(new File(pathname)); }

    public static Session loadFromFile(File file) {
        Session s = new Session();

        try {
            Document doc = DocumentUtils.parse(file);
            doc.getDocumentElement().normalize();

            try { s.setAuthor(doc.getDocumentElement().getElementsByTagName("Author").item(0).getTextContent()); } catch (Exception e) { }
            try { s.setTitle(doc.getDocumentElement().getElementsByTagName("Title").item(0).getTextContent()); } catch (Exception e) { }

            try { s.setBpm(Integer.parseInt(doc.getDocumentElement().getElementsByTagName("BPM").item(0).getTextContent())); } catch (Exception e) { }
            try { s.setTimeSignature(Fraction.parseString(doc.getDocumentElement().getElementsByTagName("TimeSignature").item(0).getTextContent())); } catch (Exception e) { }
            try { s.setDurationInMeasures(Integer.parseInt(doc.getDocumentElement().getElementsByTagName("DurationInMeasures").item(0).getTextContent())); } catch (Exception e) { }

            try { s.setPauseProbability(Integer.parseInt(doc.getDocumentElement().getElementsByTagName("PauseProbability").item(0).getTextContent())); } catch (Exception e) { }

            try {
                Node noteDuration = doc.getDocumentElement().getElementsByTagName("NoteDuration").item(0);
                s.setMinNoteDurationIndex(Integer.parseInt(noteDuration.getAttributes().getNamedItem("min").getTextContent()));
                s.setMaxNoteDurationIndex(Integer.parseInt(noteDuration.getAttributes().getNamedItem("max").getTextContent()));
            } catch (Exception e) { }

            try {
                Node pauseDuration = doc.getDocumentElement().getElementsByTagName("PauseDuration").item(0);
                s.setMinPauseDurationIndex(Integer.parseInt(pauseDuration.getAttributes().getNamedItem("min").getTextContent()));
                s.setMaxPauseDurationIndex(Integer.parseInt(pauseDuration.getAttributes().getNamedItem("max").getTextContent()));
            } catch (Exception e) { }

            try {
                Node pitch = doc.getDocumentElement().getElementsByTagName("Pitch").item(0);
                s.setMinPitch(Integer.parseInt(pitch.getAttributes().getNamedItem("min").getTextContent()));
                s.setMaxPitch(Integer.parseInt(pitch.getAttributes().getNamedItem("max").getTextContent()));
            } catch (Exception e) { }

            try {
                Node tonality = doc.getDocumentElement().getElementsByTagName("Tonality").item(0);
                for (int i = 0; i < tonality.getChildNodes().getLength(); ++i) {
                    Node note = doc.getDocumentElement().getElementsByTagName("Note").item(i);
                    int id = Integer.parseInt(note.getAttributes().getNamedItem("id").getTextContent());
                    s.getTonality().enableNote(id);
                    s.getTonality().setNoteName(id, Integer.valueOf(note.getAttributes().getNamedItem("name_pos").getTextContent()));
                    s.getTonality().setNoteAccidental(id, Integer.valueOf(note.getAttributes().getNamedItem("accidental_pos").getTextContent()));
                }
            } catch (Exception e) { }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            ErrorHandler.xmlImportException(file.getName());
        }

        return s;
    }

    public void saveSessionAsFile(String pathname) { saveSessionAsFile(new File(pathname)); }

    public void saveSessionAsFile(File file) {
        try {
            Document doc = DocumentUtils.newDocument();
            Element root = doc.createElement("Session");
            doc.appendChild(root);

            Element e = doc.createElement("Author");
            e.setTextContent(author);
            root.appendChild(e);

            e = doc.createElement("Title");
            e.setTextContent(String.valueOf(title));
            root.appendChild(e);

            e = doc.createElement("BPM");
            e.setTextContent(String.valueOf(bpm));
            root.appendChild(e);

            e = doc.createElement("TimeSignature");
            e.setTextContent(timeSignature.toString());
            root.appendChild(e);

            e = doc.createElement("DurationInMeasures");
            e.setTextContent(String.valueOf(durationInMeasures));
            root.appendChild(e);

            e = doc.createElement("PauseProbability");
            e.setTextContent(String.valueOf(pauseProbability));
            root.appendChild(e);

            e = doc.createElement("NoteDuration");
            e.setAttribute("min", String.valueOf(minNoteDurationIndex));
            e.setAttribute("max", String.valueOf(maxNoteDurationIndex));
            root.appendChild(e);

            e = doc.createElement("PauseDuration");
            e.setAttribute("min", String.valueOf(minPauseDurationIndex));
            e.setAttribute("max", String.valueOf(maxPauseDurationIndex));
            root.appendChild(e);

            e = doc.createElement("Pitch");
            e.setAttribute("min", String.valueOf(minPitch));
            e.setAttribute("max", String.valueOf(maxPitch));
            root.appendChild(e);

            e = doc.createElement("Tonality");
            for (int i = 0; i < tonality.getNumberOfEnabledNotes(); ++i) {
                Element n = doc.createElement("Note");
                int id = tonality.getEnabledNoteId(i);
                if (id == -1)
                    break;
                n.setAttribute("id", String.valueOf(id));
                n.setAttribute("name", String.valueOf(tonality.getNoteName(id)));
                n.setAttribute("name_pos", String.valueOf(tonality.getNoteNamePos(id)));
                n.setAttribute("accidental", String.valueOf(tonality.getNoteAccidental(id)));
                n.setAttribute("accidental_pos", String.valueOf(tonality.getNoteAccidentalPos(id)));
                e.appendChild(n);
            }
            root.appendChild(e);

            DocumentUtils.transform(doc, file);

        } catch (FileNotFoundException e) {
            FileHandler.fileNotFoundExceptionInfo(file.getName());
        } catch (ParserConfigurationException | TransformerException e) {
            ErrorHandler.xmlExportException(file.getName());
        }
    }

    public void generateIEEE1599(File file) {
        IEEE1599XML.saveToXML(this, file);
    }
}
