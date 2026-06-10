package render;

import java.awt.Color;
import java.awt.Graphics2D;

import modelo.PartidaLectura;
import ui.GestorRecursos;

public class RenderJugador {
	
	private GestorRecursos recursos;

	public RenderJugador(GestorRecursos recursos) {
		this.recursos = recursos;
	}
	
	public void render(Graphics2D g2, PartidaLectura estadoActual) {
		g2.setColor(Color.BLUE);
		int pantallaX = estadoActual.getX() * 48;
		int pantallaY = estadoActual.getY() * 48;

		g2.fillRect(
		    pantallaX,
		    pantallaY,
		    48,
		    48
		);
	}

}
