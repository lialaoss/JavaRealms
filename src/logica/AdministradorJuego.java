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
	private ModoTester tester;
	
	private boolean modoTesterActivo = false;
	
	// CONSTRUCTOR
	public AdministradorJuego() {
		
	    this.adminCiudades = new AdministradorCiudades();
	    this.ciudades = this.adminCiudades.getCiudades();
	    this.jugador = new Jugador();
	    this.recursos = new GestorRecursos();
	    this.guardado = new CargaDeDatos(this);
	    this.tester = new ModoTester(ciudades, jugador);
	    
	}
	
	// =========================== LOGICA JUEGO ====================================
	
	/*
	 * Pre: El juego debe tener al menos la ciudad inicial (ID 1) cargada en la memoria.
	 * Post: Desbloquea la primera ciudad (si es que no estaba completada de antes) y arranca la ventana principal para empezar a jugar.
	 */
	public void iniciarJuego() {
		if(this.ciudades.get(1).getEstado() != EstadoCiudad.COMPLETADA) {
			this.ciudades.get(1).setEstado(EstadoCiudad.DESBLOQUEADA);
		}
	    new Ventana(this);
	}
	
	/*
	 * Pre: Ninguna.
	 * Post: Chequea si ganaste el juego. Si el jugador intenta entrar a una ciudad, verifica si cumple los requisitos; si no, lo devuelve al mapa. Si todo está bien, crea y arranca el minijuego de esa ciudad.
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

	/* 
	 * Pre: Tiene que haber una ciudad seleccionada (ciudadActual no puede ser nula).
	 * Post: Devuelve true si la ciudad está disponible (no está bloqueada ni ya la pasaste) y si te alcanzan los puntos de experiencia para llegar hasta ahí. Si no, devuelve false.
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
	
	/*
	 *  Pre: El ID ingresado tiene que ser mayor a cero y la ciudad debe existir en nuestro mapa de ciudades.
	 * Post: Actualiza la variable ciudadActual para que apunte a la ciudad que el jugador acaba de elegir en el mapa.
	 */
	public void cambiarDeCiudad(int idCiudad) {
		Validaciones.validarMayorACero(idCiudad, "idCiudad");
		setCiudadActual(ciudades.get(idCiudad));
	}
	
	/*
	 *  Pre: La ciudad que le pasamos no puede ser nula y debe estar en el grafo.
	 * Post: Le avisa al administrador del mapa que cambie a DESBLOQUEADA todas las ciudades que estén conectadas directamente a la que acabamos de pasar.
	 */
	public void desbloquearVecinos(Ciudad ciudad) {
		adminCiudades.getGrafo().desbloquearVecinos(ciudad);
	}
	
	/*
	 *  Pre: Ninguna.
	 * Post: Borra el minijuego que se estaba jugando (lo deja en null) para limpiar la memoria y estar listos para la siguiente ciudad.
	 */
	public void limpiarJuegoActual() {
	    this.juegoActual = null;
	}
	
	/*
	 *  Pre: El mapa de ciudades tiene que estar inicializado.
	 * Post: Recorre todas las ciudades del juego. Si absolutamente todas están en estado COMPLETADA, devuelve true (ganaste la partida). Si falta alguna, devuelve false.
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

	/*
	 *  Pre: El sistema de guardado (CargaDeDatos) tiene que estar funcionando.
	 * Post: Agarra todo el progreso actual del jugador y lo guarda en el archivo de texto para no perder la partida si cerramos el programa.
	 */
	public void actualizarDatos() {
		this.guardado.actualizarArchivoDeDatos();
	}
	
	/*
	 * Pre: Ninguna.
	 * Post: Borra el archivo de guardado o lo sobreescribe para reiniciar el progreso de la partida (ideal para cuando el usuario toca "Nueva Partida").
	 */
	public void eliminarDatos() {
		this.guardado.eliminarDatosDeArchivo();
	}
	
	// ================== TESTER MODE ====================
	
	public void habilitarModoTester() {
		if(!modoTesterActivo) {
			this.tester.habilitarModo();
			modoTesterActivo = true;
			System.out.println("================ MODO TESTER ACTIVADO ================");
		} else {
			this.guardado.restaurarUltimosDatos();
			modoTesterActivo = false;
			System.out.println("=============== MODO TESTER DESACTIVADO ==============");
		}
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