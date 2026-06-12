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
import render.FinMinijuegoPantalla;
import render.RenderElementos;
import render.RenderJugador;
import ui.ConfiguracionPantalla;
import ui.GestorRecursos;
import modelo.ciudad1.Antorcha;
import modelo.ciudad1.Bengala;
import modelo.ciudad1.Elemento;
import modelo.ciudad1.ObservadorRecoleccion;
import modelo.ciudad1.Partida;
import modelo.ciudad1.PartidaLectura;
import modelo.ciudad1.Radar;


public class Ciudad1Minijuego implements Minijuego, ObservadorRecoleccion {
	
	private final int TILE_SIZE = ConfiguracionPantalla.TILE_SIZE; // estoy pensando como tener esto para todos los mapas ahre
	
    private Ciudad ciudad;
    private Partida partida;
    private Jugador jugador;
    
    private FinMinijuegoPantalla pantallaFinal = new FinMinijuegoPantalla();

	private boolean ganado = false;
    private GestorRecursos recursos;
    private RenderJugador renderJugador;
    private List<RenderElementos> elementosRender = new ArrayList<>();

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
        
        Elemento antorcha = new Antorcha();
        this.partida.getMapa().colocarElemento(10, 5, 0, antorcha);
        elementosRender.add(new RenderElementos(10, 5, 0, antorcha));

        Elemento radar = new Radar();
        this.partida.getMapa().colocarElemento(8, 9, 1, radar);
        elementosRender.add(new RenderElementos(8, 9, 1, radar));

        Elemento bengala = new Bengala();
        this.partida.getMapa().colocarElemento(19, 1, 2, bengala);
        elementosRender.add(new RenderElementos(19, 1, 2, bengala));
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
    	subirNivel();
    	for(RenderElementos elemento : elementosRender) {
    		if(item.equals(elemento.getElemento())) {
    			elemento.elementoEncontrado();
    		}
    	}
        this.mensajeRecoleccion = "Recolectaste: " + item.getClass().getSimpleName();
    }
    
    private void subirNivel() {
    	if(this.partida == null) {
    		return;
    	}
    	this.partida.setZ(this.partida.getZ() + 1);
    	if(ganado()) {
    		this.ganado = true;
    	}
    }
    
	public boolean ganado() {
		int cantidadElementosVictoria = 3;
		return this.partida.getCantidadElementosMochila() >= cantidadElementosVictoria;
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
        dibujarElementos(g2, centroX, centroY);
        renderVision(g2, centroX, centroY);
        mostrarMochila(g2);
        this.renderJugador.render(g2, this.estadoActual, centroX, centroY);
        renderHUD(g2, centroX);
        
        if(ganado) {
			this.pantallaFinal.mostrarResultados(g2, ciudad);
		}
    }
    
    public void dibujarElementos(Graphics2D g2, int centroX, int centroY) {
    	for (RenderElementos elemento : elementosRender) {
    	    if (elemento.getZ() == estadoActual.getZ() && !elemento.getEncontrado()) {
    	    	elemento.dibujar(g2, centroX, centroY);
    	    }
    	}
    }
    
    
    /**
     * 
     * @param g2
     * @param centroX
     * @param centroY
     */
    public void renderVision(Graphics2D g2, int centroX, int centroY) {
        int radio = estadoActual.getRadioVision();

        for (int x = 0; x < estadoActual.getMapa().getAncho(); x++) {
            for (int y = 0; y < estadoActual.getMapa().getAlto(); y++) {

                int dx = Math.abs(x - estadoActual.getX());
                int dy = Math.abs(y - estadoActual.getY());

                // de esta manera solo se pinta lo que esta fuera del radio del pj
                if (dx > radio || dy > radio) {

                    int pantallaX = centroX + x * TILE_SIZE;
                    int pantallaY = centroY + y * TILE_SIZE;

                    g2.setColor(Color.BLACK);
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
	        g2.setColor(Color.WHITE);
	        g2.drawString(mensajeRadar, 50, 50);
	    }
	    if (!mensajeRecoleccion.isEmpty()) {
	        g2.setColor(Color.YELLOW);
	        g2.drawString(mensajeRecoleccion, 50, 70);
	    }
	}
    
    public void mostrarMochila(Graphics2D g2) {
        g2.setColor(Color.GRAY);
        String texto = "Objetos recolectados:";

		FontMetrics fm = g2.getFontMetrics();

		int y = ConfiguracionPantalla.SCREEN_HEIGHT - 30;
		int xActual = 300 + fm.stringWidth(texto) + 20;
		
		g2.drawString(texto, 300, y);

		for (int i = 0; i < this.partida.getCantidadElementosMochila(); i++) {
		    String nombreObjeto = this.partida.getMochila().get(i).getNombre();
		    g2.drawString(nombreObjeto, xActual, y);
		    xActual += fm.stringWidth(nombreObjeto) + 20;
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
    	if(ganado) {
    		desbloquearVecinos();
    	}
    }

    @Override
    public void desbloquearVecinos() {
        ciudad.setEstado(EstadoCiudad.COMPLETADA);
    }

}