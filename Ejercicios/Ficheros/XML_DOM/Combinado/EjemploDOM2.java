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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EjemploDOM2 {
    private static final Path ENTRADA = Paths.get(
            "Ejercicios", "Ficheros", "XML_DOM", "Combinado", "datos", "productos.xml");
    private static final Path SALIDA = Paths.get(
            "Ejercicios", "Ficheros", "XML_DOM", "Combinado", "resultados", "medicinas.xml");
    private static final String CATEGORIA_BUSCADA = "Medicina";

    public static void main(String[] args) {
        try {
            Document original = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(ENTRADA.toFile());
            Document resultado = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .newDocument();
            Element raizResultado = resultado.createElement("productos");
            resultado.appendChild(raizResultado);

            NodeList productos = original.getDocumentElement().getElementsByTagName("producto");
            int copiados = 0;
            for (int i = 0; i < productos.getLength(); i++) {
                Element producto = (Element) productos.item(i);
                if (CATEGORIA_BUSCADA.equalsIgnoreCase(textoDe(producto, "categoria"))) {
                    // Importamos una copia del nodo porque un nodo solo puede pertenecer a un documento.
                    Node copia = resultado.importNode(producto, true);
                    raizResultado.appendChild(copia);
                    copiados++;
                }
            }

            java.nio.file.Files.createDirectories(SALIDA.getParent());
            guardar(resultado, SALIDA);
            System.out.println("Productos copiados: " + copiados);
            System.out.println("XML filtrado guardado en " + SALIDA);
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
            System.err.println("No se pudo procesar el XML: " + e.getMessage());
        }
    }

    private static String textoDe(Element padre, String etiqueta) {
        NodeList elementos = padre.getElementsByTagName(etiqueta);
        return elementos.getLength() == 0 ? "" : elementos.item(0).getTextContent().trim();
    }

    private static void guardar(Document documento, Path ruta) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(new DOMSource(documento), new StreamResult(ruta.toFile()));
    }
}
