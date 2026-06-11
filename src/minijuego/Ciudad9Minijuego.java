package minijuego;

import java.awt.Color;
import java.awt.Graphics2D;
import ciudades.Ciudad;
import ciudades.EstadoCiudad;
import entidad.Jugador;
import modelo.ciudad9.Accion;
import modelo.ciudad9.ControladorCombate;
import modelo.ciudad9.VistaCombate;
import utiles.ObservadorVictoria;

public class Ciudad9Minijuego implements Minijuego, ObservadorVictoria {

    private Ciudad ciudad;
    private Jugador jugador;
    private VistaCombate vista;
    private ControladorCombate combate;

    public Ciudad9Minijuego(Ciudad ciudad, Jugador jugador) {
        this.ciudad = ciudad;
        this.jugador = jugador;
    }

    @Override
    public void iniciar() {
        combate = new ControladorCombate();
        vista = new VistaCombate(this);
        vista.mostrarEstado(combate);
        new Thread(() -> correrCombate()).start();
    }

    private void correrCombate() {
        while (!combate.victoria() && !combate.derrota()) {
            if (combate.esTurnoJugador()) {
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
                combate.ejecutarTurno(objetivo);
            } else {
                combate.ejecutarTurno(0);
                try { Thread.sleep(800); } catch (InterruptedException e) { break; }
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
        g2.setColor(Color.WHITE);
        g2.drawString("Ciudad 9 - Batalla", 50, 50);
        g2.drawString("Completá el desafío en la ventana del juego.", 50, 80);
        g2.drawString("Q para volver al mapa", 50, 110);
    }

    @Override
    public void resultadoPartida() {
        desbloquearVecinos();
    }

    @Override
    public void desbloquearVecinos() {
        ciudad.setEstado(EstadoCiudad.COMPLETADA);
    }

    @Override
    public void notificarVictoria() {
        resultadoPartida();
    }

    @Override
    public void procesarClick(int mouseX, int mouseY) {}
}