package Ejercicios.Ficheros.TextoPlano.Combinado;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EjemploTexto1 {
    private static final Path ENTRADA = Paths.get(
            "Ejercicios", "Ficheros", "TextoPlano", "Combinado", "datos", "temperaturas.txt");
    private static final Path SALIDA = Paths.get(
            "Ejercicios", "Ficheros", "TextoPlano", "Combinado", "resultados", "resumenTemperaturas.txt");

    public static void main(String[] args) {
        int cantidad = 0;
        int lineasIncorrectas = 0;
        double suma = 0;

        try (BufferedReader lector = Files.newBufferedReader(ENTRADA, StandardCharsets.UTF_8)) {
            String linea;
            int numeroLinea = 0;

            while ((linea = lector.readLine()) != null) {
                numeroLinea++;
                try {
                    suma += Double.parseDouble(linea.trim());
                    cantidad++;
                } catch (NumberFormatException e) {
                    lineasIncorrectas++;
                    System.out.println("Se omite la línea " + numeroLinea + ": no es una temperatura.");
                }
            }

            Files.createDirectories(SALIDA.getParent());
            try (BufferedWriter escritor = Files.newBufferedWriter(SALIDA, StandardCharsets.UTF_8)) {
                escritor.write("Temperaturas válidas: " + cantidad);
                escritor.newLine();
                if (cantidad > 0) {
                    double media = suma / cantidad;
                    escritor.write(String.format("Media: %.2f", media));
                    escritor.newLine();
                } else {
                    escritor.write("No había temperaturas válidas.");
                    escritor.newLine();
                }
                escritor.write("Líneas incorrectas: " + lineasIncorrectas);
            }

            System.out.println("Resumen guardado en " + SALIDA);
        } catch (IOException e) {
            System.err.println("Error al leer o escribir: " + e.getMessage());
        }
    }
}
