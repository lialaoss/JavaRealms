package ciudad9;

/**
 * Modela las entidades que participan en el combate (Jugador y Enemigos).
 */
public class Personaje {
    private static final int VIDA_MAXIMA_JUGADOR = 100;

    private final String nombre;
    private int vida;
    private final int ataque;
    private boolean defendiendo;
    private final boolean esJugador;

    /**
     * Pre: 'nombre' no debe ser nulo o vacío. 'vida' y 'ataque' deben ser valores positivos.
     * Post: Se crea un personaje con los atributos especificados. Si la vida es menor a 0, se inicializa en 0.
     */
    public Personaje(String nombre, int vida, int ataque, boolean esJugador) {
        this.nombre = nombre;
        this.vida = Math.max(0, vida);
        this.ataque = ataque;
        this.esJugador = esJugador;
        this.defendiendo = false;
    }

    public String getNombre() { return nombre; }
    public int getVida() { return vida; }
    public int getAtaque() { return ataque; }
    
    /**
     * Pre: Ninguna.
     * Post: Retorna true si la vida del personaje es mayor a 0, false en caso contrario.
     */
    public boolean estaVivo() { return vida > 0; }

    /**
     * Pre: El personaje debe estar vivo.
     * Post: El estado 'defendiendo' cambia a true, lo que anulará el próximo daño recibido.
     */
    public void activarDefensa() {
        this.defendiendo = true;
    }

    /**
     * Pre: 'danio' debe ser un valor entero positivo.
     * Post: La vida del personaje se reduce según el daño. Si estaba defendiendo, el daño se reduce a la mitad y se desactiva la defensa. La vida nunca será menor a 0.
     */
    public void recibirDanio(int danio) {
        if (defendiendo) {
            danio /= 2;
            defendiendo = false;
        }
        this.vida = Math.max(0, this.vida - danio);
    }

    /**
     * Pre: 'puntos' debe ser un valor entero positivo.
     * Post: La vida del personaje aumenta en la cantidad de puntos. Si es el jugador, no superará la VIDA_MAXIMA_JUGADOR.
     */
    public void curar(int puntos) {
        this.vida += puntos;
        if (esJugador && this.vida > VIDA_MAXIMA_JUGADOR) {
            this.vida = VIDA_MAXIMA_JUGADOR;
        }
    }
}