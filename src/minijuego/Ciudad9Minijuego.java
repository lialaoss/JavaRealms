package minijuego;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import ciudades.Ciudad;
import ciudades.EstadoCiudad;
import entidad.Jugador;
import modelo.ciudad9.Accion;
import modelo.ciudad9.ControladorCombate;
import modelo.ciudad9.Pregunta;
import modelo.ciudad9.Personaje;
import render.FinMinijuegoPantalla;
import render.RenderCiudad9; 
import ui.GestorRecursos;
import utiles.ObservadorVictoria;

public class Ciudad9Minijuego implements Minijuego, ObservadorVictoria {

    private Ciudad ciudad;
    private Jugador jugador;
    private ControladorCombate combate;
    private GestorRecursos recursosGlobales; 
    private RenderCiudad9 renderizador; 
    
    private boolean ganado = false;
    private FinMinijuegoPantalla pantallaFinal = new FinMinijuegoPantalla();

    private BufferedImage spriteJugadorActual;
    private BufferedImage efectoAtaque; 
    private int objetivoImpacto = -1; 
    private String mensajeActual = "¡Comienza el desafío!"; 

    // --- VARIABLES DE FASE (CON VOLATILE AGREGADO) ---
    private String faseActual = "ESPERA"; 
    private volatile boolean esperandoInput = false;
    private volatile String accionSeleccionada = "";
    private volatile int objetivoSeleccionado = -1;
    private volatile int respuestaSeleccionada = -1;
    private Pregunta preguntaActual = null;

    public Ciudad9Minijuego(Ciudad ciudad, Jugador jugador, GestorRecursos recursosGlobales) {
        this.ciudad = ciudad;
        this.jugador = jugador;
        this.recursosGlobales = recursosGlobales;
        this.renderizador = new RenderCiudad9(recursosGlobales); 
    }

    @Override
    public void iniciar() {
        Pregunta.cargarDesdeArchivo("preguntas.txt");
        combate = new ControladorCombate();
        
        if (recursosGlobales.getJugadorLeft() != null && recursosGlobales.getJugadorLeft().length > 0) {
            spriteJugadorActual = recursosGlobales.getJugadorLeft()[0];
        }
        
        new Thread(() -> correrCombate()).start();
    }

    private void correrCombate() {
        while (!combate.victoria() && !combate.derrota()) {
            
            if (combate.esTurnoJugador()) {
                int accionesRequeridas = combate.isComboDisponible() ? 2 : 1;
                
                for (int i = 0; i < accionesRequeridas; i++) {
                    mensajeActual = "Turno del Héroe [" + (i+1) + "/" + accionesRequeridas + "]. ¡Elige sabiamente!";
                    faseActual = "ACCION";
                    pausarHastaClic();
                    combate.agregarAccionJugador(accionSeleccionada);
                }
                
                java.util.List<Personaje> enemigosVivos = combate.getListaEnemigos().obtenerEnemigos();
                if (enemigosVivos.size() > 1) {
                    mensajeActual = "Selecciona tu objetivo de ataque:";
                    faseActual = "OBJETIVO";
                    pausarHastaClic();
                } else {
                    objetivoSeleccionado = 0; 
                }
                
                preguntaActual = Pregunta.obtenerAleatoria();
                mensajeActual = "¡Desafío mental para ejecutar tu movimiento!";
                faseActual = "PREGUNTA";
                pausarHastaClic();
                
                faseActual = "ESPERA"; 
                
                // --- 1. CAPTURAMOS EL OBJETIVO ANTES DE QUE EL MODELO CAMBIE ---
                String nombreTarget = "";
                java.util.List<Personaje> enemigosAntes = combate.getListaEnemigos().obtenerEnemigos();
                if (objetivoSeleccionado >= 0 && objetivoSeleccionado < enemigosAntes.size()) {
                    nombreTarget = enemigosAntes.get(objetivoSeleccionado).getNombre().toLowerCase();
                }

                boolean respondioBien = (respuestaSeleccionada == preguntaActual.getIndiceCorrecto());
                
                // --- 2. PRIMERO SE ACTIVA Y SE MUESTRA LA ANIMACIÓN EN PANTALLA ---
                if (respondioBien && recursosGlobales.getAtaque() != null) {
                    mensajeActual = "¡Golpe certero!";
                    efectoAtaque = recursosGlobales.getAtaque();
                    
                    // Asignación de IDs fijos para el renderizador según el enemigo
                    if (nombreTarget.contains("dragon") || nombreTarget.contains("dragón")) {
                        objetivoImpacto = 10; 
                    } else if (nombreTarget.contains("demon") || nombreTarget.contains("demonio")) {
                        objetivoImpacto = 11; 
                    } else if (nombreTarget.contains("jinn") || nombreTarget.contains("genio")) {
                        objetivoImpacto = 12; 
                    } else {
                        objetivoImpacto = objetivoSeleccionado; 
                    }
                    
                    try { Thread.sleep(600); } catch (InterruptedException e) {}
                    efectoAtaque = null;
                    objetivoImpacto = -1;
                } else {
                    mensajeActual = "¡Respuesta incorrecta, fallaste!";
                    try { Thread.sleep(1000); } catch (InterruptedException e) {}
                }
                
                // --- 3. DESPUÉS APLICAMOS EL DAÑO REAL EN EL CONTROLADOR ---
                combate.ejecutarTurno(objetivoSeleccionado, respondioBien);
                }
        }
        
        faseActual = "ESPERA"; 
        mensajeActual = combate.victoria() ? "¡VICTORIA!" : "DERROTA...";
        
        if (combate.victoria()) {
            notificarVictoria();
        }
    }

