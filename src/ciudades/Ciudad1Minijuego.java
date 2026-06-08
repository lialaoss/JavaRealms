package ciudades;

import java.awt.Color;
import java.awt.Graphics2D;

import entidad.Jugador;
import modelo.Elemento;
import modelo.Partida;
import modelo.PartidaLectura;
import modelo.ObservadorRecoleccion;

public class Ciudad1Minijuego implements Minijuego, ObservadorRecoleccion {

    private Ciudad ciudad;
    private Partida partida;
    private Jugador jugador;

    // Estado para que render() sepa qué dibujar
    private PartidaLectura estadoActual;
    private String mensajeRadar = "";
    private String mensajeRecoleccion = "";

    public Ciudad1Minijuego(Ciudad ciudad, Jugador jugador) {
        this.ciudad = ciudad;
        this.jugador = jugador;
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
        // Por ahora: dibuja posición del jugador y mensajes
        // Tu amiga puede reemplazar esto con los BMP reales
        g2.setColor(Color.WHITE);
        g2.drawString("Ciudad 1 - Recolección", 50, 50);
        g2.drawString("Pos: X=" + estadoActual.getX()
                    + " Y=" + estadoActual.getY()
                    + " Z=" + estadoActual.getZ(), 50, 80);
        g2.drawString("Radio visión: " + estadoActual.getRadioVision(), 50, 110);

        if (!mensajeRadar.isEmpty()) {
            g2.setColor(Color.CYAN);
            g2.drawString(mensajeRadar, 50, 140);
        }
        if (!mensajeRecoleccion.isEmpty()) {
            g2.setColor(Color.YELLOW);
            g2.drawString(mensajeRecoleccion, 50, 170);
        }
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