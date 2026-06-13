package test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.ciudad10.EcuacionRecurrencia;
import modelo.ciudad10.SolucionadorTeoremaMaestro;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Clase de testeo unitario para verificar la precisión matemática del Teorema Maestro.
 */
public class SolucionadorTeoremaMaestroTest {

    private SolucionadorTeoremaMaestro solucionador;

    @BeforeEach
    public void setUp() {
        solucionador = new SolucionadorTeoremaMaestro();
    }

    @Test
    public void testDivisionCaso1() {
        // Caso 1: a < b^k -> Domina el costo de combinación
        // Ejemplo: T(n) = 2T(n/2) + O(n^2) -> Theta(n^2)
        EcuacionRecurrencia eq = new EcuacionRecurrencia(EcuacionRecurrencia.TipoEcuacion.DIVISION, 2, 2, 2);
        assertEquals("Θ(n^2)", solucionador.resolver(eq));
    }

    @Test
    public void testDivisionCaso2() {
        // Caso 2: a == b^k -> Empate (Aparece el logaritmo)
        // Ejemplo: T(n) = 2T(n/2) + O(n) -> Theta(n * log(n))
        EcuacionRecurrencia eq = new EcuacionRecurrencia(EcuacionRecurrencia.TipoEcuacion.DIVISION, 2, 2, 1);
        assertEquals("Θ(n * log(n))", solucionador.resolver(eq));
    }

    @Test
    public void testDivisionCaso3() {
        // Caso 3: a > b^k -> Domina la recursión (Hojas del árbol)
        // Ejemplo: T(n) = 4T(n/2) + O(n) -> Theta(n^2)
        EcuacionRecurrencia eq = new EcuacionRecurrencia(EcuacionRecurrencia.TipoEcuacion.DIVISION, 4, 2, 1);
        assertEquals("Θ(n^2)", solucionador.resolver(eq));
    }

    @Test
    public void testSustraccionCasos() {
        // Sustracción Caso a = 1
        // Ejemplo: T(n) = T(n-1) + O(n) -> Theta(n^2)
        EcuacionRecurrencia eq1 = new EcuacionRecurrencia(EcuacionRecurrencia.TipoEcuacion.SUSTRACCION, 1, 1, 1);
        assertEquals("Θ(n^2)", solucionador.resolver(eq1));

        // Sustracción Caso a > 1
        // Ejemplo: T(n) = 2T(n-1) + O(1) -> Theta(2^n)
        EcuacionRecurrencia eq2 = new EcuacionRecurrencia(EcuacionRecurrencia.TipoEcuacion.SUSTRACCION, 2, 1, 0);
        assertEquals("Θ(2^n)", solucionador.resolver(eq2));
    }

    @Test
    public void testParametroNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            solucionador.resolver(null);
        });
    }
}
