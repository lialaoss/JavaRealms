package modelo;

public interface ObservadorOrdenamiento {
	
	/**
	 * Pre: el vector no es nulo.
	 * Post: actualiza el estado visual del vector y genera una pausa para permitir la animacion.
	 * @param vector El arreglo que se esta ordenando.
	 * @param indiceA Primer indice involucrado en la operacion actual.
	 * @param indiceB Segundo indice involucrado.
	 * @param pivote Indice del elemento elegido como pivote (usar -1 si no aplica).
	 */
	
	void notificarCambio(int[] vector, int indiceA, int indiceB, int pivote);

}
