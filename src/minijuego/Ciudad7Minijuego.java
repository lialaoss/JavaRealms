package minijuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ciudades.Ciudad;
import ciudades.EstadoCiudad;
import entidad.Jugador;
import modelo.ciudad7.AlgoritmosFlujo;
import modelo.ciudad7.AristaFlujo;
import modelo.ciudad7.GrafoFlujo;
import modelo.ciudad7.SnapshotFlujo;
import render.FinMinijuegoPantalla;
import ui.GestorRecursos;

public class Ciudad7Minijuego implements Minijuego, MinijuegoTexto {

    private Ciudad ciudad;
    private Jugador jugador;

    private GrafoFlujo grafo;
    private String inputActual = "";
    private List<String> aristasCargadas = new ArrayList<>();
    private String fuente = "";
    private String sumidero = "";

    private FinMinijuegoPantalla pantallaFinal = new FinMinijuegoPantalla();

    private enum Fase { CARGA, FUENTE, SUMIDERO, RESULTADO }
    private Fase fase = Fase.CARGA;
    
    private Map<String, int[]> posicionesNodos = new HashMap<>();
    private BufferedImage[] gemas;
    private int framGema = 0;
    private int contadorFrame = 0;
    private static final int FRAME_WIDTH = 18;
    private static final int FRAME_HEIGHT = 30;
    private static final int TOTAL_FRAMES = 10;
    private static final int VELOCIDAD_ANIMACION = 6;
    private int tickParticula = 0;

    private List<SnapshotFlujo> snapshots;
    private List<String> caminoMinimo;
    private int frameActual = 0;
    private String error = "";
    private boolean ganado = false;

    public Ciudad7Minijuego(Ciudad ciudad, Jugador jugador, GestorRecursos recursos) {
        this.ciudad = ciudad;
        this.jugador = jugador;
        this.gemas = recursos.getGemasNodos();
        this.pantallaFinal.setFondoVictoria(recursos.getFondoVictoria());
    }

    @Override
    public void iniciar() {
        grafo = new GrafoFlujo();
    }

    @Override
    public void render(Graphics2D g2) {
    	renderFondoMesa(g2);

        actualizarAnimacionGemas();
        g2.setFont(new Font("Monospaced", Font.BOLD, 16));
        dibujarTextoConFondo(g2, "Ciudad 7 - Red de Energia", 50, 35, Color.YELLOW);

        g2.setFont(new Font("Monospaced", Font.PLAIN, 13));

        if (fase == Fase.RESULTADO) {
            dibujarResultado(g2);
        } else {
            if (!posicionesNodos.isEmpty()) {
                dibujarGrafoBase(g2);
            }
            dibujarInstruccionesFase(g2);
        }
    }
    
    private void renderFondoMesa(Graphics2D g2) {
        int w = 1152;
        int h = 576;

        g2.setColor(new Color(45, 25, 10));
        g2.fillRect(0, 0, w, h);

        g2.setColor(new Color(35, 18, 7));
        for (int x = 0; x < w; x += 40) {
            g2.drawLine(x, 0, x, h);
        }
        for (int y = 0; y < h; y += 40) {
            g2.drawLine(0, y, w, y);
        }

        int margenX = 80;
        int margenY = 60;
        g2.setColor(new Color(20, 60, 30));
        g2.fillRoundRect(margenX, margenY, w - margenX * 2, h - margenY * 2, 30, 30);

        g2.setColor(new Color(180, 140, 40));
        g2.drawRoundRect(margenX, margenY, w - margenX * 2, h - margenY * 2, 30, 30);
        g2.drawRoundRect(margenX + 4, margenY + 4, w - margenX * 2 - 8, h - margenY * 2 - 8, 26, 26);

        int esquina = 20;
        g2.setColor(new Color(220, 180, 60));
        g2.fillOval(margenX - esquina / 2, margenY - esquina / 2, esquina, esquina);
        g2.fillOval(w - margenX - esquina / 2, margenY - esquina / 2, esquina, esquina);
        g2.fillOval(margenX - esquina / 2, h - margenY - esquina / 2, esquina, esquina);
        g2.fillOval(w - margenX - esquina / 2, h - margenY - esquina / 2, esquina, esquina);

        g2.setColor(new Color(15, 45, 20));
        for (int x = margenX + 10; x < w - margenX; x += 30) {
            for (int y = margenY + 10; y < h - margenY; y += 30) {
                g2.drawLine(x, y, x + 15, y + 15);
            }
        }
    }
    
