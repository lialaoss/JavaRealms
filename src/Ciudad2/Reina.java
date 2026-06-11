package Ciudad2;

import java.util.Objects;

/**
 * TDA Reina.
 * Representa de forma inmutable la posición de una pieza en el tablero.
 */
public class Reina {
    private final int fila;
    private final int columna;

    /**
     * Construye una nueva Reina validando sus coordenadas.
     * @pre La fila y columna deben ser números naturales (>= 0).
     * @post Se crea una instancia inmutable de la Reina.
     * @param fila Posición en el eje Y (0 a N-1).
     * @param columna Posición en el eje X (0 a N-1).
     * @throws IllegalArgumentException Si alguna coordenada es negativa.
     */
    public Reina(int fila, int columna) {
     validarCoordenadas(fila, columna);
     this.fila = fila;
     this.columna = columna;
     }

    /**
     * Valida que las coordenadas ingresadas pertenezcan al dominio de los números naturales.
     */
    private void validarCoordenadas(int fila, int columna) {
        if (fila < 0 || columna < 0) {
            throw new IllegalArgumentException("Error: Las coordenadas de la reina no pueden ser negativas.");
        }
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reina reina = (Reina) obj;
        return fila == reina.fila && columna == reina.columna;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fila, columna);
    }
}
