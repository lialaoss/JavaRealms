package minijuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;

import ciudades.Ciudad;
import ciudades.EstadoCiudad;
import entidad.Jugador;
import modelo.ciudad2.Reina;
import modelo.ciudad2.SolucionadorNReinas;
import render.FinMinijuegoPantalla;

public class Ciudad2Minijuego implements Minijuego {

    private Ciudad ciudad;
    private Jugador jugador;

    private enum Fase { INPUT_N, INPUT_FILA, INPUT_COLUMNA, RESOLVIENDO }
    private Fase fase = Fase.INPUT_N;

    private FinMinijuegoPantalla pantallaFinal = new FinMinijuegoPantalla();

    private String inputActual = "";
    private String error = "";

    private int dimension = 0;
    private int filaInicial = 0;
    private int columnaInicial = 0;

    private List<List<Reina>> historial;
    private int frameActual = 0;
    private boolean ganado = false;
    private boolean sinSolucion = false;

    /* 
     * Pre: La ciudad y el jugador ya deben estar inicializados (no pueden ser nulos).
     * Post: Crea el minijuego guardando la referencia de ambos para poder usar sus datos después.
     */
    public Ciudad2Minijuego(Ciudad ciudad, Jugador jugador) {
        this.ciudad = ciudad;
        this.jugador = jugador;
    }
    
    /* 
     * Pre: Ninguna.
     * Post: Método requerido por la interfaz Minijuego. Queda vacío porque este juego arranca automáticamente pidiendo el primer dato (fase INPUT_N).
     */

    @Override
    public void iniciar() {

    }

    /* 
     * Pre: El motor gráfico de Java (Graphics2D) debe estar listo para dibujar.
     * Post: Dibuja toda la pantalla negra y, dependiendo de en qué fase estemos, muestra los textos pidiendo que el jugador escriba el tamaño del tablero, o dibuja el tablero con las reinas si ya se está resolviendo. Si hay error o victoria, muestra el mensaje correspondiente.
     */
    @Override
    public void render(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 1152, 576);

        g2.setFont(new Font("Monospaced", Font.BOLD, 16));
        g2.setColor(Color.YELLOW);
        g2.drawString("Ciudad 2 - Problema de las N Reinas", 50, 35);

        g2.setFont(new Font("Monospaced", Font.PLAIN, 14));
        g2.setColor(Color.WHITE);

        if (fase == Fase.INPUT_N) {
            g2.drawString("Ingresá el tamaño del tablero (N >= 4):", 50, 70);
            g2.drawString("> " + inputActual + "_", 50, 95);
        } else if (fase == Fase.INPUT_FILA) {
            g2.drawString("Tablero: " + dimension + "x" + dimension, 50, 70);
            g2.drawString("Ingresá la fila de la reina inicial (0 a " + (dimension - 1) + "):", 50, 95);
            g2.drawString("> " + inputActual + "_", 50, 120);
        } else if (fase == Fase.INPUT_COLUMNA) {
            g2.drawString("Tablero: " + dimension + "x" + dimension + " | Fila inicial: " + filaInicial, 50, 70);
            g2.drawString("Ingresá la columna de la reina inicial (0 a " + (dimension - 1) + "):", 50, 95);
            g2.drawString("> " + inputActual + "_", 50, 120);
        } else if (fase == Fase.RESOLVIENDO) {
            dibujarTablero(g2);
        }

        if (!error.isEmpty()) {
            g2.setColor(Color.RED);
            g2.drawString("Error: " + error, 50, 555);
        }

        g2.setColor(Color.GRAY);
        g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
        if (fase == Fase.RESOLVIENDO) {
            g2.drawString("ENTER = siguiente paso | Q para volver", 50, 570);
        }
        
