package minijuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import ciudades.Ciudad;
import ciudades.EstadoCiudad;
import entidad.Jugador;
import ui.ConfiguracionPantalla;
import ui.GestorRecursos;
import render.FinMinijuegoPantalla;

import modelo.ciudad6.TablaHash;
import modelo.ciudad6.CeldaHash;
import modelo.ciudad6.ResultadoBusqueda;

public class Ciudad6Minijuego implements Minijuego {

    // ATRIBUTOS DE CONTROL
    private Ciudad ciudad;
    private Jugador jugador;
    private FinMinijuegoPantalla pantallaFinal = new FinMinijuegoPantalla();
    private boolean ganado = false;
    private boolean mostrandoCartelVictoria = false; // Flag para retener el cartel en pantalla

    // LÓGICA INTERNA DEL DESAFÍO
    private TablaHash tablaHash;
    private List<String> bitacoraVisual = new ArrayList<>();
    private int colisionesLogradas = 0;
    private final int COLISIONES_REQUERIDAS = 3;
    private boolean busquedaColisionadaExitosa = false;
    private String mensajeEstado = "Seleccioná tus datos arriba y ejecutá la acción.";

    // VARIABLES DE INTERFAZ GRÁFICA PROPIA (Selectores en pantalla)
    private char claveSeleccionada = 'A';
    private int valorSeleccionado = 10;
    private char claveBuscarSeleccionada = 'A';

    // =========================================================================
    //   NUEVA ARQUITECTURA DE COORDENADAS (ESPACIADO ARMÓNICO Y SIMÉTRICO)
    // =========================================================================
    private final int filaY = 190;        // Fila base para los controles
    private final int btnW = 30;         // Ancho optimizado para los selectores [<] y [>]
    private final int btnH = 30;         // Alto optimizado para los selectores [<] y [>]

    // Módulo de Inserción (Sección Izquierda / Centro)
    private final int cDecX = 85,   cIncX = 145;  // Selector de Clave
    private final int vDecX = 245,  vIncX = 325;  // Selector de Valor
    private final int btnInsertarX = 375, btnInsertarY = 188, btnAccionW = 105, btnAccionH = 34;
    
    // Módulo de Búsqueda (Sección Derecha)
    private final int cbDecX = 595, cbIncX = 655; // Selector de Búsqueda
    private final int btnBuscarX = 695, btnBuscarY = 188; 

    // Bloques inferiores (Desplazados a Y=275 para dar un respiro visual)
    private final int bloquesY = 275; 

    public Ciudad6Minijuego(Ciudad ciudad, Jugador jugador, GestorRecursos recursos) {
        this.ciudad = ciudad;
        this.jugador = jugador;
    }

    @Override
    public void iniciar() {
        this.tablaHash = new TablaHash();
        this.bitacoraVisual.clear();
        this.bitacoraVisual.add("SISTEMA: Oráculo Hash listo. Ajustá las variables con [<] [>] e insertá.");
        this.colisionesLogradas = 0;
        this.busquedaColisionadaExitosa = false;
        this.ganado = false;
        this.mostrandoCartelVictoria = false;
        this.claveSeleccionada = 'A';
        this.valorSeleccionado = 10;
        this.claveBuscarSeleccionada = 'A';
        this.mensajeEstado = "Panel de control listo.";
    }

    // ===================== CONTROL DE CLICS (INTERACTIVIDAD) ========================

