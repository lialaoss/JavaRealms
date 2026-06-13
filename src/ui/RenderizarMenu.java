package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import logica.AdministradorJuego;
import logica.EstadoJuego;

public class RenderizarMenu {
	
	// ATRIBUTOS
	private AdministradorJuego admin;
	private GestorRecursos recursos;
	
	private int screenWidth = ConfiguracionPantalla.SCREEN_WIDTH;
	private int screenHeight = ConfiguracionPantalla.SCREEN_HEIGHT;
	
	private Boton botonJugar, botonSalir, botonInstrucciones;
	private Boton botonGuardado, botonBorrar;
	
	private List<NodoCiudad> ciudades;
	
	// CONSTRUCTOR
	public RenderizarMenu(GestorRecursos recursos, AdministradorJuego admin) {
		setRecursos(recursos);
		setAdmin(admin);
		crearBotones();
		crarCiudades();
		crearBotonGuardado();
		crearBotonBorrarDatos();
	}
	
	// ============================== MENU =======================================
	
	public void crearBotonGuardado() {
		int anchoBoton = 100;
		int altoBoton = 60;
		
		int x = 0;
		int y = 10;
		
		botonGuardado = new Boton(recursos.getIconoGuardar(),
				x, y, anchoBoton, altoBoton);
	}
	
	public void crearBotonBorrarDatos() {
		int anchoBoton = 100;
		int altoBoton = 60;
		
		int x = 0;
		int y = 60;
		
		botonBorrar = new Boton(recursos.getIconoBorrar(),
				x, y, anchoBoton, altoBoton);
	}
	
	public void crearBotones() {
		int anchoBoton = 300;
		int altoBoton = 70;

		int x = (this.screenWidth - anchoBoton) / 2;
		int y = (this.screenHeight - altoBoton) / 2 - 50;
		
		botonJugar = new Boton(recursos.getBotonMenu1(),
				x, y, anchoBoton, altoBoton);
		
		botonInstrucciones = new Boton(recursos.getBotonMenu2(),
				x, y + 100, anchoBoton, altoBoton);
		
		botonSalir = new Boton(recursos.getBotonMenu3(),
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
	public void procesarClickMenu(int mouseX, int mouseY) {
	    if(botonJugar.contiene(mouseX, mouseY)) {
	        admin.setEstado(EstadoJuego.MAPA_GENERAL);
	    } else if(botonInstrucciones.contiene(mouseX, mouseY)) {
	        admin.setEstado(EstadoJuego.MENU_INSTRUCCIONES);
	    } else if(botonSalir.contiene(mouseX, mouseY)) {
	        System.exit(0);
	    }
	}
	
	public void renderizarMenuInstrucciones(Graphics2D g2) {
	    g2.drawImage(recursos.getFondoMenu(), 0, 0, screenWidth, screenHeight, null);
		g2.drawString("Jeje lol (apreta Q para salir)", screenWidth / 2 - 80, screenHeight / 2);

	}	
	
	// ================================ MAPA ====================================

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
	    botonGuardado.dibujar(g2);
	    botonBorrar.dibujar(g2);
	    for(NodoCiudad ciudad : ciudades) {
	        ciudad.dibujar(g2);
	    }
	}
	
	/**
	 * Detecta el click sobre una ciudad.
	 * @param mouseX
	 * @param mouseY
	 * @param admin
	 */
	public void procesarClickMapa(int mouseX, int mouseY) {
		for (NodoCiudad ciudad : ciudades) {
	        if (ciudad.contiene(mouseX, mouseY)) {
	            admin.setEstado(EstadoJuego.EN_PROGRESO);
	            admin.cambiarDeCiudad(ciudad.getId());
	            break;
	        }
	    }
		if(botonGuardado.contiene(mouseX, mouseY)) {
			admin.actualizarDatos();
		}

		if(botonBorrar.contiene(mouseX, mouseY)) {
            admin.eliminarDatos();
	        System.exit(0);
		}
	}
	
	
	public void procesarClickMenuConTransicion(int mouseX, int mouseY, ui.Panel panel) {
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
	    g2.setColor(Color.BLACK);
	    g2.fillRect(0, 0, screenWidth, screenHeight);

	    g2.setColor(Color.WHITE);
	    g2.setFont(new Font("Arial", Font.BOLD, 48));

	    String texto = "¡GANASTE!";
	    FontMetrics fm = g2.getFontMetrics();

	    int x = (screenWidth - fm.stringWidth(texto)) / 2;
	    int y = screenHeight / 2;

	    g2.drawString(texto, x, y);
	    g2.setFont(new Font("Arial", Font.PLAIN, 20));

	    String subtitulo = "Completaste todas las ciudades... vuelve a jugar!";
	    int x2 = (screenWidth - g2.getFontMetrics().stringWidth(subtitulo)) / 2;

	    g2.drawString(subtitulo, x2, y + 50);
	    
		botonBorrar.dibujar(g2);
	}
	
	public void procesarClickFinal(int mouseX, int mouseY) {
		if(botonBorrar.contiene(mouseX, mouseY)) {
            admin.eliminarDatos();
	        System.exit(0);
		}
	}
	
	
	// SETTERS
	private void setRecursos(GestorRecursos recursos) {
		this.recursos = recursos;
	}

	private void setAdmin(AdministradorJuego admin) {
		this.admin = admin;
	}
	

}
