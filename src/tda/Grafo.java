package tda;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

/**
 * TDA Grafo (Dirigido y Ponderado)
 * Implementado con una "lista de vértices" (un Map para eficiencia)
 * donde cada vértice tiene su "sublista de aristas".
 *
 * @param <T> El tipo del valor almacenado en los vértices (ej. String, Integer)
 * @param <U> El tipo del peso almacenado en las aristas (ej. Integer, Double)
 */
public class Grafo<T, U> {

    // Esta es tu "lista de vértices", implementada como un Map
    // para búsquedas eficientes O(1) usando el valor T.
    private List<Vertice<T, U>> vertices;
    
    public Grafo() {
        this.vertices = new ArrayList<>();
    }

    /**
     * Agrega un nuevo vértice al grafo.
     * @param valor El valor del vértice (su "ID" o "payload").
     */
    public void agregarVertice(T valor) {
        if (!existeVertice(valor)) {
            Vertice<T, U> nuevoVertice = new Vertice<>(valor);
            vertices.add(nuevoVertice);
        }
    }

    /**
     * Agrega una arista dirigida desde un vértice origen a uno destino.
     * @param origenValor El valor del vértice de origen.
     * @param destinoValor El valor del vértice de destino.
     * @param peso El peso (tipo U) de la arista.
     */
    public void agregarArista(T origenValor, T destinoValor, U peso) {
        Vertice<T, U> origen = getVertice(origenValor);
        Vertice<T, U> destino = getVertice(destinoValor);

        // El vértice origen mantiene su propia "sublista de aristas"
        origen.agregarArista(destino, peso);
    }

    /**
     * Devuelve la colección de todos los vértices del grafo.
     * (Esta es tu "lista de vértices").
     */
    public Collection<Vertice<T, U>> getVertices() {
        return vertices;
    }

    /**
     * Obtiene el objeto Vértice basado en su valor.
     */
    public Vertice<T, U> getVertice(T valor) {
    	for (Vertice<T, U> v : vertices) {
            if (v.getValor().equals(valor)) {
                return v;
            }
        }
        throw new NoSuchElementException("El vértice no existe: " + valor);
    }
    
    public boolean existeVertice(T valor) {
    	for (Vertice<T, U> v : vertices) {
            if (v.getValor().equals(valor)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene la "sublista de aristas" (adyacencias) para un vértice dado.
     */
    public List<Arista<T, U>> getAdyacentes(T valor) {
        return getVertice(valor).getAdyacencias();
    }
    
    public U getPeso(T origen, T destino) {
        for (Arista<T, U> arista : getAdyacentes(origen)) {
            if (arista.getDestino().getValor().equals(destino)) {
                return arista.getPeso();
            }
        }
        throw new NoSuchElementException("No existe arista");
    }

    /**
     * Encuentra el camino más corto (en número de aristas) entre dos vértices.
     * Usa BFS.
     * 
     * 
     * 
     * @param valorInicio El valor del vértice de inicio.
     * @param valorFin El valor del vértice de fin.
     * @param filtro : filtra reglas que se le indiquen para tomar en cuenta al momento de
     * buscar vertices y armar un camino.
     * @return Una lista de valores (el camino) o una lista vacía si no hay camino.
     */
    public List<T> caminoMinimoBFS(T valorInicio, T valorFin, Predicate<T> esValido) {
        Vertice<T, U> inicio = getVertice(valorInicio);
        Vertice<T, U> fin = getVertice(valorFin);

        Queue<Vertice<T, U>> cola = new LinkedList<>();
        Set<Vertice<T, U>> visitados = new HashSet<>();
        // Mapa para reconstruir el camino (hijo -> padre)
        Map<Vertice<T, U>, Vertice<T, U>> predecesores = new HashMap<>();

        cola.add(inicio);
        visitados.add(inicio);
        predecesores.put(inicio, null); // El inicio no tiene predecesor
        
        boolean encontrado = false;

        while (!cola.isEmpty() && !encontrado) {
            Vertice<T, U> actual = cola.poll();

            if (actual.equals(fin)) {
                encontrado = true;
                break;
            }

            for (Arista<T, U> arista : actual.getAdyacencias()) {
                Vertice<T, U> vecino = arista.getDestino();
                if (!visitados.contains(vecino) && 
                		(esValido.test(vecino.getValor()))) {
                    visitados.add(vecino);
                    cola.add(vecino);
                    predecesores.put(vecino, actual); // Guardamos el camino
                }
            }
        }

        // Reconstruir el camino
        List<T> camino = new LinkedList<>(); // Usamos LinkedList por addFirst
        if (encontrado) {
            Vertice<T, U> paso = fin;
            while (paso != null) {
                camino.add(0, paso.getValor()); // add(0) es como addFirst
                paso = predecesores.get(paso);
            }
        }
        return camino; // Devuelve lista vacía si no se encontró
    }
    
}