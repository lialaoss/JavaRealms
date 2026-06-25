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
	
	private BufferedImage fondoMapa, fondoMenu, titulo;
	
	private BufferedImage nodoCiudad1, nodoCiudad2, nodoCiudad3, nodoCiudad4, nodoCiudad5;
	private BufferedImage nodoCiudad6, nodoCiudad7, nodoCiudad8, nodoCiudad9, nodoCiudad10;
	
	private BufferedImage piedra, piedra2, pasto, pasto2, pasto3, cofre, madera;

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
	
	
	public void cargarSpritesMenu() {
		try {
			botonMenu1 = ImageIO.read(getClass().getResourceAsStream("/sprites/menu/boton1.png"));
			botonMenu2 = ImageIO.read(getClass().getResourceAsStream("/sprites/menu/boton2.png"));
			botonMenu3 = ImageIO.read(getClass().getResourceAsStream("/sprites/menu/boton3.png"));
			botonVolver = ImageIO.read(getClass().getResourceAsStream("/sprites/menu/botonVolver.png"));
			titulo = ImageIO.read(getClass().getResourceAsStream("/sprites/menu/titulo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
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

	public BufferedImage getBotonMenu1() {
		return botonMenu1;
	}

	public BufferedImage getBotonMenu2() {
		return botonMenu2;
	}
	
	public BufferedImage getBotonVolver() {
	    return botonVolver;
	}

	public BufferedImage getBotonMenu3() {
		return botonMenu3;
	}

	public BufferedImage[] getJugadorUp() {
		return jugadorUp;
	}

	public BufferedImage[] getJugadorDown() {
		return jugadorDown;
	}
	
	public BufferedImage getFondoMapa() {
	    return fondoMapa;
	}
	
	public BufferedImage getFondoMenu() {
	    return fondoMenu;
	}

	public BufferedImage[] getJugadorLeft() {
		return jugadorLeft;
	}

	public BufferedImage[] getJugadorRight() {
		return jugadorRight;
	}


	public BufferedImage getPiedra() {
		return piedra;
	}


	public BufferedImage getPiedra2() {
		return piedra2;
	}


	public BufferedImage getPasto() {
		return pasto;
	}


	public BufferedImage getPasto2() {
		return pasto2;
	}
	
	public BufferedImage getPasto3() {
		return pasto3;
	}
	
	public BufferedImage getMadera() {
		return madera;
	}

	public BufferedImage getCofre() {
		return cofre;
	}
	
	public BufferedImage getBotonArbol() {
		return botonArbol;
	}


	public BufferedImage getBotonLista() {
		return botonLista;
	}

	public BufferedImage getNodoCiudad1() {
	    return nodoCiudad1;
	}
	
	public BufferedImage getNodoCiudad2() {
	    return nodoCiudad2;
	}
	
	public BufferedImage getNodoCiudad3() {
	    return nodoCiudad3;
	}
	
	public BufferedImage getNodoCiudad4() {
	    return nodoCiudad4;
	}
	
	public BufferedImage getNodoCiudad5() {
	    return nodoCiudad5;
	}
	
	public BufferedImage getNodoCiudad6() {
	    return nodoCiudad6;
	}
	
	public BufferedImage getNodoCiudad7() {
	    return nodoCiudad7;
	}
	
	public BufferedImage getNodoCiudad8() {
	    return nodoCiudad8;
	}
	
	public BufferedImage getNodoCiudad9() {
	    return nodoCiudad9;
	}
	
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
