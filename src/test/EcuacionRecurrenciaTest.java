package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.ciudad10.EcuacionRecurrencia;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de testeo unitario para el TDA EcuacionRecurrencia.
 */
public class EcuacionRecurrenciaTest {

    private EcuacionRecurrencia ecuacionDivision;
    private EcuacionRecurrencia ecuacionSustraccion;

    @BeforeEach
    public void setUp() {
        // Inicializamos escenarios limpios antes de cada @Test (Regla de la cátedra)
        ecuacionDivision = new EcuacionRecurrencia(EcuacionRecurrencia.TipoEcuacion.DIVISION, 2, 2, 1);
        ecuacionSustraccion = new EcuacionRecurrencia(EcuacionRecurrencia.TipoEcuacion.SUSTRACCION, 3, 1, 2);
    }

    @Test
    public void testGettersYEstadoInicial() {
        // Verificamos que los datos se guarden y expongan correctamente
        assertEquals(EcuacionRecurrencia.TipoEcuacion.DIVISION, ecuacionDivision.getTipo());
        assertEquals(2, ecuacionDivision.getParametroA());
        assertEquals(2, ecuacionDivision.getParametroB());
        assertEquals(1, ecuacionDivision.getGradoK());
    }

    @Test
    public void testToStringFormatoCorrecto() {
        // Verificamos la correcta sobreescritura del método toString()
        assertEquals("T(n) = 2T(n/2) + O(n^1)", ecuacionDivision.toString());
        assertEquals("T(n) = 3T(n-1) + O(n^2)", ecuacionSustraccion.toString());
    }

    @Test
    public void testValidacionMatematicaExcepciones() {
        // Verificamos que el TDA explote si le pasamos valores matemáticamente inválidos

        // Caso: a < 1
        assertThrows(IllegalArgumentException.class, () -> {
            new EcuacionRecurrencia(EcuacionRecurrencia.TipoEcuacion.DIVISION, 0, 2, 1);
        }, "Debería fallar porque 'a' no puede ser menor a 1.");

        // Caso: k < 0
        assertThrows(IllegalArgumentException.class, () -> {
            new EcuacionRecurrencia(EcuacionRecurrencia.TipoEcuacion.DIVISION, 2, 2, -1);
        }, "Debería fallar porque el grado 'k' no puede ser negativo.");

        // Caso: División por 1 o menos
        assertThrows(IllegalArgumentException.class, () -> {
            new EcuacionRecurrencia(EcuacionRecurrencia.TipoEcuacion.DIVISION, 2, 1, 1);
        }, "Debería fallar porque en división 'b' debe ser mayor a 1.");
    }
}
