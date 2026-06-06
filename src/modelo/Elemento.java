package modelo;

import utiles.ValidacionesUtiles;

/**
 * Clase abstracta que representa un ítem en el tablero 3D.
 * Post: define la estructura básica de datos para los elementos recolectables.
 */

public abstract class Elemento {
	private String nombre;
	
	/**
	 * Pre: nombre no es nulo ni vacío.
	 * Post: inicializa el elemento con su identificación básica.
	 */
	
	public Elemento(String nombre) {
		ValidacionesUtiles.validarNoNulo(nombre, "El nombre del elemento no puede ser nulo");
		if (nombre.trim().isEmpty()) {
			throw new RuntimeException("El nombre del elemento no puede estar vacio");
		}
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Post: cada subclase debe proveer su propio administrador de comportamiento.
	 */
	
	public abstract AdministradorElemento getAdministrador();
}
