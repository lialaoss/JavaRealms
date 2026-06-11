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

public class Ciudad2Minijuego implements Minijuego {

    private Ciudad ciudad;
    private Jugador jugador;

    private enum Fase { INPUT_N, INPUT_FILA, INPUT_COLUMNA, RESOLVIENDO }
    private Fase fase = Fase.INPUT_N;

    private String inputActual = "";
    private String error = "";

    private int dimension = 0;
    private int filaInicial = 0;
    private int columnaInicial = 0;

    private List<List<Reina>> historial;
    private int frameActual = 0;
    private boolean ganado = false;
    private boolean sinSolucion = false;

    public Ciudad2Minijuego(Ciudad ciudad, Jugador jugador) {
        this.ciudad = ciudad;
        this.jugador = jugador;
    }

    @Override
    public void iniciar() {}

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
    }

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

    public void avanzarFrame() {
        if (fase == Fase.RESOLVIENDO && historial != null && frameActual < historial.size() - 1) {
            frameActual++;
            if (frameActual == historial.size() - 1) {
                List<Reina> ultimo = historial.get(frameActual);
                if (ultimo.size() == dimension) {
                    ganado = true;
                    resultadoPartida();
                } else {
                    sinSolucion = true;
                }
            }
        }
    }

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

    @Override
    public void resultadoPartida() {
        if (ganado) { desbloquearVecinos(); }
    }

    @Override
    public void desbloquearVecinos() {
        ciudad.setEstado(EstadoCiudad.COMPLETADA);
    }

    @Override
    public void procesarClick(int mouseX, int mouseY) {}
}
