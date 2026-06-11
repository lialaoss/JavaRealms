package modelo.ciudad7;

import java.util.*;

public class AlgoritmosFlujo {

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

    public List<String> caminoMinimo(GrafoFlujo grafo, String inicio, String fin) {
        return bfsAumentante(grafo, inicio, fin);
    }

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

    private List<String> reconstruirCamino(Map<String, String> predecesores, String fin) {
        List<String> camino = new ArrayList<>();
        String actual = fin;
        while (actual != null) {
            camino.add(0, actual);
            actual = predecesores.get(actual);
        }
        return camino;
    }

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

    private List<AristaFlujo> copiarAristas(GrafoFlujo grafo) {
        List<AristaFlujo> copia = new ArrayList<>();
        for (AristaFlujo a : grafo.getTodasLasAristas()) {
            AristaFlujo c = new AristaFlujo(a.getOrigen(), a.getDestino(), a.getCapacidad());
            c.agregarFlujo(a.getFlujo());
            copia.add(c);
        }
        return copia;
    }
}
