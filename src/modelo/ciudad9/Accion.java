package modelo.ciudad9;

/**
 * Representa una acción que puede realizar un personaje.
 */
public class Accion {
    public static final String ATAQUE = "ATAQUE";
    public static final String DEFENSA = "DEFENSA";
    public static final String HABILIDAD = "HABILIDAD";

    private final String tipo;

    /**
     * Pre: El parámetro 'tipo' debe ser una de las constantes definidas en la clase.
     * Post: Se crea una nueva Accion con el tipo especificado.
     */
    public Accion(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Pre: Ninguna.
     * Post: Retorna el tipo de la acción.
     */
    public String getTipo() {
        return tipo;
    }
}
