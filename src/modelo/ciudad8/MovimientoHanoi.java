package modelo.ciudad8;

/**
 * Representa un movimiento individual de un disco dentro del juego.
 */
public class MovimientoHanoi {
    private int disco;
    private char origen;
    private char destino;

    /**
     * @pre El número de disco debe ser mayor a 0.
     * @post Se crea un registro de movimiento de un disco desde un origen a un destino.
     */
    public MovimientoHanoi(int disco, char origen, char destino) {
        this.disco = disco;
        this.origen = origen;
        this.destino = destino;
    }

    public int getDisco() {
        return this.disco;
    }

    public char getOrigen() {
        return this.origen;
    }

    public char getDestino() {
        return this.destino;
    }
}