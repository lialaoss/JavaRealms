package grafos;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Representa un Vértice (nodo) en el grafo.
 * @param <T> El tipo del valor almacenado en el vértice.
 * @param <U> El tipo del peso almacenado en las aristas.
 */
public class Vertice<T, U> {
    private T valor;
    // Esta es tu "sublista de aristas"
    private List<Arista<T, U>> adyacencias;

    public Vertice(T valor) {
        this.valor = valor;
        this.adyacencias = new LinkedList<>(); // Usamos LinkedList por eficiencia en inserción
    }

    public T getValor() {
        return valor;
    }

    public List<Arista<T, U>> getAdyacencias() {
        return adyacencias;
    }

    /**
     * Método de conveniencia para agregar una arista saliente desde ESTE vértice.
     */
    public void agregarArista(Vertice<T, U> destino, U peso) {
        Arista<T, U> nuevaArista = new Arista<>(peso, destino);
        this.adyacencias.add(nuevaArista);
    }

    // Es crucial implementar equals y hashCode para que el TDA Grafo
    // pueda encontrar vértices rápidamente usando su valor.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertice<?, ?> vertice = (Vertice<?, ?>) o;
        // Dos vértices son iguales si sus valores son iguales.
        return Objects.equals(valor, vertice.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return "Vertice(" + valor + ")";
    }
}
