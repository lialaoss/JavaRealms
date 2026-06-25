package modelo.ciudad4;

import utiles.ValidacionesUtiles;

public class QuickSort implements AlgoritmoOrdenamiento {
	
	/*
	 * pre: el vector no es nulo.
	 * post: ordena el vector usando la estrategia de dividir y vencerás.
	 */
	
	public void ordenar(int[] vector, ObservadorOrdenamiento observador) {
		ValidacionesUtiles.validarVector(vector);
		quicksort(vector, 0, vector.length - 1, observador);
	}
	
	/*
	 * pre: v no es nulo, izq y der son índices válidos.
	 * post: aplica recursivamente el algoritmo de ordenamiento rápido en los subvectores.
	 */
	
	private void quicksort(int[] v, int izq, int der, ObservadorOrdenamiento obs) {
		if(izq < der) {
			int indicePivote = particionar(v, izq, der, obs);
			quicksort(v, izq, indicePivote - 1, obs);
			quicksort(v, indicePivote + 1, der, obs);
		}
	}
	
	
	/*
	 * post: selecciona un pivote y ubica los menores a la izquierda y los mayores a la derecha.
	 */
	private int particionar(int[] v, int izq, int der, ObservadorOrdenamiento obs) {
		int pivote = v[der];
		int i = izq -1;
		
		for(int j = izq; j < der; j++) {
			if(v[j] <= pivote) {
				i++;
				intercambiar(v, i, j);
				obs.notificarCambio(v, i, j, der);
			}
		}
		intercambiar(v, i + 1, der);
		obs.notificarCambio(v, i + 1, der, i + 1);
		return i + 1;
	}
	
	/*
	 * Pre: v no es nulo y los índices i y j están dentro del rango.
	 * Post: intercambia los valores de las posiciones i y j en el vector v.
	 */
	
	private void intercambiar(int[] v, int i, int j) {
		int temp = v[i];
		v[i] = v[j];
		v[j] = temp;
	}
}
