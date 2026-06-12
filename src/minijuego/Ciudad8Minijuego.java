package minijuego;

import java.awt.Color;
import java.awt.Graphics2D;
import ciudades.Ciudad;
import ciudades.EstadoCiudad;
import entidad.Jugador;
import modelo.ciudad8.VentanaCiudad8;
import render.FinMinijuegoPantalla;
import utiles.ObservadorVictoria;

public class Ciudad8Minijuego implements Minijuego, ObservadorVictoria {

    private Ciudad ciudad;
    private Jugador jugador;
    private VentanaCiudad8 ventana;

    private FinMinijuegoPantalla pantallaFinal = new FinMinijuegoPantalla();
    
    private boolean ganado = false;

    public Ciudad8Minijuego(Ciudad ciudad, Jugador jugador) {
        this.ciudad = ciudad;
        this.jugador = jugador;
    }

    @Override
    public void iniciar() {
        ventana = new VentanaCiudad8(this);
        ventana.setVisible(true);
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.drawString("Ciudad 8 - Torres de Hanoi", 50, 50);
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

    @Override
    public void notificarVictoria() {
        this.ganado = true;
    }

    @Override
    public void procesarClick(int mouseX, int mouseY) {
    	
    }
}