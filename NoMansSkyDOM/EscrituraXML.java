package NoMansSkyDOM;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EscrituraXML {
    private EscrituraXML() {
    }

    public static int agregarEstacion(
            Document documento,
            String nombre,
            String galaxia,
            int distanciaNucleo,
            String gremio,
            String mejora,
            String naveEspecial) {
        Element raiz = documento.getDocumentElement();
        if (raiz == null) {
            raiz = documento.createElement("estaciones");
            documento.appendChild(raiz);
        }

        int id = obtenerIdDisponible(raiz);
        Element estacion = documento.createElement("estacion");
        estacion.setAttribute("id", String.valueOf(id));
        raiz.appendChild(estacion);

        crearElemento(documento, estacion, "nombre", nombre);
        Element elementoGalaxia = crearElemento(documento, estacion, "galaxia", galaxia);
        elementoGalaxia.setAttribute("distanciaNucleo", String.valueOf(distanciaNucleo));

        Element elementoGremio = crearElemento(documento, estacion, "gremio", gremio);
        if (mejora != null && !mejora.isBlank()) {
            elementoGremio.setAttribute("mejora", mejora);
        }
        if (naveEspecial != null && !naveEspecial.isBlank()) {
            crearElemento(documento, estacion, "naveEspecial", naveEspecial);
        }

        return id;
    }

    public static boolean borrarEstacion(Document documento, int id) {
        Element estacion = buscarEstacion(documento, id);
        if (estacion == null) {
            return false;
        }
        estacion.getParentNode().removeChild(estacion);
        return true;
    }

    public static boolean modificarEstacion(
            Document documento,
            int id,
            String nombre,
            String galaxia,
            int distanciaNucleo,
            String gremio,
            String mejora,
            String naveEspecial) {
        Element estacion = buscarEstacion(documento, id);
        if (estacion == null) {
            return false;
        }

        Element elementoNombre = obtenerOCrearElemento(documento, estacion, "nombre");
        elementoNombre.setTextContent(nombre);

        Element elementoGalaxia = obtenerOCrearElemento(documento, estacion, "galaxia");
        elementoGalaxia.setTextContent(galaxia);
        elementoGalaxia.setAttribute("distanciaNucleo", String.valueOf(distanciaNucleo));

        Element elementoGremio = obtenerOCrearElemento(documento, estacion, "gremio");
        elementoGremio.setTextContent(gremio);
        if (mejora == null || mejora.isBlank()) {
            elementoGremio.removeAttribute("mejora");
        } else {
            elementoGremio.setAttribute("mejora", mejora);
        }

        Element elementoNave = obtenerElemento(estacion, "naveEspecial");
        if (naveEspecial == null || naveEspecial.isBlank()) {
            if (elementoNave != null) {
                estacion.removeChild(elementoNave);
            }
        } else if (elementoNave == null) {
            crearElemento(documento, estacion, "naveEspecial", naveEspecial);
        } else {
            elementoNave.setTextContent(naveEspecial);
        }
        return true;
    }

    private static int obtenerIdDisponible(Element raiz) {
        NodeList estaciones = raiz.getElementsByTagName("estacion");
        int id = 0;

        // Busca desde 0 el primer hueco para poder reutilizar los ID borrados.
        while (true) {
            boolean ocupado = false;
            for (int i = 0; i < estaciones.getLength(); i++) {
                Element estacion = (Element) estaciones.item(i);
                if (String.valueOf(id).equals(estacion.getAttribute("id"))) {
                    ocupado = true;
                    break;
                }
            }
            if (!ocupado) {
                return id;
            }
            if (id == Integer.MAX_VALUE) {
                throw new IllegalStateException("No quedan identificadores disponibles.");
            }
            id++;
        }
    }

    private static Element buscarEstacion(Document documento, int id) {
        Element raiz = documento.getDocumentElement();
        if (raiz == null) {
            return null;
        }

        NodeList estaciones = raiz.getElementsByTagName("estacion");
        for (int i = 0; i < estaciones.getLength(); i++) {
            Element estacion = (Element) estaciones.item(i);
            if (String.valueOf(id).equals(estacion.getAttribute("id"))) {
                return estacion;
            }
        }
        return null;
    }

    private static Element crearElemento(Document documento, Element padre, String nombre, String valor) {
        Element elemento = documento.createElement(nombre);
        elemento.setTextContent(valor);
        padre.appendChild(elemento);
        return elemento;
    }

    private static Element obtenerOCrearElemento(Document documento, Element padre, String nombre) {
        Element elemento = obtenerElemento(padre, nombre);
        if (elemento == null) {
            elemento = documento.createElement(nombre);
            padre.appendChild(elemento);
        }
        return elemento;
    }

    private static Element obtenerElemento(Element padre, String nombre) {
        NodeList hijos = padre.getChildNodes();
        for (int i = 0; i < hijos.getLength(); i++) {
            Node hijo = hijos.item(i);
            if (hijo instanceof Element && nombre.equals(hijo.getNodeName())) {
                return (Element) hijo;
            }
        }
        return null;
    }
}
