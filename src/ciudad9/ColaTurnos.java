package ciudad9;

import java.util.LinkedList;
import java.util.Queue;

/*
  Cola FIFO que gestiona el orden de los turnos en el combate.
 */
public class ColaTurnos {
    private final Queue<Personaje> cola;

    /*
     Pre: Ninguna.
     Post: Se inicializa la cola de turnos vacía.
     */
    public ColaTurnos() {
        this.cola = new LinkedList<>();
    }

    /*
     Pre: 'personaje' no debe ser nulo.
     Post: El personaje se inserta al final de la cola (FIFO).
     */
    public void encolar(Personaje personaje) {
        if (ValidacionesUtiles.esNulo(personaje)) return; 
        this.cola.offer(personaje);
    }

    /*
     Pre: Ninguna.
     Post: Devuelve y elimina el elemento al frente de la cola. Devuelve null si está vacía.
     */
    public Personaje desencolar() {
        return this.cola.poll();
    }

    /*
     Pre Ninguna.
     Post: Devuelve el elemento al frente sin eliminarlo. Devuelve null si está vacía.
     */
    public Personaje espiar() {
        return this.cola.peek(); 
    }

    /*
     Pre: Ninguna.
     Post: Retorna true si la cola no tiene elementos.
     */
    public boolean estaVacia() {
        return this.cola.isEmpty();
    }
}