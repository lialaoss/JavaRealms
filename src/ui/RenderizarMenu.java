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
	    g2.drawImage(recursos.getFondoMenu(), 0, 0, screenWidth, screenHeight, null);
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
		
		ciudades.add(new NodoCiudad(80,  80,  1,  recursos.getNodoCiudad1(),  "Recolección"));
		ciudades.add(new NodoCiudad(220, 60,  2,  recursos.getNodoCiudad2(),  "N-Reinas"));
		ciudades.add(new NodoCiudad(370, 100, 3,  recursos.getNodoCiudad3(),  "Laberinto"));
		ciudades.add(new NodoCiudad(500, 60,  4,  recursos.getNodoCiudad4(),  "Ordenamiento"));
		ciudades.add(new NodoCiudad(650, 100, 5,  recursos.getNodoCiudad5(),  "Búsqueda"));
		ciudades.add(new NodoCiudad(800, 60,  6,  recursos.getNodoCiudad6(),  "Hashing"));
		ciudades.add(new NodoCiudad(900, 200, 7,  recursos.getNodoCiudad7(),  "Grafos"));
		ciudades.add(new NodoCiudad(750, 350, 8,  recursos.getNodoCiudad8(),  "Hanoi"));
		ciudades.add(new NodoCiudad(550, 420, 9,  recursos.getNodoCiudad9(),  "Batalla"));
		ciudades.add(new NodoCiudad(350, 380, 10, recursos.getNodoCiudad10(), "Complejidad"));
	}
	
	public void renderizarMapaGeneral(Graphics2D g2) {
	    g2.drawImage(recursos.getFondoMapa(), 0, 0, screenWidth, screenHeight, null);
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
	
	
	public void procesarClickMenuConTransicion(int mouseX, int mouseY, AdministradorJuego admin, ui.Panel panel) {
	    if(botonJugar.contiene(mouseX, mouseY)) {
	        panel.iniciarTransicion(EstadoJuego.MAPA_GENERAL);
	    } else if(botonInstrucciones.contiene(mouseX, mouseY)) {
	        admin.setEstado(EstadoJuego.MENU_INSTRUCCIONES);
	    } else if(botonSalir.contiene(mouseX, mouseY)) {
	        System.exit(0);
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
