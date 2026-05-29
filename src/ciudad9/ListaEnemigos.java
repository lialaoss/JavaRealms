package ciudad9;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Gestiona los enemigos activos del combate.
 */
public class ListaEnemigos {

    private List<Personaje> enemigos;

    public ListaEnemigos() {
        enemigos = new ArrayList<>();
    }

    /**
     * Agrega un enemigo.
     */
    public void agregarEnemigo(Personaje enemigo) {

        if (enemigo != null) {
            enemigos.add(enemigo);
        }
    }

    /**
     * Elimina enemigos derrotados.
     */
    public void eliminarDerrotados() {

        Iterator<Personaje> iterator = enemigos.iterator();

        while (iterator.hasNext()) {

            Personaje enemigo = iterator.next();

            if (!enemigo.estaVivo()) {
                iterator.remove();
            }
        }
    }

    public List<Personaje> obtenerEnemigos() {
        return enemigos;
    }

    public boolean quedanEnemigos() {
        return !enemigos.isEmpty();
    }
}