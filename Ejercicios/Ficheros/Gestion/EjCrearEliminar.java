
package Ejercicios.Ficheros.Gestion;

/*Crear fichero, vemos sus datos y lo eliminamos
 *@Author: GAD
 *@Param: Damos ya en el programa un nombre al fichero
 *@Return: Datos de ese fichero, como nombre, ruta, permisos
 *@Data:12/10/2026
*/
import java.io.File;

public class EjCrearEliminar {
    public static void main(String args[]) {
        String nombre = "Ejercicios/Ficheros/Gestion/Ejemplo2.txt";

        Editar.crearFichero(nombre); // Llamamos a la función externa Editar.crear

        File fichero = new File(nombre);

        if (fichero.exists()) { // Al no leer ni escribir en el fichero, con esto vale
            System.out.println("Nombre del archivo " + fichero.getName());
            System.out.println("Ruta " + fichero.getPath());
            System.out.println("Ruta absoluta " + fichero.getAbsolutePath());
            System.out.println("Se puede escribir " + fichero.canWrite());
            System.out.println("Se puede leer " + fichero.canRead());
            System.out.println("Se puede ejecutar: " + fichero.canExecute());
            System.out.println("Tamaño " + fichero.length());

            Editar.eliminarFichero(nombre); // Llamamos a la función externa Eliminar.eliminar
        } else {
            System.out.println("El fichero no existe");
        }

    }
}
