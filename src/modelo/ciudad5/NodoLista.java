package modelo.ciudad5;

/**
 * Clase que representa un nodo (o vagón) dentro de la Lista Dinámica.
 * Guarda la palabra, sus coordenadas y se conecta con el siguiente elemento.
 */

public class NodoLista {
    public String palabra;
    public int linea;
    public int posicion;

    public NodoLista siguiente;

    /**
     * Constructor
     * * @param palabra La palabra limpia que se va a guardar.
     * @param linea El número de línea del archivo donde apareció.
     * @param posicion El número de palabra dentro de esa línea.
     */
    
    public NodoLista(String palabra, int linea, int posicion) {
        this.palabra = palabra;
        this.linea = linea;
        this.posicion = posicion;
        this.siguiente = null;
    }
}
