package modelo.ciudad10;
import java.util.Objects;

/**
 * TDA EcuacionRecurrencia.
 * Representa una ecuación de recurrencia inmutable para el análisis de algoritmos.
 * Soporta de forma nativa tanto la reducción por División como por Sustracción.
 */
public class EcuacionRecurrencia {

    /**
     * Enumeración que define los tipos de reducción soportados por el Teorema Maestro.
     */
    public enum TipoEcuacion {
        DIVISION, SUSTRACCION
    }

    private final TipoEcuacion tipo;
    private final int parametroA;
    private final int parametroB;
    private final int gradoK;

    /**
     * Construye una nueva ecuación de recurrencia validando sus reglas matemáticas.
     * * @pre El parámetro 'a' debe ser >= 1. El grado 'k' debe ser >= 0.
     * Si el tipo es DIVISION, 'b' > 1. Si es SUSTRACCION, 'b' >= 1.
     * @post Se inicializa una instancia inmutable con los valores proporcionados, lista para ser resuelta.
     * @param tipo El tipo de ecuación detectada (DIVISION o SUSTRACCION).
     * @param parametroA La cantidad de subproblemas (a).
     * @param parametroB El factor de reducción del problema original (b).
     * @param gradoK El grado del polinomio que representa el costo de combinación (k).
     * @throws IllegalArgumentException Si alguno de los parámetros rompe las reglas matemáticas del teorema.
     */
    public EcuacionRecurrencia(TipoEcuacion tipo, int parametroA, int parametroB, int gradoK) {
        validarParametros(tipo, parametroA, parametroB, gradoK);
        this.tipo = tipo;
        this.parametroA = parametroA;
        this.parametroB = parametroB;
        this.gradoK = gradoK;
    }

    /**
     * Valida internamente que los coeficientes ingresados formen una ecuación resoluble.
     * * @param tipo Tipo de ecuación.
     * @param a Coeficiente de subproblemas.
     * @param b Coeficiente de reducción.
     * @param k Grado del costo.
     * @throws IllegalArgumentException Si se violan las restricciones del dominio matemático.
     */
    private void validarParametros(TipoEcuacion tipo, int a, int b, int k) {
        if (a < 1) {
            throw new IllegalArgumentException("Error: El parámetro 'a' debe ser mayor o igual a 1.");
        }
        if (k < 0) {
            throw new IllegalArgumentException("Error: El grado 'k' no puede ser negativo.");
        }

        if (tipo == TipoEcuacion.DIVISION && b <= 1) {
            throw new IllegalArgumentException("Error: En división, el divisor 'b' debe ser estrictamente mayor a 1.");
        }
        if (tipo == TipoEcuacion.SUSTRACCION && b < 1) {
            throw new IllegalArgumentException("Error: En sustracción, el sustraendo 'b' debe ser mayor o igual a 1.");
        }
    }

    /**
     * Obtiene el tipo de reducción de la ecuación.
     * @return El tipo de ecuación (DIVISION o SUSTRACCION).
     */
    public TipoEcuacion getTipo() {
        return this.tipo;
    }

    /**
     * Obtiene la cantidad de subproblemas recursivos.
     * @return El valor del parámetro 'a'.
     */
    public int getParametroA() {
        return this.parametroA;
    }

    /**
     * Obtiene el factor de reducción del tamaño del problema.
     * @return El valor del parámetro 'b'.
     */
    public int getParametroB() {
        return this.parametroB;
    }

    /**
     * Obtiene el grado polinómico del costo de combinación.
     * @return El valor del parámetro 'k'.
     */
    public int getGradoK() {
        return this.gradoK;
    }

    /**
     * Compara esta ecuación con otro objeto para determinar si son matemáticamente idénticos.
     * @param obj El objeto a comparar.
     * @return true si ambos objetos representan la misma ecuación, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        EcuacionRecurrencia otraEcuacion = (EcuacionRecurrencia) obj;
        return this.tipo == otraEcuacion.tipo &&
                this.parametroA == otraEcuacion.parametroA &&
                this.parametroB == otraEcuacion.parametroB &&
                this.gradoK == otraEcuacion.gradoK;
    }

    /**
     * Genera un código hash único basado en los coeficientes de la ecuación.
     * @return Un entero representativo del estado del objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.tipo, this.parametroA, this.parametroB, this.gradoK);
    }

    /**
     * Devuelve la representación en cadena de la ecuación de recurrencia.
     * @return String formateado como "T(n) = aT(n/b) + O(n^k)" o "T(n) = aT(n-b) + O(n^k)".
     */
    @Override
    public String toString() {
        String operador = (this.tipo == TipoEcuacion.DIVISION) ? "/" : "-";
        return "T(n) = " + this.parametroA + "T(n" + operador + this.parametroB + ") + O(n^" + this.gradoK + ")";
    }
}