    private void pausarHastaClic() {
        esperandoInput = true;
        while (esperandoInput) {
            try { Thread.sleep(50); } catch (InterruptedException e) {}
        }
    }

    @Override
    public void render(Graphics2D g2) {
        renderizador.dibujar(g2, ciudad, pantallaFinal, ganado, combate, spriteJugadorActual, 
                             efectoAtaque, objetivoImpacto, mensajeActual, faseActual, preguntaActual);
    }

    @Override
    public void procesarClick(int mouseX, int mouseY) {
        if (!esperandoInput) return;

        // Rango de altura de los botones del menú inferior
        if (mouseY >= 430 && mouseY <= 490) { 
            
            // Columna 1 (Botón 1)
            if (mouseX >= 20 && mouseX <= 205) {
                registrarSeleccion(0);
            } 
            // Columna 2 (Botón 2)
            else if (mouseX >= 210 && mouseX <= 395) {
                registrarSeleccion(1);
            } 
            // Columna 3 (Botón 3)
            else if (mouseX >= 400 && mouseX <= 585) {
                registrarSeleccion(2);
            }
            // Columna 4 (Botón 4 - ¡NUEVA AGREGADA!)
            else if (mouseX >= 590 && mouseX <= 790) {
                registrarSeleccion(3);
            }
        }
    }
    
    private void registrarSeleccion(int indice) {
        if (faseActual.equals("ACCION")) {
            if(indice == 0) accionSeleccionada = Accion.ATAQUE;
            else if(indice == 1) accionSeleccionada = Accion.DEFENSA;
            else accionSeleccionada = Accion.HABILIDAD;
            esperandoInput = false; 
        } 
        else if (faseActual.equals("OBJETIVO")) {
            java.util.List<Personaje> vivos = combate.getListaEnemigos().obtenerEnemigos();
            if (indice < vivos.size()) {
                objetivoSeleccionado = indice;
                esperandoInput = false;
            }
        }
        else if (faseActual.equals("PREGUNTA")) {
            Object[] opciones = preguntaActual.getOpciones().toArray();
            if(indice < opciones.length) {
                respuestaSeleccionada = indice;
                esperandoInput = false;
            }
        }
    }

    @Override
    public void resultadoPartida() {
        if(ganado) {
            ciudad.setEstado(EstadoCiudad.COMPLETADA);
            jugador.sumarPuntos(ciudad.getPuntosDeExperiencia());
        }
    }

    @Override
    public void notificarVictoria() { this.ganado = true; }
}