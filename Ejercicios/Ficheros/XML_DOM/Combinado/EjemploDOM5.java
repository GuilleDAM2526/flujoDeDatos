/*
 * Lee un XML de tareas, elimina del documento las tareas completadas,
 * guarda las pendientes en otro XML y escribe las eliminadas en un TXT.
 */
package Ejercicios.Ficheros.XML_DOM.Combinado;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Ejemplo de lectura, eliminación de nodos DOM y escritura de dos resultados.
 */
public class EjemploDOM5 {
    private static final Path ENTRADA = Paths.get(
            "Ejercicios", "Ficheros", "XML_DOM", "Combinado", "datos", "tareas.xml");
    private static final Path SALIDA_XML = Paths.get(
            "Ejercicios", "Ficheros", "XML_DOM", "Combinado", "resultados", "tareasPendientes.xml");
    private static final Path SALIDA_TXT = Paths.get(
            "Ejercicios", "Ficheros", "XML_DOM", "Combinado", "resultados", "tareasEliminadas.txt");

    /**
     * Separa las tareas completadas de las pendientes y guarda ambos resultados.
     */
    public static void main(String[] args) {
        try {
            Document documento = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(ENTRADA.toFile());
            Element raiz = documento.getDocumentElement();
            NodeList tareas = raiz.getElementsByTagName("tarea");
            List<String> eliminadas = new ArrayList<>();

            // NodeList cambia cuando se quitan elementos; primero guardamos los nodos a eliminar.
            List<Element> completadas = new ArrayList<>();
            for (int i = 0; i < tareas.getLength(); i++) {
                Element tarea = (Element) tareas.item(i);
                if ("completada".equalsIgnoreCase(textoDe(tarea, "estado"))) {
                    eliminadas.add(textoDe(tarea, "descripcion"));
                    completadas.add(tarea);
                }
            }

            for (Element tarea : completadas) {
                Node padre = tarea.getParentNode();
                padre.removeChild(tarea);
            }

            Files.createDirectories(SALIDA_XML.getParent());
            guardar(documento, SALIDA_XML);
            Files.write(SALIDA_TXT, eliminadas, StandardCharsets.UTF_8);

            System.out.println("Tareas completadas eliminadas: " + eliminadas.size());
            System.out.println("Tareas pendientes guardadas en " + SALIDA_XML);
            System.out.println("Descripciones eliminadas guardadas en " + SALIDA_TXT);
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
            System.err.println("No se pudo procesar el XML: " + e.getMessage());
        }
    }

    /**
     * Devuelve el texto de una etiqueta descendiente o texto vacío si falta.
     */
    private static String textoDe(Element padre, String etiqueta) {
        NodeList elementos = padre.getElementsByTagName(etiqueta);
        return elementos.getLength() == 0 ? "" : elementos.item(0).getTextContent().trim();
    }

    /**
     * Transforma el árbol DOM resultante en un archivo XML.
     */
    private static void guardar(Document documento, Path ruta) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(new DOMSource(documento), new StreamResult(ruta.toFile()));
    }
}
