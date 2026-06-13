package minijuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
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

	/* 
	 * Pre: La ciudad, el jugador y los recursos gráficos no pueden ser nulos.
	 * Post: Inicializa el minijuego guardando los datos y crea los botones para que el jugador elija el algoritmo a usar.
	 */
	public Ciudad3Minijuego(Ciudad ciudad, Jugador jugador, GestorRecursos recursos) {
		this.ciudad = ciudad;
		this.jugador = jugador;
		this.recursos = recursos;
		crearBotones();
	}
	
	// ===================== JUEGO ========================

	/* 
	 * Pre: Ninguna.
	 * Post: Intenta buscar y cargar el archivo de texto "lab2.txt" para armar el mapa del laberinto. Si el archivo no existe o hay un problema, guarda el mensaje de error.
	 */
	@Override
	public void iniciar() {
		try {
			java.net.URL url = getClass().getResource("/laberintos/lab2.txt");
			String ruta = new java.io.File(url.toURI()).getAbsolutePath();
			this.lab = new Laberinto(ruta);
		} catch (Exception e) {
			error = "Error: " + e.getMessage();
			System.out.println("ERROR CIUDAD 3: " + e.getMessage());
		}
	}
	
	/* 
	 * Pre: El laberinto debe estar cargado correctamente.
	 * Post: Ejecuta el algoritmo elegido (BFS o DFS) para buscar la salida y guarda en la lista "frames" el paso a paso de cómo la computadora lo fue resolviendo.
	 */
	private void empezarLaberinto() {
		if (usarBFS) {
			frames = new BFS().buscar(lab);
		} else {
			frames = new DFS().buscar(lab);
		}
	}

	// ======================== RENDER ========================
	
	/* 
	 * Pre: El gestor de recursos debe tener las imágenes de los botones.
	 * Post: Crea y ubica geométricamente los botones en el centro de la pantalla.
	 */
	private void crearBotones() {
		int anchoBoton = 300;
		int altoBoton = 70;

		int x = (ConfiguracionPantalla.SCREEN_WIDTH - anchoBoton) / 2;
		int y = (ConfiguracionPantalla.SCREEN_HEIGHT - altoBoton) / 2 - 50;
		
		bfsBoton = new Boton(recursos.getBotonMenu1(), x, y, anchoBoton, altoBoton);
		dfsBoton = new Boton(recursos.getBotonMenu1(), x, y + 150, anchoBoton, altoBoton);
	}
	
	/* 
	 * Pre: Los botones deben estar creados y el motor gráfico inicializado.
	 * Post: Dibuja los dos botones en la pantalla para que el usuario pueda hacerles click.
	 */
	private void mostrarOpciones(Graphics2D g2) {
		bfsBoton.dibujar(g2);
		dfsBoton.dibujar(g2);
	}

	/* 
	 * Pre: El usuario hace un click con el ratón.
	 * Post: Si el laberinto todavía no empezó a resolverse y se hace click en alguno de los botones, guarda qué algoritmo se eligió y manda a resolver el laberinto.
	 */
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

	/* 
	 * Pre: El motor gráfico de Java debe estar listo.
	 * Post: Si todavía no se eligió algoritmo, dibuja el menú de selección. Si hay error, lo escribe. Si ya se eligió, va dibujando la animación paso a paso del laberinto resolviéndose. Cuando termina, muestra la victoria.
	 */
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

	/* 
	 * Pre: Los "frames" (pasos de resolución) tienen que estar calculados.
	 * Post: Controla el reloj del juego. Cada 100 milisegundos avanza un cuadro en la animación. Si llega al último cuadro, avisa que el nivel está ganado.
	 */
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
	
	/* 
	 * Pre: El frame actual tiene que ser válido.
	 * Post: Lee la cuadrícula de texto del paso actual y dibuja la textura correspondiente (pared, pasto, jugador, etc.) centrando todo el laberinto en el medio de la pantalla.
	 */
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
	
	/* 
	 * Pre: El motor gráfico g2 está inicializado.
	 * Post: Dibuja el botón para volver atrás en la esquina superior derecha y un mensaje de éxito cuando el laberinto se resuelve por completo.
	 */
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
	
	/* 
	 * Pre: Recibe un carácter que representa lo que hay en una casilla del laberinto.
	 * Post: Devuelve la imagen exacta (sprite) que hay que dibujar en pantalla para ese símbolo.
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
	
	/* 
	 * Pre: La animación terminó de correr completa.
	 * Post: Si el jugador vio toda la resolución, cambia la ciudad a COMPLETADA y le da los puntos.
	 */
	@Override
	public void resultadoPartida() {
		if (ganado) {
			ciudad.setEstado(EstadoCiudad.COMPLETADA);
			this.jugador.sumarPuntos(ciudad.getPuntosDeExperiencia());
		}
	}

}
