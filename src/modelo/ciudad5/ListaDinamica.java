package modelo.ciudad5;

public class ListaDinamica {
    private NodoLista cabeza;
    private int operacionesUltimaBusqueda;

    public ListaDinamica() {
        this.cabeza = null;
    }

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

    public int getOperacionesUltimaBusqueda() {
        int resultado = operacionesUltimaBusqueda;
        return resultado;
    }
}
