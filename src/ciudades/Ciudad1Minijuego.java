package ciudades;

import java.awt.Color;
import java.awt.Graphics2D;

import entidad.Jugador;
import modelo.Elemento;
import modelo.Partida;
import modelo.PartidaLectura;
import render.RenderCiudad1;
import render.RenderJugador;
import ui.GestorRecursos;
import modelo.ObservadorRecoleccion;

public class Ciudad1Minijuego implements Minijuego, ObservadorRecoleccion {

    private Ciudad ciudad;
    private Partida partida;
    private Jugador jugador;
    
    private GestorRecursos recursos;
    private RenderCiudad1 renderMapa;
    private RenderJugador renderJugador;

    // Estado para que render() sepa qué dibujar
    private PartidaLectura estadoActual;
    private String mensajeRadar = "";
    private String mensajeRecoleccion = "";

    public Ciudad1Minijuego(Ciudad ciudad, Jugador jugador, GestorRecursos recursos) {
        this.ciudad = ciudad;
        this.jugador = jugador;
        this.recursos = recursos;
        this.renderMapa = new RenderCiudad1(recursos);
        this.renderJugador = new RenderJugador(recursos);
    }

    @Override
    public void iniciar() {
        // Creamos la Partida pasándonos como observador
        this.partida = new Partida(jugador, 5, 5, 3, this);
        // La primera notificación para que haya estado inicial
        this.estadoActual = this.partida;
    }

    // ---- ObservadorRecoleccion ----

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

    // ---- Minijuego ----

    @Override
    public void render(Graphics2D g2) {
        if (estadoActual == null) {
            return;
        }
        this.renderMapa.render(g2, this.partida.getMapa(), this.estadoActual, this.mensajeRadar, this.mensajeRecoleccion);
        this.renderJugador.render(g2, this.estadoActual);
    }

    @Override
    public void resultadoPartida() {
        // Acá evaluás si ganó (mochila completa, etc.) y llamás desbloquearVecinos()
    }

    @Override
    public void desbloquearVecinos() {
        ciudad.setEstado(EstadoCiudad.COMPLETADA);
        // acá el admin de ciudades desbloquea los vecinos del grafo
    }

    // Para que Panel pueda pasarle input
    public void mover(int dx, int dy, int dz) {
        if (partida != null) {
            partida.mover(dx, dy, dz);
        }
    }
}