package Ejercicios.Ficheros.TextoPlano.PipBoy;

public class Registro {
    private final int id;
    private final String nombre;
    private final String campo1;
    private final String campo2;

    public Registro(int id, String nombre, String campo1, String campo2) {
        this.id = id;
        this.nombre = nombre;
        this.campo1 = campo1;
        this.campo2 = campo2;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCampo1() {
        return campo1;
    }

    public String getCampo2() {
        return campo2;
    }

    public String aLinea() {
        return id + "|" + nombre + "|" + campo1 + "|" + campo2;
    }

    /**
     * Convierte una línea del TXT en un objeto Registro.
     * Primero separa los campos usando | y luego comprueba que el ID
     * y el nombre tengan un formato válido.
     */
    public static Registro desdeLinea(String linea) {
        // El límite -1 conserva campos vacíos al final de la línea.
        String[] campos = linea.split("\\|", -1);
        if (campos.length != 4) {
            throw new IllegalArgumentException("Error, se esperaban 4 campos separados por |");
        }

        try {
            int id = Integer.parseInt(campos[0]);
            if (id < 0) {
                throw new IllegalArgumentException("Error, el ID no puede ser negativo");
            }
            if (campos[1].isBlank()) {
                throw new IllegalArgumentException("Error, el nombre no puede estar vacío");
            }
            return new Registro(id, campos[1], campos[2], campos[3]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error, el ID ha de ser un número entero", e);
        }
    }
}