    @Override
    public void procesarClick(int mouseX, int mouseY) {
        // Si el cartel de victoria ya está activo, el siguiente clic en cualquier lado cierra el juego
        if (mostrandoCartelVictoria) {
            resultadoPartida();
            return;
        }

        if (ganado) return;

        // ---- CONTROLES DE INSERCIÓN ----
        // Decrementar Clave
        if (clickEnRango(mouseX, mouseY, cDecX, filaY, btnW, btnH) && claveSeleccionada > 'A') {
            claveSeleccionada--;
        }
        // Incrementar Clave
        if (clickEnRango(mouseX, mouseY, cIncX, filaY, btnW, btnH) && claveSeleccionada < 'Z') {
            claveSeleccionada++;
        }
        // Decrementar Valor (-5)
        if (clickEnRango(mouseX, mouseY, vDecX, filaY, btnW, btnH) && valorSeleccionado > 0) {
            valorSeleccionado -= 5;
        }
        // Incrementar Valor (+5)
        if (clickEnRango(mouseX, mouseY, vIncX, filaY, btnW, btnH) && valorSeleccionado < 999) {
            valorSeleccionado += 5;
        }
        // Botón Insertar
        if (clickEnRango(mouseX, mouseY, btnInsertarX, btnInsertarY, btnAccionW, btnAccionH)) {
            ejecutarInsercionNativa();
        }

        // ---- CONTROLES DE BÚSQUEDA ----
        // Decrementar Clave Buscar
        if (clickEnRango(mouseX, mouseY, cbDecX, filaY, btnW, btnH) && claveBuscarSeleccionada > 'A') {
            claveBuscarSeleccionada--;
        }
        // Incrementar Clave Buscar
        if (clickEnRango(mouseX, mouseY, cbIncX, filaY, btnW, btnH) && claveBuscarSeleccionada < 'Z') {
            claveBuscarSeleccionada++;
        }
        // Botón Buscar
        if (clickEnRango(mouseX, mouseY, btnBuscarX, btnBuscarY, btnAccionW, btnAccionH)) {
            ejecutarBusquedaNativa();
        }
    }

    private boolean clickEnRango(int mx, int my, int x, int y, int w, int h) {
        return (mx >= x && mx <= x + w && my >= y && my <= y + h);
    }

    private void ejecutarInsercionNativa() {
        try {
            List<String> pasos = tablaHash.insertar(String.valueOf(claveSeleccionada), valorSeleccionado);
            procesarNuevosLogs(pasos);
            this.mensajeEstado = "Insertado '" + claveSeleccionada + "' con valor " + valorSeleccionado;
            chequearVictoria();
        } catch (IllegalStateException e) {
            this.bitacoraVisual.add("[ALERTA] ¡La memoria de la Tabla Hash está llena!");
        }
    }

    private void ejecutarBusquedaNativa() {
        ResultadoBusqueda resultado = tablaHash.buscar(String.valueOf(claveBuscarSeleccionada));
        procesarNuevosLogs(resultado.getPasosExplicativos());

        if (resultado.getValorEncontrado() != -1) {
            this.mensajeEstado = "¡ÉXITO! Encontrado '" + claveBuscarSeleccionada + "' -> Valor: " + resultado.getValorEncontrado();
            
            for (String log : resultado.getPasosExplicativos()) {
                if (log.contains("Linear Probing") || log.contains("ocupada")) {
                    this.busquedaColisionadaExitosa = true;
                    this.bitacoraVisual.add("[LOGRO] ¡Detectada clave reubicada por colisión!");
                    break;
                }
            }
        } else {
            this.mensajeEstado = "La clave '" + claveBuscarSeleccionada + "' no existe.";
        }
        chequearVictoria();
    }

    private void procesarNuevosLogs(List<String> nuevosPasos) {
        for (String paso : nuevosPasos) {
            this.bitacoraVisual.add(paso);
            if (paso.toUpperCase().contains("COLISION")) {
                this.colisionesLogradas++;
            }
        }
        if (bitacoraVisual.size() > 18) {
            bitacoraVisual.subList(0, bitacoraVisual.size() - 18).clear();
        }
    }

    private void chequearVictoria() {
        // Se activa el cartel en lugar de disparar el cierre inmediato de la pantalla
        if (this.colisionesLogradas >= COLISIONES_REQUERIDAS && this.busquedaColisionadaExitosa) {
            this.ganado = true;
            this.mostrandoCartelVictoria = true; 
        }
    }

    // ======================== RENDER NATIVO (DIBUJO DE INTERFAZ) ========================

