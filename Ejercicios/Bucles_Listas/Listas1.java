/* Crea una lista con cinco nombres. 
 * Usa un for tradicional (int i = 0; i < ...; i++) para mostrar cada nombre en una línea. 
 */
package Ejercicios.Bucles_Listas;

import java.util.ArrayList;
import java.util.List;

public class Listas1 {
    public static void main(String[] args) {
        List<String> lista = new ArrayList<>(List.of("Luis", "Juan", "Fede", "María"));

        lista.add("Belén");
        lista.add("Rubén");

        System.out.println(lista);

        for (String nombre : lista) {
            System.out.println(nombre);            
        }


    }
}
