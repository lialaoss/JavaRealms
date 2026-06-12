package ciudad9;

/*
  Administra al Jugador y a los Enemigos.
 */
public class Personaje {
    private static final int VIDA_MAXIMA_JUGADOR = 150;

    private final String nombre;
    private int vida;
    private final int ataque;
    private boolean defendiendo;
    private final boolean esJugador;

    /*
      Pre: 'nombre' no debe ser nulo. 'vida' y 'ataque' deben ser positivos.
      Post: Se crea un personaje. Los valores negativos de vida o ataque se ajustan a 0.
     */
    public Personaje(String nombre, int vida, int ataque, boolean esJugador) {
        this.nombre = nombre;
        this.vida = ValidacionesUtiles.asegurarPositivo(vida);
        this.ataque = ValidacionesUtiles.asegurarPositivo(ataque);
        this.esJugador = esJugador;
        this.defendiendo = false;
    }

    /*
      Pre: Ninguna.
      Post: Devuelve el nombre del personaje.
     */
    public String getNombre() { return nombre; }

    /*
      Pre: Ninguna.
      Post: Devuelve la vida actual del personaje.
     */
    public int getVida() { return vida; }

    /*
      Pre: Ninguna.
      Post: Devuelve el valor de ataque del personaje.
     */
    public int getAtaque() { return ataque; }
    
    /*
      Pre: Ninguna.
      Post: Devuelve true si la vida del personaje es mayor a 0, false si es 0.
     */
    public boolean estaVivo() { return vida > 0; }

    /*
      Pre: El personaje debe estar vivo.
      Post: El estado 'defendiendo' cambia a true para mitigar el próximo daño.
     */
    public void activarDefensa() {
        this.defendiendo = true;
    }

    /*
      Pre: 'danio' debe ser un valor positivo.
      Post: Reduce la vida del personaje. Si estaba defendiendo, el daño se reduce a la mitad. La vida nunca baja de 0.
     */
    public void recibirDanio(int danio) {
        if (defendiendo) {
            danio /= 2;
            defendiendo = false;
        }
        this.vida = ValidacionesUtiles.asegurarPositivo(this.vida - danio);
    }

    /*
      Pre: 'puntos' debe ser un valor positivo.
      Post: Aumenta la vida del personaje. Si es el jugador, se topa en VIDA_MAXIMA_JUGADOR.
     */
    public void curar(int puntos) {
        this.vida += puntos;
        if (esJugador && this.vida > VIDA_MAXIMA_JUGADOR) {
            this.vida = VIDA_MAXIMA_JUGADOR;
        }
    }
}