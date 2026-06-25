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

	/*
	 * Pre: El 'gestorRecursos' y el 'jugador' no deben ser nulos y estar bien inicializados.
	 * Post: Crea el componente encargado de dibujar al jugador en pantalla vinculando sus recursos de imágenes y sus datos lógicos.
	 */
	public RenderJugador(GestorRecursos recursos, Jugador jugador) {
		this.recursos = recursos;
		this.jugador = jugador;
	}
	
	/*
	 * Pre: El motor gráfico 'g2' y el 'estadoActual' de la partida no deben ser nulos. 'offsetX' y 'offsetY' deben ser los desplazamientos de la cámara.
	 * Post: Calcula las coordenadas en píxeles dentro de la pantalla y dibuja la imagen del jugador con el tamaño correcto (TILE_SIZE) según su dirección actual.
	 */
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
	
	/*
	 * Pre: El jugador debe tener una dirección asignada (UP, DOWN, LEFT, RIGHT) y un número de sprite válido (1 o 2).
	 * Post: Devuelve la imagen (sprite) correspondiente a la dirección hacia la que está mirando el jugador y al paso de la animación en el que se encuentra.
	 */
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
