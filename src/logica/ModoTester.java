package logica;

import java.util.Map;

import ciudades.Ciudad;
import ciudades.EstadoCiudad;
import entidad.Jugador;

public class ModoTester {
	
	private final int PUNTOS = 500;
	
	private Map<Integer, Ciudad> ciudades;
	private Jugador jugador;
	
	public ModoTester(Map<Integer, Ciudad> ciudades, Jugador jugador) {
		this.ciudades = ciudades;
		this.jugador = jugador;
	}
	
	protected void habilitarModo() {
		desbloquearCiudades();
		aumentarPuntosJugador();
	}
	
	private void desbloquearCiudades() {
		for(Integer id : ciudades.keySet()) {
			ciudades.get(id).setEstado(EstadoCiudad.DESBLOQUEADA);
		}
	}
	
	private void aumentarPuntosJugador() {
		this.jugador.sumarPuntos(PUNTOS);
	}

}
