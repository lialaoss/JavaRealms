package entidad;

import logica.DireccionJugador;



public class Jugador {
	private int puntosExperiencia = 0;
	private DireccionJugador direccion = DireccionJugador.DOWN;
	
	public int contadorSprite = 0;
	public int numeroDeSprite = 1;
	
	/**
     * Pre: puntosASumar >= 0.
     * Post: los puntos de experiencia del jugador se incrementan en puntosASumar.
     */
	
	public void sumarPuntos(int puntosASumar) {
		setPuntosExperiencia(this.puntosExperiencia + puntosASumar);
	}
	
	/**
     * Pre: ninguna.
     * Post: devuelve los puntos de experiencia actuales del jugador.
     */

	public int getPuntosExperiencia() {
		return puntosExperiencia;
	}
	
	/**
     * Pre: puntosExperiencia >= 0.
     * Post: los puntos de experiencia del jugador quedan establecidos en el valor indicado.
     */

	public void setPuntosExperiencia(int puntosExperiencia) {
		this.puntosExperiencia = puntosExperiencia;
	}
	
	/**
     * Pre: ninguna.
     * Post: devuelve la dirección actual del jugador.
     */
	
    public DireccionJugador getDireccion() {
		return direccion;
	}
    
    
    /**
     * Pre: direccion no es nula.
     * Post: la dirección del jugador queda establecida en el valor indicado.
     */

	public void setDireccion(DireccionJugador direccion) {
		this.direccion = direccion;
	}

	
	
}
