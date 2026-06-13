package minijuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import ciudades.Ciudad;
import ciudades.EstadoCiudad;
import entidad.Jugador;
import ui.Boton;
import ui.ConfiguracionPantalla;
import ui.GestorRecursos;
import modelo.ciudad4.BubbleSort;
import modelo.ciudad4.ObservadorOrdenamiento;
import modelo.ciudad4.QuickSort;
import render.FinMinijuegoPantalla;

public class Ciudad4Minijuego implements Minijuego, ObservadorOrdenamiento {

    private Ciudad ciudad;
    private Jugador jugador;

    private FinMinijuegoPantalla pantallaFinal = new FinMinijuegoPantalla();
    
    private GestorRecursos recursos;
    private Boton botonBubble, botonQuick;
    
    private int[] vectorActual;
    private int indiceA = -1;
    private int indiceB = -1;
    private int pivote = -1;
    
    private boolean completado = false;
    private boolean cargado = false;
    
    private String algoritmoSeleccionado = "Seleccion de Algoritmo";

    /* 
     * Pre: La ciudad, el jugador y los recursos gráficos no pueden ser nulos.
     * Post: Inicializa el minijuego de ordenamiento y prepara los botones para elegir el algoritmo.
     */
    public Ciudad4Minijuego(Ciudad ciudad, Jugador jugador, GestorRecursos recursos) {
        this.ciudad = ciudad;
        this.jugador = jugador;
        this.recursos = recursos;
        crearBotones();
    }
    
    // ======================= LOGICA JUEGO ==========================
    
    /*
     * Pre: Ninguna.
     * Post: Reinicia el estado del minijuego (por si se vuelve a jugar) y carga el vector de números desordenados que se va a usar en la prueba.
     */

    @Override
    public void iniciar() {
        this.completado = false;
        
        // Vector de prueba para validar la conexion
        this.vectorActual = new int[]{34, 12, 5, 89, 56, 21, 7};
    }
    
    /* 
     * Pre: El usuario tiene que haber elegido "Bubble Sort" o "Quick Sort" y el vector actual debe estar cargado.
     * Post: Arranca el algoritmo elegido en un proceso paralelo (hilo/Thread) para que vaya ordenando los números y avisando cada vez que hace un cambio sin congelar la pantalla.
     */
    
    public void ejecutarOrdenamiento() {
    	Thread hiloOrdenamiento = new Thread(() -> {

            switch (algoritmoSeleccionado) {

                case "Bubble Sort":
                    BubbleSort bubble = new BubbleSort();
                    bubble.ordenar(vectorActual, Ciudad4Minijuego.this);
                    this.completado = true;
                    break;

                case "Quick Sort":
                    QuickSort quick = new QuickSort();
                    quick.ordenar(vectorActual, Ciudad4Minijuego.this);
                    this.completado = true;
                    break;
                default:
                	break;
            }
    	});

        hiloOrdenamiento.start();
    }
    
    
    /* 
     * Pre: El vector recibido no es nulo y la lógica de ordenamiento hizo un movimiento.
     * Post: Actualiza los datos visuales (cómo quedó el vector, qué posiciones se están comparando y cuál es el pivote) y frena el proceso medio segundo (500ms) para que el jugador llegue a ver la animación en pantalla.
     */

