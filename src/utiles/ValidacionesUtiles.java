package utiles;

/**
 * Clase de utilidades para centralizar validaciones de robustez.
 * Post: provee metodos estaticos para verificar contratos y lanzar excepciones.
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
	 * Post: lanza RuntimeException si el valor no esta entre min y max (inclusive).
	 */
	public static void validarRango(int valor, int min, int max, String mensaje) {
		if(valor < min || valor > max) {
			 throw new RuntimeException("Error de Rango: " + mensaje + " (Valor: " + valor + ", Rango esperado: [" + min + "," + max + "])");
		}
	}
	
	/**
	 * Pre: mensaje no es nulo.
	 * Post: lanza RuntimeException si el texto es nulo, esta vacio o solo contiene espacios.
	 */
	public static void validarTextoNoVacio(String texto, String mensaje) {
		if (texto == null || texto.trim().isEmpty()) {
			throw new RuntimeException("Error de Texto: " + mensaje);
		}
	}
	
	/**
	 * Pre: mensaje no es nulo.
	 * Post: lanza RuntimeException si el texto es nulo o vacío.
	 */
	public static void validarNoVacio(String texto, String mensaje) {
	    if (texto == null || texto.trim().isEmpty()) {
	        throw new RuntimeException("Error de Texto: " + mensaje + " no puede ser nulo ni vacío.");
	    }
	}

	/**
	 * Pre: mensaje no es nulo.
	 * Post: lanza RuntimeException si el valor es negativo.
	 */
	public static void validarNoNegativo(int valor, String mensaje) {
	    if (valor < 0) {
	        throw new RuntimeException("Error de Valor: " + mensaje + " no puede ser negativo. Valor: " + valor);
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
	 * Post: lanza RuntimeException si la condicion recibida es falsa.
	 */
	public static void validarVerdadero(boolean condicion, String mensaje) {
		if (!condicion) {
			throw new RuntimeException("Error de Logica: " + mensaje);
		}
	}

	/**
	 * Post: valida que el vector de entrada para el ordenamiento sea valido.
	 */
	public static void validarVector(int[] vector) {
		validarNoNulo(vector, "El vector no puede ser nulo.");
		if(vector.length == 0) {
			throw new RuntimeException("El vector no puede estar vacio.");
		}
	}
}