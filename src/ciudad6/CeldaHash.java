package ciudad6;

/**
 * Representa cada casilla o nodo dentro de la tabla hash.
 * Almacena una clave de tipo String y un valor entero.
 */
public class CeldaHash {
    private String clave;
    private int valor;

    /**
     * Constructor de la celda hash.
     * * @pre La clave no debe ser nula ni vacía.
     * @post Se crea una instancia de CeldaHash con la clave y el valor especificados.
     */
    public CeldaHash(String clave, int valor) {
        this.clave = clave;
        this.valor = valor;
    }

    /**
     * @pre Ninguna.
     * @post Devuelve la clave almacenada en la celda.
     */
    public String getClave() {
        return this.clave;
    }

    /**
     * @pre Ninguna.
     * @post Devuelve el valor numérico almacenada en la celda.
     */
    public int getValor() {
        return this.valor;
    }
}