package Ejercicios.Ficheros.TextoPlano.PipBoy;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    private static final Path CARPETA_DATOS = Paths.get(
            "Ejercicios", "Ficheros", "TextoPlano", "PipBoy", "datos");

    /**
     * Carga primero los TXT para que el menú trabaje con los datos actuales.
     * try-with-resources cierra el Scanner al salir del programa.
     */
    public static void main(String[] args) {
        CatalogoTxt catalogo = new CatalogoTxt(CARPETA_DATOS);
        try {
            catalogo.cargar();
            try (Scanner teclado = new Scanner(System.in)) {
                new MenuCatalogo(teclado, catalogo).iniciar();
            }
        } catch (IOException e) {
            System.err.println("No se pudo cargar o guardar el catálogo: " + e.getMessage());
        }
    }
}
