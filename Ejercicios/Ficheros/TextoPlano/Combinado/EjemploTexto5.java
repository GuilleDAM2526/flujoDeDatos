package Ejercicios.Ficheros.TextoPlano.Combinado;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EjemploTexto5 {
    private static final Path ENTRADA_A = Paths.get(
            "Ejercicios", "Ficheros", "TextoPlano", "Combinado", "datos", "numerosA.txt");
    private static final Path ENTRADA_B = Paths.get(
            "Ejercicios", "Ficheros", "TextoPlano", "Combinado", "datos", "numerosB.txt");
    private static final Path SALIDA = Paths.get(
            "Ejercicios", "Ficheros", "TextoPlano", "Combinado", "resultados", "numerosOrdenados.txt");

    public static void main(String[] args) {
        try {
            List<Integer> numeros = new ArrayList<>();
            numeros.addAll(leerNumeros(ENTRADA_A));
            numeros.addAll(leerNumeros(ENTRADA_B));

            // Juntamos los valores de los dos archivos y los ordenamos antes de escribir.
            Collections.sort(numeros);
            Files.createDirectories(SALIDA.getParent());
            try (BufferedWriter escritor = Files.newBufferedWriter(SALIDA, StandardCharsets.UTF_8)) {
                for (int numero : numeros) {
                    escritor.write(String.valueOf(numero));
                    escritor.newLine();
                }
            }

            System.out.println("Se combinaron " + numeros.size() + " números en " + SALIDA);
        } catch (IOException e) {
            System.err.println("Error al leer o escribir: " + e.getMessage());
        }
    }

    private static List<Integer> leerNumeros(Path archivo) throws IOException {
        List<Integer> numeros = new ArrayList<>();

        try (BufferedReader lector = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)) {
            String linea;
            int numeroLinea = 0;

            while ((linea = lector.readLine()) != null) {
                numeroLinea++;
                try {
                    numeros.add(Integer.parseInt(linea.trim()));
                } catch (NumberFormatException e) {
                    System.out.println(archivo.getFileName() + ", línea " + numeroLinea
                            + ": se omite un valor que no es entero.");
                }
            }
        }
        return numeros;
    }
}
