package modelo.ciudad7;

import utiles.ValidacionesUtiles;

public class AristaFlujo {
    private String origen;
    private String destino;
    private int capacidad;
    private int flujo;

    /*
     * Pre: origen y destino no son nulos ni vacíos. capacidad > 0.
     * Post: se crea una arista con flujo inicial 0.
     */
    public AristaFlujo(String origen, String destino, int capacidad) {
        ValidacionesUtiles.validarNoVacio(origen, "origen");
        ValidacionesUtiles.validarNoVacio(destino, "destino");
        ValidacionesUtiles.validarMayorACero(capacidad, "capacidad");
        this.origen = origen;
        this.destino = destino;
        this.capacidad = capacidad;
        this.flujo = 0;
    }
    
    AristaFlujo(String origen, String destino, int capacidad, boolean esInversa) {
        this.origen = origen;
        this.destino = destino;
        this.capacidad = capacidad;
        this.flujo = 0;
    }

    /*
     * Pre: ninguna.
     * Post: devuelve la capacidad residual (capacidad - flujo).
     */
    public int getCapacidadResidual() {
        return capacidad - flujo;
    }

    /*
     * Pre: cantidad no es negativa. flujo + cantidad <= capacidad.
     * Post: el flujo se incrementa en cantidad.
     */
    public void agregarFlujo(int cantidad) {
        this.flujo += cantidad;
    }

    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public int getCapacidad() { return capacidad; }
    public int getFlujo() { return flujo; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null || getClass() != obj.getClass()) { return false; }
        AristaFlujo otra = (AristaFlujo) obj;
        return capacidad == otra.capacidad &&
               origen.equals(otra.origen) &&
               destino.equals(otra.destino);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(origen, destino, capacidad);
    }

    @Override
    public String toString() {
        return origen + " -> " + destino + " [" + flujo + "/" + capacidad + "]";
    }
}
