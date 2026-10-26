package Ejercicios.Ficheros.Gestion;

import java.io.File;

public class CambiarNombre {
    public static void main(String[] args) {
        File original = new File("Ejercicios/Ficheros/Gestion/Ejemplo.txt");
        File destino = new File("Ejercicios/Ficheros/Gestion/EjemploRenombrado.txt");

        if (original.renameTo(destino)) {
            System.out.println("Renombrado correctamente");
        } else {
            System.out.println("No se pudo renombrar");
        }
    }

}
