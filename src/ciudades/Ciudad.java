package ciudades;

import java.util.Objects;

public class Ciudad {
	
	// ATRIBUTOS
	private String nombre;
	private int id;
	private int puntosDeExperiencia;
	private EstadoCiudad estado = EstadoCiudad.BLOQUEADA;
	
	// CONSTRUCTOR
	public Ciudad(String nombre, int puntosDeExperiencia, int id) {
		setNombre(nombre);
		setPuntosDeExperiencia(puntosDeExperiencia);
		setId(id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(id));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ciudad other = (Ciudad) obj;
		return id == other.id;
	}

	// GETTERS
	
	public int getId() {
		return id;
	}

	public int getPuntosDeExperiencia() {
		return puntosDeExperiencia;
	}

	
	public EstadoCiudad getEstado() {
		return estado;
	}

	public String getNombre() {
		return nombre;
	}

	// SETTERS
	private void setId(int id) {
		this.id = id;
	}

	private void setPuntosDeExperiencia(int puntosDeExperiencia) {
		this.puntosDeExperiencia = puntosDeExperiencia;
	}
	
	public void setEstado(EstadoCiudad estado) {
		this.estado = estado;
	}

	private void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
