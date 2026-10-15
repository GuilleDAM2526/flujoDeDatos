package flujoDeDatos.texto;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class EscribirFichero {

    // Escribe en el fichero los valores que recibe en el array.
    public static void escribirArray(String ruta, String[] valores) {
        PrintWriter pw = null;

        try {
            // Si el fichero existe, reemplaza su contenido.
            pw = new PrintWriter(new FileWriter(ruta));

            // Recorre el array y escribe cada valor en una línea.
            for (int i = 0; i < valores.length; i++) {
                pw.println(valores[i]);
            }

            System.out.println("Se escribieron los datos.");

        } catch (IOException e) {
            System.out.println("Error al escribir el fichero: "
                    + e.getMessage());
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    // Pide líneas al usuario y las escribe hasta que introduzca "salir".
    public static void escribirDesdeConsola(String ruta, Scanner teclado) {
        PrintWriter pw = null;

        try {
            // Si el fichero existe, reemplaza su contenido. para escribir añadiendo añadir a ruta:  , true
            pw = new PrintWriter(new FileWriter(ruta));
            
            // Con número limitado de lineas:
            //añadir en la cabecera tras teclado:  , int cantidadLineas 
            //for (int i = 0; i < cantidadLineas; i++) {             //con límite de lineas
            //    System.out.println("Escribe la línea " + (i + 1) + ":");
            //    String linea = teclado.nextLine();
            //    pw.println(linea);
            //}                                 
            
            //método con "salir"
            System.out.println("Escribe líneas. Escribe \"salir\" para terminar:");

            while (teclado.hasNextLine()) {
                String linea = teclado.nextLine();

                if (linea.equalsIgnoreCase("salir")) {
                    break;
                }

                pw.println(linea);
            }
            //
            
            System.out.println("Se escribieron los datos introducidos.");

        } catch (IOException e) {
            System.out.println("Error al escribir el fichero: "
                    + e.getMessage());
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    public static void escribirLista(String ruta, List<String> valores) {
        PrintWriter pw = null;

        try {
            pw = new PrintWriter(new FileWriter(ruta));

            // Recorremos la lista usando un bucle for tradicional
            for (int i = 0; i < valores.size(); i++) {
                pw.println(valores.get(i));
            }

            System.out.println("Se escribieron los datos.");

        } catch (IOException e) {
            System.out.println("Error al escribir el fichero: " + e.getMessage());
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }
}
