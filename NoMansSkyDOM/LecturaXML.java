package NoMansSkyDOM;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LecturaXML {
    private LecturaXML() {
    }

    public static List<Element> todasLasEstaciones(Document documento) {
        return filtrar(documento, estacion -> true);
    }

    public static List<Element> buscarPorId(Document documento, int id) {
        return filtrar(documento, estacion -> String.valueOf(id).equals(estacion.getAttribute("id")));
    }

    public static List<Element> buscarPorNombre(Document documento, String nombre) {
        String consulta = normalizar(nombre);
        return filtrar(documento, estacion -> normalizar(obtenerTexto(estacion, "nombre")).contains(consulta));
    }

    public static List<Element> buscarPorGalaxia(Document documento, String galaxia) {
        String consulta = normalizar(galaxia);
        return filtrar(documento, estacion -> normalizar(obtenerTexto(estacion, "galaxia")).contains(consulta));
    }

    public static List<Element> buscarPorGremio(Document documento, String gremio) {
        return filtrar(documento,
                estacion -> obtenerTexto(estacion, "gremio").equalsIgnoreCase(gremio));
    }

    public static List<Element> buscarPorMejora(Document documento, String mejora) {
        return filtrar(documento, estacion -> {
            Element gremio = obtenerElemento(estacion, "gremio");
            if (gremio == null) {
                return mejora == null || mejora.isEmpty();
            }
            String mejoraGuardada = gremio.getAttribute("mejora");
            if (mejora == null || mejora.isEmpty()) {
                return mejoraGuardada.isBlank();
            }
            return mejoraGuardada.equalsIgnoreCase(mejora);
        });
    }

    public static void mostrarEstaciones(List<Element> estaciones) {
        if (estaciones.isEmpty()) {
            System.out.println("No se encontraron estaciones.");
            return;
        }
        for (Element estacion : estaciones) {
            imprimirEstacion(estacion);
        }
    }

    private static List<Element> filtrar(Document documento, Predicate<Element> criterio) {
        List<Element> resultados = new ArrayList<>();
        Element raiz = documento.getDocumentElement();
        if (raiz == null) {
            return resultados;
        }

        NodeList estaciones = raiz.getElementsByTagName("estacion");
        for (int i = 0; i < estaciones.getLength(); i++) {
            Element estacion = (Element) estaciones.item(i);
            if (criterio.test(estacion)) {
                resultados.add(estacion);
            }
        }
        return resultados;
    }

    private static void imprimirEstacion(Element estacion) {
        Element galaxia = obtenerElemento(estacion, "galaxia");
        Element gremio = obtenerElemento(estacion, "gremio");
        Element nave = obtenerElemento(estacion, "naveEspecial");
        String mejora = gremio == null || gremio.getAttribute("mejora").isBlank()
                ? "Sin mejora"
                : gremio.getAttribute("mejora");

        System.out.println("\nID: " + estacion.getAttribute("id"));
        System.out.println("Nombre: " + obtenerTexto(estacion, "nombre"));
        System.out.println("Galaxia: " + (galaxia == null ? "" : galaxia.getTextContent().trim()));
        if (galaxia != null && galaxia.hasAttribute("distanciaNucleo")) {
            System.out.println("Distancia al núcleo: " + galaxia.getAttribute("distanciaNucleo"));
        }
        System.out.println("Gremio: " + (gremio == null ? "" : gremio.getTextContent().trim()));
        System.out.println("Mejora: " + mejora);
        System.out.println("Nave especial: "
                + (nave == null || nave.getTextContent().isBlank()
                        ? "Ninguna"
                        : nave.getTextContent().trim()));
    }

    private static String obtenerTexto(Element padre, String nombre) {
        Element elemento = obtenerElemento(padre, nombre);
        return elemento == null ? "" : elemento.getTextContent().trim();
    }

    private static Element obtenerElemento(Element padre, String nombre) {
        if (padre == null) {
            return null;
        }
        NodeList hijos = padre.getChildNodes();
        for (int i = 0; i < hijos.getLength(); i++) {
            Node hijo = hijos.item(i);
            if (hijo instanceof Element && nombre.equals(hijo.getNodeName())) {
                return (Element) hijo;
            }
        }
        return null;
    }

    private static String normalizar(String texto) {
        return texto.toLowerCase(Locale.ROOT);
    }
}
