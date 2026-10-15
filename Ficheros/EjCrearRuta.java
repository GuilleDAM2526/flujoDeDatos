
/*Crear fichero y vemos sus datos
 *@Author: GAD
 *@Param: Damos ya en el programa un nombre al fichero
 *@Return: Datos de ese fichero, como nombre, ruta, permisos
 *@Data:12/10/2026
*/
import java.io.File;

public class EjCrearRuta {
    public static void main(String args[]) {

        String carpeta = "Ficheros/carpeta1/carpeta2";
        String rutaFichero = carpeta + "/EjCrearRuta.txt";
        
        if (Editar.crearCarpetas(carpeta)) {

            Editar.crearFichero(rutaFichero);
            File fichero = new File(rutaFichero);

            if (fichero.exists()) {
                System.out.println("Nombre del archivo: " + fichero.getName());
                System.out.println("Ruta: " + fichero.getPath());
                System.out.println("Ruta absoluta: " + fichero.getAbsolutePath());
                System.out.println("Carpeta padre: " + fichero.getParent());
                System.out.println("Se puede escribir: " + fichero.canWrite());
                System.out.println("Se puede leer: " + fichero.canRead());
                System.out.println("Tamaño: " + fichero.length());
                

                Editar.eliminarFichero(rutaFichero); //Opcional

            } else {
                System.out.println("El fichero no existe");
            }

        }
    }
}