    private void actualizarAnimacionGemas() {
        contadorFrame++;
        tickParticula++;
        if (contadorFrame >= VELOCIDAD_ANIMACION) {
            contadorFrame = 0;
            framGema = (framGema + 1) % TOTAL_FRAMES;
        }
    }
    
    private int getIndiceGema(String nodo) {
        if (!fuente.isEmpty() && nodo.equals(fuente)) { return 1; }
        if (!sumidero.isEmpty() && nodo.equals(sumidero)) { return 5; }
        List<String> nodos = grafo.getNodos();
        int idx = nodos.indexOf(nodo);
        int[] colores = {0, 2, 3, 4, 6, 7, 1, 5};
        return colores[idx % colores.length];
    }

    private void dibujarResultado(Graphics2D g2) {
        if (snapshots == null || snapshots.isEmpty()) {
            g2.setColor(Color.RED);
            g2.drawString("No se encontro flujo entre los nodos dados.", 50, 55);
            return;
        }

        SnapshotFlujo snap = snapshots.get(frameActual);

        dibujarAristas(g2, snap);
        dibujarNodos(g2, snap);

        g2.setFont(new Font("Monospaced", Font.PLAIN, 13));
        dibujarTextoConFondo(g2, "Paso " + (frameActual + 1) + "/" + snapshots.size() + ": " + snap.descripcion, 50, 55, Color.CYAN);
        dibujarTextoConFondo(g2, "Camino minimo: " + (caminoMinimo != null ? caminoMinimo.toString() : "No encontrado"), 50, 72, Color.WHITE);
        dibujarTextoConFondo(g2, "ENTER = siguiente paso | Q para volver", 50, 560, Color.GRAY);

        if (ganado) {
            pantallaFinal.mostrarResultados(g2, ciudad);
        }
    }

    private void dibujarAristas(Graphics2D g2, SnapshotFlujo snap) {
        int radioNodo = 22;

        for (AristaFlujo arista : snap.aristas) {
            int[] posOrigen = posicionesNodos.get(arista.getOrigen());
            int[] posDestino = posicionesNodos.get(arista.getDestino());
            if (posOrigen == null || posDestino == null) { continue; }

            boolean enCamino = snap.caminoActual != null
                && snap.caminoActual.contains(arista.getOrigen())
                && snap.caminoActual.contains(arista.getDestino());

            if (enCamino) {
                dibujarConexionActiva(g2, posOrigen[0], posOrigen[1], posDestino[0], posDestino[1]);
            } else {
                g2.setColor(new Color(80, 80, 80));
                g2.drawLine(posOrigen[0], posOrigen[1], posDestino[0], posDestino[1]);
            }

            int midX = (posOrigen[0] + posDestino[0]) / 2;
            int midY = (posOrigen[1] + posDestino[1]) / 2;
            g2.setFont(new Font("Monospaced", Font.PLAIN, 11));
            dibujarTextoConFondo(g2, arista.getFlujo() + "/" + arista.getCapacidad(), midX, midY, Color.WHITE);

            double dx = posDestino[0] - posOrigen[0];
            double dy = posDestino[1] - posOrigen[1];
            double len = Math.sqrt(dx * dx + dy * dy);
            if (len == 0) { continue; }
            double ux = dx / len;
            double uy = dy / len;
            int ax = (int)(posDestino[0] - ux * radioNodo);
            int ay = (int)(posDestino[1] - uy * radioNodo);
            int px = (int)(-uy * 8);
            int py = (int)(ux * 8);
            int[] flechaX = { ax, ax - (int)(ux * 12) + px, ax - (int)(ux * 12) - px };
            int[] flechaY = { ay, ay - (int)(uy * 12) + py, ay - (int)(uy * 12) - py };
            g2.setColor(enCamino ? new Color(255, 165, 0) : new Color(120, 120, 120));
            g2.fillPolygon(flechaX, flechaY, 3);
        }
    }

