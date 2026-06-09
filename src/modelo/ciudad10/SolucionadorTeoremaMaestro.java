package modelo.ciudad10;

/**
 * Clase funcional encargada de aplicar el Teorema Maestro matemático,
 * resolviendo la complejidad asintótica basada en los parámetros del TDA.
 */
public class SolucionadorTeoremaMaestro {

    /**
     * Analiza el tipo de ecuación y deriva el cálculo a la función matemática correspondiente.
     * * @pre El objeto ecuación proporcionado no debe ser nulo.
     * @post Devuelve una cadena con el resultado en notación asintótica estricta.
     * @param ecuacion La instancia del TDA EcuacionRecurrencia completamente cargada.
     * @return Un String formateado con la complejidad (Ej: "Θ(n * log(n))").
     * @throws IllegalArgumentException Si se pasa una referencia nula por parámetro.
     */
    public String resolver(EcuacionRecurrencia ecuacion) {
        if (ecuacion == null) {
            throw new IllegalArgumentException("Error: La ecuación no puede ser nula.");
        }

        if (ecuacion.getTipo() == EcuacionRecurrencia.TipoEcuacion.DIVISION) {
            return resolverPorDivision(ecuacion.getParametroA(), ecuacion.getParametroB(), ecuacion.getGradoK());
        } else {
            return resolverPorSustraccion(ecuacion.getParametroA(), ecuacion.getParametroB(), ecuacion.getGradoK());
        }
    }

    /**
     * Aplica la fórmula del Teorema Maestro por Reducción de División.
     * * @param a Coeficiente de llamadas recursivas.
     * @param b Factor de división espacial.
     * @param k Grado polinómico del costo.
     * @return La resolución de la complejidad en formato Theta.
     */
    private String resolverPorDivision(int a, int b, int k) {
        double bElevadoK = Math.pow(b, k);

        if (a < bElevadoK) {
            return "Θ(n^" + k + ")";
        } else if (a == bElevadoK) {
            if (k == 0) {
                return "Θ(log n)";
            } else if (k == 1) {
                return "Θ(n * log(n))";
            } else {
                return "Θ(n^" + k + " * log(n))";
            }
        } else {
            double logaritmo = Math.log(a) / Math.log(b);
            if (logaritmo == Math.floor(logaritmo)) {
                return "Θ(n^" + (int) logaritmo + ")";
            } else {
                return "Θ(n^log_" + b + "(" + a + "))";
            }
        }
    }

    /**
     * Aplica la fórmula del Teorema Maestro por Reducción de Sustracción.
     * * @param a Coeficiente de llamadas recursivas.
     * @param b Factor de sustracción constante.
     * @param k Grado polinómico del costo.
     * @return La resolución de la complejidad en formato Theta.
     */
    private String resolverPorSustraccion(int a, int b, int k) {
        if (a == 1) {
            int nuevoGrado = k + 1;
            if (nuevoGrado == 1) {
                return "Θ(n)";
            } else {
                return "Θ(n^" + nuevoGrado + ")";
            }
        } else {
            String partePolinomica = "";
            if (k > 0) {
                if (k == 1) {
                    partePolinomica = "n * ";
                } else {
                    partePolinomica = "n^" + k + " * ";
                }
            }

            String parteExponencial;
            if (b == 1) {
                parteExponencial = a + "^n";
            } else {
                parteExponencial = a + "^(n/" + b + ")";
            }

            return "Θ(" + partePolinomica + parteExponencial + ")";
        }
    }
}
