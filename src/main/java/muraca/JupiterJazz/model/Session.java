package muraca.JupiterJazz.model;

import lombok.Data;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Data
public class Session {
    private static DocumentBuilder db = null;
    private static Transformer tr = null;

    private String author = "";
    private String title = "";

    private int bpm = 60;
    private TimeSignature timeSignature = new TimeSignature();
    private int durationInMeasures = 60;

    private int pauseProbability = 25; //0 to 99

    private int minNoteDuration = 0;
    private int maxNoteDuration = Note.DURATIONS.length-1;

    private int minPauseDuration = 4;
    private int maxPauseDuration = Note.DURATIONS.length-2;

    //TODO pitch and key

    private int minPitch = 1;
    private int maxPitch = 88;

    public float getDurationInSeconds() {
        float beatDurationInSeconds = 60.f / bpm * 4.f / timeSignature.getInverseBeatNoteLength();
        float durationInSeconds = timeSignature.getBeatsPerMeasure() * beatDurationInSeconds * durationInMeasures;
        return Math.round(durationInSeconds * 10) / 10.f; //only one decimal digit
    }

    private static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        if (db == null) db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        return db;
    }

    private static Transformer getTransformer() throws TransformerConfigurationException {
        if (tr == null) {
            tr = TransformerFactory.newInstance().newTransformer();

            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        }
        return tr;
    }

    public static Session loadFromFile(String pathname) { return loadFromFile(new File(pathname)); }

    public static Session loadFromFile(File file) {
        Session s = new Session();

        try {
            Document doc = getDocumentBuilder().parse(file);
            doc.getDocumentElement().normalize();

            try { s.setAuthor(doc.getDocumentElement().getElementsByTagName("Author").item(0).getTextContent()); } catch (Exception e) { }
            try { s.setTitle(doc.getDocumentElement().getElementsByTagName("Title").item(0).getTextContent()); } catch (Exception e) { }

            try { s.setBpm(Integer.parseInt(doc.getDocumentElement().getElementsByTagName("BPM").item(0).getTextContent())); } catch (Exception e) { }
            try { s.setTimeSignature(TimeSignature.parseString(doc.getDocumentElement().getElementsByTagName("TimeSignature").item(0).getTextContent())); } catch (Exception e) { }
            try { s.setDurationInMeasures(Integer.parseInt(doc.getDocumentElement().getElementsByTagName("DurationInMeasures").item(0).getTextContent())); } catch (Exception e) { }

            try { s.setPauseProbability(Integer.parseInt(doc.getDocumentElement().getElementsByTagName("PauseProbability").item(0).getTextContent())); } catch (Exception e) { }

            try {
                Node noteDuration = doc.getDocumentElement().getElementsByTagName("NoteDuration").item(0);
                s.setMinNoteDuration(Integer.parseInt(noteDuration.getAttributes().getNamedItem("min").getTextContent()));
                s.setMaxNoteDuration(Integer.parseInt(noteDuration.getAttributes().getNamedItem("max").getTextContent()));
            } catch (Exception e) { }

            try {
                Node pauseDuration = doc.getDocumentElement().getElementsByTagName("PauseDuration").item(0);
                s.setMinPauseDuration(Integer.parseInt(pauseDuration.getAttributes().getNamedItem("min").getTextContent()));
                s.setMaxPauseDuration(Integer.parseInt(pauseDuration.getAttributes().getNamedItem("max").getTextContent()));
            } catch (Exception e) { }

            //TODO key

            try {
                Node pitch = doc.getDocumentElement().getElementsByTagName("Pitch").item(0);
                s.setMinPitch(Integer.parseInt(pitch.getAttributes().getNamedItem("min").getTextContent()));
                s.setMaxPitch(Integer.parseInt(pitch.getAttributes().getNamedItem("max").getTextContent()));
            } catch (Exception e) { }


        } catch (ParserConfigurationException | SAXException | IOException e) {
            //TODO
        }

        return s;
    }

    public void saveSessionAsFile(String pathname) { saveSessionAsFile(new File(pathname)); }

    public void saveSessionAsFile(File file) { //TODO
        try {
            Document dom = getDocumentBuilder().newDocument();
            Element root = dom.createElement("Session");

            Element e = dom.createElement("Author");
            e.setTextContent(author);
            root.appendChild(e);

            e = dom.createElement("Title");
            e.setTextContent(String.valueOf(title));
            root.appendChild(e);

            e = dom.createElement("BPM");
            e.setTextContent(String.valueOf(bpm));
            root.appendChild(e);

            e = dom.createElement("TimeSignature");
            e.setTextContent(timeSignature.toString());
            root.appendChild(e);

            e = dom.createElement("DurationInMeasures");
            e.setTextContent(String.valueOf(durationInMeasures));
            root.appendChild(e);

            e = dom.createElement("PauseProbability");
            e.setTextContent(String.valueOf(pauseProbability));
            root.appendChild(e);

            e = dom.createElement("NoteDuration");
            e.setAttribute("min", String.valueOf(minNoteDuration));
            e.setAttribute("max", String.valueOf(maxNoteDuration));
            root.appendChild(e);

            e = dom.createElement("PauseDuration");
            e.setAttribute("min", String.valueOf(minPauseDuration));
            e.setAttribute("max", String.valueOf(maxPauseDuration));
            root.appendChild(e);

            //TODO key

            e = dom.createElement("Pitch");
            e.setAttribute("min", String.valueOf(minPitch));
            e.setAttribute("max", String.valueOf(maxPitch));
            root.appendChild(e);

            dom.appendChild(root);

            getTransformer().transform(new DOMSource(dom), new StreamResult(new FileOutputStream(file)));

        } catch (FileNotFoundException | TransformerException | ParserConfigurationException e) {
            //TODO
        }
    }

    public void generateIEEE1599() { //TODO
        System.out.println("generate ieee1599");
    }
}
