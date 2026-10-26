/*Realizar una clase CuentaPalabras con un método main que haga lo siguiente:
Leer un fichero de texto sin formato del sistema local que solo contenga palabras.
Contar el número de veces que se repite cada palabra.
Ordenar alfabéticamente las palabras.
Escribir en otro fichero de nombre contador.txt la palabra y el número de veces que aparece.
PISTA: Una posible solución podría basarse en este algoritmo:
        1. Crear un HashMap (TreeMap) para almacenar como claves las palabras del fichero origen y como valores el número de veces que se repiten.
        2. Crear flujo y filtro para leer el fichero origen. Cada línea del mismo es una String, por tanto, puede crearse una StringTokenizer usando el delimitador por defecto.
            Luego, recorrer sus tokens (palabras) y obtener en el HashMap (TreeMap) la frecuencia de repetición de cada palabra teniendo en cuenta que si no hay ningún valor asociado
            a la palabra analizada devuelve null (esto implicará que la palabra es la primera vez que aparece). Si es distinto de null, la palabra ya ha aparecido antes.
            En ese caso debemos incrementar en uno el contador correspondiente a dicha palabra clave.
        3. Crear un ArrayList con las claves del HashMap y ordenarlas mediante el método estático void sort(..) de la clase java.util.Collections.
            Si has utilizado un TreeMap, las claves están ordenadas.
        4. Crear flujo y filtro para escribir en el fichero destino los pares del HashMap (TreeMap) por orden alfabético, en base a las claves. No olvidar vaciar y cerrar el filtro con flush() y close() respectivamente.
*/
package Ejercicios.Ficheros.TextoPlano.Combinado;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

public class CuentaPalabrasB {

    public static void main(String[] args) {

        String ficheroOrigen = "Palabras";

        String ficheroDestino = "contador.txt";

        // Llamamos al método que lee el fichero y cuenta las palabras.
        TreeMap<String, Integer> frecuencias = contarPalabras(ficheroOrigen);

        // Llamamos al método que escribe el mapa en el fichero de salida.
        escribirFrecuencias(ficheroDestino, frecuencias);
    }

    public static TreeMap<String, Integer> contarPalabras(String ruta) {

        // Creamos un TreeMap:
        // - La clave será una palabra.
        // - El valor será el número de veces que aparece.
        // - TreeMap ordena las claves alfabéticamente.
        TreeMap<String, Integer> frecuencias = new TreeMap<>();

        // Abrimos el fichero para leerlo.
        // try-with-resources lo cierra automáticamente al terminar.
        try (BufferedReader buffer = new BufferedReader(new FileReader(ruta))) {

            String linea;

            // Leemos una línea cada vez, hasta llegar al final del fichero.
            while ((linea = buffer.readLine()) != null) {

                // Separamos la línea por espacios en blanco.
                // "\\s+" significa uno o más espacios, tabuladores, etc.
                String[] palabras = linea.split("\\s+");

                // Recorremos las palabras que encontramos en esta línea.
                for (int i = 0; i < palabras.length; i++) {

                    String palabra = palabras[i];

                    // Si la línea está vacía, split puede dejar una cadena vacía.
                    // La ignoramos para no contarla como palabra.
                    if (palabra.isEmpty()) {
                        continue;
                    }

                    // Buscamos cuántas veces se ha contado ya esta palabra.
                    Integer cantidadActual = frecuencias.get(palabra);

                    if (cantidadActual == null) {
                        // Si devuelve null, la palabra aún no está en el mapa.
                        // La añadimos con valor 1.
                        frecuencias.put(palabra, 1);
                    } else {
                        // Si ya estaba, aumentamos su contador en uno.
                        frecuencias.put(palabra, cantidadActual + 1);
                    }
                }
            }

        } catch (IOException e) {

            System.out.println("Error al leer el fichero: " + e.getMessage());
        }

        // Devolvemos el mapa con todas las palabras y sus frecuencias.
        return frecuencias;
    }

    public static void escribirFrecuencias(String ruta, TreeMap<String, Integer> frecuencias) {

        // Abrimos el fichero de salida para escribir.
        // Si ya existe, se reemplaza su contenido. Usar true para evitar machacar 
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(ruta)))) {

            // Recorremos cada pareja palabra-frecuencia del mapa.
            // El TreeMap las devuelve en orden alfabético.
            for (Map.Entry<String, Integer> entrada : frecuencias.entrySet()) {

                String palabra = entrada.getKey();
                Integer cantidad = entrada.getValue();

                // Escribimos una palabra y su cantidad en cada línea.
                pw.println(palabra + " " + cantidad);
            }

            System.out.println("Se escribieron los datos en " + ruta);

        } catch (IOException e) {
            // Este bloque se ejecuta si ocurre un error al escribir el fichero.
            System.out.println("Error al escribir el fichero: " + e.getMessage());
        }
    }
}


/* El try-with-resources es una forma de try que cierra automáticamente los recursos declarados entre paréntesis,
 * como BufferedReader y PrintWriter, al terminar el bloque, incluso si ocurre una excepción.
 * Con un try-catch normal tienes que cerrarlos tú, normalmente en un bloque finally.
 * En cuanto a los mapas, HashMap no mantiene las palabras ordenadas.
 * TreeMap sí las ordena por clave, por lo que en este programa permite escribirlas alfabéticamente sin ordenarlas aparte.
 */