package ciudad9;

/*
  Representa una acción que puede realizar un personaje.
 */
public class Accion {
    public static final String ATAQUE = "ATAQUE";
    public static final String DEFENSA = "DEFENSA";
    public static final String HABILIDAD = "HABILIDAD";

    private final String tipo;

    /*
      Pre: 'tipo' debe ser una constante válida.
      Post: Se crea una nueva acción con el tipo especificado.
     */
    public Accion(String tipo) {
        this.tipo = ValidacionesUtiles.esNulo(tipo) ? DEFENSA : tipo;
    }

    /*
      Pre: Ninguna.
      Post: Devuelve el tipo de la acción.
     */
    public String getTipo() {
        return tipo;
    }
}