package logica;

import java.util.Map;

import ciudades.AdministradorCiudades;
import ciudades.Ciudad;
import ciudades.EstadoCiudad;
import entidad.Jugador;
import ui.GestorRecursos;
import minijuego.CrearMinijuegos;
import minijuego.Minijuego;
import persistencia.CargaDeDatos;
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
	
	private GestorRecursos recursos;
	private CargaDeDatos guardado;
	
	// CONSTRUCTOR
	public AdministradorJuego() {
		
	    this.adminCiudades = new AdministradorCiudades();
	    this.ciudades = this.adminCiudades.getCiudades();
	    this.jugador = new Jugador();
	    this.recursos = new GestorRecursos();
	    this.guardado = new CargaDeDatos(this);
	}
	
	// =========================== LOGICA JUEGO ====================================
	
	/**
	 * Inicializa el juego y configura sus valores iniciales.
	 * Pre: Debe existir una ciudad con id 1.
	 * Post:
	 * - La ciudad 1 queda desbloqueada si no estaba completada.
	 * - El jugador recibe la experiencia inicial.
	 * - Se crea la ventana principal del juego.
	 */
	public void iniciarJuego() {
		if(this.ciudades.get(1).getEstado() != EstadoCiudad.COMPLETADA) {
			this.ciudades.get(1).setEstado(EstadoCiudad.DESBLOQUEADA);
		}
	    new Ventana(this);
	}
	
	/**
	 * Actualiza el estado actual del juego.
	 * Si el estado no es EN_PROGRESO no realiza ninguna acción.
	 * Si la ciudad seleccionada es accesible, crea e inicia
	 * el minijuego correspondiente.
	 * Pre: Debe existir una ciudad seleccionada cuando el estado sea EN_PROGRESO.
	 * Post:
	 * - Si no puede accederse a la ciudad, el estado pasa a
	 *   MAPA_GENERAL.
	 * - Si corresponde, se crea e inicia un minijuego.
	 */
	public void update() {
		if(juegoCompletado()) {
		    setEstado(EstadoJuego.FIN_DEL_JUEGO);
		}
	    if(estado != EstadoJuego.EN_PROGRESO) {
	    	return; 
	    }
	    if(!puedeEntrar()) {
	    	setEstado(EstadoJuego.MAPA_GENERAL); 
	    	return;
	    }

	    if(juegoActual == null) {
		    juegoActual = CrearMinijuegos.crear(ciudadActual, jugador, recursos);
		    if(juegoActual != null) {
		       juegoActual.iniciar();
		    }
	    }
	}

	/**
	 * Verifica si el jugador puede ingresar a la ciudad actual.
	 * Pre: ciudadActual != null.
	 * Post: 
	 * - Devuelve true si la ciudad no está bloqueada ni completada
	 *   y el jugador posee los requisitos necesarios para acceder.
	 * - No modifica el estado de ningún objeto.
	 * @return true si puede ingresar a la ciudad.
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
	
	/**
	 * Cambia la ciudad actual seleccionada por el jugador,
	 * Pre: ciudades.containsKey(idCiudad), donde idCiudad > 0, 
	 * y debe existir una ciudad asociada al identificador.
	 * Post: ciudadActual referencia a la ciudad indicada.
	 * @param idCiudad: identificador de la ciudad.
	 */
	public void cambiarDeCiudad(int idCiudad) {
		Validaciones.validarMayorACero(idCiudad, "idCiudad");
		setCiudadActual(ciudades.get(idCiudad));
	}
	
	/**
	 * Desbloquea las ciudades vecinas de la ciudad indicada.
	 * Pre: ciudad != null.
	 * Post: Las ciudades vecinas quedan desbloqueadas según las
	 * reglas definidas por el grafo de ciudades.
	 * @param ciudad: ciudad desde la cual se desbloquean vecinos.
	 */
	public void desbloquearVecinos(Ciudad ciudad) {
		adminCiudades.getGrafo().desbloquearVecinos(ciudad);
	}
	
	/**
	 * Elimina la referencia al minijuego actual.
	 * Post: juegoActual == null.
	 */
	public void limpiarJuegoActual() {
	    this.juegoActual = null;
	}
	
	/**
	 * 
	 * @return devuelve true en caso de que todas las ciudades se encuentran
	 * completadas.
	 */
	public boolean juegoCompletado() {
	    for(Ciudad ciudad : ciudades.values()) {
	        if(ciudad.getEstado() != EstadoCiudad.COMPLETADA) {
	            return false;
	        }
	    }
	    return true;
	}
	
	// ================== MANEJO DE DATOS DEL JUEGO ====================

	/**
	 * Guarda el estado actual del juego en el archivo de datos.
	 * Pre: El sistema de persistencia debe estar inicializado.
	 * Post: Los datos actuales del juego quedan almacenados.
	 */
	public void actualizarDatos() {
		this.guardado.actualizarArchivoDeDatos();
	}
	
	/**
	 * Elimina los datos almacenados del juego al sobreescribir
	 * el archivo de datos.
	 * Post: Los datos guardados son eliminados.
	 */
	public void eliminarDatos() {
		this.guardado.eliminarDatosDeArchivo();
	}

	// GETTERS ==============================================
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

	public Minijuego getJuegoActual() {
	    return juegoActual;
	}
	
	public GestorRecursos getRecursos() {
		return this.recursos;
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
