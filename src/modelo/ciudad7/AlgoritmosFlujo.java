package modelo.ciudad7;

import java.util.*;

public class AlgoritmosFlujo {

	/*
     * Pre: El grafo debe existir. Los nodos 'fuente' y 'sumidero' tienen que ser válidos y distintos.
     * Post: Calcula el flujo máximo de la red y devuelve una lista con el paso a paso (el historial) de cómo lo resolvió.
     */
	
    public List<SnapshotFlujo> fordFulkerson(GrafoFlujo grafo, String fuente, String sumidero) {
        List<SnapshotFlujo> snapshots = new ArrayList<>();

        while (true) {
            List<String> camino = bfsAumentante(grafo, fuente, sumidero);
            if (camino == null) { break; }

            int flujoAumentante = calcularFlujoAumentante(grafo, camino);
            actualizarFlujo(grafo, camino, flujoAumentante);

            snapshots.add(new SnapshotFlujo(
                copiarAristas(grafo),
                new ArrayList<>(camino),
                "Camino: " + camino + " | Flujo aumentante: " + flujoAumentante
            ));
        }
        return snapshots;
    }

    /*
     * Pre: El grafo, el nodo de 'inicio' y el nodo de 'fin' deben existir y no estar vacíos.
     * Post: Devuelve la lista de nodos que forman el camino más corto entre el inicio y el fin. Si no hay camino, devuelve null.
     */
    public List<String> caminoMinimo(GrafoFlujo grafo, String inicio, String fin) {
        return bfsAumentante(grafo, inicio, fin);
    }

    /*
     * Pre: El grafo, la 'fuente' y el 'sumidero' deben ser válidos.
     * Post: Devuelve un camino posible (una lista de nodos) donde todavía queda espacio para pasar flujo. Si ya no hay espacio por ningún lado, devuelve null.
     */
    private List<String> bfsAumentante(GrafoFlujo grafo, String fuente, String sumidero) {
        Map<String, String> predecesores = new HashMap<>();
        Queue<String> cola = new LinkedList<>();
        Set<String> visitados = new HashSet<>();

        cola.add(fuente);
        visitados.add(fuente);
        predecesores.put(fuente, null);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            if (actual.equals(sumidero)) {
                return reconstruirCamino(predecesores, sumidero);
            }
            for (AristaFlujo a : grafo.getAdyacentes(actual)) {
                if (!visitados.contains(a.getDestino()) && a.getCapacidadResidual() > 0) {
                    visitados.add(a.getDestino());
                    predecesores.put(a.getDestino(), actual);
                    cola.add(a.getDestino());
                }
            }
        }
        return null;
    }

    /*
     * Pre: El mapa de 'predecesores' tiene que tener guardados los pasos previos y 'fin' no debe estar vacío.
     * Post: Arma y devuelve la ruta ordenada desde el principio del camino hasta llegar a 'fin'.
     */
    private List<String> reconstruirCamino(Map<String, String> predecesores, String fin) {
        List<String> camino = new ArrayList<>();
        String actual = fin;
        while (actual != null) {
            camino.add(0, actual);
            actual = predecesores.get(actual);
        }
        return camino;
    }

    /*
     * Pre: El grafo y el 'camino' deben existir, y el camino tiene que tener al menos dos nodos unidos.
     * Post: Devuelve un número que es la cantidad máxima de flujo que puede pasar por ese camino (es decir, el valor de la conexión más angosta o "cuello de botella").
     */
    private int calcularFlujoAumentante(GrafoFlujo grafo, List<String> camino) {
        int minimo = Integer.MAX_VALUE;
        for (int i = 0; i < camino.size() - 1; i++) {
            String origen = camino.get(i);
            String destino = camino.get(i + 1);
            for (AristaFlujo a : grafo.getAdyacentes(origen)) {
                if (a.getDestino().equals(destino)) {
                    minimo = Math.min(minimo, a.getCapacidadResidual());
                    break;
                }
            }
        }
        return minimo;
    }

    /*
     * Pre: El grafo y el 'camino' deben existir. 'flujo' debe ser el número positivo calculado previamente para ese camino.
     * Post: Suma esa cantidad de flujo a las conexiones que van hacia adelante y se la resta a las conexiones que van hacia atrás.
     */
    private void actualizarFlujo(GrafoFlujo grafo, List<String> camino, int flujo) {
        for (int i = 0; i < camino.size() - 1; i++) {
            String origen = camino.get(i);
            String destino = camino.get(i + 1);
            for (AristaFlujo a : grafo.getAdyacentes(origen)) {
                if (a.getDestino().equals(destino)) {
                    a.agregarFlujo(flujo);
                    break;
                }
            }
            for (AristaFlujo a : grafo.getAdyacentes(destino)) {
                if (a.getDestino().equals(origen)) {
                    a.agregarFlujo(-flujo);
                    break;
                }
            }
        }
    }

    /*
     * Pre: El grafo no debe ser nulo.
     * Post: Devuelve una copia nueva y separada de todas las aristas y sus flujos, ideal para sacar una "foto" del momento y guardarla en el historial.
     */
    private List<AristaFlujo> copiarAristas(GrafoFlujo grafo) {
        List<AristaFlujo> copia = new ArrayList<>();
        for (AristaFlujo a : grafo.getTodasLasAristas()) {
            AristaFlujo c = new AristaFlujo(a.getOrigen(), a.getDestino(), a.getCapacidad(), true);
            c.agregarFlujo(a.getFlujo());
            copia.add(c);
        }
        return copia;
    }
}
