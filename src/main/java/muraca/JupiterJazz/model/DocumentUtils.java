package muraca.JupiterJazz.model;

import org.w3c.dom.Document;
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

public class DocumentUtils {
    private static DocumentBuilder db = null;
    private static Transformer tr = null;

    public static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        if (db == null) db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        return db;
    }

    public static Document newDocument() throws ParserConfigurationException {
        return getDocumentBuilder().newDocument();
    }

    public static Document parse(File file) throws ParserConfigurationException, IOException, SAXException {
        return getDocumentBuilder().parse(file);
    }

    public static Transformer getTransformer() throws TransformerConfigurationException {
        if (tr == null) {
            tr = TransformerFactory.newInstance().newTransformer();

            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        }
        return tr;
    }

    public static void transform(Document dom, File file) throws TransformerException, FileNotFoundException {
        getTransformer().transform(new DOMSource(dom), new StreamResult(new FileOutputStream(file)));
    }
}
