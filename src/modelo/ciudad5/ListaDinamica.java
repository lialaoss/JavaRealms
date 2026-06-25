package modelo.ciudad5;


public class ListaDinamica {
    private NodoLista cabeza;
    private int operacionesUltimaBusqueda;

    /*
     * Pre: Ninguna.
     * Post: Crea una lista dinámica vacía (donde la cabeza o primer elemento apunta a null).
     */
    public ListaDinamica() {
        this.cabeza = null;
    }

    /*
     * Pre: La 'palabra' no debe ser nula. 'linea' y 'posicion' deben ser números válidos mayores o iguales a cero.
     * Post: Crea un nuevo nodo ("vagon") y lo conecta al principio de la lista, transformándolo en la nueva cabeza.
     */
    public void insertar(String palabra, int linea, int posicion) {
        NodoLista nuevoVagon = new NodoLista(palabra, linea, posicion);
        nuevoVagon.siguiente = cabeza;
        cabeza = nuevoVagon;
    }
    
    public NodoLista buscarLineal(String palabraBuscada) {
        operacionesUltimaBusqueda = 0;

        NodoLista actual = cabeza;
        NodoLista vagonEncontrado = null;
        String objetivo = palabraBuscada.toLowerCase().trim();
        boolean terminado = false;

        while (actual != null && !terminado) {
            operacionesUltimaBusqueda++;

            if (actual.palabra.equals(objetivo)) {
                vagonEncontrado = actual;
                terminado = true;
            } else {
                actual = actual.siguiente;
            }
        }
        return vagonEncontrado;
    }

    /*
     * Pre: Ninguna.
     * Post: Devuelve la cantidad de nodos que se tuvieron que examinar durante la última búsqueda lineal realizada.
     */
    public int getOperacionesUltimaBusqueda() {
        int resultado = operacionesUltimaBusqueda;
        return resultado;
    }
}
