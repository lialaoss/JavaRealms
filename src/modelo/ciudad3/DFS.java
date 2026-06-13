package modelo.ciudad3;

import java.util.*;

public class DFS {

	/*
     * Pre: El 'lab' (laberinto) debe existir, estar bien cargado y tener definidos un nodo de inicio y uno de fin.
     * Post: Ejecuta una búsqueda en profundidad utilizando una pila para encontrar la salida. Devuelve una lista de snapshots ("fotogramas") que muestran el avance de la exploración paso a paso y la ruta final encontrada.
     */
    public List<Snapshot> buscar(Laberinto lab) {

        Stack<Nodo> pila = new Stack<>();
        Set<String> visitados = new HashSet<>();
        Map<String, Nodo> padre = new HashMap<>();

        List<Snapshot> frames = new ArrayList<>();

        Nodo inicio = lab.getInicio();
        Nodo fin = lab.getFin();

        Nodo encontrado = null;

        pila.push(inicio);
        visitados.add(key(inicio));

        while (!pila.isEmpty()) {
            Nodo actual = pila.pop();

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

                    pila.push(vecino);
                }
            }

            frames.add(crearSnapshot(lab, visitados, actual));
        }

        // Reconstrucción del camino
        if (encontrado != null) {
            List<Nodo> camino =
                reconstruirCamino(encontrado, padre);

            System.out.println(
                "Movimientos DFS: " + (camino.size() - 1)
            );

            frames.add(
                crearSnapshotCamino(
                    lab,
                    visitados,
                    camino
                )
            );
        }
        return frames;
    }

    /*
     * Pre: El nodo 'fin' debe ser válido y el mapa de 'padre' tiene que contener las conexiones hacia atrás desde cada nodo explorado.
     * Post: Reconstruye la ruta desde el final hacia el principio siguiendo los padres guardados, la da vuelta para que quede en orden cronológico y devuelve la lista de nodos del camino.
     */
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

    /*
     * Pre: El nodo 'n' no debe ser nulo.
     * Post: Devuelve un texto con las coordenadas del nodo separadas por coma (ejemplo: "4,2"), ideal para usarlo como clave única en conjuntos o mapas.
     */
    private String key(Nodo n) {
        return n.getFila() + "," + n.getColumna();
    }

    /*
     * Pre: El laberinto, la lista de visitados y el nodo actual deben existir y ser válidos.
     * Post: Crea y devuelve una foto del mapa marcando con un '*' los casilleros ya visitados por la búsqueda y con una 'A' el casillero donde está parado el algoritmo actualmente.
     */
    private Snapshot crearSnapshot(Laberinto lab,
                                   Set<String> visitados,
                                   Nodo actual) {

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

    /*
     * Pre: El laberinto, la lista de visitados y el camino final no deben ser nulos.
     * Post: Crea y devuelve un snapshot final del mapa donde, además de marcar los casilleros visitados con '*', resalta los nodos que forman la solución definitiva pintándolos con una 'P'.
     */
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

    /*
     * Pre: El laberinto debe tener una grilla de mapa válida cargada internamente.
     * Post: Devuelve una matriz de caracteres totalmente nueva que es una copia idéntica (espejo) de la grilla del laberinto original.
     */
    private char[][] copiar(Laberinto lab) {

        char[][] original = lab.getMapa();
        char[][] copia = new char[original.length][original[0].length];

        for (int i = 0; i < original.length; i++) {
            System.arraycopy(
                original[i],
                0,
                copia[i],
                0,
                original[i].length
            );
        }

        return copia;
    }
}