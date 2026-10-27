package Ejercicios.Ficheros.XML_DOM.Combinado;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EjemploDOM4 {
    private static final Path ENTRADA = Paths.get(
            "Ejercicios", "Ficheros", "XML_DOM", "Combinado", "datos", "alumnos.xml");
    private static final Path SALIDA = Paths.get(
            "Ejercicios", "Ficheros", "XML_DOM", "Combinado", "resultados", "alumnosActualizados.xml");

    public static void main(String[] args) {
        try {
            Document documento = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(ENTRADA.toFile());
            Element raiz = documento.getDocumentElement();

            Element alumno = documento.createElement("alumno");
            alumno.setAttribute("id", String.valueOf(siguienteId(raiz)));
            agregarTexto(documento, alumno, "nombre", "Lucía");
            agregarTexto(documento, alumno, "ciclo", "DAM");
            agregarTexto(documento, alumno, "curso", "2");
            raiz.appendChild(alumno);

            java.nio.file.Files.createDirectories(SALIDA.getParent());
            guardar(documento, SALIDA);
            System.out.println("Nuevo alumno añadido. XML guardado en " + SALIDA);
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
            System.err.println("No se pudo actualizar el XML: " + e.getMessage());
        }
    }

    private static int siguienteId(Element raiz) {
        NodeList alumnos = raiz.getElementsByTagName("alumno");
        int mayorId = 0;

        // Se recorre la lista para encontrar el ID más alto y no repetirlo.
        for (int i = 0; i < alumnos.getLength(); i++) {
            Element alumno = (Element) alumnos.item(i);
            try {
                int id = Integer.parseInt(alumno.getAttribute("id"));
                mayorId = Math.max(mayorId, id);
            } catch (NumberFormatException e) {
                System.out.println("Aviso: se ignora un ID de alumno no numérico.");
            }
        }
        return mayorId + 1;
    }

    private static void agregarTexto(Document documento, Element padre, String etiqueta, String valor) {
        Element elemento = documento.createElement(etiqueta);
        elemento.setTextContent(valor);
        padre.appendChild(elemento);
    }

    private static void guardar(Document documento, Path ruta) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(new DOMSource(documento), new StreamResult(ruta.toFile()));
    }
}
