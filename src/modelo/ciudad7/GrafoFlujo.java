package modelo.ciudad7;

import java.util.*;

public class GrafoFlujo {
    private Map<String, List<AristaFlujo>> adyacencias;
    private List<String> nodos;

    public GrafoFlujo() {
        this.adyacencias = new HashMap<>();
        this.nodos = new ArrayList<>();
    }

    public void agregarNodo(String nodo) {
        if (!adyacencias.containsKey(nodo)) {
            adyacencias.put(nodo, new ArrayList<>());
            nodos.add(nodo);
        }
    }

    public void agregarArista(String origen, String destino, int capacidad) {
        agregarNodo(origen);
        agregarNodo(destino);
        adyacencias.get(origen).add(new AristaFlujo(origen, destino, capacidad));
        adyacencias.get(destino).add(new AristaFlujo(destino, origen, 0, true));
    }
    
    public int getCantidadDeNodos() {
    	return nodos.size();
    }

    public List<String> getNodos() { 
    	return nodos; 
    }
    public List<AristaFlujo> getAdyacentes(String nodo) { return adyacencias.get(nodo); }

    public List<AristaFlujo> getTodasLasAristas() {
        List<AristaFlujo> todas = new ArrayList<>();
        for (List<AristaFlujo> lista : adyacencias.values()) {
            for (AristaFlujo a : lista) {
                if (a.getCapacidad() > 0) {
                    todas.add(a);
                }
            }
        }
        return todas;
    }
}