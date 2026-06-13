package modelo.ciudad7;

import java.util.*;

public class GrafoFlujo {
    private Map<String, List<AristaFlujo>> adyacencias;
    private List<String> nodos;

    /*
     * Pre: Ninguna.
     * Post: Crea un grafo de flujo vacío, inicializando la estructura para las adyacencias y la lista de nodos.
     */
    public GrafoFlujo() {
        this.adyacencias = new HashMap<>();
        this.nodos = new ArrayList<>();
    }

    /*
     * Pre: El nombre del 'nodo' no debe ser nulo ni vacío.
     * Post: Si el nodo no existía en el grafo, lo agrega a la lista y le crea su espacio para almacenar sus conexiones futuras.
     */
    public void agregarNodo(String nodo) {
        if (!adyacencias.containsKey(nodo)) {
            adyacencias.put(nodo, new ArrayList<>());
            nodos.add(nodo);
        }
    }

    /*
     * Pre: Los nodos 'origen' y 'destino' deben ser válidos y la 'capacidad' debe ser un número mayor o igual a cero.
     * Post: Asegura que ambos nodos existan en el grafo y crea la conexión (arista) de ida con la capacidad dada, más la conexión residual de vuelta con capacidad cero.
     */
    public void agregarArista(String origen, String destino, int capacidad) {
        agregarNodo(origen);
        agregarNodo(destino);
        adyacencias.get(origen).add(new AristaFlujo(origen, destino, capacidad));
        adyacencias.get(destino).add(new AristaFlujo(destino, origen, 0, true));
    }

    /*
     * Pre: Ninguna.
     * Post: Devuelve la lista con todos los nombres de los nodos que se registraron en el grafo.
     */
    public List<String> getNodos() { return nodos; }
    
    /*
     * Pre: El 'nodo' debe existir en el grafo.
     * Post: Devuelve la lista de todas las conexiones (aristas) que salen de ese nodo.
     */
    public List<AristaFlujo> getAdyacentes(String nodo) { return adyacencias.get(nodo); }

    /*
     * Pre: Ninguna.
     * Post: Devuelve una lista con todas las aristas del grafo que sean conexiones de ida reales (aquellas que tengan una capacidad mayor a cero).
     */
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