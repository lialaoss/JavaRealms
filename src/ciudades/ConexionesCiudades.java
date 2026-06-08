package ciudades;

import java.util.List;
import java.util.Map;

import grafos.Arista;
import grafos.Grafo;
import utiles.Validaciones;


public class ConexionesCiudades {

	// ATRIBUTOS

    private int ciudadInicial;
	private Map<Integer, Ciudad> ciudades;
    private Grafo<Ciudad, Integer> grafoCiudades;

	// CONSTRUCTOR
    
	public ConexionesCiudades(Map<Integer, Ciudad> ciudades, int ciudadInicial) {
		grafoCiudades  = new Grafo<>();
		setCiudades(ciudades);
		setCiudadInicial(ciudadInicial);
		agregarVertices();
	}

	// METODOS
	
	private void agregarVertices() {
		for(Integer id : this.ciudades.keySet()) {
	        this.grafoCiudades.agregarVertice(ciudades.get(id));
		}
	}
	
	public void crearConexion(Ciudad ciudad, Ciudad vecino, int puntos) {
		this.grafoCiudades.agregarArista(ciudad, vecino, puntos);
	}

	// ============================= LOGICA JUEGO ===================================
	
	/**
	 * Durante todo el metodo se evalua:
	 * Accesibilidad a la ciudad encontrando un camino,
	 * que los puntos del jugador sean suficientes para igualar al peso de la arista
	 * y que la ciudad a la que queremos acceder se encuentre previamente desbloqueada.
	 * @param idFinal
	 * @param puntosJugador
	 * @return devuelve true en caso de que la ciudad a la que el jugador quiere
	 * ingresar es accesible.
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
		System.out.println("Camino libre!"); // solo estan para saber q pasa por la terminal ahre
		return true;
	}
	
	/**
	 * Crea una lista con las ciudades que se recorren hasta llegar a nuestro destino
	 * unicamente recorriendo ciudades que se encuentren desbloqueadas.
	 * En el caso de que no exista un camino se devolvera una lista vacia.
	 * @return devuelve una lista con las ciudades que forman un camino desde la primera
	 * ciudad hasta la ciudad que se indica.
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
	
	// =========================== DESBLOQUEAR VECINOS =============================== ?
	
	/**
	 * Pensaba usar este metodo para desbloquear los vecinos de las ciudades
	 * @param ciudadActual
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

	public Grafo<Ciudad, Integer> getGrafoCiudades() {
		return grafoCiudades;
	}

	// SETTERS

	private void setCiudades(Map<Integer, Ciudad> ciudades) {
		Validaciones.esDistintoDeNull(ciudades, "ciudades");
		this.ciudades = ciudades;
	}
	
	private void setCiudadInicial(int ciudadInicial) {
		Validaciones.validarMayorACero(ciudadInicial, "id");
		this.ciudadInicial = ciudadInicial;
	}

	
}
