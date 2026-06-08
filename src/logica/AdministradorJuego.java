package logica;

import java.util.Map;

import ciudades.AdministradorCiudades;
import ciudades.Ciudad;
import ciudades.EstadoCiudad;
import ciudades.Minijuego;
import entidad.Jugador;
import ui.Ventana;
import utiles.Validaciones;

public class AdministradorJuego {
	
	// ATRIBUTOS
	private AdministradorCiudades adminCiudades;
	private Map<Integer, Ciudad> ciudades;
	private EstadoJuego estado = EstadoJuego.MENU_PRINCIPAL;
	private Ciudad ciudadActual;
	private Jugador jugador;
	private Minijuego juegoActual;
	private Ventana ventana;
	
	// CONSTRUCTOR
	public AdministradorJuego() {

	    this.adminCiudades = new AdministradorCiudades();
	    this.ciudades = this.adminCiudades.getCiudades();
	    this.jugador = new Jugador();

	}
	
	// =========================== LOGICA JUEGO ====================================
	
	/**
	 * Inicia el juego y desbloquea por default la ciudad 1 (mas no se encuentra completa)
	 */
	public void iniciarJuego() {
		ciudades.get(1).setEstado(EstadoCiudad.DESBLOQUEADA);
		this.ventana = new Ventana(this);
	}
	
	/**
	 * Actualiza el juego. En caso de que el jugador se encuentre en el menu no
	 * sucede nada. En caso de que haya entrado a una ciudad, se dice que el
	 * juego se encuentra en progreso... este metodo valida que la ciudad sea
	 * accesible antes de entrar.
	 */
	public void update() {
	    if(estado != EstadoJuego.EN_PROGRESO) { return; }
	    if(!puedeEntrar()) { setEstado(EstadoJuego.MAPA_GENERAL); return; }

	    if(juegoActual == null) {
	        juegoActual = CrearMinijuegos.crear(ciudadActual, jugador);
	        if(juegoActual != null) {
	            juegoActual.iniciar();
	        }
	    }
	}

	/**
	 * Se evalua que la ciudad al que el usuario quiere visitar se encuentre Desbloqueada
	 * y tambien que contenga los puntos necesarios para entrar. 
	 * Que la ciudad se encuentre bloqueada o completa no es valido.
	 * @return : Devuelve true si es posible acceder dentro de la ciudad
	 */
	private boolean puedeEntrar() {
	    if(ciudadActual.getEstado() == EstadoCiudad.COMPLETADA) {
	        return false;
	    }
	    if(ciudadActual.getEstado() == EstadoCiudad.BLOQUEADA) {
	        return false;
	    }
	    return adminCiudades.getGrafo().verificarCamino(ciudadActual, jugador.getPuntosExperiencia());
	}

	public Minijuego getJuegoActual() {
	    return juegoActual;
	}

	public void limpiarJuegoActual() {
	    this.juegoActual = null;
	}
	
	/**
	 * Actualiza la ciudad en la que el jugador quiere entrar
	 * @param idCiudad : id de la ciudad
	 */
	public void cambiarDeCiudad(int idCiudad) {
		Validaciones.validarMayorACero(idCiudad, "idCiudad");
		setCiudadActual(ciudades.get(idCiudad));
	}

	// GETTERS
	public AdministradorCiudades getAdminCiudades() {
		return adminCiudades;
	}

	public EstadoJuego getEstado() {
		return estado;
	}

	public Ciudad getCiudadActual() {
		return ciudadActual;
	}

	public Jugador getJugador() {
		return jugador;
	}
	
	public Ventana getVentana() {
        return this.ventana;
    }
	
	
	// SETTERS
	public void setEstado(EstadoJuego estado) {
		Validaciones.validarRangoDeEnum(estado, EstadoJuego.values());
		this.estado = estado;
	}

	private void setCiudadActual(Ciudad ciudadActual) {
		this.ciudadActual = ciudadActual;
	}

}
