/* Guarda diez números enteros en una lista. 
 * Recorre la lista para mostrar solo los números mayores que 5 y,
 * al final, indica cuántos has mostrado. */
package Ejercicios.Bucles_Listas;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Listas2 {
    public static void main(String[] args) {
        List<Integer> num = new ArrayList<>();
        int total = 10;
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduzca Números enteros: ");

        for (int i = 0; i < total;) {
            try {
                num.add(sc.nextInt());
                i++;
            } catch (InputMismatchException e) {

                System.out.println("Error: " + e);
                System.out.println("Por favor introduzca solo números enteros");
                sc.next();
            }
        }
        sc.close();
        
        int mayor = 0;
        System.out.println(num);
        
        for (int n : num) {
            if (n > 5){
                System.out.println(n);
                mayor++;
            }
            
        }
        System.out.println("La cantidad de números mayores que 5 es de: "+ mayor);
    }

}
