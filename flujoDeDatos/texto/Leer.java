package flujoDeDatos.texto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Leer {

    //Lee el fichero y lo imprime por pantalla
    public static void leerFichero(String ruta) {
        File archivo = null;
        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            archivo = new File(ruta);
            reader = new FileReader(archivo);
            buffer = new BufferedReader(reader);

            String linea;
            while ((linea = buffer.readLine()) != null) {
                System.out.println(linea); // imprimimos cada linea
            }

        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el fichero: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        } finally {
            try {
                // Cerrar buffer también cierra reader.
                if (buffer != null) {
                    buffer.close();
                } else if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el fichero: " + e.getMessage());
            }
        }
    }
    // Lee un fichero y lo guarda en un arrayList
    public static List<String> leerFicheroList(String ruta) {
        File archivo = null;
        FileReader reader = null;
        BufferedReader buffer = null;
        List<String> lineas = new ArrayList<>();

        try {
            archivo = new File(ruta);
            reader = new FileReader(archivo);
            buffer = new BufferedReader(reader);

            String linea;
            while ((linea = buffer.readLine()) != null) {
                lineas.add(linea); // Guardamos en la lista
            }

        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el fichero: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        } finally {
            try {
                if (buffer != null) {
                    buffer.close();
                } else if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el fichero: " + e.getMessage());
            }
        }
        return lineas; // Devolvemos la lista completa
    }
}

