package minijuego;

import java.awt.Graphics2D;

import ciudades.Ciudad;
import ciudades.EstadoCiudad;

import java.awt.Color;
import entidad.Jugador;
import modelo.ciudad6.VentanaCiudad6;
import render.FinMinijuegoPantalla;

public class Ciudad6Minijuego implements Minijuego {

    private Ciudad ciudad;
    private Jugador jugador;

    private FinMinijuegoPantalla pantallaFinal = new FinMinijuegoPantalla();
    
    private VentanaCiudad6 ventana;
    private boolean ganado = false;

    public Ciudad6Minijuego(Ciudad ciudad, Jugador jugador) {
        this.ciudad = ciudad;
        this.jugador = jugador;
    }

    @Override
    public void iniciar() {
        ventana = new VentanaCiudad6(this);
        ventana.setVisible(true);
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.drawString("Ciudad 6 - Hashing", 50, 50);
        g2.drawString("Completá el desafío en la ventana del juego.", 50, 80);
        g2.drawString("Q para volver al mapa", 50, 110);
        
        if(ganado) {
			pantallaFinal.mostrarResultados(g2, ciudad);
		}
    }

    @Override
    public void resultadoPartida() {
    	if(ganado) {
            ciudad.setEstado(EstadoCiudad.COMPLETADA);
			jugador.sumarPuntos(ciudad.getPuntosDeExperiencia());
    	}
    }
    
    public void setGanado(boolean ganado) {
    	this.ganado = ganado;
    }

	@Override
	public void procesarClick(int mouseX, int mouseY) {
		
	}
}