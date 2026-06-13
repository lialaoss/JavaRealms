package modelo.ciudad5;

/**
 * Clase que representa un nodo dentro del Árbol Binario.
 * Guarda la palabra y sus coordenadas, además de conectarse con sus nodos hijos.
 */

public class NodoArbol {
    public String palabra;
    public int linea;
    public int posicion;

    public NodoArbol izquierdo;
    public NodoArbol derecho;

    /**
     * Constructor
     * * @param palabra La palabra limpia que se va a guardar.
     * @param linea El número de línea del archivo donde apareció.
     * @param posicion El número de palabra dentro de esa línea.
     */
    
    public NodoArbol(String palabra, int linea, int posicion) {
        this.palabra = palabra;
        this.linea = linea;
        this.posicion = posicion;
        this.izquierdo = null;
        this.derecho = null;
    }
}
