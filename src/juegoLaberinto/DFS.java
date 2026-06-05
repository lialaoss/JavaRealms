package juegoLaberinto;

import java.util.*;

public class DFS {

    public List<Snapshot> buscar(Laberinto lab) {

        Stack<Nodo> pila = new Stack<>();
        Set<String> visitados = new HashSet<>();

        List<Snapshot> frames = new ArrayList<>();

        Nodo inicio = lab.getInicio();
        Nodo fin = lab.getFin();

        pila.push(inicio);
        visitados.add(key(inicio));

        while (!pila.isEmpty()) {

            Nodo actual = pila.pop();

            frames.add(crearSnapshot(lab, visitados, actual));

            if (actual.getFila() == fin.getFila() &&
                actual.getColumna() == fin.getColumna()) {
                break;
            }

            for (Nodo vecino : lab.obtenerVecinos(actual)) {

                String k = key(vecino);

                if (!visitados.contains(k)) {
                    visitados.add(k);
                    pila.push(vecino);
                }
            }

            frames.add(crearSnapshot(lab, visitados, actual));
        }

        return frames;
    }

    private String key(Nodo n) {
        return n.getFila() + "," + n.getColumna();
    }

    private Snapshot crearSnapshot(Laberinto lab, Set<String> visitados, Nodo actual) {

        char[][] copia = copiar(lab);

        for (String v : visitados) {
            String[] p = v.split(",");
            int f = Integer.parseInt(p[0]);
            int c = Integer.parseInt(p[1]);

            if (copia[f][c] == '.') {
                copia[f][c] = '*';
            }
        }

        copia[actual.getFila()][actual.getColumna()] = 'A';

        Snapshot s = new Snapshot();
        s.estado = copia;

        return s;
    }

    private char[][] copiar(Laberinto lab) {

        char[][] original = lab.getMapa();
        char[][] copia = new char[original.length][original[0].length];

        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, copia[i], 0, original[i].length);
        }

        return copia;
    }
    
    
    
}