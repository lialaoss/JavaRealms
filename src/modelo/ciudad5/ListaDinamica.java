package modelo.ciudad5;

public class ListaDinamica {
    private NodoLista cabeza;
    private int operacionesUltimaBusqueda;

    public ListaDinamica() {
        this.cabeza = null;
    }

    /**
     * Inserta un nuevo elemento al principio de la lista enlazada.
     * * PRE: palabra != null.
     * POST: El nuevo vágon de datos se engancha en la cabeza de la lista.
     * * @param palabra Texto a almacenar.
     * @param linea Línea del archivo de origen.
     * @param posicion Orden de la palabra en la línea.
     */
    
    public void insertar(String palabra, int linea, int posicion) {
        NodoLista nuevoVagon = new NodoLista(palabra, linea, posicion);
        nuevoVagon.siguiente = cabeza;
        cabeza = nuevoVagon;
    }

    /**
     * Busca una palabra de forma lineal, revisando nodo por nodo desde el inicio,
     * * PRE: palabraBuscada != null.
     * POST: Retorna el NodoLista si lo halla o null si llega al final de la lista
     * Deja guardada la cantidad total de comparaciones realizadas
     * * @param palabraBuscada Texto que se desea buscar.
     * @return NodoLista Referencia del nodo encontrado o null si no existe.
     */
    
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

    public int getOperacionesUltimaBusqueda() {
        int resultado = operacionesUltimaBusqueda;
        return resultado;
    }
}
