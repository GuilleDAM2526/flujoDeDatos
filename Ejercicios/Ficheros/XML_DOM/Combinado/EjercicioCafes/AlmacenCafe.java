/*
 * Lee las marcas y sus lotes desde MisCafes.txt, muestra el inventario
 * y permite vender café empezando por los lotes que caducan antes.
 */
package Ejercicios.Ficheros.XML_DOM.Combinado.EjercicioCafes;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;

public class AlmacenCafe {
    private static final Path ARCHIVO = Paths.get(
            "Ejercicios", "Ficheros", "XML_DOM", "Combinado",
            "EjercicioCafes", "MisCafes.txt");

    public static void main(String[] args) throws IOException {
        Vector<Cafe> cafes = leerCafes();
        mostrarCafes(cafes);
        venderCafe(cafes);
        mostrarCafes(cafes);
    }

    /**
     * Lee el archivo. Cada marca tiene un precio y varios pares de kilos y
     * fecha de caducidad; una línea con 0 indica que termina esa marca.
     */
    public static Vector<Cafe> leerCafes() throws IOException {
        Vector<Cafe> cafes = new Vector<>();

        try (BufferedReader lector = Files.newBufferedReader(ARCHIVO)) {
            String nombre;
            while ((nombre = lector.readLine()) != null) {
                nombre = nombre.trim();
                double precio = Double.parseDouble(lector.readLine().trim());
                double stock = 0;
                Vector<Lote> lotes = new Vector<>();
                String kilosTexto;

                while ((kilosTexto = lector.readLine()) != null
                        && !kilosTexto.trim().equals("0")) {
                    // El archivo incluye "kilos" en uno de los valores.
                    double kilos = Double.parseDouble(
                            kilosTexto.trim().replace(" kilos", ""));
                    String caducidad = lector.readLine();
                    if (caducidad == null) {
                        throw new IOException("Falta la fecha de caducidad de un lote.");
                    }

                    lotes.add(new Lote(kilos, caducidad.trim()));
                    stock += kilos;
                }

                if (kilosTexto == null) {
                    throw new IOException("Falta el 0 que indica el final de los lotes de " + nombre + ".");
                }
                cafes.add(new Cafe(nombre, precio, stock, lotes));
            }
        }

        return cafes;
    }

    /** Muestra las marcas, sus lotes y el stock total disponible. */
    public static void mostrarCafes(Vector<Cafe> cafes) {
        System.out.println("\nCONTROL DE INVENTARIO");
        for (Cafe cafe : cafes) {
            System.out.println(cafe);
        }
    }

    /**
     * Pide una marca y una cantidad, y descuenta la venta de los lotes
     * respetando el orden en que aparecen en el archivo.
     */
    public static void venderCafe(Vector<Cafe> cafes) {
        Scanner teclado = new Scanner(System.in);
        System.out.print("\n¿Qué marca quieres vender? ");
        String nombre = teclado.nextLine().trim();

        Cafe cafeEncontrado = null;
        for (Cafe cafe : cafes) {
            if (cafe.getNombre().equalsIgnoreCase(nombre)) {
                cafeEncontrado = cafe;
                break;
            }
        }

        if (cafeEncontrado == null) {
            System.out.println("Esa marca no está en el almacén.");
            return;
        }

        System.out.print("¿Cuántos kilos quieres vender? ");
        String cantidadTexto = teclado.nextLine().trim();
        double cantidad;
        try {
            cantidad = Double.parseDouble(cantidadTexto);
        } catch (NumberFormatException e) {
            System.out.println("La cantidad debe ser un número.");
            return;
        }

        if (cantidad <= 0 || cantidad > cafeEncontrado.getStock()) {
            System.out.println("La cantidad debe ser mayor que 0 y no superar el stock disponible.");
            return;
        }

        double pendiente = cantidad;
        Vector<Lote> lotes = cafeEncontrado.getVictor();
        int indice = 0;

        while (pendiente > 0) {
            Lote lote = lotes.get(indice);
            if (lote.getKilos() <= pendiente) {
                pendiente -= lote.getKilos();
                lotes.remove(indice);
            } else {
                lote.setKilos(lote.getKilos() - pendiente);
                pendiente = 0;
            }
        }

        cafeEncontrado.setStock(cafeEncontrado.getStock() - cantidad);
        System.out.println("Venta realizada: " + cantidad + " kg de "
                + cafeEncontrado.getNombre() + ".");
    }
}
