package ciudad9;
/**
 * Representa una acción del jugador.
 */
public class Accion {

    private String tipo;

    public Accion(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}