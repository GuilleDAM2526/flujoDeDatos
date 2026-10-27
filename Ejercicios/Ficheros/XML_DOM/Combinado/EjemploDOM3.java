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

public class EjemploDOM3 {
    private static final Path ENTRADA = Paths.get(
            "Ejercicios", "Ficheros", "XML_DOM", "Combinado", "datos", "productos.xml");
    private static final Path SALIDA = Paths.get(
            "Ejercicios", "Ficheros", "XML_DOM", "Combinado", "resultados", "productosActualizados.xml");
    private static final String ID_BUSCADO = "P02";
    private static final int NUEVO_STOCK = 25;

    public static void main(String[] args) {
        try {
            Document documento = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(ENTRADA.toFile());
            NodeList productos = documento.getDocumentElement().getElementsByTagName("producto");
            boolean encontrado = false;

            for (int i = 0; i < productos.getLength(); i++) {
                Element producto = (Element) productos.item(i);
                if (ID_BUSCADO.equals(producto.getAttribute("id"))) {
                    Element stock = primerHijo(producto, "stock");
                    if (stock == null) {
                        stock = documento.createElement("stock");
                        producto.appendChild(stock);
                    }
                    stock.setTextContent(String.valueOf(NUEVO_STOCK));
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                System.out.println("No existe un producto con ID " + ID_BUSCADO + ".");
                return;
            }

            java.nio.file.Files.createDirectories(SALIDA.getParent());
            guardar(documento, SALIDA);
            System.out.println("Stock actualizado y guardado en " + SALIDA);
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
            System.err.println("No se pudo modificar el XML: " + e.getMessage());
        }
    }

    private static Element primerHijo(Element padre, String etiqueta) {
        NodeList elementos = padre.getElementsByTagName(etiqueta);
        for (int i = 0; i < elementos.getLength(); i++) {
            if (elementos.item(i).getParentNode() == padre) {
                return (Element) elementos.item(i);
            }
        }
        return null;
    }

    private static void guardar(Document documento, Path ruta) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(new DOMSource(documento), new StreamResult(ruta.toFile()));
    }
}
