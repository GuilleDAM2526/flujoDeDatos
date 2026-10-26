package NoMansSkyDOM;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class Main {
    private static final Path RUTA_XML = Paths.get("NoMansSkyDOM", "estaciones.xml");

    public static void main(String[] args) {
        try {
            CrearXML.crearSiNoExiste(RUTA_XML);
            try (Scanner scanner = new Scanner(System.in)) {
                new Menus(scanner, RUTA_XML).iniciar();
            }
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
            System.err.println("No se pudo ejecutar el programa: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
