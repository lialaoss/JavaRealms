package ui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GestorRecursos {
	
	private BufferedImage botonMenu1, botonMenu2, botonMenu3, botonVolver;
	private BufferedImage[] jugadorUp;
	private BufferedImage[] jugadorDown;
	private BufferedImage[] jugadorLeft;
	private BufferedImage[] jugadorRight;
	private BufferedImage fondoMapa;
	private BufferedImage fondoMenu;
	private BufferedImage nodoCiudad1;
	private BufferedImage nodoCiudad2;
	private BufferedImage nodoCiudad3;
	private BufferedImage nodoCiudad4;
	private BufferedImage nodoCiudad5;
	private BufferedImage nodoCiudad6;
	private BufferedImage nodoCiudad7;
	private BufferedImage nodoCiudad8;
	private BufferedImage nodoCiudad9;
	private BufferedImage nodoCiudad10;
	
	private BufferedImage piedra, piedra2, pasto, pasto2, pasto3, cofre;

	
	public GestorRecursos() {
		cargarSpritesMenu();
		cargarSpritesJugador();
		cargarFondos();
		cargarTiles();
		cargarFondoMapa();
	}
	
	
	public void cargarSpritesMenu() {
		try {
			botonMenu1 = ImageIO.read(getClass().getResourceAsStream("/sprites/menu/boton1.png"));
			botonMenu2 = ImageIO.read(getClass().getResourceAsStream("/sprites/menu/boton2.png"));
			botonMenu3 = ImageIO.read(getClass().getResourceAsStream("/sprites/menu/boton3.png"));
			botonVolver = ImageIO.read(getClass().getResourceAsStream("/sprites/menu/botonVolver.png"));
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
	
	public void cargarFondos() {
		
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
			cofre = ImageIO.read(getClass().getResourceAsStream("/tiles/cofre.png")); // se que no es tileee
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public BufferedImage getCofre() {
		return cofre;
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
}
