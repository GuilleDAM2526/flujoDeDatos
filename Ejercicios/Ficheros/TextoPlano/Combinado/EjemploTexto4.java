/*
 * Lee el inventario desde un TXT y calcula qué cantidad hay que reponer
 * para alcanzar el mínimo recomendado de cada producto.
 */
package Ejercicios.Ficheros.TextoPlano.Combinado;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Ejemplo de separación de campos, conversión a enteros y creación de un informe.
 */
public class EjemploTexto4 {
    private static final Path ENTRADA = Paths.get(
            "Ejercicios", "Ficheros", "TextoPlano", "Combinado", "datos", "inventario.txt");
    private static final Path SALIDA = Paths.get(
            "Ejercicios", "Ficheros", "TextoPlano", "Combinado", "resultados", "reponerInventario.txt");

    /**
     * Procesa los registros del inventario y escribe los productos por reponer.
     */
    public static void main(String[] args) {
        int productosLeidos = 0;
        int productosParaReponer = 0;

        try (BufferedReader lector = Files.newBufferedReader(ENTRADA, StandardCharsets.UTF_8)) {
            Files.createDirectories(SALIDA.getParent());
            try (BufferedWriter escritor = Files.newBufferedWriter(SALIDA, StandardCharsets.UTF_8)) {
                String linea;
                int numeroLinea = 0;

                while ((linea = lector.readLine()) != null) {
                    numeroLinea++;
                    if (linea.isBlank()) {
                        continue;
                    }

                    // Formato: código|producto|unidades disponibles|mínimo recomendado
                    String[] campos = linea.split("\\|", -1);
                    if (campos.length != 4) {
                        System.out.println("Línea " + numeroLinea + ": formato incorrecto.");
                        continue;
                    }

                    try {
                        int disponibles = Integer.parseInt(campos[2]);
                        int minimo = Integer.parseInt(campos[3]);
                        productosLeidos++;

                        if (disponibles < minimo) {
                            escritor.write(campos[0] + "|" + campos[1] + "|" + (minimo - disponibles));
                            escritor.newLine();
                            productosParaReponer++;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Línea " + numeroLinea + ": las cantidades no son números enteros.");
                    }
                }
            }

            System.out.println("Productos válidos revisados: " + productosLeidos);
            System.out.println("Productos que necesitan reposición: " + productosParaReponer);
            System.out.println("Informe guardado en " + SALIDA);
        } catch (IOException e) {
            System.err.println("Error al leer o escribir: " + e.getMessage());
        }
    }
}
