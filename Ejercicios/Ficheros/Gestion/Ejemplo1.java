
package Ejercicios.Ficheros.Gestion;

/*Crear fichero y vemos sus datos
 *@Author: GAD
 *@Param: Damos ya en el programa un nombre al fichero
 *@Return: Datos de ese fichero, como nombre, ruta, permisos
 *@Data:12/10/2026
*/
import java.io.File;
import java.io.IOException;

public class Ejemplo1 {
    public static void main(String args[]) {
        File fichero = new File("Ejercicios/Ficheros/Gestion/FicheroEjemplo.txt"); // Creamos un objeto File que representa la ruta del fichero
        try { // Si no metemos la creación del fichero en un try catch, puede dar error y no
              // deja ejecutar el código
            fichero.createNewFile(); // CREAMOS EL ARCHIVO
        } catch (IOException e) { // Excepción y su mensaje
            System.out.println("Error: " + e.getMessage());
        }
        if (fichero.exists()) { // Al no leer ni escribir en el fichero, no hace falta un try-catch
            System.out.println("Nombre del archivo " + fichero.getName());
            System.out.println("Ruta " + fichero.getPath());
            System.out.println("Ruta absoluta " + fichero.getAbsolutePath());
            System.out.println("Se puede escribir " + fichero.canWrite());
            System.out.println("Se puede leer " + fichero.canRead());
            System.out.println("Se puede ejecutar: " + fichero.canExecute());
            System.out.println("Tamaño " + fichero.length());
            
        } else {
            System.out.println("El fichero no existe");
        }
    }
}
