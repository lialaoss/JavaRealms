package ciudad9;
import java.util.Stack;

/**
 * Gestiona las acciones del jugador mediante una pila.
 */
public class PilaAcciones {

    private Stack<Accion> acciones;

    public PilaAcciones() {
        acciones = new Stack<>();
    }

    /**
     * Agrega una acción a la pila.
     */
    public void apilarAccion(Accion accion) {

        if (accion != null) {
            acciones.push(accion);
        }
    }

    /**
     * Obtiene la última acción cargada.
     */
    public Accion desapilarAccion() {

        if (acciones.isEmpty()) {
            return null;
        }

        return acciones.pop();
    }

    public boolean estaVacia() {
        return acciones.isEmpty();
    }

    public int cantidadAcciones() {
        return acciones.size();
    }
}