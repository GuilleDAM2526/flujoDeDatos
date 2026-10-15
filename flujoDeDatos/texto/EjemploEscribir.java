package flujoDeDatos.texto;
import java.util.Scanner;

public class EjemploEscribir {
    


    public static void main(String[] args) {
        String ruta = "C:\\directorioArchivo\\archivo.txt";

        // Opción 1: escribir valores de un array.
        String[] datos = {"Ana", "Luis", "Marta"};
        EscribirFichero.escribirArray(ruta, datos);

        // Opción 2: pedir los valores por consola.
        //
        // Scanner teclado = new Scanner(System.in);
        // EscribirFichero.escribirDesdeConsola(ruta, teclado);
    }
}

