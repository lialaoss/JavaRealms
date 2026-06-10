package entidad;

import logica.DireccionJugador;

public class Jugador {
	private int puntosExperiencia = 0;
	private DireccionJugador direccion = DireccionJugador.DOWN;
	
	public void sumarPuntos(int puntosASumar) {
		setPuntosExperiencia(this.puntosExperiencia + puntosASumar);
	}

	public int getPuntosExperiencia() {
		return puntosExperiencia;
	}

	public void setPuntosExperiencia(int puntosExperiencia) {
		this.puntosExperiencia = puntosExperiencia;
	}
	
    public DireccionJugador getDireccion() {
		return direccion;
	}


	public void setDireccion(DireccionJugador direccion) {
		this.direccion = direccion;
	}

	
	
}
