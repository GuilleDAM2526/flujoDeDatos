import java.io.File;

public class ListarContenidoCarpeta {
    public static void main(String[] args) {
        File carpeta = new File("Ficheros");
        File[] elementos = carpeta.listFiles();

        if (elementos != null) {
            for (File elemento : elementos) {
                System.out.println(elemento.getName());
            }
        } else {
            System.out.println("No se pudo listar la carpeta");
        }
    }

}
