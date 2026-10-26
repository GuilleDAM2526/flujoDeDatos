/* Escribe en resultado.txt los números del 1 al 20, uno por línea.
 * Después, añade una línea final con el total de esos números.
 */

package Ejercicios.Ficheros.TextoPlano.Escritura;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Escritura2 {
    public static void main(String[] args) throws IOException {
        FileWriter fw = new FileWriter(new File("resultado.txt"),true);
        int maximo = 20;
        int suma = 0;
        fw.write("nueva entrada\n");
        for (int i= 1; i <= maximo; i++){
            fw.write(i + "\n");
            suma += i;
        }
        fw.write("La suma es: " + suma + "\n");
        try {
            fw.close();
        } catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
        }
    }

}
