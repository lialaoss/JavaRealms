package render;

import java.awt.Graphics2D;
import java.awt.Image;

import entidad.Jugador;
import modelo.ciudad1.PartidaLectura;
import ui.ConfiguracionPantalla;
import ui.GestorRecursos;

public class RenderJugador {
	
	private final int TILE_SIZE = ConfiguracionPantalla.TILE_SIZE;
	
	private GestorRecursos recursos;
	private Jugador jugador;

	public RenderJugador(GestorRecursos recursos, Jugador jugador) {
		this.recursos = recursos;
		this.jugador = jugador;
	}
	
	public void render(Graphics2D g2, PartidaLectura estadoActual,
            int offsetX, int offsetY) {

			int pantallaX = offsetX + estadoActual.getX() * TILE_SIZE;
			int pantallaY = offsetY + estadoActual.getY() * TILE_SIZE;
			
			Image img = direccion();
			
			if (img != null) {
				 g2.drawImage(
				     img,
				     pantallaX,
				     pantallaY,
				     TILE_SIZE,
				     TILE_SIZE,
				     null
				 );
			}
}
	
	public Image direccion() {
		switch(jugador.getDireccion()) {
			case DOWN:
				return recursos.getJugadorDown()[0];
			case UP:
				return recursos.getJugadorUp()[0];
			case LEFT:
				return recursos.getJugadorLeft()[0];
			case RIGHT:
				return recursos.getJugadorRight()[0];
			default:
				throw new RuntimeException("No se encontro el sprite de jugador");
			}
	}

}
