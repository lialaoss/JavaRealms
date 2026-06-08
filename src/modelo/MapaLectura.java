package modelo;

public interface MapaLectura {
    boolean esCoordenadaValida(int x, int y, int z);
    Elemento obtenerElemento(int x, int y, int z);
    boolean estaRevelado(int x, int y, int z);
    int getAncho();
    int getAlto();
    int getNiveles();
}