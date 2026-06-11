package modelo.ciudad1;

import entidad.Jugador;

import java.util.ArrayList;
import java.util.List;
import utiles.ValidacionesUtiles;

/**
 * Coordina el estado y la logica de juego de la Ciudad 1.
 * Post: centraliza la posicion, recoleccion y visibilidad, delegando la estructura espacial al mapa.
 */
public class Partida implements PartidaLectura {
    
    private Jugador jugador;
    private Mapa3D mapa;
    private ObservadorRecoleccion observador;
    
    private int x;
    private int y;
    private int z;
    private int radioVision;
    private List<Elemento> mochila;
    
    /**
     * Pre: jugador y observador no son nulos, dimensiones mayores a cero.
     * Post: inicializa la partida en el origen (0,0,0) con radio de vision 2.
     */
    public Partida(Jugador jugador, int ancho, int alto, int niveles, ObservadorRecoleccion obs) {
        ValidacionesUtiles.validarNoNulo(jugador, "El jugador no puede ser nulo");
        ValidacionesUtiles.validarNoNulo(obs, "El observador no puede ser nulo");
        
        this.jugador = jugador;
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.radioVision = 2; 
        this.mochila = new ArrayList<>();
        this.mapa = new Mapa3D(ancho, alto, niveles);
        this.observador = obs;
        this.mapa.actualizarNiebla(this.x, this.y, this.z, this.radioVision);
    }
    
    /**
     * Pre: dx, dy, dz representan el vector de desplazamiento.
     * Post: si la coordenada de destino es transitable, mueve al avatar, recolecta y notifica.
     */
    public void mover(int dx, int dy, int dz) {
        int nx = this.x + dx;
        int ny = this.y + dy;
        int nz = this.z + dz;

        mapa.actualizarNiebla(nx, ny, nz, this.radioVision);
        if (mapa.esCoordenadaValida(nx, ny, nz)) {
            this.x = nx;
            this.y = ny;
            this.z = nz;
            
            mapa.actualizarNiebla(this.x, this.y, this.z, this.radioVision);
            recolectar();
            observador.actualizarVista(this); 
        }
    }
    
    /**
     * Post: si existe un elemento en la coordenada actual, lo transfiere a la mochila y aplica su efecto.
     */
    private void recolectar() {
        Elemento item = mapa.obtenerElemento(this.x, this.y, this.z);
        if (item != null) {
            mochila.add(item);
            item.getAdministrador().aplicarEfecto(this);
            mapa.removerElemento(this.x, this.y, this.z);
            observador.objetoRecolectado(item);
        }
    }
    
    public Jugador getJugador() { 
        return jugador; 
    }
    
    /**
     * Post: devuelve una interfaz de solo lectura del mapa para que la vista lo renderice de forma segura.
     */
    public MapaLectura getMapa() {
        return this.mapa;
    }

    public int getX() { 
        return x; 
    }
    
    public int getY() { 
        return y; 
    }
    
    public int getZ() { 
        return z; 
    }

    public int getRadioVision() { 
        return radioVision; 
    }
    
    public void setRadioVision(int nuevoRadio) { 
        ValidacionesUtiles.validarRango(nuevoRadio, 0, 10, "Radio de Vision");
        this.radioVision = nuevoRadio; 
    }
    
    /**
     * Post: despeja la niebla de guerra en el nivel actual del jugador.
     */
    public void despejarNieblaActual() {
        this.mapa.revelarNivelCompleto(this.z);
    }
    
    
    public void escanearEntorno() {
        int[] pos = this.mapa.buscarElementoMasCercano(this.x, this.y, this.z);
        if (pos != null) {
            this.observador.mostrarMensajeRadar("Senal detectada en X: " + pos[0] + " Y: " + pos[1] + " Z: " + pos[2]);
        } else {
            this.observador.mostrarMensajeRadar("No se detectaron mas elementos en la ciudad");
        }
    }
    
    public int getCantidadElementosMochila() {
        return this.mochila.size();
    }
    
    public List<Elemento> getMochila() { 
        return new ArrayList<>(mochila); 
    }
}