    private void dibujarNodos(Graphics2D g2, SnapshotFlujo snap) {
        int radioNodo = 22;

        for (String nodo : grafo.getNodos()) {
            int[] pos = posicionesNodos.get(nodo);
            if (pos == null) { continue; }

            boolean esFuente = nodo.equals(fuente);
            boolean esSumidero = nodo.equals(sumidero);
            boolean enCamino = snap.caminoActual != null && snap.caminoActual.contains(nodo);

            Color colorRelleno;
            if (esFuente) {
                colorRelleno = new Color(0, 180, 80);
            } else if (esSumidero) {
                colorRelleno = new Color(180, 0, 0);
            } else if (enCamino) {
                colorRelleno = new Color(200, 130, 0);
            } else {
                colorRelleno = new Color(40, 80, 160);
            }

            int idxGema = getIndiceGema(nodo);
            if (gemas != null && gemas[idxGema] != null) {
                BufferedImage frame = gemas[idxGema].getSubimage(framGema * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
                int tamanio = radioNodo * 2;
                g2.drawImage(frame, pos[0] - radioNodo, pos[1] - radioNodo, tamanio, tamanio, null);
            } else {
                g2.setColor(colorRelleno);
                g2.fillOval(pos[0] - radioNodo, pos[1] - radioNodo, radioNodo * 2, radioNodo * 2);
            }

            g2.setFont(new Font("Monospaced", Font.BOLD, 13));
            FontMetrics fm = g2.getFontMetrics();
            int tx = pos[0] - fm.stringWidth(nodo) / 2;
            dibujarTextoConFondo(g2, nodo, tx, pos[1] + 5, Color.WHITE);
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
        if (fase != Fase.RESULTADO || snapshots == null) {
            return;
        }
        if (frameActual < snapshots.size() - 1) {
            frameActual++;
        } else {
            ganado = true;
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
                int nodosNuevos = 0;
                if (!grafo.getNodos().contains(partes[0])) { nodosNuevos++; }
                if (!grafo.getNodos().contains(partes[1])) { nodosNuevos++; }
                if (grafo.getCantidadDeNodos() + nodosNuevos > 8) {
                    error = "Maximo 8 nodos permitidos.";
                    return;
                }
                grafo.agregarArista(partes[0], partes[1], cap);
                aristasCargadas.add(partes[0] + " -> " + partes[1] + " cap:" + cap);
                calcularPosicionesCirculares();
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
    
    private void dibujarInstruccionesFase(Graphics2D g2) {
        g2.setFont(new Font("Monospaced", Font.PLAIN, 13));

        if (fase == Fase.CARGA) {
            dibujarTextoConFondo(g2, "Ingresa conexiones (formato: A B 10). ENTER para agregar.", 50, 60, Color.WHITE);
            dibujarTextoConFondo(g2, "Escribe 'FIN' cuando termines.", 50, 78, Color.WHITE);
            dibujarTextoConFondo(g2, "> " + inputActual + "_", 50, 96, Color.WHITE);

            int y = 114;
            for (String a : aristasCargadas) {
                dibujarTextoConFondo(g2, "  + " + a, 50, y, Color.CYAN);
                y += 16;
            }
        } else if (fase == Fase.FUENTE) {
            dibujarTextoConFondo(g2, "Ingresa la central GENERADORA (fuente):", 50, 60, Color.WHITE);
            dibujarTextoConFondo(g2, "> " + inputActual + "_", 50, 78, Color.WHITE);
        } else if (fase == Fase.SUMIDERO) {
            dibujarTextoConFondo(g2, "Ingresa el punto de CONSUMO (sumidero):", 50, 60, Color.WHITE);
            dibujarTextoConFondo(g2, "> " + inputActual + "_", 50, 78, Color.WHITE);
        }
    }
    
    private void dibujarTextoConFondo(Graphics2D g2, String texto, int x, int y, Color colorTexto) {
        FontMetrics fm = g2.getFontMetrics();
        int ancho = fm.stringWidth(texto);
        int alto = fm.getHeight();

        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRoundRect(x - 4, y - alto + 2, ancho + 8, alto + 2, 4, 4);

        g2.setColor(colorTexto);
        g2.drawString(texto, x, y);
    }
    
    private void dibujarGrafoBase(Graphics2D g2) {
        int radioNodo = 22;

        for (AristaFlujo arista : grafo.getTodasLasAristas()) {
            int[] posOrigen = posicionesNodos.get(arista.getOrigen());
            int[] posDestino = posicionesNodos.get(arista.getDestino());
            if (posOrigen == null || posDestino == null) { continue; }

            g2.setColor(new Color(80, 80, 80));
            g2.drawLine(posOrigen[0], posOrigen[1], posDestino[0], posDestino[1]);

            int midX = (posOrigen[0] + posDestino[0]) / 2;
            int midY = (posOrigen[1] + posDestino[1]) / 2;
            g2.setFont(new Font("Monospaced", Font.PLAIN, 11));
            dibujarTextoConFondo(g2, arista.getFlujo() + "/" + arista.getCapacidad(), midX, midY, Color.WHITE);

            double dx = posDestino[0] - posOrigen[0];
            double dy = posDestino[1] - posOrigen[1];
            double len = Math.sqrt(dx * dx + dy * dy);
            if (len == 0) { continue; }
            double ux = dx / len;
            double uy = dy / len;
            int ax = (int)(posDestino[0] - ux * radioNodo);
            int ay = (int)(posDestino[1] - uy * radioNodo);
            int px = (int)(-uy * 8);
            int py = (int)(ux * 8);
            int[] flechaX = { ax, ax - (int)(ux * 12) + px, ax - (int)(ux * 12) - px };
            int[] flechaY = { ay, ay - (int)(uy * 12) + py, ay - (int)(uy * 12) - py };
            g2.setColor(new Color(120, 120, 120));
            g2.fillPolygon(flechaX, flechaY, 3);
        }

        for (String nodo : grafo.getNodos()) {
            int[] pos = posicionesNodos.get(nodo);
            if (pos == null) { continue; }

            int idxGema = getIndiceGema(nodo);
            if (gemas != null && gemas[idxGema] != null) {
                BufferedImage frame = gemas[idxGema].getSubimage(framGema * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
                int tamanio = radioNodo * 2;
                g2.drawImage(frame, pos[0] - radioNodo, pos[1] - radioNodo, tamanio, tamanio, null);
            } else {
                g2.setColor(new Color(40, 80, 160));
                g2.fillOval(pos[0] - radioNodo, pos[1] - radioNodo, radioNodo * 2, radioNodo * 2);
            }

            g2.setFont(new Font("Monospaced", Font.BOLD, 13));
            FontMetrics fm = g2.getFontMetrics();
            int tx = pos[0] - fm.stringWidth(nodo) / 2;
            dibujarTextoConFondo(g2, nodo, tx, pos[1] + 5, Color.WHITE);
        }
    }
    
    private void dibujarConexionActiva(Graphics2D g2, int x1, int y1, int x2, int y2) {
        g2.setColor(new Color(255, 165, 0));
        g2.drawLine(x1, y1, x2, y2);

        double dx = x2 - x1;
        double dy = y2 - y1;
        double len = Math.sqrt(dx * dx + dy * dy);
        if (len == 0) { return; }

        int cantParticulas = 4;
        for (int i = 0; i < cantParticulas; i++) {
            float offset = ((tickParticula * 2 + (i * 25)) % 100) / 100f;
            int px = (int)(x1 + dx * offset);
            int py = (int)(y1 + dy * offset);

            float brillo = (float)(Math.sin(tickParticula * 0.2 + i) * 0.5 + 0.5);
            int alpha = (int)(150 + brillo * 100);
            int radio = (int)(3 + brillo * 3);

            g2.setColor(new Color(255, 220, 80, alpha));
            g2.fillOval(px - radio, py - radio, radio * 2, radio * 2);

            g2.setColor(new Color(255, 255, 200, alpha / 2));
            g2.fillOval(px - radio - 2, py - radio - 2, (radio + 2) * 2, (radio + 2) * 2);
        }
    }

    
    private void calcular() {
        AlgoritmosFlujo alg = new AlgoritmosFlujo();
        caminoMinimo = alg.caminoMinimo(grafo, fuente, sumidero);
        snapshots = alg.fordFulkerson(grafo, fuente, sumidero);
        frameActual = 0;
        calcularPosicionesCirculares();
    }

    private void calcularPosicionesCirculares() {
        List<String> nodos = grafo.getNodos();
        int centroX = 576;
        int centroY = 280;
        int radio = 180;
        double anguloPaso = (2 * Math.PI) / nodos.size();

        for (int i = 0; i < nodos.size(); i++) {
            double angulo = i * anguloPaso - Math.PI / 2;
            int x = (int)(centroX + radio * Math.cos(angulo));
            int y = (int)(centroY + radio * Math.sin(angulo));
            posicionesNodos.put(nodos.get(i), new int[]{x, y});
        }
    }

    @Override
    public void resultadoPartida() {
        if (ganado) { 
            ciudad.setEstado(EstadoCiudad.COMPLETADA);
			jugador.sumarPuntos(ciudad.getPuntosDeExperiencia());
        }
    }

    @Override
    public void procesarClick(int mouseX, int mouseY) {}
}