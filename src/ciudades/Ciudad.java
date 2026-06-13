package ciudades;

import java.util.Objects;

public class Ciudad {
	
	// ATRIBUTOS
	private String nombre;
	private int id;
	private int puntosDeExperiencia;
	private EstadoCiudad estado = EstadoCiudad.BLOQUEADA;
	
	/*
	 * Pre: El nombre no debe estar vacío, los puntos y el id deben ser números mayores o iguales a cero.
	 * Post: Crea una nueva ciudad con los datos indicados. Por defecto, siempre arranca en estado BLOQUEADA.
	 */
	public Ciudad(String nombre, int puntosDeExperiencia, int id) {
		setNombre(nombre);
		setPuntosDeExperiencia(puntosDeExperiencia);
		setId(id);
	}
	
	/* 
	 *  Pre: Ninguna.
	 * Post: Devuelve un código numérico basado en el ID para identificar rápido a la ciudad en las colecciones.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(id));
	}
	
	/* 
	 * Pre: Recibe otro objeto cualquiera para comparar con esta ciudad.
	 * Post: Devuelve true si el objeto recibido es otra ciudad con el mismo ID, sino devuelve false.
	 */
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
	/*
	 *  Pre: La ciudad debe estar creada.
	 * Post: Devuelve el número de identificación (ID) de la ciudad.
	 */
	public int getId() {
		return id;
	}

	/* 
	 * Pre: La ciudad debe estar creada.
	 * Post: Devuelve la cantidad de puntos de experiencia que otorga esta ciudad.
	 */
	public int getPuntosDeExperiencia() {
		return puntosDeExperiencia;
	}

	/* 
	 * Pre: La ciudad debe estar creada.
	 * Post: Devuelve el estado actual de la ciudad (por ejemplo: BLOQUEADA o DESBLOQUEADA).
	 */
	public EstadoCiudad getEstado() {
		return estado;
	}

	/* 
	 * Pre: La ciudad debe estar creada.
	 * Post: Devuelve el nombre de la ciudad.
	 */
	public String getNombre() {
		return nombre;
	}

	// SETTERS
	/* 
	 * Pre: El id ingresado debe ser un número válido.
	 * Post: Reemplaza el ID de la ciudad por el nuevo valor.
	 */
	private void setId(int id) {
		this.id = id;
	}

	/*
	 * Pre: Los puntos de experiencia ingresados deben ser un número válido (mayor o igual a cero).
	 * Post: Actualiza los puntos de experiencia que tiene la ciudad.
	 */
	private void setPuntosDeExperiencia(int puntosDeExperiencia) {
		this.puntosDeExperiencia = puntosDeExperiencia;
	}
	
	/* 
	 * Pre: Recibe un estado válido que pertenezca a los definidos en EstadoCiudad.
	 * Post: Actualiza el estado de la ciudad (ideal para cuando el jugador la descubre o la pasa).
	 */
	public void setEstado(EstadoCiudad estado) {
		this.estado = estado;
	}

	/*
	 * Pre: El nombre ingresado no debe estar vacío ni ser nulo.
	 * Post: Cambia el nombre de la ciudad por el texto nuevo.
	 */
	private void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
