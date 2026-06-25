package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import logica.AdministradorJuego;
import logica.EstadoJuego;
import utiles.Validaciones;

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
	
	/**
	 * Pre: 'recursos' y 'admin' no deben ser nulos.
	 * Post: Inicializa el renderizador, configura las referencias y
	 * crea los elementos visuales (botones y ciudades).
	 * @param recursos
	 * @param admin
	 */
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
	
	/**
	 * Pre: 'recursos' está correctamente inicializado.
	 * Post: Crea e inicializa el 'botonGuardado' con su ícono y coordenadas correspondientes.
	 */
	private void crearBotonGuardado() {
		int anchoBoton = tileSize;
		int altoBoton = tileSize;
		
		int x = tileSize / 2;
		int y = (tileSize / 2) + 20;
		
		botonGuardado = new Boton(recursos.getIconoGuardar(),
				x, y, anchoBoton, altoBoton);
	}
	
	/**
	 * Pre: 'recursos' está correctamente inicializado.
	 * Post: Crea e inicializa el 'botonBorrar' con su ícono y coordenadas correspondientes.
	 */
	private void crearBotonBorrarDatos() {
		int anchoBoton = tileSize;
		int altoBoton = tileSize;
		
		int x = tileSize / 2;
		int y = (tileSize * 2) - (tileSize / 2) + 20;
		
		botonBorrar = new Boton(recursos.getIconoBorrar(),
				x, y, anchoBoton, altoBoton);
	}
	
	/**
	 * Pre: 'recursos' está correctamente inicializado.
	 * Post: Crea e inicializa el 'botonTester' con su ícono y coordenadas correspondientes.
	 */
	private void crearBotonTester() {
		int anchoBoton = tileSize;
		int altoBoton = tileSize;
		
		int x = tileSize / 2;
		int y = (tileSize * 3) - (tileSize / 2) + 22;
		
		botonTester = new Boton(recursos.getIconoTester(),
				x, y, anchoBoton, altoBoton);
	}
	
	/**
	 * Pre: 'recursos' está correctamente inicializado.
	 * Post: Crea e inicializa los botones principales del menú
	 * ('botonJugar', 'botonInstrucciones' y 'botonSalir').
	 */
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
	
	/**
	 * Pre: 'g2' no es nulo y los recursos del menú están cargados.
	 * Post: Dibuja el fondo, el título y los botones del menú principal en pantalla.
	 * @param g2
	 */
	public void renderizarMenuPrincipal(Graphics2D g2) {
	    g2.drawImage(recursos.getFondoMenu(), 0, 0, screenWidth, screenHeight, null);
	    int ancho = 400;
	    int alto = 110;
	    g2.drawImage(recursos.getTitulo(), (screenWidth - ancho)/2, 70, ancho, alto, null);
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
	
	/**
	 * Pre: 'panel' no es nulo.
	 * Post: Si se clickea Jugar, inicia la transición visual.
	 * Para Instrucciones o Salir, actúa de manera directa.
	 * @param mouseX
	 * @param mouseY
	 * @param panel
	 */
	public void procesarClickMenuConTransicion(int mouseX, int mouseY, ui.Panel panel) {
	    if(botonJugar.contiene(mouseX, mouseY)) {
	        panel.iniciarTransicion(EstadoJuego.MAPA_GENERAL);
	    } else if(botonInstrucciones.contiene(mouseX, mouseY)) {
	        admin.setEstado(EstadoJuego.MENU_INSTRUCCIONES);
	    } else if(botonSalir.contiene(mouseX, mouseY)) {
	        System.exit(0);
	    }
	}

	// =========================== INSTRUCCIONES =================================
	
	/**
	 * Pre: 'g2' no es nulo y la imagen de instrucciones está cargada.
	 * Post: Dibuja el fondo y la superposición con el texto/imagen de las instrucciones.
	 * @param g2
	 */
	public void renderizarMenuInstrucciones(Graphics2D g2) {
	    g2.drawImage(recursos.getFondoMenu(), 0, 0, screenWidth, screenHeight, null);
	    
		g2.setColor(new Color(0,0,0,87));

        g2.drawImage(recursos.getInstrucciones(), 50, 50, screenWidth - 100, screenHeight - 100, null);
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
	
	/**
	 * Pre: 'g2' no es nulo.
	 * Post: Dibuja el fondo del mapa, los botones de control, los nodos de
	 * las ciudades, el HUD y los mensajes activos.
	 * @param g2
	 */
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
	
	/**
	 * Pre: 'duracionMs' es mayor a 0.
	 * Post: Define el texto del 'mensajeTemporal' y calcula
	 * el 'tiempoFinMensaje' basándose en el tiempo actual del sistema.
	 * @param mensaje
	 * @param duracionMs
	 */
	private void mostrarMensajeTemporal(String mensaje, int duracionMs) {
	    mensajeTemporal = mensaje;
	    tiempoFinMensaje = System.currentTimeMillis() + duracionMs;
	}
	
	/**
	 * Pre: mouseX y mouseY deben ser mayor o igual a 0.
	 * Post: Evalúa la posición del clic. Puede cambiar el estado del juego
	 * a una ciudad, guardar partida (si no es modo tester), borrar datos
	 * (cierra juego) o alternar el modo tester.
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
			if(!admin.testerActivo()) {
				admin.actualizarDatos();
		        mostrarMensajeTemporal("¡Guardado con éxito!", 2000);
		        return;
			}
	        mostrarMensajeTemporal("¡No es posible guardar!", 2000);
	        return;
		}

		if(botonBorrar.contiene(mouseX, mouseY)) {
            admin.eliminarDatos();
	        System.exit(0);
		}
		if(botonTester.contiene(mouseX, mouseY)) {
			admin.habilitarModoTester();
	        return;
		}
	}
	
	/**
	 * Pre: 'g2' no es nulo y 'admin' tiene las referencias a jugador y ciudades.
	 * Post: Dibuja la barra superior negra con la información actualizada de
	 * puntos, ciudades completadas y estado del tester.
	 * @param g2
	 */
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
	
	/**
	 * Pre: 'g2' no es nulo.
	 * Post: Dibuja la pantalla negra de victoria, los textos de felicitaciones
	 * y el botón para borrar datos.
	 * @param g2
	 */
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
	
	/**
	 * Pre: mouseX y mouseY deben ser mayor o igual a 0.
	 * Post: Si se hace clic en el botón de borrar, elimina el progreso
	 * guardado y cierra el juego.
	 * @param mouseX
	 * @param mouseY
	 */
	public void procesarClickFinal(int mouseX, int mouseY) {
		if(botonBorrar.contiene(mouseX, mouseY)) {
            admin.eliminarDatos();
	        System.exit(0);
		}
	}
	
	// SETTERS
	private void setRecursos(GestorRecursos recursos) {
		Validaciones.esDistintoDeNull(recursos, "recursos");
		this.recursos = recursos;
	}

	private void setAdmin(AdministradorJuego admin) {
		Validaciones.esDistintoDeNull(admin, "admin");
		this.admin = admin;
	}
	

}
