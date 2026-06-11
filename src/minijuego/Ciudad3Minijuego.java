package minijuego;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import ciudades.Ciudad;
import ciudades.EstadoCiudad;
import entidad.Jugador;
import modelo.ciudad3.BFS;
import modelo.ciudad3.DFS;
import modelo.ciudad3.Laberinto;
import modelo.ciudad3.Snapshot;

public class Ciudad3Minijuego implements Minijuego {

    private static final int CELDA = 20; // píxeles por celda
    private static final String RUTA_LABERINTO = "/laberintos/lab2.txt";

    private Ciudad ciudad;
    private Jugador jugador;

    private List<Snapshot> frames;
    private int frameActual = 0;
    private long ultimoTick = 0;
    private final long MS_POR_FRAME = 100;

    private boolean ganado = false;
    private boolean cargado = false;
    private String error = null;

    // true = BFS, false = DFS — podés cambiar esto o hacerlo seleccionable
    private boolean usarBFS = true;

    public Ciudad3Minijuego(Ciudad ciudad, Jugador jugador) {
        this.ciudad = ciudad;
        this.jugador = jugador;
    }

    @Override
    public void iniciar() {
        try {
            java.net.URL url = getClass().getResource("/laberintos/lab2.txt");
            String ruta = new java.io.File(url.toURI()).getAbsolutePath();
            Laberinto lab = new Laberinto(ruta);
            if (usarBFS) {
                frames = new BFS().buscar(lab);
            } else {
                frames = new DFS().buscar(lab);
            }
            cargado = true;
            frameActual = 0;
        } catch (Exception e) {
            error = "Error: " + e.getMessage();
            System.out.println("ERROR CIUDAD 3: " + e.getMessage());
        }
    }

    @Override
    public void render(Graphics2D g2) {
        if (error != null) {
            g2.setColor(Color.RED);
            g2.drawString(error, 50, 50);
            return;
        }
        if (!cargado || frames == null || frames.isEmpty()) {
            g2.setColor(Color.WHITE);
            g2.drawString("Cargando laberinto...", 50, 50);
            return;
        }

        // Avanzar frame automáticamente
        long ahora = System.currentTimeMillis();
        if (ahora - ultimoTick >= MS_POR_FRAME && frameActual < frames.size() - 1) {
            frameActual++;
            ultimoTick = ahora;
        }

        if (frameActual == frames.size() - 1) {
            ganado = true;
        }

        // Dibujar frame actual
        char[][] estado = frames.get(frameActual).estado;
        for (int fila = 0; fila < estado.length; fila++) {
            for (int col = 0; col < estado[fila].length; col++) {
                char celda = estado[fila][col];
                g2.setColor(colorDeCelda(celda));
                g2.fillRect(col * CELDA + 50, fila * CELDA + 50, CELDA, CELDA);
            }
        }

        // HUD
        g2.setColor(Color.WHITE);
        g2.drawString("Frame: " + (frameActual + 1) + "/" + frames.size(), 50, 30);
        g2.drawString(usarBFS ? "BFS" : "DFS", 200, 30);
        if (ganado) {
            g2.setColor(Color.GREEN);
            g2.drawString("¡Laberinto resuelto! Q para volver", 50, 45);
        }
        g2.setColor(Color.GRAY);
        g2.drawString("Q para volver al mapa", 800, 30);
    }

    private Color colorDeCelda(char c) {
        switch (c) {
            case '#': return Color.DARK_GRAY;
            case '.': return Color.WHITE;
            case 'I': return Color.GREEN;
            case 'F': return Color.RED;
            case '*': return new Color(100, 100, 255); // visitado
            case 'A': return Color.YELLOW;             // actual
            case 'P': return Color.ORANGE;             // camino final
            default:  return Color.BLACK;
        }
    }

    @Override
    public void resultadoPartida() {
        if (ganado) {
            desbloquearVecinos();
        }
    }

    @Override
    public void desbloquearVecinos() {
        ciudad.setEstado(EstadoCiudad.COMPLETADA);
    }
}