    @Override
    public void render(Graphics2D g2) {
        // Fondo Oscuro
        g2.setColor(new Color(20, 25, 35));
        g2.fillRect(0, 0, ConfiguracionPantalla.SCREEN_WIDTH, ConfiguracionPantalla.SCREEN_HEIGHT);

        // 1. HUD Superior (Objetivos)
        g2.setColor(new Color(40, 45, 60));
        g2.fillRect(40, 15, ConfiguracionPantalla.SCREEN_WIDTH - 80, 75);
        g2.setColor(Color.CYAN);
        g2.drawRect(40, 15, ConfiguracionPantalla.SCREEN_WIDTH - 80, 75);

        g2.setFont(new Font("SansSerif", Font.BOLD, 13));
        g2.setColor(Color.WHITE);
        g2.drawString("DESAFÍO: Forzar 3 colisiones con Linear Probing y buscar una clave desplazada.", 55, 38);
        g2.drawString("• Colisiones: " + colisionesLogradas + "/" + COLISIONES_REQUERIDAS + 
                      "    • Elemento colisionado hallado: " + (busquedaColisionadaExitosa ? "SÍ" : "NO"), 55, 68);

        // 2. Panel De Control de Datos (Fila interactiva estilizada)
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        g2.setColor(Color.LIGHT_GRAY);
        
        // --- SECTOR INSERCIÓN: CLAVE ---
        g2.drawString("Clave:", 42, filaY + 19);
        dibujarBotonSelector(g2, "<", cDecX, filaY);
        g2.setColor(Color.YELLOW);
        g2.setFont(new Font("Consolas", Font.BOLD, 15));
        g2.drawString(String.valueOf(claveSeleccionada), 123, filaY + 20);
        dibujarBotonSelector(g2, ">", cIncX, filaY);

        // --- SECTOR INSERCIÓN: VALOR ---
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString("Val:", 210, filaY + 19);
        dibujarBotonSelector(g2, "<", vDecX, filaY);
        g2.setColor(Color.YELLOW);
        g2.setFont(new Font("Consolas", Font.BOLD, 15));
        g2.drawString(String.format("%03d", valorSeleccionado), 282, filaY + 20);
        dibujarBotonSelector(g2, ">", vIncX, filaY);

        // --- BOTÓN ACCIÓN INSERTAR ---
        dibujarBotonAccion(g2, "INSERTAR", btnInsertarX, btnInsertarY, new Color(46, 204, 113));

        // --- SECTOR BÚSQUEDA ---
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString("Buscar:", 540, filaY + 19);
        dibujarBotonSelector(g2, "<", cbDecX, filaY);
        g2.setColor(Color.ORANGE);
        g2.setFont(new Font("Consolas", Font.BOLD, 15));
        g2.drawString(String.valueOf(claveBuscarSeleccionada), 633, filaY + 20);
        dibujarBotonSelector(g2, ">", cbIncX, filaY);

        // --- BOTÓN ACCIÓN BUSCAR ---
        dibujarBotonAccion(g2, "BUSCAR", btnBuscarX, btnBuscarY, new Color(52, 152, 219));

        // Estado dinámico en la zona inferior de la fila
        g2.setFont(new Font("SansSerif", Font.ITALIC, 12));
        g2.setColor(Color.GREEN);
        g2.drawString("Estado: " + mensajeEstado, 42, filaY + 54);

        // 3. Dibujar Estructuras principales
        dibujarTablaVisual(g2);
        dibujarConsola(g2);
        
        // --- CARTEL INTERMEDIO DE VICTORIA (ESTILO CIUDAD 1) ---
        if (mostrandoCartelVictoria) {
            // Fondo semi-transparente que cubre la totalidad de la pantalla
            g2.setColor(new Color(0, 0, 0, 180));
            g2.fillRect(0, 0, ConfiguracionPantalla.SCREEN_WIDTH, ConfiguracionPantalla.SCREEN_HEIGHT);

            // Configuramos la tipografía idéntica a la Ciudad 1
            g2.setFont(new Font("Arial", Font.BOLD, 32));
            g2.setColor(Color.WHITE);

            String mensajeVictoria = "Puntos de experiencia ganados : " + ciudad.getPuntosDeExperiencia() + " ptos !!!";
            FontMetrics fm = g2.getFontMetrics();
            
            // Centrado dinámico adaptado a cualquier resolución de pantalla
            int mensajeX = (ConfiguracionPantalla.SCREEN_WIDTH - fm.stringWidth(mensajeVictoria)) / 2;
            int mensajeY = (ConfiguracionPantalla.SCREEN_HEIGHT / 2) + (fm.getAscent() / 2);

            g2.drawString(mensajeVictoria, mensajeX, mensajeY);
        }
    }

