package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import modelo.*;
import modelo.ciudad4.BubbleSort;
import modelo.ciudad4.ObservadorOrdenamiento;
import modelo.ciudad4.QuickSort;

/*
 * Suite de pruebas unitarias para los algoritmos de ordenamiento de Ciudad 4.
 */
public class OrdenamientoTest {
	
	private BubbleSort bubble;
	private QuickSort quick;
	private int[] datos;
	private int[] esperado;
	
	// Observador vacío para testear solo la lógica sin interfaz gráfica
	private ObservadorOrdenamiento obs = (v, a, b, p) -> {};

	/*
	 * Post: inicializa las instancias de los algoritmos y los vectores de prueba.
	 */
	@BeforeEach
	void setUp() {
		bubble = new BubbleSort();
		quick = new QuickSort();
		datos = new int[]{30, 10, 50, 20};
		esperado = new int[]{10, 20, 30, 50};
	}
	
	/*
	 * Post: valida que BubbleSort ordene correctamente un vector desordenado.
	 */
	@Test
	void testBubbleSortExito() {
	    bubble.ordenar(datos, obs);
	    assertArrayEquals(esperado, datos, "BubbleSort debería ordenar de menor a mayor");
	}
	
	/*
	 * Post: valida que QuickSort ordene correctamente un vector desordenado.
	 */
	@Test
	void testQuickSortExito() {
	    quick.ordenar(datos, obs);
	    assertArrayEquals(esperado, datos, "QuickSort debería ordenar correctamente el vector");
	}
	
	/*
	 * Post: verifica que el algoritmo maneje correctamente elementos repetidos.
	 */
	@Test
	void testVectorConDuplicados() {
	    int[] duplicados = {5, 2, 5, 1};
	    int[] expDuplicados = {1, 2, 5, 5};
	    quick.ordenar(duplicados, obs);
	    assertArrayEquals(expDuplicados, duplicados);
	}

	/*
	 * Post: valida la robustez lanzando RuntimeException ante un vector nulo.
	 */
	@Test
	void testOrdenamientoVectorNulo() {
	    assertThrows(RuntimeException.class, () -> {
	        bubble.ordenar(null, obs);
	    }, "Debe lanzar excepción si el vector es nulo");
	}
}
