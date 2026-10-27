package Ejercicios.Ficheros.TextoPlano.PipBoy;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CatalogoTxt {
    private static final String ARCHIVO_SECUENCIA = "siguienteId.txt";

    private final Path carpetaDatos;
    private final EnumMap<Categoria, List<Registro>> registros;
    private int siguienteId;

    public CatalogoTxt(Path carpetaDatos) {
        this.carpetaDatos = carpetaDatos;
        this.registros = new EnumMap<>(Categoria.class);
        for (Categoria categoria : Categoria.values()) {
            registros.put(categoria, new ArrayList<>());
        }
    }

    /**
     * Carga en memoria los registros de los cuatro TXT.
     * Si un archivo no existe, lo crea vacío. Después comprueba que los ID
     * no se repitan y que las armas apunten a munición existente.
     */
    public void cargar() throws IOException {
        Files.createDirectories(carpetaDatos);
        Set<Integer> idsUtilizados = new HashSet<>();

        for (Categoria categoria : Categoria.values()) {
            Path archivo = ruta(categoria.getNombreArchivo());
            if (!Files.exists(archivo)) {
                Files.createFile(archivo);
            }

            // Cada línea del archivo representa un registro; Registro interpreta sus cuatro campos.
            List<String> lineas = Files.readAllLines(archivo, StandardCharsets.UTF_8);
            List<Registro> lista = registros.get(categoria);
            lista.clear();

            for (int i = 0; i < lineas.size(); i++) {
                String linea = lineas.get(i);
                if (linea.isBlank()) {
                    continue;
                }

                Registro registro;
                try {
                    registro = Registro.desdeLinea(linea);
                } catch (IllegalArgumentException e) {
                    throw new IOException(
                            "Error en " + archivo + ", línea " + (i + 1) + ": " + e.getMessage(), e);
                }
                if (!idsUtilizados.add(registro.getId())) {
                    throw new IOException("El ID " + registro.getId() + " está repetido en los archivos del catálogo.");
                }
                lista.add(registro);
            }
        }

        validarReferenciasMunicion();
        cargarSiguienteId(idsUtilizados);
    }

    public List<Registro> listar(Categoria categoria) {
        return new ArrayList<>(registros.get(categoria));
    }

    /**
     * Busca un registro dentro de una categoría comparando su ID.
     * Devuelve null cuando no hay ningún registro con ese ID.
     */
    public Registro buscar(Categoria categoria, int id) {
        for (Registro registro : registros.get(categoria)) {
            if (registro.getId() == id) {
                return registro;
            }
        }
        return null;
    }

    /**
     * Añade un registro nuevo. El ID es global: se usa una sola secuencia
     * para armas, armaduras, munición y objetos.
     */
    public Registro agregar(Categoria categoria, String nombre, String campo1, String campo2)
            throws IOException {
        validarDatos(categoria, nombre, campo1, campo2);
        campo2 = normalizarIdMunicion(categoria, campo2);

        int id = siguienteId;
        if (siguienteId == Integer.MAX_VALUE) {
            throw new IOException("No quedan IDs disponibles.");
        }
        siguienteId = Math.incrementExact(siguienteId);
        guardarSiguienteId();

        Registro nuevo = new Registro(id, nombre, campo1, campo2);
        List<Registro> actualizados = new ArrayList<>(registros.get(categoria));
        actualizados.add(nuevo);
        guardarCategoria(categoria, actualizados);
        registros.put(categoria, actualizados);
        return nuevo;
    }

    /**
     * Sustituye el registro encontrado por ID y guarda de nuevo el TXT
     * de esa categoría. El ID del registro no cambia.
     */
    public boolean editar(Categoria categoria, int id, String nombre, String campo1, String campo2)
            throws IOException {
        List<Registro> actualizados = new ArrayList<>(registros.get(categoria));
        int posicion = buscarPosicion(actualizados, id);
        if (posicion == -1) {
            return false;
        }

        validarDatos(categoria, nombre, campo1, campo2);
        campo2 = normalizarIdMunicion(categoria, campo2);
        actualizados.set(posicion, new Registro(id, nombre, campo1, campo2));
        guardarCategoria(categoria, actualizados);
        registros.put(categoria, actualizados);
        return true;
    }

    /**
     * Elimina el registro y reescribe su archivo. Antes de borrar munición,
     * comprueba que ningún arma siga haciendo referencia a ella.
     */
    public boolean eliminar(Categoria categoria, int id) throws IOException {
        Registro registro = buscar(categoria, id);
        if (registro == null) {
            return false;
        }

        if (categoria == Categoria.MUNICION) {
            for (Registro arma : registros.get(Categoria.ARMAS)) {
                if (!arma.getCampo2().isBlank() && Integer.parseInt(arma.getCampo2()) == id) {
                    throw new IllegalStateException(
                            "No se puede eliminar: el arma \"" + arma.getNombre()
                                    + "\" todavía usa esta munición.");
                }
            }
        }

        List<Registro> actualizados = new ArrayList<>(registros.get(categoria));
        actualizados.removeIf(elemento -> elemento.getId() == id);
        guardarCategoria(categoria, actualizados);
        registros.put(categoria, actualizados);
        return true;
    }

    /**
     * Revisa los datos antes de guardar. El campo adicional de las armas
     * puede quedar vacío; si tiene un ID, debe existir esa munición.
     */
    private void validarDatos(Categoria categoria, String nombre, String campo1, String campo2) {
        validarCampo(nombre, "nombre", false);
        validarCampo(campo1, categoria.getEtiquetaCampo1(), false);
        validarCampo(campo2, categoria.getEtiquetaCampo2(), categoria == Categoria.ARMAS);

        if (categoria == Categoria.ARMAS && !campo2.isBlank()) {
            int idMunicion;
            try {
                idMunicion = Integer.parseInt(campo2);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("El ID de munición debe ser un número entero.", e);
            }
            if (buscar(Categoria.MUNICION, idMunicion) == null) {
                throw new IllegalArgumentException("No existe munición con el ID " + idMunicion + ".");
            }
        }
    }

    private void validarCampo(String valor, String nombreCampo, boolean puedeEstarVacio) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " no puede ser nulo.");
        }
        if (!puedeEstarVacio && valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " es obligatorio.");
        }
        if (valor.contains("|") || valor.contains("\n") || valor.contains("\r")) {
            throw new IllegalArgumentException(
                    "El campo " + nombreCampo + " no puede contener | ni saltos de línea.");
        }
    }

    /**
     * Se ejecuta después de cargar todos los archivos para poder comprobar
     * las referencias de las armas contra la lista completa de municiones.
     */
    private void validarReferenciasMunicion() throws IOException {
        for (Registro arma : registros.get(Categoria.ARMAS)) {
            if (arma.getCampo2().isBlank()) {
                continue;
            }

            int idMunicion;
            try {
                idMunicion = Integer.parseInt(arma.getCampo2());
            } catch (NumberFormatException e) {
                throw new IOException("El arma \"" + arma.getNombre()
                        + "\" contiene un ID de munición no válido: " + arma.getCampo2(), e);
            }
            if (buscar(Categoria.MUNICION, idMunicion) == null) {
                throw new IOException("El arma \"" + arma.getNombre()
                        + "\" hace referencia a la munición inexistente con ID " + idMunicion + ".");
            }
        }
    }

    /**
     * Lee el próximo ID guardado. También comprueba que sea mayor que todos
     * los ID ya usados, evitando reutilizarlos aunque se borren registros.
     */
    private void cargarSiguienteId(Set<Integer> idsUtilizados) throws IOException {
        int idMinimo = 0;
        for (int id : idsUtilizados) {
            if (id == Integer.MAX_VALUE) {
                throw new IOException("No quedan IDs disponibles.");
            }
            idMinimo = Math.max(idMinimo, id + 1);
        }

        Path archivo = ruta(ARCHIVO_SECUENCIA);
        if (Files.exists(archivo)) {
            List<String> lineas = Files.readAllLines(archivo, StandardCharsets.UTF_8);
            if (lineas.size() != 1) {
                throw new IOException("El archivo " + archivo + " debe contener un único ID.");
            }
            try {
                siguienteId = Integer.parseInt(lineas.get(0).trim());
            } catch (NumberFormatException e) {
                throw new IOException("El ID del archivo " + archivo + " no es válido.", e);
            }
            if (siguienteId < idMinimo) {
                throw new IOException("El siguiente ID guardado es menor que un ID ya utilizado.");
            }
        } else {
            siguienteId = idMinimo;
            guardarSiguienteId();
        }
    }

    private void guardarCategoria(Categoria categoria, List<Registro> lista) throws IOException {
        List<String> lineas = new ArrayList<>();
        for (Registro registro : lista) {
            // Cada objeto vuelve a convertirse en una línea con el formato ID|nombre|campo1|campo2.
            lineas.add(registro.aLinea());
        }
        Files.write(ruta(categoria.getNombreArchivo()), lineas, StandardCharsets.UTF_8);
    }

    /**
     * Guarda el próximo ID en un TXT separado para que la secuencia se
     * conserve al cerrar y volver a iniciar el programa.
     */
    private void guardarSiguienteId() throws IOException {
        Files.writeString(
                ruta(ARCHIVO_SECUENCIA),
                siguienteId + System.lineSeparator(),
                StandardCharsets.UTF_8);
    }

    private Path ruta(String nombreArchivo) {
        return carpetaDatos.resolve(nombreArchivo);
    }

    /**
     * Convierte el ID de munición, por ejemplo "008", a "8".
     * Así las referencias se guardan en una forma única y son fáciles de comparar.
     */
    private String normalizarIdMunicion(Categoria categoria, String campo2) {
        if (categoria == Categoria.ARMAS && !campo2.isBlank()) {
            return String.valueOf(Integer.parseInt(campo2));
        }
        return campo2;
    }

    private int buscarPosicion(List<Registro> lista, int id) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }
}
