/* Escribe en resultado.txt los números del 1 al 20, uno por línea.
 * Después, añade una línea final con el total de esos números.
 */

package Ejercicios.Ficheros.TextoPlano.Escritura;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Escritura2b {
    public static void main(String[] args) throws IOException {
        /*
         * Try-with-resources:
         * - Abre el archivo para añadir contenido (true).
         * - BufferedWriter almacena temporalmente el texto antes de escribirlo.
         * - Al salir del try, bfw se cierra automáticamente, incluso si ocurre un error.
         */
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(new File("resultado2.txt"), true))) {
            int maximo = 20;
            int suma = 0;
            bfw.write("nueva entrada\n");
            for (int i = 1; i <= maximo; i++) {
                bfw.write(Integer.toString(i)); //  BufferedWriter.write() recibe texto (String) o caracteres, no un int para escribir su representación decimal. Por eso debes convertir el número a texto
                bfw.newLine();
                suma += i;
            }
            bfw.write("La suma es: " + suma + "\n");
        }
        // Aquí Java cierra bfw y también el FileWriter que contiene.
        // Si abrir, escribir o cerrar falla, se produce una IOException.

        /*
         * throws IOException en main indica que no capturamos aquí ese error:
         * lo dejamos propagarse al entorno de ejecución.
         */
    }

}
