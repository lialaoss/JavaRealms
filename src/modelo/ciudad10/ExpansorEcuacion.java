package modelo.ciudad10;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase funcional encargada de aplicar el método de expansión (iteración)
 * sobre una ecuación de recurrencia para mostrar el paso a paso matemático.
 * Utiliza el patrón de responsabilidad única delegando tareas a métodos auxiliares (helpers).
 */
public class ExpansorEcuacion {

    /**
     * Expande la ecuación nivel por nivel sustituyendo la llamada recursiva.
     * @pre La ecuación ingresada no debe ser nula y la cantidad de niveles debe ser mayor o igual a 1.
     * @post Se instancia y devuelve una lista dinámica que contiene exactamente un renglón matemático por cada nivel solicitado.
     * @param ecuacion El TDA EcuacionRecurrencia con los coeficientes originales a expandir.
     * @param niveles La cantidad de pasos de expansión iterativa a generar.
     * @return List de Strings con la matemática resuelta paso a paso, lista para la interfaz gráfica.
     * @throws IllegalArgumentException Si la ecuación es nula o la cantidad de niveles es menor a 1.
     */
    public List<String> expandirPasoAPaso(EcuacionRecurrencia ecuacion, int niveles) {
        if (ecuacion == null) {
            throw new IllegalArgumentException("Error: La ecuación a expandir no puede ser nula.");
        }
        if (niveles < 1) {
            throw new IllegalArgumentException("Error: Se debe expandir al menos 1 nivel.");
        }

        List<String> pasos = new ArrayList<>();
        generarPasos(ecuacion, pasos, niveles);

        return pasos;
    }

    /**
     * Orquesta el flujo de la expansión iterativa, controlando exclusivamente el ciclo de niveles.
     * @pre La lista dinámica 'pasos' debe estar inicializada y vacía. Los parámetros de ecuación y niveles deben estar validados.
     * @post La lista 'pasos' se poblará de forma secuencial con las cadenas de texto correspondientes a cada nivel expandido.
     * @param ecuacion El TDA EcuacionRecurrencia.
     * @param pasos Estructura de datos dinámica donde se irán guardando los resultados mutados.
     * @param niveles Cantidad total de iteraciones algebraicas a realizar.
     */
    private void generarPasos(EcuacionRecurrencia ecuacion, List<String> pasos, int niveles) {
        StringBuilder acumuladorCostos = new StringBuilder();

        for (int nivelActual = 1; nivelActual <= niveles; nivelActual++) {
            String costoDesprendido = extraerCostoNivel(ecuacion, nivelActual);

            if (nivelActual == 1) {
                acumuladorCostos.append(costoDesprendido);
            } else {
                acumuladorCostos.append(" + ").append(costoDesprendido);
            }

            String renglonCompletado = armarRenglon(ecuacion, nivelActual, acumuladorCostos.toString());
            pasos.add(renglonCompletado);
        }
    }

    /**
     * Calcula y formatea algebraicamente el costo que se "desprende" de la recursión en un nivel específico.
     * @pre El parámetro 'nivelActual' debe ser estrictamente mayor o igual a 1.
     * @post Devuelve una cadena de texto puramente matemática representando el costo polinómico aislado de ese nivel.
     * @param ecuacion El TDA EcuacionRecurrencia con los parámetros matemáticos base.
     * @param nivelActual El nivel de iteración actual utilizado para calcular los exponentes correctos.
     * @return Cadena de texto con el costo algebraico calculado.
     */
    private String extraerCostoNivel(EcuacionRecurrencia ecuacion, int nivelActual) {
        int parametroAAnterior = (int) Math.pow(ecuacion.getParametroA(), nivelActual - 1);

        if (ecuacion.getTipo() == EcuacionRecurrencia.TipoEcuacion.DIVISION) {
            int parametroBAnterior = (int) Math.pow(ecuacion.getParametroB(), nivelActual - 1);
            String baseN = (parametroBAnterior == 1) ? "n" : "(n/" + parametroBAnterior + ")";
            return ensamblarTerminoPolinomico(parametroAAnterior, baseN, ecuacion.getGradoK());
        } else {
            int restaAnterior = (nivelActual - 1) * ecuacion.getParametroB();
            String baseN = (restaAnterior == 0) ? "n" : "(n-" + restaAnterior + ")";
            return ensamblarTerminoPolinomico(parametroAAnterior, baseN, ecuacion.getGradoK());
        }
    }

    /**
     * Ensambla la estructura visual final del renglón, combinando el llamado recursivo con los costos acumulados.
     * @pre El parámetro 'costosAcumulados' no debe ser nulo.
     * @post Devuelve el renglón matemático completamente ensamblado y listo para mostrar al usuario.
     * @param ecuacion El TDA EcuacionRecurrencia con los parámetros base.
     * @param nivelActual El número de paso en el que se encuentra actualmente la expansión.
     * @param costosAcumulados La sumatoria en formato String de los costos extraídos en las iteraciones previas.
     * @return String con el formato estructural "Nivel X: T(n) = [TerminoRecursivo] + [Costos]".
     */
    private String armarRenglon(EcuacionRecurrencia ecuacion, int nivelActual, String costosAcumulados) {
        int aElevado = (int) Math.pow(ecuacion.getParametroA(), nivelActual);
        String terminoA = (aElevado == 1) ? "" : String.valueOf(aElevado);

        String llamadoRecursivo;
        if (ecuacion.getTipo() == EcuacionRecurrencia.TipoEcuacion.DIVISION) {
            int bElevado = (int) Math.pow(ecuacion.getParametroB(), nivelActual);
            llamadoRecursivo = "T(n/" + bElevado + ")";
        } else {
            int restaActual = nivelActual * ecuacion.getParametroB();
            llamadoRecursivo = "T(n-" + restaActual + ")";
        }

        return "Nivel " + nivelActual + ": T(n) = " + terminoA + llamadoRecursivo + " + " + costosAcumulados;
    }

    /**
     * Helper que ensambla las partes del polinomio asintótico evitando la duplicación de código (Principio DRY).
     * @pre El coeficiente 'parametroAAnterior' debe ser >= 1, 'baseN' no debe ser nula y 'gradoK' >= 0.
     * @post Se devuelve el término algebraico unificado filtrando partes visualmente redundantes (como coeficientes "1" o exponentes "^1").
     * @param parametroAAnterior El coeficiente multiplicador numérico del término.
     * @param baseN La representación en texto de cómo se divide o resta a la variable 'n'.
     * @param gradoK El grado polinómico de la función original.
     * @return String formateado con la parte asintótica limpia.
     */
    private String ensamblarTerminoPolinomico(int parametroAAnterior, String baseN, int gradoK) {
        String coeficiente = (parametroAAnterior == 1) ? "" : String.valueOf(parametroAAnterior);
        String exponente = (gradoK == 0) ? "1" : (gradoK == 1 ? baseN : baseN + "^" + gradoK);

        if (gradoK == 0) {
            return (parametroAAnterior == 1) ? "1" : String.valueOf(parametroAAnterior);
        }
        return coeficiente + exponente;
    }
}