    private void dibujarBotonSelector(Graphics2D g2, String text, int x, int y) {
        g2.setColor(new Color(60, 65, 80));
        g2.fillRect(x, y, btnW, btnH);
        g2.setColor(Color.WHITE);
        g2.drawRect(x, y, btnW, btnH);
        
        g2.setFont(new Font("Consolas", Font.BOLD, 14));
        int stringW = g2.getFontMetrics().stringWidth(text);
        g2.drawString(text, x + (btnW / 2) - (stringW / 2), y + 19);
    }

    private void dibujarBotonAccion(Graphics2D g2, String texto, int x, int y, Color colorFondo) {
        g2.setColor(colorFondo);
        g2.fillRect(x, y, btnAccionW, btnAccionH);
        g2.setColor(Color.WHITE);
        g2.drawRect(x, y, btnAccionW, btnAccionH);
        
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        g2.setColor(Color.WHITE);
        int stringW = g2.getFontMetrics().stringWidth(texto);
        g2.drawString(texto, x + (btnAccionW / 2) - (stringW / 2), y + 21);
    }

    private void dibujarTablaVisual(Graphics2D g2) {
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        g2.setColor(Color.WHITE);
        g2.drawString("TABLA HASH (PROBING LINEAL):", 40, bloquesY - 15);

        CeldaHash[] celdas = tablaHash.getTabla(); 
        int x = 40, yInicial = bloquesY, altoCelda = 24, anchoCelda = 280;

        for (int i = 0; i < celdas.length; i++) {
            int yPos = yInicial + (i * (altoCelda + 4));

            if (celdas[i] != null) {
                g2.setColor(new Color(41, 128, 185));
            } else {
                g2.setColor(new Color(65, 70, 85));
            }

            g2.fillRect(x, yPos, anchoCelda, altoCelda);
            g2.setColor(Color.WHITE);
            g2.drawRect(x, yPos, anchoCelda, altoCelda);

            g2.setFont(new Font("Consolas", Font.PLAIN, 12));
            String textoCelda = " [" + i + "] Vacío";
            if (celdas[i] != null) {
                textoCelda = " [" + i + "] '" + celdas[i].getClave() + "' -> Valor: " + celdas[i].getValor();
            }
            g2.drawString(textoCelda, x + 10, yPos + 17);
        }
    }

    private void dibujarConsola(Graphics2D g2) {
        int x = 350, y = bloquesY, ancho = ConfiguracionPantalla.SCREEN_WIDTH - 390, alto = 280;

        g2.setColor(new Color(15, 15, 25));
        g2.fillRect(x, y, ancho, alto);
        g2.setColor(Color.GREEN);
        g2.drawRect(x, y, ancho, alto);

        g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
        int lineaY = y + 22;

        for (String log : bitacoraVisual) {
            if (log.toUpperCase().contains("COLISION")) {
                g2.setColor(Color.ORANGE); 
            } else if (log.contains("LOGRO") || log.contains("ÉXITO")) {
                g2.setColor(Color.CYAN);
            } else {
                g2.setColor(Color.GREEN);
            }
            g2.drawString("> " + log, x + 12, lineaY);
            lineaY += 15;
        }
    }

    // ========================= FIN DEL JUEGO ============================
    public void setGanado(boolean ganado) {
        this.ganado = ganado;
        if (this.ganado) {
            this.mostrandoCartelVictoria = true; // Permite forzar el render desde afuera si fuese necesario
        }
    }

    @Override
    public void resultadoPartida() {
        // Solo sumamos los puntos si el juego está ganado Y la ciudad NO figura como completada todavía
        if (ganado && this.ciudad.getEstado() != EstadoCiudad.COMPLETADA) {
            this.ciudad.setEstado(EstadoCiudad.COMPLETADA);
            this.jugador.sumarPuntos(ciudad.getPuntosDeExperiencia());
            
            // ¡LA CLAVE ACÁ! Reseteamos las banderas para que el próximo clic no vuelva a entrar a este IF
            this.ganado = false;
            this.mostrandoCartelVictoria = false;
        }
    }
}