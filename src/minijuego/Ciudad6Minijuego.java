package minijuego;

import java.awt.Graphics2D;

import ciudades.Ciudad;
import ciudades.EstadoCiudad;

import java.awt.Color;
import entidad.Jugador;
import modelo.ciudad6.VentanaCiudad6;

public class Ciudad6Minijuego implements Minijuego {

    private Ciudad ciudad;
    private Jugador jugador;
    private VentanaCiudad6 ventana;
    private boolean iniciado = false;

    public Ciudad6Minijuego(Ciudad ciudad, Jugador jugador) {
        this.ciudad = ciudad;
        this.jugador = jugador;
    }

    @Override
    public void iniciar() {
        ventana = new VentanaCiudad6(this);
        ventana.setVisible(true);
        iniciado = true;
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.drawString("Ciudad 6 - Hashing", 50, 50);
        g2.drawString("Completá el desafío en la ventana del juego.", 50, 80);
        g2.drawString("Q para volver al mapa", 50, 110);
    }

    @Override
    public void resultadoPartida() {
        ciudad.setEstado(EstadoCiudad.COMPLETADA);
    }

	@Override
	public void procesarClick(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}
}