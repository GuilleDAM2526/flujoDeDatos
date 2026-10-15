package flujoDeDatos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ReaderAndWriter {
    public static void main(String[] args) {
        String nombreFichero = "texto.txt";

        // Primero, escribir las líneas en el fichero
        try (Scanner teclado = new Scanner(System.in);
             BufferedWriter writer =
                     new BufferedWriter(new FileWriter(nombreFichero))) {
            //BufferedWriter writer =new BufferedWriter(new FileWriter(nombreFichero, true)); //Si se quisiera escribir a continuación, sin borrar lo escrito
            System.out.println("Escribe líneas. Escribe \"salir\" para terminar:");

            String linea;
            while (teclado.hasNextLine()) {
                linea = teclado.nextLine();

                if (linea.equalsIgnoreCase("salir")) {
                    break;
                }

                writer.write(linea);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al escribir el fichero: " + e.getMessage());
            return;
        }

        // Después, leer el fichero y mostrar cada línea en mayúsculas
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(nombreFichero))) {

            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea.toUpperCase());
            }

        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
    }
}