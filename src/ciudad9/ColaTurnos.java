package ciudad9;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Gestiona el orden de turnos mediante FIFO.
 */
public class ColaTurnos {

    private Queue<Personaje> colaTurnos;

    public ColaTurnos() {
        colaTurnos = new LinkedList<>();
    }

    /**
     * Inserta un personaje al final de la cola.
     */
    public void encolar(Personaje personaje) {

        if (personaje != null) {
            colaTurnos.offer(personaje);
        }
    }

    /**
     * Obtiene el siguiente turno.
     */
    public Personaje desencolar() {
        return colaTurnos.poll();
    }

    public boolean estaVacia() {
        return colaTurnos.isEmpty();
    }

    public int cantidadElementos() {
        return colaTurnos.size();
    }
}