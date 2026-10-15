/*Crear, eliminar fichero
 *@Author: GAD
 *@Param: Damos ya en el programa un nombre al fichero
 *@Return: fichero creado
 *@Data:12/10/2026
*/
import java.io.File;
import java.io.IOException;

public class Editar {
    public static void crearFichero(String nombre) {
        File fichero = new File(nombre); // Creamos un objeto File que representa la ruta del fichero
        try { // Si no metemos la creación del fichero en un try catch, puede dar error y no compila
            if (fichero.createNewFile()) {// CREAMOS EL ARCHIVO si no existe
                System.out.println("Fichero creado: " + fichero.getPath());
            } else {
                System.out.println("El fichero ya existía: " + fichero.getPath());
            }
        } catch (IOException e) { // Excepción y su mensaje
            System.out.println("No se pudo crear el fichero. Comprueba la ruta y los permisos.");
            System.out.println("Detalle técnico: " + e.getMessage());
        }
    }

    public static void eliminarFichero(String nombre) {
        File fichero = new File(nombre);
        System.out.println("Se procede a eliminar el fichero..");
        if (!fichero.exists()) {
            System.out.println("El fichero no existe");
        } else if (fichero.delete()) {
            System.out.println("Fichero eliminado");
        } else {
            System.out.println("No se pudo eliminar el fichero");
        }
    }

    public static boolean crearCarpetas(String ruta) {
        File carpeta = new File(ruta);

        if (ruta == null || ruta.isBlank()) {
            System.out.println("Error: la ruta de la carpeta está vacía.");
            return false;
        }

        if (carpeta.exists()) {
            if (carpeta.isDirectory()) {
                System.out.println("La carpeta ya existe: " + carpeta.getAbsolutePath());
                return true;
            } else {
                System.out.println("Error: esa ruta ya existe, pero corresponde a un fichero.");
                return false;
            }
        }

        if (carpeta.mkdirs()) {
            System.out.println("Carpetas creadas: " + carpeta.getAbsolutePath());
            return true;
        } else {
            System.out.println("Error: no se pudieron crear las carpetas. Comprueba la ruta y los permisos.");
            return false;
        }
    }
}
