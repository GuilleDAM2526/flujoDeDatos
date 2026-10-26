package NoMansSkyDOM;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class CrearXML {
    private CrearXML() {
    }

    public static void crearSiNoExiste(Path ruta)
            throws IOException, ParserConfigurationException, TransformerException {
        if (Files.exists(ruta)) {
            return;
        }

        Document documento = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
        documento.appendChild(documento.createElement("estaciones"));
        guardar(documento, ruta);
    }

    public static Document cargar(Path ruta)
            throws IOException, ParserConfigurationException, SAXException {
        if (!Files.exists(ruta)) {
            throw new IOException("No existe el archivo XML: " + ruta);
        }
        return DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(ruta.toFile());
    }

    public static void guardar(Document documento, Path ruta)
            throws IOException, TransformerException {
        Path carpeta = ruta.getParent();
        if (carpeta != null) {
            Files.createDirectories(carpeta);
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(new DOMSource(documento), new StreamResult(ruta.toFile()));
    }
}
