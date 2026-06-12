package logica;

public class NodoArbol {
    public String palabra;
    public int linea;
    public int posicion;

    public NodoArbol izquierdo;
    public NodoArbol derecho;

    public NodoArbol(String palabra, int linea, int posicion) {
        this.palabra = palabra;
        this.linea = linea;
        this.posicion = posicion;
        this.izquierdo = null;
        this.derecho = null;
    }
}
