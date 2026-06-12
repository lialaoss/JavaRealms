package modelo.ciudad4;

import utiles.ValidacionesUtiles;

public class BubbleSort implements AlgoritmoOrdenamiento {
	
	/**
	 * Pre: el vector no es nulo.
	 * Post: ordena el vector de menor a mayor y notifica cada intercambio al observador.
	 */
	
	public void ordenar(int[] vector, ObservadorOrdenamiento observador) {
		ValidacionesUtiles.validarVector(vector);
		int n = vector.length;
		boolean intercambiado;
		for(int i = 0; i < n - 1; i++) {
			intercambiado = false;
			for (int j = 0; j < n - i - 1; j++) {
				observador.notificarCambio(vector, j, j + 1, -1);
				if (vector[j] > vector[j + 1]) {
					intercambiar(vector, j, j + 1);
					intercambiado = true;
					observador.notificarCambio(vector, j, j + 1, -1);
				}
			}
			if (!intercambiado) {
				break;
			}
		}
	}
	
	/**
	 * Pre: el vector no es nulo y los índices i y j están dentro del rango.
	 * Post: intercambia los valores de las posiciones i y j en el vector v.
	 */
	
	public void intercambiar(int[] v, int i, int j) {
		int temp = v[i];
		v[i] = v[j];
		v[j] = temp;
	}

}
