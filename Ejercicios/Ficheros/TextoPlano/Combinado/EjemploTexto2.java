package Ejercicios.Ficheros.TextoPlano.Combinado;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EjemploTexto2 {
    private static final Path ENTRADA = Paths.get(
            "Ejercicios", "Ficheros", "TextoPlano", "Combinado", "datos", "notas.txt");
    private static final Path SALIDA = Paths.get(
            "Ejercicios", "Ficheros", "TextoPlano", "Combinado", "resultados", "resultadoNotas.txt");

    public static void main(String[] args) {
        double sumaNotas = 0;
        int numeroNotas = 0;
        int aprobados = 0;
        int lineasIncorrectas = 0;

        try (BufferedReader lector = Files.newBufferedReader(ENTRADA, StandardCharsets.UTF_8)) {
            String linea;

            while ((linea = lector.readLine()) != null) {
                if (linea.isBlank()) {
                    continue;
                }

                // El archivo guarda cada registro como nombre;nota.
                String[] campos = linea.split(";", -1);
                if (campos.length != 2 || campos[0].isBlank()) {
                    lineasIncorrectas++;
                    continue;
                }

                try {
                    double nota = Double.parseDouble(campos[1].trim());
                    if (nota < 0 || nota > 10) {
                        lineasIncorrectas++;
                        continue;
                    }

                    sumaNotas += nota;
                    numeroNotas++;
                    if (nota >= 5) {
                        aprobados++;
                    }
                } catch (NumberFormatException e) {
                    lineasIncorrectas++;
                }
            }

            Files.createDirectories(SALIDA.getParent());
            try (BufferedWriter escritor = Files.newBufferedWriter(SALIDA, StandardCharsets.UTF_8)) {
                escritor.write("Alumnos con nota válida: " + numeroNotas);
                escritor.newLine();
                escritor.write("Aprobados: " + aprobados);
                escritor.newLine();
                escritor.write("Suspensos: " + (numeroNotas - aprobados));
                escritor.newLine();
                escritor.write(numeroNotas == 0
                        ? "Nota media: no hay notas válidas"
                        : String.format("Nota media: %.2f", sumaNotas / numeroNotas));
                escritor.newLine();
                escritor.write("Líneas incorrectas: " + lineasIncorrectas);
            }

            System.out.println("Resultados guardados en " + SALIDA);
        } catch (IOException e) {
            System.err.println("Error al leer o escribir: " + e.getMessage());
        }
    }
}
