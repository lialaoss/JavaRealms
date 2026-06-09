package test;
import static org.junit.jupiter.api.Assertions.*;
import modelo.PartidaLectura;
import entidad.Jugador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import modelo.*;
import utiles.RecoleccionException;


/**
 * Suite de pruebas para la logica de la Ciudad 1.
 * Post: valida el movimiento, la recoleccion polimorfica y el diseño defensivo.
 */

class RecoleccionTest {
	
	private Partida partida;
    private Jugador jugador;
    private ObservadorSilencioso obs;
    
    @BeforeEach
    public void setup() {
        jugador = new Jugador(); // sin parámetro
        obs = new ObservadorSilencioso();
        partida = new Partida(jugador, 5, 5, 3, obs);
    }
    
    
    /**
     * Post: verifica que el jugador se desplace correctamente y actualice coordenadas.
     */
    
    @Test
    public void testMovimientoValido() {
        partida.mover(1, 1, 0);
        assertEquals(1, partida.getX(), "La posicion X deberia ser 1");
        assertEquals(1, partida.getY(), "La posicion Y deberia ser 1");
    }
    
    /**
     * Post: asegura que el sistema lance una excepcion si se intenta salir del mapa [7, 8].
     */
    
    @Test
    public void testMovimientoInvalidoLanzaExcepcion() {
        assertThrows(RecoleccionException.class, () -> {
            partida.mover(-1, 0, 0);
        }, "Deberia lanzar RecoleccionException al salir de los limites");
    }
    
    
    /**
     * Clase interna para simular la interfaz sin necesidad de Swing.
     */
    private class ObservadorSilencioso implements ObservadorRecoleccion {
        @Override public void actualizarVista(PartidaLectura p) {}
        @Override public void objetoRecolectado(Elemento e) {}
        @Override public void mostrarMensajeRadar(String mensaje) {}
    }
}
