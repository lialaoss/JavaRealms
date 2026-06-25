package logica;

import java.util.Map;

import ciudades.Ciudad;
import ciudades.EstadoCiudad;
import entidad.Jugador;

public class ModoTester {
	
	private final int PUNTOS = 500;
	
	private Map<Integer, Ciudad> ciudades;
	private Jugador jugador;
	
	/**
	 * Pre: 'ciudades' y 'jugador' no deben ser nulos.
	 * Post: Inicializa el modo tester con las ciudades y el jugador provistos.
	 */
	public ModoTester(Map<Integer, Ciudad> ciudades, Jugador jugador) {
		this.ciudades = ciudades;
		this.jugador = jugador;
	}
	
	/**
	 * Pre: Ninguno.
	 * Post: Desbloquea todas las ciudades del mapa y le suma 500 puntos al jugador.
	 */
	protected void habilitarModo() {
		desbloquearCiudades();
		aumentarPuntosJugador();
	}
	
	/**
	 * Pre: El mapa de ciudades no está vacío.
	 * Post: Cambia el estado de todas las ciudades de la colección a DESBLOQUEADA.
	 */
	private void desbloquearCiudades() {
		for(Integer id : ciudades.keySet()) {
			ciudades.get(id).setEstado(EstadoCiudad.DESBLOQUEADA);
		}
	}
	
	/**
	 * Pre: El jugador no es nulo.
	 * Post: Incrementa el puntaje del jugador en la cantidad definida por PUNTOS (500).
	 */
	private void aumentarPuntosJugador() {
		this.jugador.sumarPuntos(PUNTOS);
	}

}
