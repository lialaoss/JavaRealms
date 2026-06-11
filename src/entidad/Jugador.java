package entidad;

public class Jugador {
	private int puntosExperiencia = 0;
	
	public void sumarPuntos(int puntosASumar) {
		setPuntosExperiencia(this.puntosExperiencia + puntosASumar);
	}

	public int getPuntosExperiencia() {
		return puntosExperiencia;
	}

	public void setPuntosExperiencia(int puntosExperiencia) {
		this.puntosExperiencia = puntosExperiencia;
	}
	
	
}
