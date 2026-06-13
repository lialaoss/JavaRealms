package modelo.ciudad5;

public class ArbolABB {
    private NodoArbol raiz;
    private int operacionesUltimaBusqueda;

    public ArbolABB() {
        this.raiz = null;
    }

    /**
     * Inserta una palabra alfabéticamente en el árbol de forma jerárquica.
     * * PRE: palabra != null (La cadena no puede estar vacía).
     * linea y posicion deben ser mayores a 0.
     * POST: la palabra se pasa a minúsculas, se limpia de espacios y se ubica 
     * en el lugar que le corresponde, menores a la izquierda y mayores a la derecha.
     * * @param palabra La palabra que se va a guardar.
     * @param linea Número de línea del archivo de texto.
     * @param posicion Ubicación de la palabra dentro de esa línea.
     */
    
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

    /**
     * Busca una palabra recorriendo las ramas del árbol y cuenta las operaciones.
     * * PRE: palabraBuscada != null.
     * POST: retorna el NodoArbol si encuentra la palabra o null en caso no exista. 
     * Modifica el contador operacionesUltimaBusqueda con los ciclos dados.
     * * @param palabraBuscada La palabra que queremos encontrar.
     * @return NodoArbol El nodo que contiene la palabra y sus coordenadas, o null.
     */

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
