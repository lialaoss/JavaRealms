package ciudad9;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Gestiona a los enemigos activos mediante una Lista.
 */
public class ListaEnemigos {
    private final List<Personaje> enemigos;

    /**
     * Pre: Ninguna.
     * Post: Se inicializa una lista de enemigos vacía.
     */
    public ListaEnemigos() {
        this.enemigos = new ArrayList<>();
    }

    /**
     * Pre: 'enemigo' no debe ser nulo.
     * Post: El enemigo es agregado al final de la lista.
     */
    public void agregarEnemigo(Personaje enemigo) {
        if (enemigo == null) return; 
        this.enemigos.add(enemigo);
    }

    /**
     * Pre: La lista de enemigos debe estar inicializada.
     * Post: Todos los enemigos cuya vida sea <= 0 son removidos de la lista.
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

    /**
     * Pre: Ninguna.
     * Post: Retorna una copia de la lista de enemigos actuales.
     */
    public List<Personaje> obtenerEnemigos() {
        return new ArrayList<>(enemigos); 
    }

    /**
     * Pre: Ninguna.
     * Post: Retorna true si hay al menos un enemigo en la lista, false si está vacía.
     */
    public boolean quedanEnemigos() {
        return !enemigos.isEmpty();
    }

    /**
     * Pre: Ninguna.
     * Post: Retorna la cantidad entera de enemigos vivos en la lista.
     */
    public int cantidadEnemigos() {
        return enemigos.size();
    }
}