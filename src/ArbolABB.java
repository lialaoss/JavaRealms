package logica;

public class ArbolABB {
    private NodoArbol raiz;
    private int operacionesUltimaBusqueda;

    public ArbolABB() {
        this.raiz = null;
    }

    public void insertar(String palabra, int linea, int posicion) {
        String palabraLimpia = palabra.toLowerCase().trim();
        NodoArbol nuevoNodo = new NodoArbol(palabraLimpia, linea, posicion);

        if (raiz == null) {
            raiz = nuevoNodo;
        } else {
            NodoArbol actual = raiz;
            NodoArbol padre = null;
            boolean encontradoLugar = false;

            while (actual != null && !encontradoLugar) {
                padre = actual;
                int comparacion = palabraLimpia.compareTo(actual.palabra);

                if (comparacion < 0) {
                    actual = actual.izquierdo;
                    if (actual == null) {
                        padre.izquierdo = nuevoNodo;
                        encontradoLugar = true;
                    }
                } else if (comparacion > 0) {
                    actual = actual.derecho;
                    if (actual == null) {
                        padre.derecho = nuevoNodo;
                        encontradoLugar = true;
                    }
                } else {
                    encontradoLugar = true;
                }
            }
        }
    }

    public NodoArbol buscar(String palabraBuscada) {
        operacionesUltimaBusqueda = 0;

        NodoArbol actual = raiz;
        NodoArbol nodoEncontrado = null;
        String objetivo = palabraBuscada.toLowerCase().trim(); 
        boolean terminado = false;

        while (actual != null && !terminado) {
            operacionesUltimaBusqueda++;

            if (actual.palabra.equals(objetivo)) {
                nodoEncontrado = actual;
                terminado = true;
            } else {
                int comparacion = objetivo.compareTo(actual.palabra);

                if (comparacion < 0) {
                    actual = actual.izquierdo;
                } else {
                    actual = actual.derecho;
                }
            }
        }
        return nodoEncontrado;
    }

    public int getOperacionesUltimaBusqueda() {
        int resultado = operacionesUltimaBusqueda;
        return resultado;
    }
}