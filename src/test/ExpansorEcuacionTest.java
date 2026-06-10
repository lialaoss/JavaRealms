package test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.ciudad10.EcuacionRecurrencia;
import modelo.ciudad10.ExpansorEcuacion;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de testeo unitario para verificar la correcta expansión paso a paso de las ecuaciones.
 */
public class ExpansorEcuacionTest {

    private ExpansorEcuacion expansor;
    private EcuacionRecurrencia ecuacionBase;

    @BeforeEach
    public void setUp() {
        expansor = new ExpansorEcuacion();
        // Usamos la clásica T(n) = 2T(n/2) + O(n)
        ecuacionBase = new EcuacionRecurrencia(EcuacionRecurrencia.TipoEcuacion.DIVISION, 2, 2, 1);
    }

    @Test
    public void testExpansionTresNiveles() {
        List<String> pasos = expansor.expandirPasoAPaso(ecuacionBase, 3);

        assertEquals(3, pasos.size(), "Debería haber generado exactamente 3 niveles.");

        // Verificamos la exactitud milimétrica de las cadenas generadas
        assertEquals("Nivel 1: T(n) = 2T(n/2) + n", pasos.get(0));
        assertEquals("Nivel 2: T(n) = 4T(n/4) + n + 2(n/2)", pasos.get(1));
        assertEquals("Nivel 3: T(n) = 8T(n/8) + n + 2(n/2) + 4(n/4)", pasos.get(2));
    }

    @Test
    public void testNivelesInvalidos() {
        // Pedir 0 niveles debería hacer explotar el método
        assertThrows(IllegalArgumentException.class, () -> {
            expansor.expandirPasoAPaso(ecuacionBase, 0);
        });
    }

    @Test
    public void testEcuacionNula() {
        assertThrows(IllegalArgumentException.class, () -> {
            expansor.expandirPasoAPaso(null, 3);
        });
    }
}
