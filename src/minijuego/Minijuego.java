package minijuego;

import java.awt.Graphics2D;

public interface Minijuego {
	
	public void resultadoPartida();

	public void render(Graphics2D g2);
	
	public void desbloquearVecinos();

	public void iniciar();

}
