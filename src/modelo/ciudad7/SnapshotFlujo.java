package modelo.ciudad7;

import java.util.List;

public class SnapshotFlujo {
    public List<AristaFlujo> aristas;
    public List<String> caminoActual;
    public String descripcion;

    /*
     * Pre: La lista de 'aristas' y la de 'caminoActual' no deben ser nulas. La 'descripcion' debe ser un texto válido.
     * Post: Crea un objeto que guarda una "foto" del estado del grafo en un momento específico, registrando sus aristas, el camino recorrido y un texto explicativo.
     */
    public SnapshotFlujo(List<AristaFlujo> aristas, List<String> caminoActual, String descripcion) {
        this.aristas = aristas;
        this.caminoActual = caminoActual;
        this.descripcion = descripcion;
    }
}
