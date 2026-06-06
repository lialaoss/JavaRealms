package test;
import static org.junit.jupiter.api.Assertions.*;
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
        jugador = new Jugador("TestPlayer");
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
     * Post: valida la recoleccion de un item y el efecto polimorfico en la visibilidad [9, 10].
     */
    
    @Test
    public void testRecoleccionAumentaVisibilidad() {
        Elemento linterna = new ElementoVisibilidad("Linterna Super", 3);
        partida.getMapa().colocarElemento(1, 0, 0, linterna);
        int radioInicial = partida.getRadioVision();
        partida.mover(1, 0, 0);

        assertEquals(1, partida.getMochila().size(), "La mochila deberia tener 1 item");
        assertTrue(partida.getRadioVision() > radioInicial, "El radio de vision deberia haber aumentado");
        assertEquals(radioInicial + 3, partida.getRadioVision(), "El nuevo radio deberia ser 5");
    }
    
    /**
     * Clase interna para simular la interfaz sin necesidad de Swing.
     */
    private class ObservadorSilencioso implements ObservadorRecoleccion {
        @Override public void actualizarVista(Partida p) {}
        @Override public void objetoRecolectado(Elemento e) {}
    }
}
