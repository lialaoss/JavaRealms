package minijuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

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
import utiles.ValidadorVector;

public class Ciudad4Minijuego implements Minijuego, ObservadorOrdenamiento {

    private static final int FASE_INPUT = 0;
    private static final int FASE_SELECCION = 1;
    private static final int FASE_EJECUCION = 2;
    

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
    private int fase = FASE_INPUT;
    private int tickPulso = 0;
    private BufferedImage[] libros;

    private String inputUsuario = "";
    private String errorInput = "";
    private String algoritmoSeleccionado = "";

    public Ciudad4Minijuego(Ciudad ciudad, Jugador jugador, GestorRecursos recursos) {
        this.ciudad = ciudad;
        this.jugador = jugador;
        this.recursos = recursos;
        this.libros = recursos.getLibrosOrdenamiento();
        crearBotones();
    }

    // ======================= LOGICA JUEGO ==========================

    /**
     * Pre: ciudad, jugador y recursos no son nulos.
     * Post: reinicia el estado del minijuego a la fase de input.
     */
    @Override
    public void iniciar() {
        this.completado = false;
        this.fase = FASE_INPUT;
        this.inputUsuario = "";
        this.errorInput = "";
        this.algoritmoSeleccionado = "";
        this.vectorActual = null;
        this.indiceA = -1;
        this.indiceB = -1;
        this.pivote = -1;
    }

    /**
     * Pre: ninguna.
     * Post: acumula el caracter en el input, procesa Enter y Backspace.
     */
    public void procesarCaracter(char c) {
        if (fase != FASE_INPUT) {
            return;
        }

        if (c == '\n' || c == '\r') {
            intentarParsearVector();
        } else if (c == '\b') {
            if (!inputUsuario.isEmpty()) {
                inputUsuario = inputUsuario.substring(0, inputUsuario.length() - 1);
            }
        } else {
            inputUsuario += c;
        }
    }

    /**
     * Pre: inputUsuario no es nulo.
     * Post: intenta parsear el input como vector. Si es valido avanza a FASE_SELECCION, sino muestra el error.
     */
    private void intentarParsearVector() {
        try {
            vectorActual = ValidadorVector.parsear(inputUsuario);
            errorInput = "";
            fase = FASE_SELECCION;
        } catch (IllegalArgumentException e) {
            errorInput = e.getMessage();
        }
    }

    /**
     * Pre: algoritmoSeleccionado es "Bubble Sort" o "Quick Sort". vectorActual no es nulo.
     * Post: corre el algoritmo seleccionado en un hilo separado notificando cada cambio al observador.
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

    /**
     * Pre: vector no es nulo.
     * Post: actualiza el estado visual del vector y genera una pausa de 500ms para la animacion.
     */
    @Override
    public void notificarCambio(int[] vector, int indiceA, int indiceB, int pivote) {
        this.vectorActual = vector.clone();
        this.indiceA = indiceA;
        this.indiceB = indiceB;
        this.pivote = pivote;

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // =============================== BOTONES ===================================

    public void crearBotones() {
        int anchoBoton = 300;
        int altoBoton = 70;
        int x = (ConfiguracionPantalla.SCREEN_WIDTH - anchoBoton) / 2;
        int y = (ConfiguracionPantalla.SCREEN_HEIGHT - altoBoton) / 2 - 50;

        botonBubble = new Boton(recursos.getBotonBubble(), x, y, anchoBoton, altoBoton);
        botonQuick = new Boton(recursos.getBotonQuick(), x, y + 150, anchoBoton, altoBoton);
    }

    @Override
    public void procesarClick(int mouseX, int mouseY) {
        if (fase != FASE_SELECCION) {
            return;
        }

        if (botonBubble.contiene(mouseX, mouseY)) {
            fase = FASE_EJECUCION;
            setAlgoritmoSeleccionado("Bubble Sort");
            ejecutarOrdenamiento();
        }

        if (botonQuick.contiene(mouseX, mouseY)) {
            fase = FASE_EJECUCION;
            setAlgoritmoSeleccionado("Quick Sort");
            ejecutarOrdenamiento();
        }
    }

    // =============================== RENDER ===================================

    @Override
    public void render(Graphics2D g2) {
        renderFondoBiblioteca(g2);

        if (fase == FASE_INPUT) {
            renderFaseInput(g2);
            return;
        }

        if (fase == FASE_SELECCION) {
            renderFaseSeleccion(g2);
            return;
        }

        renderFaseEjecucion(g2);
    }

    private void renderFaseInput(Graphics2D g2) {
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.setColor(new Color(255, 215, 0));
        FontMetrics fm = g2.getFontMetrics();
        String titulo = "Ingresa los numeros a ordenar separados por coma";
        g2.drawString(titulo, (ConfiguracionPantalla.SCREEN_WIDTH - fm.stringWidth(titulo)) / 2, 80);

        g2.setFont(new Font("Monospaced", Font.PLAIN, 18));
        g2.setColor(Color.WHITE);
        g2.drawString("Ejemplo: 34,12,5,89,56", 50, 130);
        g2.drawString("Min: 3 elementos  |  Max: 10 elementos", 50, 155);

        g2.setFont(new Font("Monospaced", Font.BOLD, 20));
        g2.setColor(Color.WHITE);
        g2.drawString("Entrada: " + inputUsuario + "_", 50, 210);

        if (!errorInput.isEmpty()) {
            g2.setColor(Color.RED);
            g2.setFont(new Font("Monospaced", Font.PLAIN, 16));
            g2.drawString("Error: " + errorInput, 50, 250);
        }

        g2.setColor(Color.GRAY);
        g2.setFont(new Font("Monospaced", Font.PLAIN, 13));
        g2.drawString("ENTER para confirmar | BACKSPACE para borrar | Q para volver", 50, ConfiguracionPantalla.SCREEN_HEIGHT - 20);
    }

    private void renderFaseSeleccion(Graphics2D g2) {
        g2.setFont(new Font("Arial", Font.BOLD, 28));
        g2.setColor(new Color(255, 215, 0));
        FontMetrics fm = g2.getFontMetrics();
        String titulo = "Seleccione un algoritmo de ordenamiento";
        g2.drawString(titulo, (ConfiguracionPantalla.SCREEN_WIDTH - fm.stringWidth(titulo)) / 2, 80);

        botonBubble.dibujar(g2);
        botonQuick.dibujar(g2);
    }
    
    private void renderFondoBiblioteca(Graphics2D g2) {
        int screenW = ConfiguracionPantalla.SCREEN_WIDTH;
        int screenH = ConfiguracionPantalla.SCREEN_HEIGHT;

        g2.setColor(new Color(45, 28, 12));
        g2.fillRect(0, 0, screenW, screenH);

        g2.setColor(new Color(80, 50, 20));
        for (int y = 100; y < screenH; y += 120) {
            g2.fillRect(0, y, screenW, 12);
        }

        g2.setColor(new Color(60, 35, 10));
        g2.fillRect(0, screenH - 40, screenW, 40);

        g2.setColor(new Color(100, 65, 25));
        g2.fillRect(0, screenH - 44, screenW, 6);
    }

    private void renderFaseEjecucion(Graphics2D g2) {
        tickPulso++;
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 18));
        g2.drawString("Ciudad 4 - Modulo de Ordenamiento", 50, 50);
        g2.drawString("Algoritmo en ejecucion: " + algoritmoSeleccionado, 50, 80);

