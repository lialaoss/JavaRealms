package modelo.ciudad1;

public interface MapaLectura {
    boolean esCoordenadaValida(int x, int y, int z);
    Elemento obtenerElemento(int x, int y, int z);
    boolean estaRevelado(int x, int y, int z);
    void colocarElemento(int x, int y, int z, Elemento e);
    int getAncho();
    int getAlto();
    int getNiveles();
}