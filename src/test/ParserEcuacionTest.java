package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.ciudad10.EcuacionRecurrencia;
import modelo.ciudad10.ParserEcuacion;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Clase de testeo unitario para la clase funcional ParserEcuacion.
 */
public class ParserEcuacionTest {

    private ParserEcuacion parser;

    @BeforeEach
    public void setUp() {
        // Instanciamos el parser limpio
        parser = new ParserEcuacion();
    }

    @Test
    public void testParseoDivisionExitosa() {
        // Ecuación ingresada con espacios desordenados a propósito
        String entrada = "  2 T( n / 2) + O (n^ 1 )  ";
        EcuacionRecurrencia resultado = parser.parsear(entrada);

        assertEquals(EcuacionRecurrencia.TipoEcuacion.DIVISION, resultado.getTipo());
        assertEquals(2, resultado.getParametroA());
        assertEquals(2, resultado.getParametroB());
        assertEquals(1, resultado.getGradoK());
    }

    @Test
    public void testParseoSustraccionCompleja() {
        // Ecuación con polinomio completo, el parser debe aislar el grado mayor
        String entrada = "T(n) = 4T(n-2) + O(n^3 + n^2 + 1)";
        EcuacionRecurrencia resultado = parser.parsear(entrada);

        assertEquals(EcuacionRecurrencia.TipoEcuacion.SUSTRACCION, resultado.getTipo());
        assertEquals(4, resultado.getParametroA());
        assertEquals(2, resultado.getParametroB());
        assertEquals(3, resultado.getGradoK());
    }

    @Test
    public void testBlindajeContraBasura() {
        // Caso 1: Falta el O()
        assertThrows(IllegalArgumentException.class, () -> {
            parser.parsear("2T(n/2) + n");
        });

        // Caso 2: Letras donde van números (NumberFormatException interceptada)
        assertThrows(IllegalArgumentException.class, () -> {
            parser.parsear("2T(n/A) + O(n^2)");
        });

        // Caso 3: Basura inyectada después del llamado recursivo
        assertThrows(IllegalArgumentException.class, () -> {
            parser.parsear("2T(n/2)BASURA + O(n^2)");
        });
    }
}
