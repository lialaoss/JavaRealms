package modelo.ciudad5;

public class NodoLista {
    public String palabra;
    public int linea;
    public int posicion;

    public NodoLista siguiente;

    /*
     * Pre: El parámetro 'palabra' no debe ser nulo. 'linea' y 'posicion' deben ser números válidos mayores o iguales a cero.
     * Post: Crea un nuevo nodo ("vágon") para la lista, guardando la palabra con su ubicación y dejándolo suelto, sin ningún nodo que le siga (siguiente apunta a null).
     */
    public NodoLista(String palabra, int linea, int posicion) {
        this.palabra = palabra;
        this.linea = linea;
        this.posicion = posicion;
        this.siguiente = null;
    }
}
