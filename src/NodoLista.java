package logica;

public class NodoLista {
    public String palabra;
    public int linea;
    public int posicion;

    public NodoLista siguiente;

    public NodoLista(String palabra, int linea, int posicion) {
        this.palabra = palabra;
        this.linea = linea;
        this.posicion = posicion;
        this.siguiente = null;
    }
}
