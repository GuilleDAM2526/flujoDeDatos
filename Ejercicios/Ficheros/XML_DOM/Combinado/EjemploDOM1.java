/*
 * Lee los libros de un XML con DOM y escribe sus títulos y autores
 * en un archivo de texto.
 */
package Ejercicios.Ficheros.XML_DOM.Combinado;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Ejemplo de recorrido de nodos XML y escritura de los datos seleccionados.
 */
public class EjemploDOM1 {
    private static final Path ENTRADA = Paths.get(
            "Ejercicios", "Ficheros", "XML_DOM", "Combinado", "datos", "libros.xml");
    private static final Path SALIDA = Paths.get(
            "Ejercicios", "Ficheros", "XML_DOM", "Combinado", "resultados", "catalogoLibros.txt");

    /**
     * Carga el XML, recorre los libros y guarda sus datos principales en TXT.
     */
    public static void main(String[] args) {
        try {
            Document documento = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(ENTRADA.toFile());
            NodeList libros = documento.getDocumentElement().getElementsByTagName("libro");

            Files.createDirectories(SALIDA.getParent());
            try (BufferedWriter escritor = Files.newBufferedWriter(SALIDA, StandardCharsets.UTF_8)) {
                for (int i = 0; i < libros.getLength(); i++) {
                    Element libro = (Element) libros.item(i);
                    escritor.write(textoDe(libro, "titulo") + " - " + textoDe(libro, "autor"));
                    escritor.newLine();
                }
            }
            System.out.println("Catálogo de libros guardado en " + SALIDA);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.err.println("No se pudo procesar el XML: " + e.getMessage());
        }
    }

    /**
     * Obtiene el texto de una etiqueta hija o devuelve un aviso si no existe.
     */
    private static String textoDe(Element padre, String etiqueta) {
        NodeList elementos = padre.getElementsByTagName(etiqueta);
        if (elementos.getLength() == 0) {
            return "Sin " + etiqueta;
        }
        return elementos.item(0).getTextContent().trim();
    }
}
