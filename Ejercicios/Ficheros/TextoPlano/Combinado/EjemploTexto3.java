package Ejercicios.Ficheros.TextoPlano.Combinado;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public class EjemploTexto3 {
    private static final Path ENTRADA = Paths.get(
            "Ejercicios", "Ficheros", "TextoPlano", "Combinado", "datos", "incidencias.txt");
    private static final Path SALIDA = Paths.get(
            "Ejercicios", "Ficheros", "TextoPlano", "Combinado", "resultados", "incidenciasUrgentes.txt");
    private static final String PALABRA_BUSCADA = "urgente";

    public static void main(String[] args) {
        int coincidencias = 0;

        try {
            Files.createDirectories(SALIDA.getParent());
            try (BufferedReader lector = Files.newBufferedReader(ENTRADA, StandardCharsets.UTF_8);
                 BufferedWriter escritor = Files.newBufferedWriter(SALIDA, StandardCharsets.UTF_8)) {
                String linea;

                while ((linea = lector.readLine()) != null) {
                    // Convertimos a minúsculas para que "Urgente" y "URGENTE" también coincidan.
                    if (linea.toLowerCase(Locale.ROOT).contains(PALABRA_BUSCADA)) {
                        escritor.write(linea);
                        escritor.newLine();
                        coincidencias++;
                    }
                }
            }

            System.out.println("Líneas encontradas: " + coincidencias);
            System.out.println("Copia filtrada guardada en " + SALIDA);
        } catch (IOException e) {
            System.err.println("Error al leer o escribir: " + e.getMessage());
        }
    }
}
