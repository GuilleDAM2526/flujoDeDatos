/* Dado un número entero, cuenta cuántas veces aparece cada cifra.
 * Por ejemplo, para 1223, el mapa debería guardar la frecuencia de 1, 2 y 3.
 */

package Ejercicios.Contadores;

import java.util.Scanner;

public class Contador1 {
    public static void main(String[] args) {
        int[] frecuencia = new int[10]; // En binario usarías 2, en octal 8 y en hexadecimal 16.
        int numero = 0;
        Scanner sc = new Scanner(System.in);
        boolean control = false;
        do {
            System.out.println("Introduzca un entero de varias cifras: ");
            String entradaUsuario = sc.nextLine();
            try {
                numero = Integer.parseInt(entradaUsuario);
                numero = Math.abs(numero); // devuelve el valor absoluto. Evita que el signo interfiera
                control = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: " + e);
            }
        } while (control != true);
        sc.close();

        do {
            int cifra = numero % 10; // Damos a cifra el valor de la última cifra
            frecuencia[cifra]++; // sumamos a la frecuencia de esa cifra
            numero /= 10; // numero = numero/10;
        } while (numero > 0);

        for (int cifra = 0; cifra < frecuencia.length; cifra++) {
            if (frecuencia[cifra] > 0) { // podemos excluir los que aparecen una vez con >1, por ejemplo
                                         // O si quiero solo las cifras que no están: ==0)
                // si no pongo la condición me imprime todas las cifras del 0 al 9, estén o no presentes
                System.out.println("El " + cifra + " aparece " + frecuencia[cifra] + " veces.");
            }
        }
    }
}
