package Ejercicios.Ficheros.TextoPlano.PipBoy;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MenuCatalogo {
    private final Scanner teclado;
    private final CatalogoTxt catalogo;

    public MenuCatalogo(Scanner teclado, CatalogoTxt catalogo) {
        this.teclado = teclado;
        this.catalogo = catalogo;
    }

    /**
     * Mantiene abierto el menú principal hasta que el usuario elige salir.
     * Cada número de categoría corresponde a una posición del enum Categoria.
     */
    public void iniciar() throws IOException {
        boolean salir = false;
        while (!salir) {
            mostrarCategorias();
            int opcion = leerEntero("Elige una categoría: ");
            if (opcion == -1 || opcion == 0) {
                salir = true;
            } else if (opcion >= 1 && opcion <= Categoria.values().length) {
                menuCategoria(Categoria.values()[opcion - 1]);
            } else {
                System.out.println("Opción no válida.");
            }
        }
        System.out.println("Hasta luego.");
    }

    private void mostrarCategorias() {
        System.out.println("\n=== CATÁLOGO DEL PIP-BOY ===");
        Categoria[] categorias = Categoria.values();
        for (int i = 0; i < categorias.length; i++) {
            System.out.println((i + 1) + ". " + categorias[i].getNombre());
        }
        System.out.println("0. Salir");
    }

    /**
     * Muestra las operaciones de una categoría y repite el menú hasta volver.
     * Las operaciones de lectura y escritura se delegan en CatalogoTxt.
     */
    private void menuCategoria(Categoria categoria) throws IOException {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n=== " + categoria.getNombre().toUpperCase() + " ===");
            System.out.println("1. Listar registros");
            System.out.println("2. Añadir registro");
            System.out.println("3. Editar registro");
            System.out.println("4. Eliminar registro");
            System.out.println("0. Volver");

            int opcion = leerEntero("Elige una opción: ");
            switch (opcion) {
                case -1:
                case 0:
                    volver = true;
                    break;
                case 1:
                    listar(categoria);
                    break;
                case 2:
                    anadir(categoria);
                    break;
                case 3:
                    editar(categoria);
                    break;
                case 4:
                    eliminar(categoria);
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void listar(Categoria categoria) {
        List<Registro> registros = catalogo.listar(categoria);
        if (registros.isEmpty()) {
            System.out.println("No hay registros en esta categoría.");
            return;
        }

        for (Registro registro : registros) {
            mostrarRegistro(categoria, registro);
        }
    }

    /**
     * Pide los campos al usuario y delega las validaciones y el guardado
     * en CatalogoTxt. El ID lo asigna el catálogo, no se pide por teclado.
     */
    private void anadir(Categoria categoria) throws IOException {
        System.out.println("\nNuevo registro de " + categoria.getNombre().toLowerCase() + ":");
        String nombre = leerTexto("Nombre: ");
        String campo1 = leerTexto(categoria.getEtiquetaCampo1() + ": ");
        String campo2 = leerTexto(categoria.getEtiquetaCampo2()
                + (categoria == Categoria.ARMAS ? " (Intro si no usa munición)" : "") + ": ");
        if (nombre == null || campo1 == null || campo2 == null) {
            return;
        }

        try {
            Registro registro = catalogo.agregar(categoria, nombre, campo1, campo2);
            System.out.println("Registro añadido con ID " + registro.getId() + ".");
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo añadir: " + e.getMessage());
        }
    }

    /**
     * Pide el ID y los nuevos valores. CatalogoTxt busca el registro,
     * valida los cambios y reescribe el archivo correspondiente.
     */
    private void editar(Categoria categoria) throws IOException {
        listar(categoria);
        int id = leerEntero("ID del registro que quieres editar: ");
        if (id < 0) {
            return;
        }

        String nombre = leerTexto("Nuevo nombre: ");
        String campo1 = leerTexto("Nuevo " + categoria.getEtiquetaCampo1() + ": ");
        String campo2 = leerTexto("Nuevo " + categoria.getEtiquetaCampo2()
                + (categoria == Categoria.ARMAS ? " (Intro si no usa munición)" : "") + ": ");
        if (nombre == null || campo1 == null || campo2 == null) {
            return;
        }

        try {
            if (catalogo.editar(categoria, id, nombre, campo1, campo2)) {
                System.out.println("Registro editado.");
            } else {
                System.out.println("No existe un registro con ese ID en esta categoría.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo editar: " + e.getMessage());
        }
    }

    /**
     * Solicita el ID del registro que se quiere borrar.
     * El catálogo puede rechazar el borrado si otra arma usa esa munición.
     */
    private void eliminar(Categoria categoria) throws IOException {
        listar(categoria);
        int id = leerEntero("ID del registro que quieres eliminar: ");
        if (id < 0) {
            return;
        }

        try {
            if (catalogo.eliminar(categoria, id)) {
                System.out.println("Registro eliminado.");
            } else {
                System.out.println("No existe un registro con ese ID en esta categoría.");
            }
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Muestra los datos con etiquetas propias de la categoría.
     * Para las armas, convierte el ID de munición relacionado en su nombre.
     */
    private void mostrarRegistro(Categoria categoria, Registro registro) {
        System.out.println("\nID: " + registro.getId());
        System.out.println("Nombre: " + registro.getNombre());
        System.out.println(categoria.getEtiquetaCampo1() + ": " + registro.getCampo1());
        if (categoria == Categoria.ARMAS && !registro.getCampo2().isBlank()) {
            Registro municion = buscarMunicion(registro.getCampo2());
            String nombreMunicion = municion == null ? "ID " + registro.getCampo2() : municion.getNombre();
            System.out.println(categoria.getEtiquetaCampo2() + ": "
                    + registro.getCampo2() + " (" + nombreMunicion + ")");
        } else {
            System.out.println(categoria.getEtiquetaCampo2() + ": "
                    + (registro.getCampo2().isBlank() ? "Ninguno" : registro.getCampo2()));
        }
    }

    /**
     * Busca la munición enlazada por el ID guardado en el registro del arma.
     */
    private Registro buscarMunicion(String idTexto) {
        return catalogo.buscar(Categoria.MUNICION, Integer.parseInt(idTexto));
    }

    private int leerEntero(String mensaje) {
        while (true) {
            String entrada = leerTexto(mensaje);
            if (entrada == null) {
                return -1;
            }
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Escribe un número entero.");
            }
        }
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        if (!teclado.hasNextLine()) {
            return null;
        }
        return teclado.nextLine().trim();
    }
}
