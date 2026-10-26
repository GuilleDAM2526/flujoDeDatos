/* Pide tres frases al usuario y 
 * escribe cada una en una línea
 * de un fichero llamado frases.txt.
 */

package Ejercicios.Ficheros.TextoPlano.Escritura;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Escritura1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        File fichero = new File("frases.txt");
        FileWriter escritor = null;
        int max = 3;
        try {
            escritor = new FileWriter(fichero);
            for (int i = 0; i < max; i++) {

                System.out.println("Escribe una frase: ");
                String frase = sc.nextLine();
                System.out.println((i+1) + "/3");
                escritor.write(frase + "\n");

            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            try {
                if (escritor != null){
                escritor.close();
                }
            } catch (Exception e){
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }
        }
    }

}
