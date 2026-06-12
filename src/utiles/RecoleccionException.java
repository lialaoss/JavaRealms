package utiles;

/**
 * TDA que representa un error específico en la lógica de la Ciudad 1.
 * Post: encapsula el mensaje y un código de error para facilitar el diagnóstico.
 */

public class RecoleccionException extends RuntimeException {
	
	private int codigoError;
	
	/**
	 * Pre: el mensaje no es nulo.
	 * Post: inicializa la excepción con una descripción y un código identificador.
	 */
	
	public RecoleccionException(String mensaje, int codigo) {
		super(mensaje);
		this.codigoError = codigo;
	}
	
	/**
	 * Post: devuelve el código asociado al error.
	 */
	
	public int getCodigoError() {
		return codigoError;
	}
	
	@Override
	
	public String toString() {
		return "RecoleccionException [Código=" + codigoError + "]: " + getMessage();
	}

}