        if(ganado) {
			pantallaFinal.mostrarResultados(g2, ciudad);
		}
    }

    /* 
     * Pre: La fase actual del juego debe ser RESOLVIENDO y el historial de pasos ya debe estar creado.
     * Post: Dibuja en pantalla la cuadrícula del tablero de ajedrez y coloca la letra "Q" roja en los casilleros donde hay una reina en el momento exacto (frame) que estamos viendo.
     */
    private void dibujarTablero(Graphics2D g2) {
        if (historial == null || historial.isEmpty()) { return; }

        List<Reina> estado = historial.get(frameActual);
        int celda = Math.min(40, 500 / dimension);
        int offsetX = 50;
        int offsetY = 50;

        for (int fila = 0; fila < dimension; fila++) {
            for (int col = 0; col < dimension; col++) {
                boolean esBlanca = (fila + col) % 2 == 0;
                g2.setColor(esBlanca ? Color.WHITE : new Color(100, 100, 100));
                g2.fillRect(offsetX + col * celda, offsetY + fila * celda, celda, celda);
            }
        }

        g2.setFont(new Font("Monospaced", Font.BOLD, celda - 4));
        for (Reina r : estado) {
            g2.setColor(Color.RED);
            g2.drawString("Q", offsetX + r.getColumna() * celda + 4, offsetY + r.getFila() * celda + celda - 4);
        }

        g2.setFont(new Font("Monospaced", Font.PLAIN, 13));
        g2.setColor(Color.WHITE);
        g2.drawString("Paso: " + (frameActual + 1) + "/" + historial.size(), 560, 70);
        g2.drawString("Reinas: " + estado.size() + "/" + dimension, 560, 95);

        if (sinSolucion) {
            g2.setColor(Color.RED);
            g2.drawString("Sin solución para esta configuración.", 560, 130);
        } else if (ganado) {
            g2.setColor(Color.GREEN);
            g2.drawString("¡Solución encontrada!", 560, 130);
        }
    }
    
    /* 
     * Pre: El usuario presiona una tecla válida en su teclado mientras le piden ingresar datos.
     * Post: Si toca ENTER, avanza de fase guardando el dato. Si toca BACKSPACE, borra el último número que escribió. Si toca cualquier otra cosa, la suma al texto que se muestra en pantalla.
     */

    public void procesarCaracter(char c) {
        error = "";
        if (c == '\n' || c == '\r') {
            procesarEnter();
        } else if (c == '\b') {
            if (!inputActual.isEmpty()) {
                inputActual = inputActual.substring(0, inputActual.length() - 1);
            }
        } else {
            inputActual += c;
        }
    }
    
    /* 
     * Pre: La fase tiene que ser RESOLVIENDO y el jugador tiene que haber presionado ENTER.
     * Post: Mueve la animación del tablero al siguiente paso. Si llega al final y se lograron poner todas las reinas, marca el juego como ganado. Si no, avisa que esa combinación no tiene solución.
     */
    public void avanzarFrame() {
        if (fase == Fase.RESOLVIENDO && historial != null && frameActual < historial.size() - 1) {
            frameActual++;
            if (frameActual == historial.size() - 1) {
                List<Reina> ultimo = historial.get(frameActual);
                if (ultimo.size() == dimension) {
                    ganado = true;
                } else {
                    sinSolucion = true;
                }
            }
        }
    }

    /* 
     * Pre: El jugador tocó ENTER mientras estaba en la etapa de escribir datos numéricos.
     * Post: Intenta convertir lo que escribió el jugador a un número. Si es válido y está dentro de los límites del tablero, avanza a pedir el siguiente dato (de N -> Fila -> Columna). Si escribe letras o números que no van, tira error.
     */
    private void procesarEnter() {
        String input = inputActual.trim();
        inputActual = "";

        try {
            int valor = Integer.parseInt(input);
            if (fase == Fase.INPUT_N) {
                if (valor < 4) { error = "N debe ser >= 4."; return; }
                dimension = valor;
                fase = Fase.INPUT_FILA;
            } else if (fase == Fase.INPUT_FILA) {
                if (valor < 0 || valor >= dimension) { error = "Fila fuera de rango."; return; }
                filaInicial = valor;
                fase = Fase.INPUT_COLUMNA;
            } else if (fase == Fase.INPUT_COLUMNA) {
                if (valor < 0 || valor >= dimension) { error = "Columna fuera de rango."; return; }
                columnaInicial = valor;
                resolver();
            }
        } catch (NumberFormatException e) {
            error = "Ingresá un número entero válido.";
        }
    }
    
    /* 
     * Pre: El jugador ya ingresó bien el tamaño del tablero (N>=4) y la fila/columna de la primera reina.
     * Post: Llama al solucionador de Backtracking para que intente ubicar todas las reinas. Guarda todos los pasos que hizo la computadora (historial) y pasa a la fase RESOLVIENDO para que se empiecen a dibujar.
     */
    private void resolver() {
        try {
            SolucionadorNReinas solucionador = new SolucionadorNReinas();
            Reina reinaInicial = new Reina(filaInicial, columnaInicial);
            historial = solucionador.resolverTablero(dimension, reinaInicial);
            frameActual = 0;
            fase = Fase.RESOLVIENDO;
        } catch (Exception e) {
            error = e.getMessage();
        }
    }
    
    /* 
     * Pre: La animación de resolución llegó hasta el final.
     * Post: Si el algoritmo encontró una solución válida (ganado == true), cambia el estado de esta ciudad a COMPLETADA y le suma los puntos de experiencia al jugador.
     */
    @Override
    public void resultadoPartida() {
        if (ganado) { 
        	ciudad.setEstado(EstadoCiudad.COMPLETADA);
			jugador.sumarPuntos(ciudad.getPuntosDeExperiencia());
        }
    }

    /* 
     * Pre: El usuario hace un click con el ratón.
     * Post: No hace nada. Este juego se controla solo por teclado. Se deja vacío para cumplir con la interfaz Minijuego.
     */
    @Override
    public void procesarClick(int mouseX, int mouseY) {
    	
    }
}