        renderOrdenamiento(g2);

        if (completado) {
            pantallaFinal.mostrarResultados(g2, ciudad);
        }
    }

    public void renderOrdenamiento(Graphics2D g2) {
        if (vectorActual == null) {
            return;
        }

        int tamanioLibro = 64;
        int separacion = 80;
        int totalAncho = vectorActual.length * separacion;
        int xInicio = (ConfiguracionPantalla.SCREEN_WIDTH - totalAncho) / 2;
        int yBase = 300;

        float pulso = (float)(Math.sin(tickPulso * 0.15) * 0.5 + 0.5);
        int alphaPulso = (int)(80 + pulso * 120);
        int flotacion = (int)(pulso * 12);

        for (int i = 0; i < vectorActual.length; i++) {
            int x = xInicio + (i * separacion);
            int yLibro = yBase - tamanioLibro;

            boolean esComparando = (i == indiceA || i == indiceB);
            boolean esPivote = (i == pivote && pivote != -1);

            if (esComparando) {
                yLibro -= flotacion;
            }

            if (esPivote) {
                for (int radio = 20; radio >= 4; radio -= 4) {
                    int alphaAura = (int)(alphaPulso * (1f - radio / 24f));
                    g2.setColor(new Color(255, 200, 0, alphaAura));
                    g2.fillOval(x + tamanioLibro / 2 - radio, yLibro + tamanioLibro / 2 - radio, radio * 2, radio * 2);
                }
            }

            if (libros != null && libros[i % libros.length] != null) {
                g2.drawImage(libros[i % libros.length], x, yLibro, tamanioLibro, tamanioLibro, null);
            } else {
                g2.setColor(Color.WHITE);
                g2.fillRect(x, yLibro, tamanioLibro, tamanioLibro);
            }

            if (esComparando) {
                g2.setColor(new Color(180, 100, 255, alphaPulso));
                g2.fillRect(x, yLibro, tamanioLibro, tamanioLibro);

                g2.setColor(new Color(220, 180, 255, 200));
                g2.drawRect(x, yLibro, tamanioLibro, tamanioLibro);
                g2.drawRect(x + 1, yLibro + 1, tamanioLibro - 2, tamanioLibro - 2);
            }

            if (esPivote) {
                g2.setColor(new Color(255, 215, 0, alphaPulso));
                g2.fillRect(x, yLibro, tamanioLibro, tamanioLibro);

                g2.setColor(new Color(255, 255, 100, 220));
                g2.drawRect(x, yLibro, tamanioLibro, tamanioLibro);
                g2.drawRect(x + 1, yLibro + 1, tamanioLibro - 2, tamanioLibro - 2);
            }

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.drawString(String.valueOf(vectorActual[i]), x + 20, yBase + 20);
        }
    }


    // ========================= FIN DEL JUEGO ============================

    /**
     * Pre: ninguna.
     * Post: si completado es true desbloquea vecinos y suma puntos al jugador.
     */
    @Override
    public void resultadoPartida() {
        if (completado) {
            ciudad.setEstado(EstadoCiudad.COMPLETADA);
            jugador.sumarPuntos(ciudad.getPuntosDeExperiencia());
        }
    }

    /**
     * Pre: nombre no es nulo.
     * Post: establece el algoritmo a ejecutar en el siguiente ordenamiento.
     */
    public void setAlgoritmoSeleccionado(String nombre) {
        this.algoritmoSeleccionado = nombre;
    }
}