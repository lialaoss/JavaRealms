package minijuego;

import ciudades.Ciudad;
import entidad.Jugador;
import ui.GestorRecursos;

public class CrearMinijuegos {
	
	/**
	 * sigo pensando pensamientos el switch no me gusta aun...
	 * @param ciudad
	 * @param jugador
	 * @return
	 */
	
	public static Minijuego crear(Ciudad ciudad, Jugador jugador, GestorRecursos recursos) {
        switch (ciudad.getId()) {
            case 1:
                return new Ciudad1Minijuego(ciudad, jugador, recursos);
            case 2:
            case 3:
            	return new Ciudad3Minijuego(ciudad, jugador, recursos);
            case 4:
            	return new Ciudad4Minijuego(ciudad, jugador, recursos);
            case 5:
            case 6:
            	return new Ciudad6Minijuego(ciudad, jugador);
            case 7:
            case 8:
            case 9:
            case 10:
                return new Ciudad10Minijuego(ciudad, jugador);
            default:
                return null;
        }
    }
}
