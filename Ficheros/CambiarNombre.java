import java.io.File;

public class CambiarNombre {
    public static void main(String[] args) {
        File original = new File("Ficheros/Ejemplo.txt");
        File destino = new File("Ficheros/EjemploRenombrado.txt");

        if (original.renameTo(destino)) {
            System.out.println("Renombrado correctamente");
        } else {
            System.out.println("No se pudo renombrar");
        }
    }

}
