package ui;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import logica.AdministradorJuego;
import logica.EstadoJuego;

public class RenderizarMenu {
	
	// ATRIBUTOS
	private AdministradorJuego admin;
	private GestorRecursos recursos;
	
	private int screenWidth;
	private int screenHeight;
	
	private BotonMenu botonJugar;
	private BotonMenu botonInstrucciones;
	private BotonMenu botonSalir;
	
	private List<NodoCiudad> ciudades;
	
	// CONSTRUCTOR
	public RenderizarMenu(int screenWidth, int screenHeight, GestorRecursos recursos, AdministradorJuego admin) {
		setScreenWidth(screenWidth);
		setScreenHeight(screenHeight);
		setRecursos(recursos);
		setAdmin(admin);
		crearBotones();
		crarCiudades();
	}
	
	// ============================== MENU =======================================
	
	public void crearBotones() {
		int anchoBoton = 300;
		int altoBoton = 70;

		int x = (this.screenWidth - anchoBoton) / 2;
		int y = (this.screenHeight - altoBoton) / 2 - 50;
		
		botonJugar = new BotonMenu(recursos.getBotonMenu1(),
				x, y, anchoBoton, altoBoton);
		
		botonInstrucciones = new BotonMenu(recursos.getBotonMenu2(),
				x, y + 100, anchoBoton, altoBoton);
		
		botonSalir = new BotonMenu(recursos.getBotonMenu3(),
				x, y + 200, anchoBoton, altoBoton);
	}
	
	public void renderizarMenuPrincipal(Graphics2D g2) {
		System.out.println("ENTRANDO A RENDERMENU");
		g2.setColor(java.awt.Color.DARK_GRAY);
		g2.fillRect(0, 0, screenWidth, screenHeight);
		
		System.out.println("Dibujando botones en pantalla " + screenWidth + "x" + screenHeight);
		
		botonJugar.dibujar(g2);
		botonInstrucciones.dibujar(g2);
		botonSalir.dibujar(g2);
	}
	
	/**
	 * Detecta la ubicacion de los botones y con ello el estado del juego cambia
	 * @param mouseX
	 * @param mouseY
	 * @param admin
	 */
	public void procesarClickMenu(int mouseX, int mouseY, AdministradorJuego admin) {

	    if(botonJugar.contiene(mouseX, mouseY)) {
	        admin.setEstado(EstadoJuego.MAPA_GENERAL);
	    } else if(botonInstrucciones.contiene(mouseX, mouseY)) {
	        admin.setEstado(EstadoJuego.MENU_INSTRUCCIONES);
	    } else if(botonSalir.contiene(mouseX, mouseY)) {
	        System.exit(0);
	    }
	}
	
	public void renderizarMenuInstrucciones(Graphics2D g2) {
		g2.drawString("Jeje lol (apreta Q para salir)", screenWidth / 2 - 80, screenHeight / 2);

	}	
	
	public void renderizarMenuEnPausa(Graphics2D g2) {
		
	}
	
	// ================================ MAPA ====================================
	
	/**
	 * (Despues les pongo imagenes y mejores coordenadas dea)
	 */
	public void crarCiudades() {
		ciudades = new ArrayList<>();
		
		ciudades.add(new NodoCiudad(100, 100, 1));
		ciudades.add(new NodoCiudad(150, 150, 2));
		ciudades.add(new NodoCiudad(200, 200, 3));
		ciudades.add(new NodoCiudad(300, 300, 4));
		ciudades.add(new NodoCiudad(450, 450, 5));
		ciudades.add(new NodoCiudad(500, 500, 6));
		ciudades.add(new NodoCiudad(600, 400, 7));
		ciudades.add(new NodoCiudad(700, 300, 8));
		ciudades.add(new NodoCiudad(800, 200, 9));
		ciudades.add(new NodoCiudad(900, 100, 10));
	}
	
	public void renderizarMapaGeneral(Graphics2D g2) {
		for(NodoCiudad ciudad : ciudades) {
			ciudad.dibujar(g2);
		}
	}
	
	/**
	 * Detecta que el click sobre una ciudad (el jugador decide entrar en ella)
	 * @param mouseX
	 * @param mouseY
	 * @param admin
	 */
	public void procesarClickMapa(int mouseX, int mouseY, AdministradorJuego admin) {
		for (NodoCiudad ciudad : ciudades) {
	        if (ciudad.contiene(mouseX, mouseY)) {
	            System.out.println("Ciudad " + ciudad.getId());
	            admin.setEstado(EstadoJuego.EN_PROGRESO);
	            admin.cambiarDeCiudad(ciudad.getId());
	            break;
	        }
	    }
	}
	                         
	// ================================= FIN DEL JUEGO =========================================
	
	public void renderizarFin(Graphics2D g2) {
		
	}
	
	// SETTERS
	private void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	private void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
	
	private void setRecursos(GestorRecursos recursos) {
		this.recursos = recursos;
	}

	private void setAdmin(AdministradorJuego admin) {
		this.admin = admin;
	}
	

}
