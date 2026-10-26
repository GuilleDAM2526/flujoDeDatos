package Ejercicios.Ficheros.TextoPlano.Combinado;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeMap;

public class CuentaPalabrasC {

    public static void main(String[] args) {
        String ficheroOrigen = "Palabras";
        String ficheroDestino = "contador.txt";

        TreeMap<String, Integer> frecuencias =
                contarPalabras(ficheroOrigen);

        escribirFrecuencias(ficheroDestino, frecuencias);
    }

    public static TreeMap<String, Integer> contarPalabras(String ruta) {
        TreeMap<String, Integer> frecuencias = new TreeMap<>();

        BufferedReader buffer = null;

        try {
            // Creamos el lector para abrir el fichero de entrada.
            buffer = new BufferedReader(new FileReader(ruta));

            String linea;

            // Leemos el fichero línea por línea.
            while ((linea = buffer.readLine()) != null) {

                // Separamos la línea por espacios en blanco.
                String[] palabras = linea.split("\\s+");

                // Recorremos las palabras de la línea con un for tradicional.
                for (int i = 0; i < palabras.length; i++) {
                    String palabra = palabras[i];

                    // Ignoramos cadenas vacías, por ejemplo, si la línea está vacía.
                    if (palabra.isEmpty()) {
                        continue;
                    }

                    // Consultamos si la palabra ya está en el mapa.
                    Integer cantidad = frecuencias.get(palabra);

                    if (cantidad == null) {
                        // Si no está, es la primera vez que aparece.
                        frecuencias.put(palabra, 1);
                    } else {
                        // Si ya está, incrementamos su contador.
                        frecuencias.put(palabra, cantidad + 1);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());

        } finally {
            // En un try-catch normal cerramos el lector manualmente.
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    System.out.println("Error al cerrar el fichero: " + e.getMessage());
                }
            }
        }

        return frecuencias;
    }

    public static void escribirFrecuencias(
            String ruta, TreeMap<String, Integer> frecuencias) {

        PrintWriter pw = null;

        try {
            // Creamos el escritor para abrir el fichero de salida.
            pw = new PrintWriter(new BufferedWriter(new FileWriter(ruta)));

            // Como usamos TreeMap, las palabras se recorren alfabéticamente.
            // Este for tradicional recorre las posiciones del mapa convertido
            // temporalmente en dos listas paralelas: claves y valores.
            Object[] palabras = frecuencias.keySet().toArray();
            Object[] cantidades = frecuencias.values().toArray();

            for (int i = 0; i < palabras.length; i++) {
                pw.println(palabras[i] + " " + cantidades[i]);
            }

            // En PrintWriter, close() vacía el contenido pendiente y cierra el flujo.
            System.out.println("Se escribieron los datos en " + ruta);

        } catch (IOException e) {
            System.out.println("Error al escribir el fichero: " + e.getMessage());

        } finally {
            // Cerramos manualmente el escritor.
            if (pw != null) {
                pw.close();
            }
        }
    }
}