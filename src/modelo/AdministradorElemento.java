package modelo;

/**
 * Define el contrato para el comportamiento polimórfico de los elementos.
 * Post: permite ejecutar los efectos sobre la partida sin conocer el tipo específico del elemento.
 */

public interface AdministradorElemento {
	
	/**
	 * Pre: partida no es nula.
	 * Post: aplica el efecto específico del elemento sobre el estado actual del juego.
	 */
	
	void aplicarEfecto(Partida partida);

}
