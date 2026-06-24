package minijuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.net.URL;
import java.util.List;

import ciudades.Ciudad;
import ciudades.EstadoCiudad;
import entidad.Jugador;
import ui.Boton;
import ui.ConfiguracionPantalla;
import ui.GestorRecursos;
import modelo.ciudad3.BFS;
import modelo.ciudad3.DFS;
import modelo.ciudad3.Laberinto;
import modelo.ciudad3.Snapshot;
import render.FinMinijuegoPantalla;

public class Ciudad3Minijuego implements Minijuego {

	// CONSTANTES
	private static final int CELDA = 20;
	private final long MS_POR_FRAME = 100;
	
	// ATRIBUTOS
	private Ciudad ciudad;
	private Jugador jugador;

	private List<Snapshot> frames;
	private int frameActual = 0;
	private long ultimoTick = 0;
	
	private boolean ganado = false;
	private boolean cargado = false;
	
	private String error = null;
	
	private Laberinto lab;

    private FinMinijuegoPantalla pantallaFinal = new FinMinijuegoPantalla();
	
	private GestorRecursos recursos;	
	private Boton bfsBoton, dfsBoton;

	private boolean usarBFS = true;

	// CONSTRUCTOR
	public Ciudad3Minijuego(Ciudad ciudad, Jugador jugador, GestorRecursos recursos) {
		this.ciudad = ciudad;
		this.jugador = jugador;
		this.recursos = recursos;
		crearBotones();
	}
	
	// ===================== JUEGO ========================

	@Override
	public void iniciar() {
		try {
			URL url = getClass().getResource("/laberintos/lab2.txt");
			String ruta = new java.io.File(url.toURI()).getAbsolutePath();
			this.lab = new Laberinto(ruta);
		} catch (Exception e) {
			error = "Error: " + e.getMessage();
			System.out.println("ERROR CIUDAD 3: " + e.getMessage());
		}
	}
	
	private void empezarLaberinto() {
		if (usarBFS) {
			frames = new BFS().buscar(lab);
		} else {
			frames = new DFS().buscar(lab);
		}
	}

	// ======================== RENDER ========================
	
	private void crearBotones() {
		int anchoBoton = 300;
		int altoBoton = 70;

		int x = (ConfiguracionPantalla.SCREEN_WIDTH - anchoBoton) / 2;
		int y = (ConfiguracionPantalla.SCREEN_HEIGHT - altoBoton) / 2 - 50;
		
		bfsBoton = new Boton(recursos.getBotonBFS(), x, y, anchoBoton, altoBoton);
		dfsBoton = new Boton(recursos.getBotonDFS(), x, y + 150, anchoBoton, altoBoton);
	}
	
	private void mostrarOpciones(Graphics2D g2) {
		bfsBoton.dibujar(g2);
		dfsBoton.dibujar(g2);
	}

	@Override
	public void procesarClick(int mouseX, int mouseY) {
		if(!cargado) {
		    if (bfsBoton.contiene(mouseX, mouseY)) {
		    	cargado = true;
		    	empezarLaberinto();
			} else if (dfsBoton.contiene(mouseX, mouseY)) {
				usarBFS = false;
				cargado = true;
		    	empezarLaberinto();
			}
	    }
	}

	@Override
	public void render(Graphics2D g2) {
		g2.setColor(new Color(120, 123, 33));
		g2.fillRect(
		    0,
		    0,
		    ConfiguracionPantalla.SCREEN_WIDTH,
		    ConfiguracionPantalla.SCREEN_HEIGHT
		);
		if(!cargado || frames == null || frames.isEmpty()) {
			g2.setFont(new Font("Arial", Font.BOLD, 28));
			g2.setColor(new Color(255, 215, 0));
			FontMetrics fm = g2.getFontMetrics();
			String titulo = "Seleccione un algoritmo";
			int tx = (ConfiguracionPantalla.SCREEN_WIDTH - fm.stringWidth(titulo)) / 2;
			g2.drawString(titulo, tx, 80);
			mostrarOpciones(g2);
			return;
		} 
		if (error != null) {
			g2.setColor(Color.RED);
			g2.drawString(error, 50, 50);
			return;
		}
		actualizarAnimacion();
		dibujarLaberinto(g2);
		renderHUD(g2);
		if(ganado) {
			pantallaFinal.mostrarResultados(g2, ciudad);
		}
	}

	private void actualizarAnimacion() {
		long ahora = System.currentTimeMillis();
		if (ahora - ultimoTick >= MS_POR_FRAME && frameActual < frames.size() - 1) {
			frameActual++;
			ultimoTick = ahora;
		}
		if (frameActual == frames.size() - 1) {
			ganado = true;
		}
	}
	
	private void dibujarLaberinto(Graphics2D g2) {
	    char[][] estado = frames.get(frameActual).estado;

	    int laberintoAncho = estado[0].length * CELDA;
	    int laberintoAlto = estado.length * CELDA;

	    int centroX = (ConfiguracionPantalla.SCREEN_WIDTH - laberintoAncho) / 2;
	    int centroY = (ConfiguracionPantalla.SCREEN_HEIGHT - laberintoAlto) / 2;

	    for (int fila = 0; fila < estado.length; fila++) {
	        for (int col = 0; col < estado[fila].length; col++) {

	            char celda = estado[fila][col];
	            Image img = imagenDeCelda(celda);

	            if (img != null) {
	                g2.drawImage(
	                    img,
	                    col * CELDA + centroX,
	                    fila * CELDA + centroY,
	                    CELDA,
	                    CELDA,
	                    null
	                );
	            }
	        }
	    }
	}
	
	private void renderHUD(Graphics2D g2) {
	    int bx = ConfiguracionPantalla.SCREEN_WIDTH - 220;
	    int by = 10;
	    int bw = 40;
	    int bh = 40;
	    g2.drawImage(recursos.getBotonVolver(), bx, by, bw, bh, null);
	    g2.setColor(Color.WHITE);
	    g2.setFont(new Font("Arial", Font.BOLD, 16));
	    g2.drawString("Volver", bx + bw + 8, by + bh / 2 + 6);

	    if (ganado) {
	        g2.setColor(Color.GREEN);
	        g2.setFont(new Font("Arial", Font.BOLD, 16));
	        g2.drawString("¡Laberinto resuelto!", 50, 50);
	    }
	}
	
	/**
	 * Se que los sprites estan pobres lo dejo para lo ultimo BYE
	 * @param c
	 * @return
	 */
	private Image imagenDeCelda(char c) {
		switch (c) {
		case '#':
			return recursos.getPiedra(); // Pared
		case '.':
			return recursos.getPasto(); // Camino libre
		case 'I':
			return recursos.getPiedra2(); // Inicio
		case 'F':
			return recursos.getCofre(); // Fin
		case '*':
			return recursos.getPasto2(); // Tile visitado
		case 'A':
			return recursos.getJugadorDown()[0]; // Jugador
		case 'P':
			return recursos.getPasto3(); // Resultado del camino final
		default:
			return null;
		}
	}

	// =============== FIN DEL JUEGO =======================
	
	@Override
	public void resultadoPartida() {
		if (ganado) {
			ciudad.setEstado(EstadoCiudad.COMPLETADA);
			this.jugador.sumarPuntos(ciudad.getPuntosDeExperiencia());
		}
	}

}
