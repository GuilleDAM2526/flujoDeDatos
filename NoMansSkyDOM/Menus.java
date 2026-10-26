package NoMansSkyDOM;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Menus {
    private final Scanner scanner;
    private final Path rutaXml;

    public Menus(Scanner scanner, Path rutaXml) {
        this.scanner = scanner;
        this.rutaXml = rutaXml;
    }

    public void iniciar()
            throws IOException, ParserConfigurationException, SAXException, TransformerException {
        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion("Elige una opción: ");

            switch (opcion) {
                case -1:
                case 5:
                    salir = true;
                    break;
                case 0:
                    break;
                case 1:
                    menuLectura();
                    break;
                case 2:
                    introducirEstacion();
                    break;
                case 3:
                    borrarEstacion();
                    break;
                case 4:
                    modificarEstacion();
                    break;
                default:
                    System.out.println("Opción no válida. Elige un número del 0 al 5.");
            }
        }
        System.out.println("Hasta pronto.");
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n=== ESTACIONES DE NO MAN'S SKY ===");
        System.out.println("0. Mostrar menú");
        System.out.println("1. Consultar estaciones");
        System.out.println("2. Añadir una estación");
        System.out.println("3. Borrar una estación");
        System.out.println("4. Modificar una estación");
        System.out.println("5. Salir");
    }

    private void menuLectura() throws IOException, ParserConfigurationException, SAXException {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n=== CONSULTAR ESTACIONES ===");
            System.out.println("1. Buscar por número");
            System.out.println("2. Buscar por nombre");
            System.out.println("3. Buscar por galaxia");
            System.out.println("4. Buscar por gremio");
            System.out.println("5. Buscar por mejora");
            System.out.println("6. Mostrar todas las estaciones");
            System.out.println("7. Volver");

            int opcion = leerOpcion("Elige una opción: ");
            switch (opcion) {
                case -1:
                case 7:
                    volver = true;
                    break;
                case 1:
                    buscarPorId();
                    break;
                case 2:
                    buscarPorNombre();
                    break;
                case 3:
                    buscarPorGalaxia();
                    break;
                case 4:
                    buscarPorGremio();
                    break;
                case 5:
                    buscarPorMejora();
                    break;
                case 6:
                    LecturaXML.mostrarEstaciones(
                            LecturaXML.todasLasEstaciones(CrearXML.cargar(rutaXml)));
                    break;
                default:
                    System.out.println("Opción no válida. Elige un número del 1 al 7.");
            }
        }
    }

    private void buscarPorId() throws IOException, ParserConfigurationException, SAXException {
        int id = leerOpcion("Introduce el ID de la estación: ");
        if (id == -1) {
            return;
        }
        if (id < 0) {
            System.out.println("El ID no puede ser negativo.");
            return;
        }
        mostrar(LecturaXML.buscarPorId(CrearXML.cargar(rutaXml), id));
    }

    private void buscarPorNombre() throws IOException, ParserConfigurationException, SAXException {
        String nombre = leerTexto("Introduce el nombre (o parte del nombre): ", false);
        if (nombre != null) {
            mostrar(LecturaXML.buscarPorNombre(CrearXML.cargar(rutaXml), nombre));
        }
    }

    private void buscarPorGalaxia() throws IOException, ParserConfigurationException, SAXException {
        String galaxia = leerTexto("Introduce la galaxia (o parte del nombre): ", false);
        if (galaxia != null) {
            mostrar(LecturaXML.buscarPorGalaxia(CrearXML.cargar(rutaXml), galaxia));
        }
    }

    private void buscarPorGremio() throws IOException, ParserConfigurationException, SAXException {
        String gremio = leerSeleccion("Selecciona el gremio:",
                new String[]{"Exploradores", "Mercaderes", "Mercenarios"}, false);
        if (gremio != null) {
            mostrar(LecturaXML.buscarPorGremio(CrearXML.cargar(rutaXml), gremio));
        }
    }

    private void buscarPorMejora() throws IOException, ParserConfigurationException, SAXException {
        String mejora = leerSeleccion("Selecciona la mejora:",
                new String[]{"Multiherramienta", "Exotraje", "Nave", "Fragata", "Carguero"}, true);
        if (mejora != null) {
            mostrar(LecturaXML.buscarPorMejora(CrearXML.cargar(rutaXml), mejora));
        }
    }

    private void introducirEstacion()
            throws IOException, ParserConfigurationException, SAXException, TransformerException {
        System.out.println("\n=== AÑADIR ESTACIÓN ===");
        String nombre = leerTexto("Nombre: ", false);
        if (nombre == null) {
            return;
        }
        String galaxia = leerTexto("Galaxia: ", false);
        if (galaxia == null) {
            return;
        }
        Integer distancia = leerEnteroNoNegativo("Distancia al núcleo: ");
        if (distancia == null) {
            return;
        }
        String gremio = leerTexto("Gremio: ", false);
        if (gremio == null) {
            return;
        }
        String mejora = leerTexto("Mejora (opcional, pulsa Intro para omitir): ", true);
        if (mejora == null) {
            return;
        }
        String nave = leerTexto("Nave especial (opcional, pulsa Intro para omitir): ", true);
        if (nave == null) {
            return;
        }

        Document documento = CrearXML.cargar(rutaXml);
        int id = EscrituraXML.agregarEstacion(
                documento, nombre, galaxia, distancia, gremio, mejora, nave);
        CrearXML.guardar(documento, rutaXml);
        System.out.println("Estación añadida correctamente con el ID " + id + ".");
    }

    private void borrarEstacion()
            throws IOException, ParserConfigurationException, SAXException, TransformerException {
        Document documento = CrearXML.cargar(rutaXml);
        int id = leerOpcion("Introduce el número de la estación que quieres borrar: ");
        if (id == -1) {
            return;
        }
        if (id < 0) {
            System.out.println("El ID no puede ser negativo.");
            return;
        }
        if (EscrituraXML.borrarEstacion(documento, id)) {
            CrearXML.guardar(documento, rutaXml);
            System.out.println("Estación borrada correctamente.");
        } else {
            System.out.println("No se encontró ninguna estación con ese número.");
        }
    }

    private void modificarEstacion()
            throws IOException, ParserConfigurationException, SAXException, TransformerException {
        Document documento = CrearXML.cargar(rutaXml);
        int id = leerOpcion("Introduce el número de la estación que quieres modificar: ");
        if (id == -1) {
            return;
        }
        if (id < 0) {
            System.out.println("El ID no puede ser negativo.");
            return;
        }
        Element estacion = buscarEstacion(documento, id);
        if (estacion == null) {
            System.out.println("No se encontró ninguna estación con ese número.");
            return;
        }

        System.out.println("Introduce los nuevos datos de la estación " + id + ":");
        String nombre = leerTexto("Nombre: ", false);
        if (nombre == null) {
            return;
        }
        String galaxia = leerTexto("Galaxia: ", false);
        if (galaxia == null) {
            return;
        }
        Integer distancia = leerEnteroNoNegativo("Distancia al núcleo: ");
        if (distancia == null) {
            return;
        }
        String gremio = leerTexto("Gremio: ", false);
        if (gremio == null) {
            return;
        }
        String mejora = leerTexto("Mejora (opcional, pulsa Intro para omitir): ", true);
        if (mejora == null) {
            return;
        }
        String nave = leerTexto("Nave especial (opcional, pulsa Intro para omitir): ", true);
        if (nave == null) {
            return;
        }

        EscrituraXML.modificarEstacion(
                documento, id, nombre, galaxia, distancia, gremio, mejora, nave);
        CrearXML.guardar(documento, rutaXml);
        System.out.println("Estación " + id + " modificada correctamente.");
    }

    private Element buscarEstacion(Document documento, int id) {
        List<Element> resultados = LecturaXML.buscarPorId(documento, id);
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    private void mostrar(List<Element> estaciones) {
        LecturaXML.mostrarEstaciones(estaciones);
    }

    private String leerSeleccion(String titulo, String[] opciones, boolean incluirNinguna) {
        while (true) {
            System.out.println("\n" + titulo);
            for (int i = 0; i < opciones.length; i++) {
                System.out.println((i + 1) + ". " + opciones[i]);
            }
            if (incluirNinguna) {
                System.out.println((opciones.length + 1) + ". Sin mejora");
            }
            int opcionVolver = opciones.length + (incluirNinguna ? 2 : 1);
            System.out.println(opcionVolver + ". Volver");

            int seleccion = leerOpcion("Elige una opción: ");
            if (seleccion == -1 || seleccion == opcionVolver) {
                return null;
            }
            if (seleccion >= 1 && seleccion <= opciones.length) {
                return opciones[seleccion - 1];
            }
            if (incluirNinguna && seleccion == opciones.length + 1) {
                return "";
            }
            System.out.println("Opción no válida.");
        }
    }

    private int leerOpcion(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (!scanner.hasNextLine()) {
                return -1;
            }
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Escribe un número.");
            }
        }
    }

    private String leerTexto(String mensaje, boolean opcional) {
        while (true) {
            System.out.print(mensaje);
            if (!scanner.hasNextLine()) {
                return null;
            }
            String entrada = scanner.nextLine().trim();
            if (opcional || !entrada.isEmpty()) {
                return entrada;
            }
            System.out.println("Este campo es obligatorio.");
        }
    }

    private Integer leerEnteroNoNegativo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (!scanner.hasNextLine()) {
                return null;
            }
            try {
                int numero = Integer.parseInt(scanner.nextLine().trim());
                if (numero >= 0) {
                    return numero;
                }
                System.out.println("La distancia no puede ser negativa.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Escribe un número entero.");
            }
        }
    }
}
