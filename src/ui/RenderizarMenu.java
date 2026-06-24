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
	
	private int tileSize = ConfiguracionPantalla.TILE_SIZE;
	private int screenWidth = ConfiguracionPantalla.SCREEN_WIDTH;
	private int screenHeight = ConfiguracionPantalla.SCREEN_HEIGHT;
	
	private Boton botonJugar, botonSalir, botonInstrucciones;
	private Boton botonGuardado, botonBorrar, botonTester;
	
	private List<NodoCiudad> ciudades;
	
	private String mensajeTemporal = "";
	private long tiempoFinMensaje = 0;
	
	// CONSTRUCTOR
	public RenderizarMenu(GestorRecursos recursos, AdministradorJuego admin) {
		setRecursos(recursos);
		setAdmin(admin);
		crearBotones();
		crearCiudades();
		crearBotonGuardado();
		crearBotonBorrarDatos();
		crearBotonTester();
	}
	
	// ============================== MENU =======================================
	
	private void crearBotonGuardado() {
		int anchoBoton = tileSize;
		int altoBoton = tileSize;
		
		int x = tileSize / 2;
		int y = (tileSize / 2) + 20;
		
		botonGuardado = new Boton(recursos.getIconoGuardar(),
				x, y, anchoBoton, altoBoton);
	}
	
	private void crearBotonBorrarDatos() {
		int anchoBoton = tileSize;
		int altoBoton = tileSize;
		
		int x = tileSize / 2;
		int y = (tileSize * 2) - (tileSize / 2) + 20;
		
		botonBorrar = new Boton(recursos.getIconoBorrar(),
				x, y, anchoBoton, altoBoton);
	}
	
	private void crearBotonTester() {
		int anchoBoton = tileSize;
		int altoBoton = tileSize;
		
		int x = tileSize / 2;
		int y = (tileSize * 3) - (tileSize / 2) + 22;
		
		botonTester = new Boton(recursos.getIconoTester(),
				x, y, anchoBoton, altoBoton);
	}
	
	private void crearBotones() {
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
		g2.drawString("Apreta Q para salir)", screenWidth / 2 - 80, screenHeight / 2);

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
	
	// ================================ MAPA ====================================

	public void crearCiudades() {
		ciudades = new ArrayList<>();
		
		ciudades.add(new NodoCiudad(150,  80, recursos.getNodoCiudad1(), admin.getCiudades().get(1)));
		
		ciudades.add(new NodoCiudad(130, 300, recursos.getNodoCiudad2(), admin.getCiudades().get(2)));
		ciudades.add(new NodoCiudad(330, 250, recursos.getNodoCiudad3(), admin.getCiudades().get(3)));
		ciudades.add(new NodoCiudad(350, 80, recursos.getNodoCiudad4(), admin.getCiudades().get(4)));
		
		ciudades.add(new NodoCiudad(350, 410, recursos.getNodoCiudad5(), admin.getCiudades().get(5)));
		ciudades.add(new NodoCiudad(525, 210, recursos.getNodoCiudad6(), admin.getCiudades().get(6)));
		
		ciudades.add(new NodoCiudad(653, 380, recursos.getNodoCiudad7(),admin.getCiudades().get(7)));
		
		ciudades.add(new NodoCiudad(731, 130, recursos.getNodoCiudad8(), admin.getCiudades().get(8)));
		ciudades.add(new NodoCiudad(933, 280, recursos.getNodoCiudad9(), admin.getCiudades().get(9)));
		
		ciudades.add(new NodoCiudad(962, 80, recursos.getNodoCiudad10(), admin.getCiudades().get(10)));
	}
	
	public void renderizarMapaGeneral(Graphics2D g2) {
	    g2.drawImage(recursos.getFondoMapa(), 0, 0, screenWidth, screenHeight, null);
	    botonGuardado.dibujar(g2);
	    botonBorrar.dibujar(g2);
	    botonTester.dibujar(g2);
	    for(NodoCiudad ciudad : ciudades) {
	        ciudad.dibujar(g2);
	    }
	    mostrarHudMapa(g2);
	    dibujarMensajeTemporal(g2);
	}
	
	private void dibujarMensajeTemporal(Graphics2D g2) {
	    if (mensajeTemporal.isEmpty()) {
	        return;
	    }
	    
	    if (System.currentTimeMillis() > tiempoFinMensaje) {
	        mensajeTemporal = "";
	        return;
	    }

	    g2.setFont(new Font("Arial", Font.PLAIN, 12));
	    g2.setColor(Color.WHITE);
	    g2.drawString(mensajeTemporal, 80, 70);
	}
	
	private void mostrarMensajeTemporal(String mensaje, int duracionMs) {
	    mensajeTemporal = mensaje;
	    tiempoFinMensaje = System.currentTimeMillis() + duracionMs;
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
	        mostrarMensajeTemporal("¡Guardado con éxito!", 2000);
		}

		if(botonBorrar.contiene(mouseX, mouseY)) {
            admin.eliminarDatos();
	        System.exit(0);
		}
		if(botonTester.contiene(mouseX, mouseY)) {
			admin.habilitarModoTester();
		}
	}
	
	private void mostrarHudMapa(Graphics2D g2) {
		g2.setColor(new Color(0, 0, 0, 180));
		g2.fillRect(0, 0, ConfiguracionPantalla.SCREEN_WIDTH, 40);
		
		String modoTester = "Desactivado";
		
		if(admin.testerActivo()) {
			modoTester = "Activado";
		}
		
		String texto =  "Tester: " + modoTester +
				"    Ciudades completadas: " + admin.cantidadCiudadesCompletadas() +
				"    Puntos: " + admin.getJugador().getPuntosExperiencia();

		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", Font.PLAIN, 15));

		FontMetrics fm = g2.getFontMetrics();

		int x = (ConfiguracionPantalla.SCREEN_WIDTH - fm.stringWidth(texto)) / 2;
		int y = 30;

		g2.drawString(texto, x, y);
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
