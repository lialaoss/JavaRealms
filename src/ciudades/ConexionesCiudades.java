package ciudades;

import java.util.List;
import java.util.Map;

import tda.Arista;
import tda.Grafo;
import utiles.Validaciones;

public class ConexionesCiudades {

	// ATRIBUTOS

    private int ciudadInicial;
	private Map<Integer, Ciudad> ciudades;
    private Grafo<Ciudad, Integer> grafoCiudades;

	// CONSTRUCTOR
    
	/*
	 *  Pre: El mapa de ciudades no puede ser nulo y el id de la ciudad inicial debe ser mayor a cero.
	 * Post: Crea el manejador de conexiones, guarda las ciudades, establece la ciudad de inicio y mete todas las ciudades al grafo como vértices.
	 */
	public ConexionesCiudades(Map<Integer, Ciudad> ciudades, int ciudadInicial) {
		grafoCiudades  = new Grafo<>();
		setCiudades(ciudades);
		setCiudadInicial(ciudadInicial);
		agregarVertices();
	}

	// METODOS
	
	/* 
	 * Pre: El mapa de ciudades ya debe estar cargado.
	 * Post: Recorre el mapa y agrega cada ciudad al grafo para que puedan conectarse.
	 */
	private void agregarVertices() {
		for(Integer id : this.ciudades.keySet()) {
	        this.grafoCiudades.agregarVertice(ciudades.get(id));
		}
	}
	
	/*
	 *  Pre: Las dos ciudades deben existir en el grafo.
	 * Post: Une dos ciudades creando un camino (arista) entre ellas y le asigna el costo en puntos para cruzarlo.
	 */
	public void crearConexion(Ciudad ciudad, Ciudad vecino, int puntos) {
		this.grafoCiudades.agregarArista(ciudad, vecino, puntos);
	}

	// ============================= LOGICA JUEGO ===================================
	
	/*
	 *  Pre: La ciudad destino debe existir y los puntos del jugador no pueden ser negativos.
	 * Post: Devuelve true si existe un camino de ciudades desbloqueadas hacia el destino y además el jugador tiene los puntos suficientes para pagar el "peaje" de cada salto. Si no, devuelve false.
	 */
	public boolean verificarCamino(Ciudad ciudadFinal, int puntosJugador) {
		List<Ciudad> camino = buscarCamino(ciudadFinal);
		
		if(camino.isEmpty()) {
			return false;
		}

		for (int i = 1; i < camino.size(); i++) {

			Ciudad origen = camino.get(i - 1);
			Ciudad destino = camino.get(i);

			int peso = grafoCiudades.getPeso(origen, destino);
	        if (puntosJugador < peso) {
	            return false;
	        }
	    }
		return true;
	}
	
	/*
	 *  Pre: La ciudad final debe existir en el juego.
	 * Post: Devuelve una lista con el recorrido más corto (usando BFS) para llegar al destino, pasando solo por ciudades DESBLOQUEADAS. Si no hay camino posible, devuelve la lista vacía.
	 */
	public List<Ciudad> buscarCamino(Ciudad valorFin) {
	    Ciudad valorInicio = ciudades.get(this.ciudadInicial);
	    
	    if(valorInicio.equals(valorFin)) {
	        List<Ciudad> camino = new java.util.ArrayList<>();
	        camino.add(valorFin);
	        return camino;
	    }
	    
	    List<Ciudad> camino = grafoCiudades.caminoMinimoBFS(
	            valorInicio,
	            valorFin,
	            ciudad -> ciudad.getEstado() != EstadoCiudad.BLOQUEADA);
	    return camino;
	}
	
	// =========================== DESBLOQUEAR VECINOS =============================== 
	
	/*
	 * Pre: La ciudad actual debe existir dentro del grafo.
	 * Post: Revisa todas las ciudades conectadas directamente a la actual y cambia su estado a DESBLOQUEADA para que el jugador pueda visitarlas.
	 */
	public void desbloquearVecinos(Ciudad ciudadActual) {

	    for (Arista<Ciudad, Integer> arista : grafoCiudades.getAdyacentes(ciudadActual)) {
	        Ciudad vecino = arista.getDestino().getValor();

	        if (vecino.getEstado() == EstadoCiudad.BLOQUEADA) {
	            vecino.setEstado(EstadoCiudad.DESBLOQUEADA);
	        }
	    }
	}

	// GETTERS

	/*
	 *  Pre: El grafo debe estar inicializado.
	 * Post: Devuelve el grafo completo con todas las ciudades y sus caminos.
	 */
	public Grafo<Ciudad, Integer> getGrafoCiudades() {
		return grafoCiudades;
	}

	// SETTERS

	/*
	 *  Pre: El mapa ingresado no puede ser null (lo comprueba con Validaciones).
	 * Post: Guarda la colección de ciudades en la clase.
	 */
	private void setCiudades(Map<Integer, Ciudad> ciudades) {
		Validaciones.esDistintoDeNull(ciudades, "ciudades");
		this.ciudades = ciudades;
	}
	
	/*
	 *  Pre: El número de ciudad debe ser mayor a cero (lo comprueba con Validaciones).
	 * Post: Guarda el ID de la ciudad desde donde el jugador arranca a jugar.
	 */
	private void setCiudadInicial(int ciudadInicial) {
		Validaciones.validarMayorACero(ciudadInicial, "id");
		this.ciudadInicial = ciudadInicial;
	}
	
}