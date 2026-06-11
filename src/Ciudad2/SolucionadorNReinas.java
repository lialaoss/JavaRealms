package Ciudad2;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Servicio encargado de resolver el Problema de las N Reinas utilizando Backtracking.
 */
public class SolucionadorNReinas {

    /**
     * Inicia el proceso de resolución a partir de una reina inicial predefinida.
     * * @pre La dimensión debe ser >= 4. La reina inicial no debe ser nula y debe estar dentro del tablero.
     * @post Devuelve una lista con los estados del tablero. Si el último estado tiene N reinas, se halló solución.
     * @param dimension El tamaño N del tablero (N x N).
     * @param reinaInicial La pieza estática definida por el jugador.
     * @return El historial completo de estados del tablero (fotogramas).
     */
    public List<List<Reina>> resolverTablero(int dimension, Reina reinaInicial) {
        validarParametrosIniciales(dimension, reinaInicial);

        List<List<Reina>> historialPasos = new ArrayList<>();
        Stack<Reina> pilaReinas = new Stack<>();

        resolverFila(0, dimension, reinaInicial, pilaReinas, historialPasos);

        return historialPasos;
    }

    /**
     * Valida las precondiciones necesarias antes de iniciar el algoritmo.
     * * @pre Los parámetros no deben haber sido validados previamente en el flujo actual.
     * @post La ejecución continúa normalmente si los parámetros son válidos.
     * @param dimension El tamaño N del tablero.
     * @param reinaInicial El TDA Reina a validar.
     * @throws IllegalArgumentException Si la dimensión es < 4, la reina es nula o está fuera de límites.
     */
    private void validarParametrosIniciales(int dimension, Reina reinaInicial) {
        if (dimension < 4) {
            throw new IllegalArgumentException("Error: El tablero debe ser de al menos 4x4.");
        }
        if (reinaInicial == null) {
            throw new IllegalArgumentException("Error: Se requiere una Reina inicial válida.");
        }
        if (reinaInicial.getFila() >= dimension || reinaInicial.getColumna() >= dimension) {
            throw new IllegalArgumentException("Error: La Reina inicial está fuera de los límites.");
        }
    }

    /**
     * Método recursivo principal que recorre las filas del tablero.
     * @pre filaActual >= 0. Las estructuras de datos están inicializadas.
     * @post Evalúa la fila actual y avanza a la siguiente o retrocede según corresponda.
     * @param filaActual Nivel actual de la recursión (fila del tablero).
     * @param dimension Tamaño total del tablero.
     * @param reinaInicial Pieza inamovible definida por el jugador.
     * @param pilaReinas Estructura LIFO con las reinas colocadas exitosamente.
     * @param historial Colección donde se guarda cada estado temporal.
     * @return true si se logra completar el tablero; false en caso contrario.
     */
    private boolean resolverFila(int filaActual, int dimension, Reina reinaInicial, Stack<Reina> pilaReinas, List<List<Reina>> historial) {
        if (filaActual == dimension) {
            return true;
        }

        // Caso Especial: Es la fila bloqueada por el jugador
        if (filaActual == reinaInicial.getFila()) {
            return intentarColocarReina(reinaInicial, filaActual, dimension, reinaInicial, pilaReinas, historial);
        }

        // Caso Normal: Probamos las columnas de la fila actual
        for (int col = 0; col < dimension; col++) {
            Reina candidata = new Reina(filaActual, col);

            if (intentarColocarReina(candidata, filaActual, dimension, reinaInicial, pilaReinas, historial)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Subrutina de Backtracking que evalúa, apila, avanza y desapila una pieza específica.
     * @pre candidata no es nula.
     * @post Si la pieza lleva a una solución, devuelve true. Si no, restaura el estado y devuelve false.
     * @param candidata La reina que se intentará fijar en el tablero.
     * @param filaActual La fila que se está evaluando actualmente.
     * @param dimension El tamaño N del tablero.
     * @param reinaInicial La pieza original del jugador.
     * @param pilaReinas La pila actual de reinas consolidadas.
     * @param historial Colección para renderizar los fotogramas gráficos.
     * @return true si colocar esta candidata permitió resolver el resto del tablero.
     */
    private boolean intentarColocarReina(Reina candidata, int filaActual, int dimension, Reina reinaInicial, Stack<Reina> pilaReinas, List<List<Reina>> historial) {
        // Poda: Si la casilla es atacada, cortamos esta rama inmediatamente
        if (!esCasillaSegura(pilaReinas, candidata)) {
            return false;
        }

        // 1. Avanzar
        pilaReinas.push(candidata);
        guardarFotograma(pilaReinas, historial);

        // 2. Recursión hacia la siguiente fila
        if (resolverFila(filaActual + 1, dimension, reinaInicial, pilaReinas, historial)) {
            return true;
        }

        // 3. Retroceder (Backtrack)
        pilaReinas.pop();
        guardarFotograma(pilaReinas, historial);

        return false;
    }
    /**
     * Evalúa geométricamente si una nueva pieza colisiona con las ya colocadas.
     * * @pre pilaReinas y candidata no deben ser nulos.
     * @post Retorna el estado de seguridad de la casilla evaluada.
     * @param pilaReinas Conjunto de piezas fijadas en las filas superiores.
     * @param candidata La nueva pieza a insertar en el tablero.
     * @return true si la posición no interseca columnas ni diagonales de reinas anteriores; false si es atacada.
     */
    private boolean esCasillaSegura(Stack<Reina> pilaReinas, Reina candidata) {
        for (Reina reinaColocada : pilaReinas) {
            if (reinaColocada.getColumna() == candidata.getColumna()) {
                return false;
            }

            int distVertical = Math.abs(candidata.getFila() - reinaColocada.getFila());
            int distHorizontal = Math.abs(candidata.getColumna() - reinaColocada.getColumna());

            if (distVertical == distHorizontal) {
                return false;
            }
        }
        return true;
    }

    /**
     * Clona el estado actual de la pila para el renderizado visual paso a paso.
     * * @pre pilaReinas e historial no deben ser nulos.
     * @post Se añade una nueva instancia de ArrayList al historial con los elementos actuales de la pila.
     * @param pilaReinas El estado actual de las piezas en el tablero.
     * @param historial Colección a la que se añadirá la copia del estado.
     */
    private void guardarFotograma(Stack<Reina> pilaReinas, List<List<Reina>> historial) {
        historial.add(new ArrayList<>(pilaReinas));
    }
}
