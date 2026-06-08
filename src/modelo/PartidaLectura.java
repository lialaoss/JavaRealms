package modelo;

import java.util.List;

public interface PartidaLectura {
    int getX();
    int getY();
    int getZ();
    int getRadioVision();
    MapaLectura getMapa();
    List<Elemento> getMochila();
    Jugador getJugador();
}