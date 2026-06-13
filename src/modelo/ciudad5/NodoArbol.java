package modelo.ciudad5;

public class NodoArbol {
    public String palabra;
    public int linea;
    public int posicion;

    public NodoArbol izquierdo;
    public NodoArbol derecho;

    /*
     * Pre: El parámetro 'palabra' no debe ser nulo. 'linea' y 'posicion' deben ser números válidos mayores o iguales a cero.
     * Post: Crea un nuevo nodo para el árbol binario, guardando la palabra con su ubicación en el texto y dejando sus ramas izquierda y derecha vacías (apuntando a null).
     */
    public NodoArbol(String palabra, int linea, int posicion) {
        this.palabra = palabra;
        this.linea = linea;
        this.posicion = posicion;
        this.izquierdo = null;
        this.derecho = null;
    }
}
