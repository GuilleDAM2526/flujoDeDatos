/* Lee un fichero (palabras) que contiene una palabra por línea
 * y escribe en otro fichero cuántas líneas se han leído.
 */

package Ejercicios.Ficheros.TextoPlano.Combinado;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Combinado1 {

    public static void main(String[] args) {
        int lineas = 0;
        BufferedReader lector = null;
        BufferedWriter escriba = null;
        String entrada = "Ejercicios/Ficheros/TextoPlano/Combinado/datos/palabras";
        String salida = "Ejercicios/Ficheros/TextoPlano/Combinado/datos/contador.txt";
        try {
            lector = new BufferedReader(new FileReader(entrada));
            while (lector.readLine() != null) {
                lineas++;
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("Error: No se a encontrado el archivo. " + e);
            ;
        } catch (IOException e) {
            System.out.println("Error en la lectura del archivo. " + e);
        } finally {
            if (lector != null) {
                try {
                    lector.close();
                } catch (IOException e) {
                    System.out.println("Error al cerrar el archivo.." + e);
                }
            }
        }
        try {
            escriba = new BufferedWriter(new FileWriter(salida));
            escriba.write(String.valueOf(lineas));
            escriba.newLine();
        } catch (FileNotFoundException e) {
            System.out.println("Error: No se a encontrado el archivo. " + e);
        } catch (IOException e) {
            System.out.println("Error en la lectura del archivo. " + e);
        } finally {
            if (escriba != null) {
                try {
                    escriba.close();
                } catch (IOException e) {
                    System.out.println("Error al cerrar el archivo.." + e);
                }
            }

        }
    }

}
