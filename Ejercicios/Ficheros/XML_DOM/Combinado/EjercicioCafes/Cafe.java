package Ejercicios.Ficheros.XML_DOM.Combinado.EjercicioCafes;
import java.util.Vector;

public class Cafe {
    private String nombre;
    private double precio;
    private double stock;
    // Aqui usamos Vector, que trabaja como una ArrayList,
    // con la diferencia de que trabaja mejor con hilos (multitareas)
    private Vector<Lote> victor; //Hacemos un Vector con Lote

    public Cafe(String nombre, double precio, double stock, Vector<Lote> victor){
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.victor = victor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public Vector<Lote> getVictor() {
        return victor;
    }

    public void setVictor(Vector<Lote> victor) {
        this.victor = victor;
    }
    @Override
    public String toString() {
    StringBuilder texto = new StringBuilder();

    texto.append(nombre)
         .append(" a ")
         .append(precio)
         .append(" euros/kg\n");

    for (Lote lote : victor) {
        texto.append("    ")
             .append(lote)
             .append("\n");
    }

    texto.append("Stock Total: ")
         .append(stock);

    return texto.toString();
}

}

