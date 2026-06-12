package modelo.ciudad9;

import java.util.Stack;

/**
 * Pila LIFO que almacena las acciones seleccionadas por el jugador.
 */
public class PilaAcciones {
    private final Stack<Accion> acciones;

    /**
     * Pre: Ninguna.
     * Post: Se inicializa una pila de acciones vacía.
     */
    public PilaAcciones() {
        this.acciones = new Stack<>();
    }

    /**
     * Pre: 'accion' no debe ser nula.
     * Post: La acción es apilada en la cima de la estructura (LIFO).
     */
    public void apilarAccion(Accion accion) {
        if (accion == null) return; 
        this.acciones.push(accion);
    }

    /**
     * Pre: Ninguna.
     * Post: Retorna y elimina la acción en la cima de la pila. Si la pila está vacía, retorna null.
     */
    public Accion desapilarAccion() {
        if (acciones.isEmpty()) return null;
        return this.acciones.pop();
    }
}
