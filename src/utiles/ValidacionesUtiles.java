package utiles;

<<<<<<< HEAD
/**
 * Clase de utilidades para centralizar validaciones de robustez.
 * Post: provee métodos estáticos para verificar contratos y lanzar excepciones.
 */

public class ValidacionesUtiles {
	
	/**
	 * Pre: el mensaje no es nulo.
	 * Post: lanza RuntimeException si el objeto recibido es nulo.
	 */
	
	public static void validarNoNulo(Object obj, String mensaje) {
		if(obj == null) {
			throw new RuntimeException("Error de Robustez: " + mensaje);
		}
	}
	
	/**
	 * Pre: mensaje no es nulo.
	 * Post: lanza RuntimeException si el valor no está entre min y max (inclusive).
	 */
	
	public static void validarRango(int valor, int min, int max, String mensaje) {
		if(valor < min || valor > max) {
			 throw new RuntimeException("Error de Rango: " + mensaje + " (Valor: " + valor + ", Rango esperado: [" + min + "," + max + "])");
		}
	}
	
	/**
	 * Pre: mensaje no es nulo.
	 * Post: lanza RuntimeException si el texto es nulo, está vacío o solo contiene espacios.
	 */
	
	public static void validarTextoNoVacio(String texto, String mensaje) {
		if (texto == null || texto.trim().isEmpty()) {
			throw new RuntimeException("Error de Texto: " + mensaje);
		}
	}
	
	/**
	 * Pre: mensaje no es nulo.
	 * Post: lanza RuntimeException si el valor no es estrictamente mayor a cero.
	 */
	
	public static void validarMayorACero(double valor, String mensaje) {
		if (valor <= 0) {
			throw new RuntimeException("Error de Valor: " + mensaje + " debe ser mayor a 0.");
		}
	}
	
	/**
	 * Pre: mensaje no es nulo.
	 * Post: lanza RuntimeException si la condición recibida es falsa.
	 */
	
	public static void validarVerdadero(boolean condicion, String mensaje) {
		if (!condicion) {
			throw new RuntimeException("Error de Lógica: " + mensaje);
		}
	}

=======

/**
 * Clase de soporte con métodos estáticos para validaciones comunes.
 * Post: asegura la integridad de los datos en todo el sistema.
 */

public class ValidacionesUtiles {
	
	/**
	 * Post: lanza una excepción si el objeto pasado es nulo.
	 */
	
	public static void validarNoNulo(Object obj, String mensaje) {
		if(obj == null) {
			throw new RuntimeException("Error: " + mensaje);
		}
	}
	
	/**
	 * Post: valida que el vector de entrada para el ordenamiento sea válido.
	 */
	
	public static void validarVector(int[] vector) {
		validarNoNulo(vector, "El vector no puede ser nulo.");
		if(vector.length == 0) {
			throw new RuntimeException("El vector no puede estar vacío.");
		}
	}
	
	/**
	 * Post: lanza una excepción si el valor no está dentro de [min, max].
	 */
	
	public static void validarRango(int valor, int min, int max, String campo) {
		if (valor < min || valor > max) {
	            throw new RuntimeException(campo + " debe estar entre " + min + " y " + max);
	     }
	}
>>>>>>> refs/remotes/origin/feature/ciudad-4-ordenamiento
}
