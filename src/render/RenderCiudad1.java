package render;

import java.awt.Color;
import java.awt.Graphics2D;

import modelo.ciudad1.MapaLectura;
import modelo.ciudad1.PartidaLectura;
import ui.GestorRecursos;

public class RenderCiudad1 {
	
	private final int TILE_SIZE = 48; // estoy pensando como tener esto para todos los mapas ahre
	
	private GestorRecursos recursos;
	
	public RenderCiudad1(GestorRecursos recursos) {
		this.recursos = recursos;
	}
	
	public void render(Graphics2D g2, MapaLectura mapa, PartidaLectura estadoActual,
			String mensajeRadar, String mensajeRecoleccion) {
		renderMapa(mapa, g2);
	    // Por ahora: dibuja posición del jugador y mensajes
	    // Tu amiga puede reemplazar esto con los BMP reales
	    g2.setColor(Color.WHITE);
	    g2.drawString("Ciudad 1 - Recolección", 50, 50);
	    g2.drawString("Pos: X=" + estadoActual.getX()
	                + " Y=" + estadoActual.getY()
	                + " Z=" + estadoActual.getZ(), 50, 80);
	    g2.drawString("Radio visión: " + estadoActual.getRadioVision(), 50, 110);
	
	    if (!mensajeRadar.isEmpty()) {
	        g2.setColor(Color.CYAN);
	        g2.drawString(mensajeRadar, 50, 140);
	    }
	    if (!mensajeRecoleccion.isEmpty()) {
	        g2.setColor(Color.YELLOW);
	        g2.drawString(mensajeRecoleccion, 50, 170);
	    }
	}
	
	private void renderMapa(MapaLectura mapa, Graphics2D g2) {
		for(int x = 0; x < mapa.getAncho(); x++) {
		    for(int y = 0; y < mapa.getAlto(); y++) {

		        int pantallaX = x * TILE_SIZE;
		        int pantallaY = y * TILE_SIZE;

		        g2.drawRect(
		            pantallaX,
		            pantallaY,
		            TILE_SIZE,
		            TILE_SIZE
		        );
		    }
		}
	}

}
