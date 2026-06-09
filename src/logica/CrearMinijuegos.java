package logica;

import ciudades.Ciudad;

import ciudades.Ciudad1Minijuego;
import ciudades.Ciudad3Minijuego;
import ciudades.Ciudad4Minijuego;
import ciudades.Minijuego;
import entidad.Jugador;
import ciudades.PlaceholderMinijuego;

public class CrearMinijuegos {
	
	/**
	 * sigo pensando pensamientos el switch no me gusta aun...
	 * @param ciudad
	 * @param jugador
	 * @return
	 */
	
	public static Minijuego crear(Ciudad ciudad, Jugador jugador) {
        switch (ciudad.getId()) {
            case 1:
                return new Ciudad1Minijuego(ciudad, jugador);
            case 2:
            case 3:
            	return new Ciudad3Minijuego(ciudad, jugador);
            case 4:
            	return new Ciudad4Minijuego(ciudad, jugador);
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                return new PlaceholderMinijuego(ciudad, jugador);
            default:
                return null;
        }
    }
}
