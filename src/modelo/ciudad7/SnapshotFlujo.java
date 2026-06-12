package modelo.ciudad7;

import java.util.List;

public class SnapshotFlujo {
    public List<AristaFlujo> aristas;
    public List<String> caminoActual;
    public String descripcion;

    public SnapshotFlujo(List<AristaFlujo> aristas, List<String> caminoActual, String descripcion) {
        this.aristas = aristas;
        this.caminoActual = caminoActual;
        this.descripcion = descripcion;
    }
}
