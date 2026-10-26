/* Dada una lista de nombres, cuenta cuántas veces aparece cada nombre y muestra el resultado.
 * Si un nombre no está todavía en el mapa, empieza su contador en 1.
 * Añadimos un mapa nuevo con los repetidos
 */

package Ejercicios.Contadores;

import java.util.List;
import java.util.TreeMap;
import java.util.ArrayList;

public class Contador2 {
    public static void main(String[] args) {
        List<String> nombres = new ArrayList<>(List.of("Juan", "Luis", "Alfredo", "Marta", "Luis"));                                                                                           // con nombres
        TreeMap<String, Integer> contadorNombres = new TreeMap<>();
        TreeMap<String, Integer> repetidos = new TreeMap<>();

        // Recorre la lista "nombres".
        // En cada vuelta, la variable "nombre" contiene un elemento de la lista.
        for (String nombre : nombres) {
            // Comprueba si ese nombre ya está guardado como clave en el TreeMap.
            if (contadorNombres.containsKey(nombre)) {
                // Si ya existe, obtiene el número de veces que aparece.
                int veces = contadorNombres.get(nombre);
                // Actualiza el valor: suma 1 a las veces que ya aparecía.
                contadorNombres.put(nombre, veces + 1);
            } else {
                contadorNombres.put(nombre, 1);
            }
        }
        // contador.keySet() obtiene todas las claves del mapa (los nombres).
        //El for va tomando cada clave, una por una, y la guarda temporalmente en la variable nombre.
        for (String nombre : contadorNombres.keySet()) { 
            // Busca en el mapa el valor asociado a ese nombre. Ej: si nombre = "Luis", contador.get(nombre) devuelve 2.                   
            int veces = contadorNombres.get(nombre); 
            
            System.out.println("el nombre " + nombre + " aparece " + veces + " veces.");

            if(veces>1){
                repetidos.put(nombre, veces);
            }
            

        }
        System.out.println(contadorNombres);
        System.out.println(repetidos);
    }

}
