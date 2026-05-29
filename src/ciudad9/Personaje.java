package ciudad9;
/**
 * Representa un participante del combate.
 */
public class Personaje {

    private String nombre;
    private int vida;
    private int ataque;
    private boolean defendiendo;

    public Personaje(String nombre, int vida, int ataque) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataque = ataque;
        this.defendiendo = false;
    }

    public String getNombre() {
        return nombre;
    }

    public int getVida() {
        return vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    /**
     * Activa el estado de defensa.
     */
    public void activarDefensa() {
        defendiendo = true;
    }

    /**
     * Recibe daño.
     */
    public void recibirDanio(int danio) {

        if (defendiendo) {
            danio /= 2;
            defendiendo = false;
        }

        vida -= danio;

        if (vida < 0) {
            vida = 0;
        }
    }

    /**
     * Recupera puntos de vida.
     */
    public void curar(int puntos) {
        vida += puntos;
    }
}