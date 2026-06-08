package logica;

import ciudades.Ciudad;
import ciudades.Minijuego;
import entidad.Jugador;

public class CrearMinijuegos {
	
	/**
	 * sigo pensando pensamientos el switch no me gusta aun...
	 * @param ciudad
	 * @param jugador
	 * @return
	 */
	public static Minijuego crear(Ciudad ciudad, Jugador jugador) {
		int id = ciudad.getId();
        switch (id) {
            default:
            	throw new IllegalArgumentException("Ciudad no existe (aunnn)");
        }
    }
}