    @Override
    public void notificarCambio(int[] vector, int indiceA, int indiceB, int pivote) {
        this.vectorActual = vector.clone();
        this.indiceA = indiceA;
        this.indiceB = indiceB;
        this.pivote = pivote;

        // Cumplimiento del contrato: generamos una pausa para permitir la animacion
        try {
            Thread.sleep(500); // Pausa de 500 milisegundos para que la interfaz dibuje
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    // =============================== RENDER ===================================
    
    /* 
     * Pre: El gestor de recursos debe tener cargada la imagen de los botones.
     * Post: Crea y posiciona los botones de "Bubble Sort" y "Quick Sort" en el centro de la pantalla.
     */
    public void crearBotones() {
    	int anchoBoton = 300;
		int altoBoton = 70;

		int x = (ConfiguracionPantalla.SCREEN_WIDTH - anchoBoton) / 2;
		int y = (ConfiguracionPantalla.SCREEN_HEIGHT - altoBoton) / 2 - 50;
		
		botonBubble = new Boton(recursos.getBotonMenu1(), x, y, anchoBoton, altoBoton);
		botonQuick = new Boton(recursos.getBotonMenu1(), x, y + 150, anchoBoton, altoBoton);
    }
    
    /* 
     * Pre: Los botones deben estar creados y el motor gráfico inicializado.
     * Post: Dibuja los botones en la pantalla para que el jugador elija.
     */
    public void mostrarOpciones(Graphics2D g2) {
    	botonBubble.dibujar(g2);
    	botonQuick.dibujar(g2);
    }
    
    /* 
     * Pre: El usuario hace un click con el ratón.
     * Post: Si todavía no arrancó la animación, revisa si el jugador clickeó alguno de los botones. Si es así, guarda qué algoritmo eligió y arranca el proceso de ordenamiento.
     */
	@Override
	public void procesarClick(int mouseX, int mouseY) {
		if(!cargado) {
			if(botonBubble.contiene(mouseX, mouseY)) {
		    	cargado = true;
		        setAlgoritmoSeleccionado("Bubble Sort");
		        ejecutarOrdenamiento();
		    }

		    if(botonQuick.contiene(mouseX, mouseY)) {
		    	cargado = true;
		        setAlgoritmoSeleccionado("Quick Sort");
		        ejecutarOrdenamiento();
		    }
		}
	}

	/* 
	 * Pre: El motor gráfico de Java debe estar listo.
     * Post: Dibuja el fondo y, si no se eligió algoritmo, muestra el menú. Si ya se eligió, muestra el título, la animación de los números ordenándose en vivo, y el mensaje final si ya terminó.
     */
    @Override
    public void render(Graphics2D g2) {
		g2.setColor(new Color(160, 120, 80));
		g2.fillRect(
		    0,
		    0,
		    ConfiguracionPantalla.SCREEN_WIDTH,
		    ConfiguracionPantalla.SCREEN_HEIGHT
		);
		if(!cargado) {
			g2.setFont(new Font("Arial", Font.BOLD, 28));
			g2.setColor(new Color(255, 215, 0));
			FontMetrics fm = g2.getFontMetrics();
			String titulo = "Seleccione un algoritmo de ordenamiento";
			int tx = (ConfiguracionPantalla.SCREEN_WIDTH - fm.stringWidth(titulo)) / 2;
			g2.drawString(titulo, tx, 80);
			mostrarOpciones(g2);
			return;
		}
    	
        g2.setColor(Color.WHITE);
        g2.drawString("Ciudad 4 - Modulo de Ordenamiento", 50, 50);
        g2.drawString("Algoritmo en ejecucion: " + algoritmoSeleccionado, 50, 80);
        
        renderOrdenamiento(g2);
        renderEstado(g2);
        if(completado) {
        	pantallaFinal.mostrarResultados(g2, ciudad);
        }
    }
    
    /* 
     * Pre: El vector numérico tiene que estar cargado.
     * Post: Dibuja una caja por cada número del vector. Pinta de verde el número que funciona de pivote y de rojo los dos números que se están comparando o cambiando de lugar.
     */
    public void renderOrdenamiento(Graphics2D g2) {
        if (vectorActual != null) {
            for (int i = 0; i < vectorActual.length; i++) {
                
                if (i == pivote && pivote != -1) {
                    g2.setColor(Color.GREEN); 
                } else if (i == indiceA || i == indiceB) {
                    g2.setColor(Color.RED); 
                } else {
                    g2.setColor(Color.WHITE); 
                }
                
                g2.drawRect(50 + (i * 60), 120, 50, 50);
                g2.drawString(String.valueOf(vectorActual[i]), 70 + (i * 60), 150);
            }
        }
    }
    
    /* 
     * Pre: El motor gráfico debe estar listo.
     * Post: Escribe en pantalla si la computadora sigue haciendo los cálculos o si ya terminó de ordenar el vector.
     */
    public void renderEstado(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        if (completado) {
            g2.setColor(Color.CYAN);
            g2.drawString("Estado: Vector ordenado. Ciudad superada.", 50, 220);
        } else {
            g2.setColor(Color.YELLOW);
            g2.drawString("Procesando iteraciones del algoritmo...", 50, 220);
        }
    }
    
    // ========================= FIN DEL JUEGO ============================

    /* 
     * Pre: La animación de ordenamiento llegó a su fin.
     * Post: Si la variable completado es true, le suma los puntos de experiencia al jugador y marca la ciudad como completada en el mapa general.
     */
    @Override
    public void resultadoPartida() {
    	if(completado) {
            ciudad.setEstado(EstadoCiudad.COMPLETADA);
            jugador.sumarPuntos(ciudad.getPuntosDeExperiencia());
    	}
    }
    
    /* 
     * Pre: El nombre del algoritmo ingresado debe ser válido ("Bubble Sort" o "Quick Sort").
     * Post: Guarda en una variable qué algoritmo eligió el jugador para mostrarlo en pantalla y usarlo en la ejecución.
     */
    public void setAlgoritmoSeleccionado(String nombre) {
        this.algoritmoSeleccionado = nombre;
    }
}