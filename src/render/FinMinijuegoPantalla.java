package render;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import ciudades.Ciudad;
import ui.ConfiguracionPantalla;

public class FinMinijuegoPantalla {
	
	public void mostrarResultados(Graphics2D g2, Ciudad ciudad) {
		g2.setColor(new Color(0, 0, 0, 180));
		g2.fillRect(
		    0,
		    0,
		    ConfiguracionPantalla.SCREEN_WIDTH,
		    ConfiguracionPantalla.SCREEN_HEIGHT
		);
		
		String texto = "Puntos de experiencia ganados : "
		        + ciudad.getPuntosDeExperiencia()
		        + " ptos !!!";

		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", Font.BOLD, 30));

		FontMetrics fm = g2.getFontMetrics();

		int x = (ConfiguracionPantalla.SCREEN_WIDTH - fm.stringWidth(texto)) / 2;
		int y = ConfiguracionPantalla.SCREEN_HEIGHT / 2;

		g2.drawString(texto, x, y);
	}

}
