import java.io.File;

public class FicheroOCarpeta {
    public static void main(String[] args) {
        File ruta = new File("Ficheros");

        if (ruta.isFile()) {
            System.out.println("Es un fichero");
        } else if (ruta.isDirectory()) {
            System.out.println("Es una carpeta");
        } else {
            System.out.println("No existe o no es accesible");
        }
    }

}
