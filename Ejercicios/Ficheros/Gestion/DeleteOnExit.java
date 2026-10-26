package Ejercicios.Ficheros.Gestion;

import java.io.File;

public class DeleteOnExit {
    public static void main(String[] args) {

            File temporal = new File("Ejercicios/Ficheros/Gestion/temporal.txt");
            temporal.deleteOnExit();
            
    }
}
