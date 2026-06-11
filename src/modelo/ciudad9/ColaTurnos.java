package modelo.ciudad9;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Cola FIFO que gestiona el orden de los turnos en el combate.
 */
public class ColaTurnos {
    private final Queue<Personaje> cola;

    /**
     * Pre: Ninguna.
     * Post: Se inicializa una cola de turnos vacía.
     */
    public ColaTurnos() {
        this.cola = new LinkedList<>();
    }

    /**
     * Pre: 'personaje' no debe ser nulo.
     * Post: El personaje se inserta al final de la cola (FIFO).
     */
    public void encolar(Personaje personaje) {
        if (personaje == null) return; 
        this.cola.offer(personaje);
    }

    /**
     * Pre: Ninguna.
     * Post: Retorna y elimina el personaje al frente de la cola. Si la cola está vacía, retorna null.
     */
    public Personaje desencolar() {
        return this.cola.poll();
    }

    /**
     * Pre: Ninguna.
     * Post: Retorna el personaje al frente de la cola sin removerlo. Si la cola está vacía, retorna null.
     */
    public Personaje espiar() {
        return this.cola.peek(); 
    }

    /**
     * Pre: Ninguna.
     * Post: Retorna true si la cola no tiene elementos, false en caso contrario.
     */
    public boolean estaVacia() {
        return this.cola.isEmpty();
    }
}
