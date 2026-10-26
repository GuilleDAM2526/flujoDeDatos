package Ejercicios.Ficheros.Gestion;

import java.io.File;

public class ListarContenidoCarpeta {
    public static void main(String[] args) {
        File carpeta = new File("Ejercicios/Ficheros/Gestion");
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
