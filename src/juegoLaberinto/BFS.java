package juegoLaberinto;

import java.util.*;

public class BFS {

    public List<Snapshot> buscar(Laberinto lab) {

        Queue<Nodo> cola = new LinkedList<>();
        Set<String> visitados = new HashSet<>();
        Map<String, Nodo> padre = new HashMap<>();

        List<Snapshot> frames = new ArrayList<>();

        Nodo inicio = lab.getInicio();
        Nodo fin = lab.getFin();

        cola.add(inicio);
        visitados.add(key(inicio));
        
        Nodo encontrado = null;

        while (!cola.isEmpty()) {

            Nodo actual = cola.poll();

            // frame antes de expandir
            frames.add(crearSnapshot(lab, visitados, actual));

            if (actual.getFila() == fin.getFila() &&
                actual.getColumna() == fin.getColumna()) {
            	encontrado = actual;
                break;
            }

            for (Nodo vecino : lab.obtenerVecinos(actual)) {

                String k = key(vecino);

                if (!visitados.contains(k)) {

                    visitados.add(k);
                    padre.put(k, actual);
                    cola.add(vecino);
                }
            }

            // frame despues de expandir
            frames.add(crearSnapshot(lab, visitados, actual));
        }
        if (encontrado != null) {

            List<Nodo> camino =
                    reconstruirCamino(encontrado, padre);
            
            System.out.println("Movimientos BFS: " + (camino.size() - 1));

            frames.add(
                    crearSnapshotCamino(lab,
                                        visitados,
                                        camino));
        }

        return frames;
    }
    
    
    private String key(Nodo n) {
        return n.getFila() + "," + n.getColumna();
    }
    
    
    private Snapshot crearSnapshot(Laberinto lab, Set<String> visitados, Nodo actual) {

        char[][] copia = copiar(lab);

        for (String v : visitados) {
            String[] parts = v.split(",");
            int f = Integer.parseInt(parts[0]);
            int c = Integer.parseInt(parts[1]);

            if (copia[f][c] == '.') {
                copia[f][c] = '*'; // visitado
            }
        }

        copia[actual.getFila()][actual.getColumna()] = 'A'; // actual

        Snapshot s = new Snapshot();
        s.estado = copia;

        return s;
    }
    
    
    private char[][] copiar(Laberinto lab) {

        char[][] original = lab.getMapa(); // te falta getter
        char[][] copia = new char[original.length][original[0].length];

        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, copia[i], 0, original[i].length);
        }

        return copia;
    }
    
    
    private List<Nodo> reconstruirCamino(
            Nodo fin,
            Map<String, Nodo> padre) {

        List<Nodo> camino = new ArrayList<>();

        Nodo actual = fin;

        while (actual != null) {

            camino.add(actual);

            actual = padre.get(key(actual));
        }

        Collections.reverse(camino);

        return camino;
    }
    
    
    private Snapshot crearSnapshotCamino(
            Laberinto lab,
            Set<String> visitados,
            List<Nodo> camino) {

        char[][] copia = copiar(lab);

        for (String v : visitados) {

            String[] p = v.split(",");

            int f = Integer.parseInt(p[0]);
            int c = Integer.parseInt(p[1]);

            if (copia[f][c] == '.') {
                copia[f][c] = '*';
            }
        }

        for (Nodo n : camino) {

            char actual =
                    copia[n.getFila()][n.getColumna()];

            if (actual == '.' || actual == '*') {
                copia[n.getFila()][n.getColumna()] = 'P';
            }
        }

        Snapshot s = new Snapshot();
        s.estado = copia;

        return s;
    }
    
    
    
}