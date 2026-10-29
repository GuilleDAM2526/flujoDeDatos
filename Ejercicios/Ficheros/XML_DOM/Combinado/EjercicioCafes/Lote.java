package Ejercicios.Ficheros.XML_DOM.Combinado.EjercicioCafes;


public class Lote {
    private double kilos;
    private String caducidad;

    public Lote(double kilos, String caducidad){
        this.kilos = kilos;
        this.caducidad = caducidad;
    }

    public double getKilos() {
        return kilos;
    }

    public void setKilos(double kilos) {
        this.kilos = kilos;
    }

    public String getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(String caducidad) {
        this.caducidad = caducidad;
    }
    // En Lote.java
    @Override
    public String toString() {
        return caducidad + ": " + kilos + " kg";
    }
}