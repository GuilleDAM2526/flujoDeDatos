/*
 * Lee un XML de alumnos, calcula un ID nuevo, añade un alumno al DOM
 * y guarda el resultado sin modificar el archivo de entrada.
 */
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

/**
 * Ejemplo de inserción de un nuevo elemento en un documento XML.
 */
public class EjemploDOM4 {
    private static final Path ENTRADA = Paths.get(
            "Ejercicios", "Ficheros", "XML_DOM", "Combinado", "datos", "alumnos.xml");
    private static final Path SALIDA = Paths.get(
            "Ejercicios", "Ficheros", "XML_DOM", "Combinado", "resultados", "alumnosActualizados.xml");

    /**
     * Crea el nodo alumno con sus datos y lo añade a la raíz del documento.
     */
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

    /**
     * Recorre los alumnos y devuelve el número siguiente al ID más alto.
     */
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

    /**
     * Crea una etiqueta de texto y la añade como hija del elemento indicado.
     */
    private static void agregarTexto(Document documento, Element padre, String etiqueta, String valor) {
        Element elemento = documento.createElement(etiqueta);
        elemento.setTextContent(valor);
        padre.appendChild(elemento);
    }

    /**
     * Guarda el árbol DOM en formato XML con sangrado para facilitar su lectura.
     */
    private static void guardar(Document documento, Path ruta) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(new DOMSource(documento), new StreamResult(ruta.toFile()));
    }
}
