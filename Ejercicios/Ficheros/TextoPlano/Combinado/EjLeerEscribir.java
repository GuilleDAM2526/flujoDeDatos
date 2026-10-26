package Ejercicios.Ficheros.TextoPlano.Combinado;

import Ejercicios.Ficheros.TextoPlano.Lectura.Leer;
import Ejercicios.Ficheros.TextoPlano.Escritura.EscribirFichero;
import java.util.List;
// Usando lista
public class EjLeerEscribir {

    public static void main(String[] args) {
        // 1. Llamamos al método que ahora nos devuelve una List
        List<String> datos = Leer.leerFicheroList("texto.txt");

        // 2. Pasamos esa misma lista al método de escritura
        EscribirFichero.escribirLista("copia.txt", datos);
    }
}
