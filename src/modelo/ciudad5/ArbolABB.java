package modelo.ciudad5;

/*
 * Pre: Ninguna.
 * Post: Crea un árbol binario de búsqueda vacío (con su raíz apuntando a null).
 */
public class ArbolABB {
    private NodoArbol raiz;
    private int operacionesUltimaBusqueda;

    public ArbolABB() {
        this.raiz = null;
    }

    /*
     * Pre: La 'palabra' no debe ser nula ni vacía. 'linea' y 'posicion' deben ser números válidos mayores o iguales a cero.
     * Post: Limpia la palabra pasándola a minúsculas, busca la posición que le corresponde alfabéticamente en el árbol e inserta el nuevo nodo (si la palabra ya existía, no hace nada).
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

    /*
     * Pre: La 'palabraBuscada' no debe ser nula.
     * Post: Recorre el árbol comparando los textos. Devuelve el nodo completo si encuentra la palabra, o null si no existe. Además, deja registrado cuántas comparaciones (operaciones) hizo en el intento.
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

    /*
     * Pre: Ninguna.
     * Post: Devuelve la cantidad de nodos visitados durante la última búsqueda que se ejecutó en el árbol.
     */
    public int getOperacionesUltimaBusqueda() {
        int resultado = operacionesUltimaBusqueda;
        return resultado;
    }
}
