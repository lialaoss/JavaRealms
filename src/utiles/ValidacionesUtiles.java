package utiles;


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
}
