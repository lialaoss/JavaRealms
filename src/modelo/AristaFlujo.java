package modelo;

import utiles.ValidacionesUtilesCiudad7;

public class AristaFlujo {
    private String origen;
    private String destino;
    private int capacidad;
    private int flujo;

    /**
     * Pre: origen y destino no son nulos ni vacíos. capacidad > 0.
     * Post: se crea una arista con flujo inicial 0.
     */
    public AristaFlujo(String origen, String destino, int capacidad) {
        ValidacionesUtilesCiudad7.validarNoVacio(origen, "origen");
        ValidacionesUtilesCiudad7.validarNoVacio(destino, "destino");
        ValidacionesUtilesCiudad7.validarMayorACero(capacidad, "capacidad");
        this.origen = origen;
        this.destino = destino;
        this.capacidad = capacidad;
        this.flujo = 0;
    }

    /**
     * Pre: ninguna.
     * Post: devuelve la capacidad residual (capacidad - flujo).
     */
    public int getCapacidadResidual() {
        return capacidad - flujo;
    }

    /**
     * Pre: cantidad no es negativa. flujo + cantidad <= capacidad.
     * Post: el flujo se incrementa en cantidad.
     */
    public void agregarFlujo(int cantidad) {
        ValidacionesUtilesCiudad7.validarNoNegativo(cantidad, "cantidad de flujo");
        if (flujo + cantidad > capacidad) {
            throw new IllegalArgumentException("El flujo no puede superar la capacidad.");
        }
        this.flujo += cantidad;
    }

    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public int getCapacidad() { return capacidad; }
    public int getFlujo() { return flujo; }

    /**
     * Post: dos aristas son iguales si tienen el mismo origen, destino y capacidad.
     */
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

    /**
     * Post: devuelve representación legible de la arista.
     */
    @Override
    public String toString() {
        return origen + " -> " + destino + " [" + flujo + "/" + capacidad + "]";
    }
}