package modelo.ciudad9;

import java.util.Stack;

import utiles.ValidacionesUtiles;

/*
  Pila LIFO que almacena las acciones seleccionadas por el jugador.
 */
public class PilaAcciones {
    private final Stack<Accion> acciones;

    /*
     Pre: Ninguna.
     Post: Se inicializa la pila de acciones vacía.
     */
    public PilaAcciones() {
        this.acciones = new Stack<>();
    }

    /*
     Pre: 'accion' no debe ser nula.
     Post: La acción es insertada en la cima de la pila (LIFO).
     */
    public void apilarAccion(Accion accion) {
        if (ValidacionesUtiles.esNulo(accion)) return; 
        this.acciones.push(accion);
    }

    /*
     Pre: Ninguna.
     Post: Devuelve y elimina la acción en la cima de la pila. Devuelve null si está vacía.
     */
    public Accion desapilarAccion() {
        if (acciones.isEmpty()) return null;
        return this.acciones.pop();
    }
}