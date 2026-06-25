package minijuego;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import ciudades.Ciudad;
import ciudades.EstadoCiudad;
import entidad.Jugador;
import modelo.ciudad9.Accion;
import modelo.ciudad9.ControladorCombate;
import modelo.ciudad9.Pregunta;
import modelo.ciudad9.VistaCombate;
import modelo.ciudad9.Personaje;
import modelo.ciudad9.GestorRecursosCiudad9; // 1. Importamos el nuevo gestor local
import render.FinMinijuegoPantalla;
import render.RenderCiudad9; 
import ui.GestorRecursos;
import utiles.ObservadorVictoria;

public class Ciudad9Minijuego implements Minijuego, ObservadorVictoria {

    private Ciudad ciudad;
    private Jugador jugador;
    private VistaCombate vista;
    private ControladorCombate combate;
    private GestorRecursos recursosGlobales; // Mantiene accesos generales (ej: sprites jugador)
    
    private GestorRecursosCiudad9 recursosLocales; // 2. Instancia del gestor especializado
    private RenderCiudad9 renderizador; 
    
    private boolean ganado = false;
    private int idFondoAleatorio; 

    private FinMinijuegoPantalla pantallaFinal = new FinMinijuegoPantalla();

    /*
     * Pre: La 'ciudad', el 'jugador' y 'recursos' no deben ser nulos.
     * Post: Crea el minijuego vinculando el gestor local e inyectándolo en el renderizador.
     */
    public Ciudad9Minijuego(Ciudad ciudad, Jugador jugador, GestorRecursos recursosGlobales) {
        this.ciudad = ciudad;
        this.jugador = jugador;
        this.recursosGlobales = recursosGlobales;
        
        // Inicializamos los componentes locales específicos de Ciudad 9
        this.recursosLocales = new GestorRecursosCiudad9();
        this.renderizador = new RenderCiudad9(recursosLocales); // El render ahora usa el gestor local
    }

    /*
     * Pre: El archivo "preguntas.txt" debe existir.
     * Post: Activa la carga diferida de imágenes locales (Lazy Loading) y dispara el combate.
     */
    
    @Override

    public void iniciar() {
        Pregunta.cargarDesdeArchivo("preguntas.txt");
        recursosLocales.cargarRecursos(); // Carga las imágenes sin el bug de /assets/
        
        this.idFondoAleatorio = (int) (Math.random() * 4);

        combate = new ControladorCombate();
        vista = new VistaCombate();
        
        // Ponemos al jugador en su sitio de la ventana
        if (recursosGlobales.getJugadorDown() != null && recursosGlobales.getJugadorDown().length > 0) {
            vista.actualizarJugador(recursosGlobales.getJugadorDown()[0]);
        }
        
        // MOSTRAR AL ENEMIGO INICIAL EN SU SITIO DESDE EL PRINCIPIO
        actualizarSpriteEnemigoReposo();
        
        vista.mostrarEstado(combate);
        new Thread(() -> correrCombate()).start();
    }

    private void actualizarSpriteEnemigoReposo() {
        java.util.List<Personaje> vivos = combate.getListaEnemigos().obtenerEnemigos();
        if (!vivos.isEmpty()) {
            String nombre = vivos.get(0).getNombre();
            BufferedImage img = null;
            if (nombre.equalsIgnoreCase("Dragon") && recursosLocales.getDragonAttack() != null) img = recursosLocales.getDragonAttack()[0];
            else if (nombre.equalsIgnoreCase("Demon") && recursosLocales.getDemonIdle() != null) img = recursosLocales.getDemonIdle()[0];
            else if (nombre.equalsIgnoreCase("Jinn") && recursosLocales.getJinnIdle() != null) img = recursosLocales.getJinnIdle()[0];
            vista.actualizarEnemigo(img);
        }
    }

    private void correrCombate() {
        while (!combate.victoria() && !combate.derrota()) {
            
            if (combate.esTurnoJugador()) {
                actualizarSpriteEnemigoReposo(); // Nos aseguramos que el enemigo se vea en su estado idle
                if (recursosGlobales.getJugadorDown() != null && recursosGlobales.getJugadorDown().length > 0) {
                    vista.actualizarJugador(recursosGlobales.getJugadorDown()[0]);
                }

                int accionesRequeridas = combate.isComboDisponible() ? 2 : 1;
                boolean requiereObjetivo = false;
                for (int i = 0; i < accionesRequeridas; i++) {
                    int opcion = vista.solicitarAccion(i + 1, accionesRequeridas);
                    String tipo;
                    switch (opcion) {
                        case 1: tipo = Accion.ATAQUE; requiereObjetivo = true; break;
                        case 3: tipo = Accion.HABILIDAD; break;
                        default: tipo = Accion.DEFENSA; break;
                    }
                    combate.agregarAccionJugador(tipo);
                }
                int objetivo = 0;
                if (requiereObjetivo && combate.getListaEnemigos().quedanEnemigos()) {
                    objetivo = vista.solicitarObjetivo(combate.getListaEnemigos());
                }

                Pregunta preguntaAleatoria = Pregunta.obtenerAleatoria();
                boolean respondioBien = vista.hacerPreguntaEstructuras(preguntaAleatoria);
                
                combate.ejecutarTurno(objetivo, respondioBien);
                
            } else {
                // --- TURNO DEL ENEMIGO ---
                java.util.List<Personaje> enemigosVivos = combate.getListaEnemigos().obtenerEnemigos();
                
                if (!enemigosVivos.isEmpty()) {
                    Personaje enemigoActual = enemigosVivos.get(0); 
                    String nombre = enemigoActual.getNombre();
                    
                    BufferedImage[] cuadrosAtaque = null;
                    
                    if (nombre.equalsIgnoreCase("Dragon")) cuadrosAtaque = recursosLocales.getDragonFireAttack(); 
                    else if (nombre.equalsIgnoreCase("Demon")) cuadrosAtaque = recursosLocales.getDemonAttack();
                    else if (nombre.equalsIgnoreCase("Jinn")) cuadrosAtaque = recursosLocales.getJinnMagicAttack();
                    
                    if (cuadrosAtaque != null) {
                        for (BufferedImage cuadro : cuadrosAtaque) {
                            if (cuadro != null) {
                                vista.actualizarEnemigo(cuadro); // Se anima el enemigo en su propio casillero
                                try { Thread.sleep(120); } catch (InterruptedException e) { break; }
                            }
                        }
                    }
                    
                    combate.ejecutarTurno(0, true);
                }
            }
            
            if (combate.victoria()) {
                if (recursosLocales.getDragonDeath() != null && recursosLocales.getDragonDeath().length > 0) {
                    vista.actualizarEnemigo(recursosLocales.getDragonDeath()[0]);
                    try { Thread.sleep(600); } catch (InterruptedException e) { break; }
                }
            }
            
            vista.mostrarEstado(combate);
        }
        
        vista.mostrarMensajeFin(combate.victoria());
        if (combate.victoria()) {
            notificarVictoria();
        }
    }

    @Override
    public void render(Graphics2D g2) {
        renderizador.dibujar(g2, ciudad, pantallaFinal, ganado, idFondoAleatorio);
    }

    @Override
    public void resultadoPartida() {
    	if(ganado) {
            ciudad.setEstado(EstadoCiudad.COMPLETADA);
			jugador.sumarPuntos(ciudad.getPuntosDeExperiencia());
    	}
    }

    @Override
    public void notificarVictoria() {
        this.ganado = true;
    }

    @Override
    public void procesarClick(int mouseX, int mouseY) {}
}