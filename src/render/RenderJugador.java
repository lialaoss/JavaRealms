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
				if(jugador.numeroDeSprite == 1) {
					return recursos.getJugadorDown()[0];
				}
				return recursos.getJugadorDown()[1];
			case UP:
				if(jugador.numeroDeSprite == 1) {
					return recursos.getJugadorUp()[0];
				}
				return recursos.getJugadorUp()[1];
			case LEFT:
				if(jugador.numeroDeSprite == 1) {
					return recursos.getJugadorLeft()[0];
				}
				return recursos.getJugadorLeft()[1];
			case RIGHT:
				if(jugador.numeroDeSprite == 1) {
					return recursos.getJugadorRight()[0];
				}
				return recursos.getJugadorRight()[1];
			default:
				throw new RuntimeException("No se encontro el sprite de jugador");
			}
	}

}
