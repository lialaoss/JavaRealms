package minijuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import ciudades.Ciudad;
import ciudades.EstadoCiudad;
import entidad.Jugador;
import modelo.ciudad7.AlgoritmosFlujo;
import modelo.ciudad7.AristaFlujo;
import modelo.ciudad7.GrafoFlujo;
import modelo.ciudad7.SnapshotFlujo;

public class Ciudad7Minijuego implements Minijuego {

    private Ciudad ciudad;
    private Jugador jugador;

    private GrafoFlujo grafo;
    private String inputActual = "";
    private List<String> aristasCargadas = new ArrayList<>();
    private String fuente = "";
    private String sumidero = "";

    private enum Fase { CARGA, FUENTE, SUMIDERO, RESULTADO }
    private Fase fase = Fase.CARGA;

    private List<SnapshotFlujo> snapshots;
    private List<String> caminoMinimo;
    private int frameActual = 0;
    private String error = "";
    private boolean ganado = false;

    public Ciudad7Minijuego(Ciudad ciudad, Jugador jugador) {
        this.ciudad = ciudad;
        this.jugador = jugador;
    }

    @Override
    public void iniciar() {
        grafo = new GrafoFlujo();
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 1152, 576);

        g2.setFont(new Font("Monospaced", Font.BOLD, 16));
        g2.setColor(Color.YELLOW);
        g2.drawString("Ciudad 7 - Red de Energia", 50, 35);

        g2.setFont(new Font("Monospaced", Font.PLAIN, 13));

        if (fase == Fase.CARGA) {
            g2.setColor(Color.WHITE);
            g2.drawString("Ingresa conexiones (formato: A B 10). ENTER para agregar.", 50, 60);
            g2.drawString("Escribe 'FIN' cuando termines.", 50, 80);
            g2.drawString("> " + inputActual + "_", 50, 105);

            g2.setColor(Color.CYAN);
            int y = 130;
            for (String a : aristasCargadas) {
                g2.drawString("  + " + a, 50, y);
                y += 18;
            }
        } else if (fase == Fase.FUENTE) {
            g2.setColor(Color.WHITE);
            g2.drawString("Ingresa la central GENERADORA (fuente):", 50, 60);
            g2.drawString("> " + inputActual + "_", 50, 85);
        } else if (fase == Fase.SUMIDERO) {
            g2.setColor(Color.WHITE);
            g2.drawString("Ingresa el punto de CONSUMO (sumidero):", 50, 60);
            g2.drawString("> " + inputActual + "_", 50, 85);
        } else if (fase == Fase.RESULTADO) {
            dibujarResultado(g2);
        }

        if (!error.isEmpty()) {
            g2.setColor(Color.RED);
            g2.drawString("Error: " + error, 50, 555);
        }
    }

    private void dibujarResultado(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.drawString("Camino minimo: " + (caminoMinimo != null ? caminoMinimo.toString() : "No encontrado"), 50, 55);

        if (snapshots != null && !snapshots.isEmpty()) {
            SnapshotFlujo snap = snapshots.get(frameActual);
            g2.setColor(Color.CYAN);
            g2.drawString("Paso " + (frameActual + 1) + "/" + snapshots.size() + ": " + snap.descripcion, 50, 80);

            int y = 110;
            for (AristaFlujo arista : snap.aristas) {
                boolean enCamino = snap.caminoActual != null &&
                    snap.caminoActual.contains(arista.getOrigen()) &&
                    snap.caminoActual.contains(arista.getDestino());
                g2.setColor(enCamino ? Color.ORANGE : Color.WHITE);
                g2.drawString(arista.toString(), 50, y);
                y += 18;
            }
        }

        g2.setColor(Color.GRAY);
        g2.drawString("ENTER = siguiente paso | Q para volver", 50, 555);

        if (ganado) {
            g2.setColor(Color.GREEN);
            g2.drawString("¡Ciudad completada!", 600, 555);
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
        if (fase == Fase.RESULTADO && snapshots != null && frameActual < snapshots.size() - 1) {
            frameActual++;
            if (frameActual == snapshots.size() - 1) {
                ganado = true;
                resultadoPartida();
            }
        }
    }

    private void procesarEnter() {
        String input = inputActual.trim().toUpperCase();
        inputActual = "";

        if (fase == Fase.CARGA) {
            if (input.equals("FIN")) {
                if (aristasCargadas.isEmpty()) {
                    error = "Ingresa al menos una arista.";
                    return;
                }
                fase = Fase.FUENTE;
                return;
            }
            String[] partes = input.split("\\s+");
            if (partes.length != 3) {
                error = "Formato invalido. Usa: A B 10";
                return;
            }
            try {
                int cap = Integer.parseInt(partes[2]);
                grafo.agregarArista(partes[0], partes[1], cap);
                aristasCargadas.add(partes[0] + " -> " + partes[1] + " cap:" + cap);
            } catch (NumberFormatException e) {
                error = "La capacidad debe ser un numero entero.";
            }
        } else if (fase == Fase.FUENTE) {
            if (!grafo.getNodos().contains(input)) {
                error = "Nodo '" + input + "' no existe en el grafo.";
                return;
            }
            fuente = input;
            fase = Fase.SUMIDERO;
        } else if (fase == Fase.SUMIDERO) {
            if (!grafo.getNodos().contains(input)) {
                error = "Nodo '" + input + "' no existe en el grafo.";
                return;
            }
            sumidero = input;
            calcular();
            fase = Fase.RESULTADO;
        }
    }

    private void calcular() {
        AlgoritmosFlujo alg = new AlgoritmosFlujo();
        caminoMinimo = alg.caminoMinimo(grafo, fuente, sumidero);
        snapshots = alg.fordFulkerson(grafo, fuente, sumidero);
        frameActual = 0;
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