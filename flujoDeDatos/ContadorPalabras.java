package flujoDeDatos;

import java.io.*;    // Importa clases para leer y escribir ficheros.
import java.util.*;  // Importa TreeMap, Set y StringTokenizer.

public class ContadorPalabras {

    public static void main(String[] args) {

        // Creamos un TreeMap para guardar:
        // - La palabra como clave (String).
        // - La cantidad de veces que aparece como valor (Integer).
        //
        // TreeMap mantiene las claves ordenadas alfabéticamente.
        TreeMap<String, Integer> contadorpalabras = new TreeMap<>();

        // El bloque try contiene las operaciones que podrían producir
        // errores de entrada/salida, como no encontrar un fichero.
        try {

            // Abrimos "palabras.txt" para leerlo.
            // FileReader lee caracteres y BufferedReader permite leer
            // el contenido línea por línea.
            BufferedReader bf =
                    new BufferedReader(new FileReader("palabras.txt"));

            // Leemos la primera línea del fichero.
            // readLine() devuelve null cuando ya no quedan líneas.
            String linea = bf.readLine();

            // Repetimos el proceso mientras haya una línea que leer.
            while (linea != null) {

                // Dividimos la línea en palabras usando el espacio " "
                // como separador.
                //
                // StringTokenizer permite obtener una palabra cada vez.
                StringTokenizer st = new StringTokenizer(linea, " ");

                // Mientras queden palabras en esta línea...
                while (st.hasMoreTokens()) {

                    // Obtenemos la siguiente palabra.
                    String palabra = st.nextToken();

                    // Buscamos en el mapa la frecuencia actual de esa palabra.
                    // Si la palabra aún no está, get devuelve null.
                    Integer frecuencia = contadorpalabras.get(palabra);

                    if (frecuencia == null) {
                        // La palabra aparece por primera vez.
                        // La guardamos con frecuencia 1.
                        contadorpalabras.put(palabra, 1);
                    } else {
                        // La palabra ya estaba en el mapa.
                        // Actualizamos su frecuencia sumándole 1.
                        contadorpalabras.put(palabra, frecuencia + 1);
                    }
                }

                // Leemos la siguiente línea para continuar el bucle.
                linea = bf.readLine();

                // Esta línea alternativa, comentada, separaría una línea
                // en un arreglo de palabras usando split().
                // String[] palabras = linea.split(" ");
            }

            // Cerramos el lector cuando hemos terminado de leer el fichero.
            bf.close();

            // Abrimos "contador.txt" para escribir el resultado.
            // Si ya existe, su contenido se reemplaza.
            BufferedWriter bw =
                    new BufferedWriter(new FileWriter("contador.txt"));

            // keySet() devuelve el conjunto de claves del mapa,
            // es decir, todas las palabras que se han contado.
            Set<String> clave = contadorpalabras.keySet();

            // Recorremos las palabras del mapa.
            // Al ser un TreeMap, se recorren en orden alfabético.
            for (String key : contadorpalabras.keySet()) {

                // Escribimos la palabra, un espacio y su frecuencia.
                // "\n" añade un salto de línea después de cada resultado.
                bw.write(key + " " + contadorpalabras.get(key) + "\n");
            }

            // Cerramos el escritor para terminar de guardar el contenido
            // y liberar el fichero.
            bw.close();

        } catch (IOException e) {
            // Si ocurre un error al leer o escribir, se convierte en
            // RuntimeException y el programa se detiene mostrando el error.
            throw new RuntimeException(e);
        }
    }
}


/* Un detalle: la variable clave se crea con contadorpalabras.keySet(),
 * pero luego no se utiliza. Por eso se puede quitar esa línea y 
 * recorrer directamente contadorpalabras.keySet() en el for, como ya hace el código.
 * El StringTokenizer usa aquí el espacio como separador.Si el fichero pudiera contener
 * varios espacios seguidos o tabuladores, convendría ajustar los delimitadores.
 * Además, las mayúsculas y minúsculas cuentan como palabras distintas: Casa y casa se guardarían por separado.
 */

// Hola Estoy probando la rama de luis
