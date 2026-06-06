package modelo;

import utiles.ValidacionesUtiles;

/**
 * Representa el entorno tridimensional de la Ciudad 1.
 * Post: gestiona la ubicación de los elementos y la visibilidad de las celdas.
 */
public class Mapa3D {
	
	private Elemento[][][] celdas;
	private boolean[][][] revelado;
	private int ancho, alto, niveles;
	
	/**
     * Pre: dimensiones mayores a cero.
     * Post: inicializa el mapa vacío y cubierto por la oscuridad.
     * @param ancho Cantidad de celdas en el eje X.
     * @param alto Cantidad de celdas en el eje Y.
     * @param niveles Cantidad de planos (eje Z).
     */
	 public Mapa3D(int ancho, int alto, int niveles) {
		 ValidacionesUtiles.validarRango(ancho, 1, Integer.MAX_VALUE, "Ancho");
		 ValidacionesUtiles.validarRango(alto, 1, Integer.MAX_VALUE, "Alto");
		 ValidacionesUtiles.validarRango(niveles, 1, Integer.MAX_VALUE, "Niveles");
		 
		 this.ancho = ancho;
		 this.alto = alto;
		 this.niveles = niveles;
		 this.celdas = new Elemento[ancho][alto][niveles];
		 this.revelado = new boolean[ancho][alto][niveles];
	 }
	 
	 /**
	  * Pre: coordenadas dentro del rango del mapa, e no es nulo.
	  * Post: coloca un elemento en la posición indicada.
	  */
	 public void colocarElemento(int x, int y, int z, Elemento e) {
	        validarCoordenadas(x, y, z);
	        ValidacionesUtiles.validarNoNulo(e, "El elemento a colocar no puede ser nulo");
	        this.celdas[x][y][z] = e;
	 }
	 
	 /**
	  * Post: devuelve true si la coordenada está dentro de los límites de la matriz.
	  */
	 public boolean esCoordenadaValida(int x, int y, int z) {
		 return (x >= 0 && x < ancho) && (y >= 0 && y < alto) && (z >= 0 && z < niveles);
	 }
	 
	 /**
	  * Pre: coordenada válida.
	  * Post: devuelve el elemento en la posición o null si está vacía.
	  * @return El ítem presente en la celda.
	  */
	 public Elemento obtenerElemento(int x, int y, int z) {
		 validarCoordenadas(x, y, z);
		 return this.celdas[x][y][z];
	 }

	 /**
	  * Pre: coordenada válida.
	  * Post: elimina el elemento de la celda (lo pone en null).
	  */
	 public void removerElemento(int x, int y, int z) {
		 validarCoordenadas(x, y, z);
		 this.celdas[x][y][z] = null;
	 }
	 
	 /**
	  * Pre: radio >= 0.
	  * Post: marca como 'reveladas' las celdas dentro del radio de visión en el nivel (Z) actual.
	  */
	 public void actualizarNiebla(int px, int py, int pz, int radio) {
		 for (int i = px - radio; i <= px + radio; i++) {
			 for (int j = py - radio; j <= py + radio; j++) {
				 if (esCoordenadaValida(i, j, pz)) {
					 this.revelado[i][j][pz] = true;
				 }
			 }
		 }
	 }
	 
	 /**
	  * Post: devuelve true si la celda ya fue visitada o está bajo el radio de visión.
	  */
	 public boolean estaRevelado(int x, int y, int z) {
		 validarCoordenadas(x, y, z);
		 return this.revelado[x][y][z];
	 }
	 
	 /**
	  * Post: lanza RuntimeException si las coordenadas rompen los límites del TDA.
	  */
	 private void validarCoordenadas(int x, int y, int z) {
		 if (!esCoordenadaValida(x, y, z)) {
			 throw new RuntimeException("Coordenadas fuera de los límites del Mapa3D");
		 }
	 }
	 
	 // Getters de dimensiones para la interfaz gráfica
	 public int getAncho() { return ancho; }
	 public int getAlto() { return alto; }
	 public int getNiveles() { return niveles; }
}