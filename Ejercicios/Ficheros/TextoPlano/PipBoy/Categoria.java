package Ejercicios.Ficheros.TextoPlano.PipBoy;

public enum Categoria {
    ARMAS("Armas", "armas.txt", "Daño", "ID de munición"),
    ARMADURAS("Armaduras", "armaduras.txt", "Defensa", "Peso"),
    MUNICION("Munición", "municion.txt", "Daño por unidad", "Unidades"),
    OBJETOS("Objetos", "objetos.txt", "Tipo", "Cantidad");

    private final String nombre;
    private final String nombreArchivo;
    private final String etiquetaCampo1;
    private final String etiquetaCampo2;

    Categoria(String nombre, String nombreArchivo, String etiquetaCampo1, String etiquetaCampo2) {
        this.nombre = nombre;
        this.nombreArchivo = nombreArchivo;
        this.etiquetaCampo1 = etiquetaCampo1;
        this.etiquetaCampo2 = etiquetaCampo2;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public String getEtiquetaCampo1() {
        return etiquetaCampo1;
    }

    public String getEtiquetaCampo2() {
        return etiquetaCampo2;
    }
}
