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

public class Ciudad4Minijuego implements Minijuego, ObservadorOrdenamiento {

    private Ciudad ciudad;
    private Jugador jugador;
    
    private GestorRecursos recursos;
    private Boton botonBubble, botonQuick;
    
    private int[] vectorActual;
    private int indiceA = -1;
    private int indiceB = -1;
    private int pivote = -1;
    
    private boolean completado = false;
    private boolean cargado = false;
    
    private String algoritmoSeleccionado = "Seleccion de Algoritmo";

    public Ciudad4Minijuego(Ciudad ciudad, Jugador jugador, GestorRecursos recursos) {
        this.ciudad = ciudad;
        this.jugador = jugador;
        this.recursos = recursos;
        crearBotones();
    }
    
    // ======================= LOGICA JUEGO ==========================
    
    /**
     * "Los algoritmos de ordenamiento implementan una interfaz común AlgoritmoOrdenamiento, 
     * permitiendo seleccionar dinámicamente la estrategia de ordenamiento elegida por el jugador."
     */

    @Override
    public void iniciar() {
        this.completado = false;
        
        // Vector de prueba para validar la conexion
        this.vectorActual = new int[]{34, 12, 5, 89, 56, 21, 7};
    }
    
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

            resultadoPartida();
    	});

        hiloOrdenamiento.start();
    }

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
    
    public void crearBotones() {
    	int anchoBoton = 300;
		int altoBoton = 70;

		int x = (ConfiguracionPantalla.SCREEN_WIDTH - anchoBoton) / 2;
		int y = (ConfiguracionPantalla.SCREEN_HEIGHT - altoBoton) / 2 - 50;
		
		botonBubble = new Boton(recursos.getBotonMenu1(), x, y, anchoBoton, altoBoton);
		botonQuick = new Boton(recursos.getBotonMenu1(), x, y + 150, anchoBoton, altoBoton);
    }
    
    public void mostrarOpciones(Graphics2D g2) {
    	botonBubble.dibujar(g2);
    	botonQuick.dibujar(g2);
    }
    
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
			g2.setColor(new Color(255, 215, 0)); // dorado igual que los botones
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
    }
    
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

    @Override
    public void resultadoPartida() {
    	if(completado) {
            this.completado = true;
            ciudad.setEstado(EstadoCiudad.COMPLETADA);
            jugador.sumarPuntos(ciudad.getPuntosDeExperiencia());
    	}
    }
    
    public void setAlgoritmoSeleccionado(String nombre) {
        this.algoritmoSeleccionado = nombre;
    }
}