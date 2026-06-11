package minijuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import ciudades.Ciudad;
import ciudades.EstadoCiudad;
import entidad.Jugador;
import render.RenderJugador;
import ui.ConfiguracionPantalla;
import ui.GestorRecursos;
import modelo.ciudad1.Elemento;
import modelo.ciudad1.ObservadorRecoleccion;
import modelo.ciudad1.Partida;
import modelo.ciudad1.PartidaLectura;


public class Ciudad1Minijuego implements Minijuego, ObservadorRecoleccion {
	
	private final int TILE_SIZE = ConfiguracionPantalla.TILE_SIZE; // estoy pensando como tener esto para todos los mapas ahre
	
    private Ciudad ciudad;
    private Partida partida;
    private Jugador jugador;
    
    private GestorRecursos recursos;
    private RenderJugador renderJugador;

    // Estado para que render() sepa qué dibujar   <- ella jura
    private PartidaLectura estadoActual;
    private String mensajeRadar = "";
    private String mensajeRecoleccion = "";

    public Ciudad1Minijuego(Ciudad ciudad, Jugador jugador, GestorRecursos recursos) {
        this.ciudad = ciudad;
        this.jugador = jugador;
        this.recursos = recursos;
        this.renderJugador = new RenderJugador(recursos,  this.jugador);
    }
    

	// ===================== JUEGO ========================

    @Override
    public void iniciar() {
        // Creamos la Partida pasándonos como observador
        this.partida = new Partida(jugador, 20, 10, 3, this);
        
        // La primera notificación para que haya estado inicial
        this.estadoActual = this.partida;
    }


    @Override
    public void actualizarVista(PartidaLectura partida) {
        this.estadoActual = partida;
    }

    @Override
    public void mostrarMensajeRadar(String mensaje) {
        this.mensajeRadar = mensaje;
    }

    @Override
    public void objetoRecolectado(Elemento item) {
        this.mensajeRecoleccion = "Recolectaste: " + item.getClass().getSimpleName();
    }
    
    // ========================= RENDER ===========================

    @Override
    public void render(Graphics2D g2) {
        if (estadoActual == null) {
            return;
        }

        int anchoMapa = estadoActual.getMapa().getAncho() * TILE_SIZE;
        int altoMapa = estadoActual.getMapa().getAlto() * TILE_SIZE;

        int centroX = (ConfiguracionPantalla.SCREEN_WIDTH - anchoMapa) / 2;
        int centroY = (ConfiguracionPantalla.SCREEN_HEIGHT - altoMapa) / 2;

        renderMapa(g2, centroX, centroY);
        this.renderJugador.render(g2, this.estadoActual, centroX, centroY);
        renderVision(g2, centroX, centroY);
        renderHUD(g2, centroX);
    }
    
    public void renderVision(Graphics2D g2, int centroX, int centroY) {

        for (int x = 0; x < estadoActual.getMapa().getAncho(); x++) {
            for (int y = 0; y < estadoActual.getMapa().getAlto(); y++) {

                if (!estadoActual.getMapa().estaRevelado(x, y, estadoActual.getZ())) {

                    int pantallaX = centroX + x * TILE_SIZE;
                    int pantallaY = centroY + y * TILE_SIZE;

                    g2.setColor(new Color(0, 0, 0, 180));
                    g2.fillRect(
                        pantallaX,
                        pantallaY,
                        TILE_SIZE,
                        TILE_SIZE
                    );
                }
            }
        }
    }
    
    private void renderMapa(Graphics2D g2, int centroX, int centroY) {

        for (int x = 0; x < estadoActual.getMapa().getAncho(); x++) {
            for (int y = 0; y < estadoActual.getMapa().getAlto(); y++) {

                int pantallaX = centroX + x * TILE_SIZE;
                int pantallaY = centroY + y * TILE_SIZE;

                g2.drawImage(
                    recursos.getMadera(),
                    pantallaX,
                    pantallaY,
                    TILE_SIZE,
                    TILE_SIZE,
                    null
                );
            }
        }
    }
    
    
    public void renderHUD(Graphics2D g2, int centroX) {
	    g2.setColor(Color.WHITE);

		g2.setFont(new Font("Arial", Font.ITALIC, 20));
	    
	    String texto = "Ciudad 1 - Recolección   ";
	    String posX = "Pos: X=" + estadoActual.getX() + "  ";
	    String posY = " Y=" + estadoActual.getY() + "  ";
	    String posZ = " Z=" + estadoActual.getZ() + "  ";
	    String radioVision = "Radio visión: " + estadoActual.getRadioVision();
	    String hud = texto + posX + posY + posZ + radioVision;
		FontMetrics fm = g2.getFontMetrics();
		
	    g2.drawString(hud, fm.stringWidth(hud) / 2, 30);
	
	    if (!mensajeRadar.isEmpty()) {
	        g2.setColor(Color.CYAN);
	        g2.drawString(mensajeRadar, 50, 140);
	    }
	    if (!mensajeRecoleccion.isEmpty()) {
	        g2.setColor(Color.YELLOW);
	        g2.drawString(mensajeRecoleccion, 50, 170);
	    }
	}

    // ================= EVENTOS ========================
    
    // Para que Panel pueda pasarle input
    public void mover(int dx, int dy, int dz) {
        if (partida != null) {
            partida.mover(dx, dy, dz);
        }
    }

	@Override
	public void procesarClick(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}

	// =============== FIN DEL JUEGO =======================

    @Override
    public void resultadoPartida() {
    	int cantidadElementosVictoria = 3;
    	if(this.partida == null) {
    		return;
    	}
    	
    	if(this.partida.getCantidadElementosMochila() >= cantidadElementosVictoria) {
    		desbloquearVecinos();
    	}
    }

    @Override
    public void desbloquearVecinos() {
        ciudad.setEstado(EstadoCiudad.COMPLETADA);
    }

}