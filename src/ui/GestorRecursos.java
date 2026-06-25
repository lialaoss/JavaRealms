package ui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GestorRecursos {
	
	private BufferedImage botonMenu1, botonMenu2, botonMenu3, botonVolver;
	private BufferedImage botonArbol, botonLista;
	
	private BufferedImage[] jugadorUp;
	private BufferedImage[] jugadorDown;
	private BufferedImage[] jugadorLeft;
	private BufferedImage[] jugadorRight;
	
	private BufferedImage fondoMapa, fondoMenu, titulo, fondoVictoria;
	
	private BufferedImage nodoCiudad1, nodoCiudad2, nodoCiudad3, nodoCiudad4, nodoCiudad5;
	private BufferedImage nodoCiudad6, nodoCiudad7, nodoCiudad8, nodoCiudad9, nodoCiudad10;
	
	private BufferedImage piedra, piedra2, pasto, pasto2, pasto3, cofre, madera;


	/*
	 * Pre: Los archivos de imagen requeridos deben existir dentro de las carpetas de recursos del proyecto con sus nombres correctos.
	 * Post: Crea el gestor y gatilla de manera secuencial la lectura de todas las imágenes del juego (menús, jugador, mapas, terrenos y botones de minijuegos) para tenerlas listas en memoria.
	 */

	private BufferedImage iconoBorrar, iconoGuardar, iconoTester;
	private BufferedImage botonBubble, botonQuick, botonBFS, botonDFS;
	
	private BufferedImage radar, antorcha, bengala;
	private BufferedImage instrucciones;
	private BufferedImage[] librosOrdenamiento;
	private BufferedImage[] gemasNodos;
	
	public GestorRecursos() {
		cargarSpritesMenu();
		cargarSpritesJugador();
		cargarTiles();
		cargarFondoMapa();
		cargarBotonesArdillas();
		cargarBotonesAdicionales();
		cargarItemsMatrices();
		cargarInstrucciones();
		cargarLibrosOrdenamiento();
		cargarGemasNodos();
	}
	
	/*
	 * Pre: Ninguna.
	 * Post: Carga desde el disco las imágenes para los botones del menú principal y el botón de regresar. Si ocurre un error al leer los archivos, imprime el rastro del error.
	 */
	public void cargarSpritesMenu() {
		try {
			botonMenu1 = ImageIO.read(getClass().getResourceAsStream("/sprites/menu/boton1.png"));
			botonMenu2 = ImageIO.read(getClass().getResourceAsStream("/sprites/menu/boton2.png"));
			botonMenu3 = ImageIO.read(getClass().getResourceAsStream("/sprites/menu/boton3.png"));
			botonVolver = ImageIO.read(getClass().getResourceAsStream("/sprites/menu/botonVolver.png"));
			fondoVictoria = ImageIO.read(getClass().getResourceAsStream("/sprites/menu/Victoria.png"));
			titulo = ImageIO.read(getClass().getResourceAsStream("/sprites/menu/titulo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/*
	 * Pre: Ninguna.
	 * Post: Inicializa los vectores de animación para el jugador en las cuatro direcciones básicas y carga los dos cuadros (sprites) de movimiento correspondientes a cada una.
	 */

	public void cargarSpritesJugador() {
		
		jugadorUp = new BufferedImage[2];
		jugadorDown = new BufferedImage[2];
		jugadorLeft = new BufferedImage[2];
		jugadorRight = new BufferedImage[2];
		
		try {
			
			jugadorUp[0] = ImageIO.read(getClass().getResourceAsStream("/sprites/player/up1.png"));
			jugadorUp[1] = ImageIO.read(getClass().getResourceAsStream("/sprites/player/up2.png"));
			
			jugadorDown[0] = ImageIO.read(getClass().getResourceAsStream("/sprites/player/down1.png"));
			jugadorDown[1] = ImageIO.read(getClass().getResourceAsStream("/sprites/player/down2.png"));
			
			jugadorLeft[0] = ImageIO.read(getClass().getResourceAsStream("/sprites/player/left1.png"));
			jugadorLeft[1] = ImageIO.read(getClass().getResourceAsStream("/sprites/player/left2.png"));
			
			jugadorRight[0] = ImageIO.read(getClass().getResourceAsStream("/sprites/player/right1.png"));
			jugadorRight[1] = ImageIO.read(getClass().getResourceAsStream("/sprites/player/right2.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/*
	 * Pre: Ninguna.
	 * Post: Reservado para futuras cargas de fondos generales si fuera necesario.
	 */
	public void cargarFondos() {
		
	}
	
	/*
	 * Pre: Ninguna.
	 * Post: Carga la imagen de fondo para el mapa global del juego, las ilustraciones de los íconos (nodos) de las 10 ciudades y el fondo del menú inicial.
	 */

	public void cargarFondoMapa() {
	    try {
	        fondoMapa = ImageIO.read(getClass().getResourceAsStream("/sprites/mapa/fondo.png"));
	        
	        nodoCiudad1 = ImageIO.read(getClass().getResourceAsStream("/sprites/mapa/ciudad1.png"));
	        nodoCiudad2 = ImageIO.read(getClass().getResourceAsStream("/sprites/mapa/ciudad2.png"));
	        nodoCiudad3 = ImageIO.read(getClass().getResourceAsStream("/sprites/mapa/ciudad3.png"));
	        nodoCiudad4 = ImageIO.read(getClass().getResourceAsStream("/sprites/mapa/ciudad4.png"));
	        nodoCiudad5 = ImageIO.read(getClass().getResourceAsStream("/sprites/mapa/ciudad5.png"));
	        nodoCiudad6 = ImageIO.read(getClass().getResourceAsStream("/sprites/mapa/ciudad6.png"));
	        nodoCiudad7 = ImageIO.read(getClass().getResourceAsStream("/sprites/mapa/ciudad7.png"));
	        nodoCiudad8 = ImageIO.read(getClass().getResourceAsStream("/sprites/mapa/ciudad8.png"));
	        nodoCiudad9 = ImageIO.read(getClass().getResourceAsStream("/sprites/mapa/ciudad9.png"));
	        nodoCiudad10 = ImageIO.read(getClass().getResourceAsStream("/sprites/mapa/ciudad10.png"));
	        fondoMenu = ImageIO.read(getClass().getResourceAsStream("/sprites/menu/fondo.png"));
	    } catch (Exception e) {
	        System.out.println("ERROR cargarFondoMapa: " + e.getMessage());
	    }
	}
	
	
	/*
	 * Pre: Ninguna.
	 * Post: Carga las texturas básicas de bloques (pasto, piedra, madera) y elementos especiales (cofre) utilizados para armar el escenario de los minijuegos.
	 */
	private void cargarTiles() {
		try {
			piedra = ImageIO.read(getClass().getResourceAsStream("/tiles/piedra.png"));
			piedra2 = ImageIO.read(getClass().getResourceAsStream("/tiles/piedra2.png"));
			pasto = ImageIO.read(getClass().getResourceAsStream("/tiles/pasto.png"));
			pasto2 = ImageIO.read(getClass().getResourceAsStream("/tiles/pasto2.png"));
			pasto3 = ImageIO.read(getClass().getResourceAsStream("/tiles/pasto3.png"));
			madera = ImageIO.read(getClass().getResourceAsStream("/tiles/madera.png"));
			cofre = ImageIO.read(getClass().getResourceAsStream("/tiles/cofre.png")); // se que no es tileee
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Pre: Ninguna.
	 * Post: Lee y carga los recursos visuales referidos a los gráficos de las ardillas competidoras para el minijuego de búsqueda.
	 */
	private void cargarBotonesArdillas() {
		try {
			botonArbol = ImageIO.read(getClass().getResourceAsStream("/busquedaArchivos/ardilla1.png"));
			botonLista = ImageIO.read(getClass().getResourceAsStream("/busquedaArchivos/ardilla2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void cargarBotonesAdicionales() {
		try {
			botonBubble = ImageIO.read(getClass().getResourceAsStream("/sprites/botones/BotonBubbleSort.png"));
			botonQuick = ImageIO.read(getClass().getResourceAsStream("/sprites/botones/BotonQuickSort.png"));

			botonBFS = ImageIO.read(getClass().getResourceAsStream("/sprites/botones/BotonBusquedaBFS.png"));
			botonDFS = ImageIO.read(getClass().getResourceAsStream("/sprites/botones/BotonBusquedaDFS.png"));

			iconoBorrar = ImageIO.read(getClass().getResourceAsStream("/sprites/botones/IconoEliminarDatos.png"));
			iconoGuardar = ImageIO.read(getClass().getResourceAsStream("/sprites/botones/IconoGuardarDatos.png"));
			iconoTester = ImageIO.read(getClass().getResourceAsStream("/sprites/botones/IconoTester.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void cargarLibrosOrdenamiento() {
	    librosOrdenamiento = new BufferedImage[10];
	    try {
	        for (int i = 0; i < 10; i++) {
	        	librosOrdenamiento[i] = ImageIO.read(getClass().getResourceAsStream("/ciudad4/libro" + i + ".png"));
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	private void cargarGemasNodos() {
	    gemasNodos = new BufferedImage[8];
	    String[] colores = {"turquoise", "lightgreen", "blue", "purple", "lilac", "red", "gold", "darkblue"};
	    try {
	        for (int i = 0; i < colores.length; i++) {
	            gemasNodos[i] = ImageIO.read(getClass().getResourceAsStream("/ciudad7/gema_" + colores[i] + ".png"));
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	private void cargarItemsMatrices() {
		try {
			
			radar = ImageIO.read(getClass().getResourceAsStream("/sprites/matrices/Radar.png"));
			antorcha = ImageIO.read(getClass().getResourceAsStream("/sprites/matrices/Antorcha.png"));
			bengala = ImageIO.read(getClass().getResourceAsStream("/sprites/matrices/Bengala.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void cargarInstrucciones() {
		try {
			instrucciones = ImageIO.read(getClass().getResourceAsStream("/sprites./instrucciones/Instrucciones.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ========================================= GETTERS ==============================================
	
	public BufferedImage getTitulo() {
		return titulo;
	}
	
	public BufferedImage getInstrucciones() {
		return instrucciones;
	}
	
	public BufferedImage getRadar() {
		return radar;
	}

	public BufferedImage getAntorcha() {
		return antorcha;
	}

	public BufferedImage getBengala() {
		return bengala;
	}

	public BufferedImage getIconoBorrar() {
		return iconoBorrar;
	}


	public BufferedImage getIconoGuardar() {
		return iconoGuardar;
	}

	public BufferedImage getIconoTester() {
		return iconoTester;
	}

	public BufferedImage getBotonBubble() {
		return botonBubble;
	}


	public BufferedImage getBotonQuick() {
		return botonQuick;
	}


	public BufferedImage getBotonBFS() {
		return botonBFS;
	}


	public BufferedImage getBotonDFS() {
		return botonDFS;
	}

	/* Pre: Ninguna. Post: Devuelve la imagen del botón 1 del menú. */
	public BufferedImage getBotonMenu1() {
		return botonMenu1;
	}

	/* Pre: Ninguna. Post: Devuelve la imagen del botón 2 del menú. */
	public BufferedImage getBotonMenu2() {
		return botonMenu2;
	}
	
	/* Pre: Ninguna. Post: Devuelve la imagen del botón volver. */
	public BufferedImage getBotonVolver() {
	    return botonVolver;
	}
	
	public BufferedImage getFondoVictoria() {
	    return fondoVictoria;
	}

	/* Pre: Ninguna. Post: Devuelve la imagen del botón 3 del menú. */
	public BufferedImage getBotonMenu3() {
		return botonMenu3;
	}

	/* Pre: Ninguna. Post: Devuelve el arreglo con los sprites del jugador moviéndose hacia arriba. */
	public BufferedImage[] getJugadorUp() {
		return jugadorUp;
	}

	/* Pre: Ninguna. Post: Devuelve el arreglo con los sprites del jugador moviéndose hacia abajo. */
	public BufferedImage[] getJugadorDown() {
		return jugadorDown;
	}
	
	/* Pre: Ninguna. Post: Devuelve la imagen del fondo del mapa mundial. */
	public BufferedImage getFondoMapa() {
	    return fondoMapa;
	}
	
	/* Pre: Ninguna. Post: Devuelve la imagen del fondo del menú. */
	public BufferedImage getFondoMenu() {
	    return fondoMenu;
	}


	/* Pre: Ninguna. Post: Devuelve el arreglo con los sprites del jugador moviéndose hacia la izquierda. */

	public BufferedImage[] getJugadorLeft() {
		return jugadorLeft;
	}

	/* Pre: Ninguna. Post: Devuelve el arreglo con los sprites del jugador moviéndose hacia la derecha. */
	public BufferedImage[] getJugadorRight() {
		return jugadorRight;
	}

	/* Pre: Ninguna. Post: Devuelve la textura de piedra tipo 1. */
	public BufferedImage getPiedra() {
		return piedra;
	}

	/* Pre: Ninguna. Post: Devuelve la textura de piedra tipo 2. */
	public BufferedImage getPiedra2() {
		return piedra2;
	}

	/* Pre: Ninguna. Post: Devuelve la textura de pasto tipo 1. */
	public BufferedImage getPasto() {
		return pasto;
	}

	/* Pre: Ninguna. Post: Devuelve la textura de pasto tipo 2. */
	public BufferedImage getPasto2() {
		return pasto2;
	}
	
	/* Pre: Ninguna. Post: Devuelve la textura de pasto tipo 3. */
	public BufferedImage getPasto3() {
		return pasto3;
	}
	
	/* Pre: Ninguna. Post: Devuelve la textura de madera. */
	public BufferedImage getMadera() {
		return madera;
	}

	/* Pre: Ninguna. Post: Devuelve la imagen del cofre. */
	public BufferedImage getCofre() {
		return cofre;
	}
	
	/* Pre: Ninguna. Post: Devuelve el sprite de la ardilla del árbol. */
	public BufferedImage getBotonArbol() {
		return botonArbol;
	}

	/* Pre: Ninguna. Post: Devuelve el sprite de la ardilla de la lista. */
	public BufferedImage getBotonLista() {
		return botonLista;
	}

	/* Pre: Ninguna. Post: Devuelve el ícono de la ciudad 1. */
	public BufferedImage getNodoCiudad1() {
	    return nodoCiudad1;
	}
	
	/* Pre: Ninguna. Post: Devuelve el ícono de la ciudad 2. */
	public BufferedImage getNodoCiudad2() {
	    return nodoCiudad2;
	}
	
	/* Pre: Ninguna. Post: Devuelve el ícono de la ciudad 3. */
	public BufferedImage getNodoCiudad3() {
	    return nodoCiudad3;
	}
	
	/* Pre: Ninguna. Post: Devuelve el ícono de la ciudad 4. */
	public BufferedImage getNodoCiudad4() {
	    return nodoCiudad4;
	}
	
	/* Pre: Ninguna. Post: Devuelve el ícono de la ciudad 5. */
	public BufferedImage getNodoCiudad5() {
	    return nodoCiudad5;
	}
	
	/* Pre: Ninguna. Post: Devuelve el ícono de la ciudad 6. */
	public BufferedImage getNodoCiudad6() {
	    return nodoCiudad6;
	}
	
	/* Pre: Ninguna. Post: Devuelve el ícono de la ciudad 7. */
	public BufferedImage getNodoCiudad7() {
	    return nodoCiudad7;
	}
	
	/* Pre: Ninguna. Post: Devuelve el ícono de la ciudad 8. */
	public BufferedImage getNodoCiudad8() {
	    return nodoCiudad8;
	}
	
	/* Pre: Ninguna. Post: Devuelve el ícono de la ciudad 9. */
	public BufferedImage getNodoCiudad9() {
	    return nodoCiudad9;
	}
	
	/* Pre: Ninguna. Post: Devuelve el ícono de la ciudad 10. */
	public BufferedImage getNodoCiudad10() {
	    return nodoCiudad10;
	}
	
	public BufferedImage[] getLibrosOrdenamiento() {
	    return librosOrdenamiento;
	}
	
	public BufferedImage[] getGemasNodos() {
	    return gemasNodos;
	}
}
