package ui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GestorRecursos {
	
	private BufferedImage botonMenu1, botonMenu2, botonMenu3;
	private BufferedImage[] jugadorUp;
	private BufferedImage[] jugadorDown;
	private BufferedImage[] jugadorLeft;
	private BufferedImage[] jugadorRight;

	
	public GestorRecursos() {
		cargarSpritesMenu();
		cargarSpritesJugador();
		cargarFondos();
	}
	
	
	public void cargarSpritesMenu() {
		try {
			
			botonMenu1 = ImageIO.read(getClass().getResourceAsStream("/sprites/menu/boton1.png"));
			botonMenu2 = ImageIO.read(getClass().getResourceAsStream("/sprites/menu/boton2.png"));
			botonMenu3 = ImageIO.read(getClass().getResourceAsStream("/sprites/menu/boton3.png"));
			
			System.out.println("boton1: " + botonMenu1);
	        System.out.println("boton2: " + botonMenu2);
	        System.out.println("boton3: " + botonMenu3);

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

	public BufferedImage getBotonMenu1() {
		return botonMenu1;
	}

	public BufferedImage getBotonMenu2() {
		return botonMenu2;
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


	public BufferedImage[] getJugadorLeft() {
		return jugadorLeft;
	}

	public BufferedImage[] getJugadorRight() {
		return jugadorRight;
	}
	
}
