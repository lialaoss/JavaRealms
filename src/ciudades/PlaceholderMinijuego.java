package ciudades;

import java.awt.Color;
import java.awt.Graphics2D;
import entidad.Jugador;
import ui.GestorRecursos;

public class PlaceholderMinijuego implements Minijuego {

    private Ciudad ciudad;
    private Jugador jugador;
    private GestorRecursos recursos;

    public PlaceholderMinijuego(Ciudad ciudad, Jugador jugador, GestorRecursos recursos) {
        this.ciudad = ciudad;
        this.jugador = jugador;
        this.recursos = recursos;
    }

    @Override
    public void iniciar() {}

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.drawString("Ciudad " + ciudad.getId() + " - " + ciudad.getNombre(), 50, 50);
        g2.setColor(Color.GRAY);
        g2.drawString("[ En construcción ]", 50, 80);
        g2.drawString("Q para volver al mapa", 50, 110);
    }

    @Override
    public void resultadoPartida() {}

    @Override
    public void desbloquearVecinos() {}
}
