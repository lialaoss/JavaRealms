package modelo.ciudad9;

import java.util.Vector;

import utiles.ValidacionesUtiles;

/*
  Gestiona la colección de enemigos activos usando un Vector (Regla UBASOFT).
 */
public class ListaEnemigos {
    private final Vector<Personaje> enemigos;

    /*
      Pre: Ninguna.
      Post: Se inicializa un vector vacío para almacenar los enemigos.
     */
    public ListaEnemigos() {
        this.enemigos = new Vector<>();
    }

    /*
      Pre: 'enemigo' no debe ser nulo.
      Post: El enemigo se añade al final de la colección.
     */
    public void agregarEnemigo(Personaje enemigo) {
        if (ValidacionesUtiles.esNulo(enemigo)) return; 
        this.enemigos.add(enemigo);
    }

    /*
     Pre: La lista debe estar inicializada.
     Post: Remueve a todos los enemigos con vida 0 utilizando Stream API (removeIf).
     */
    public void eliminarDerrotados() {
        this.enemigos.removeIf(enemigo -> !enemigo.estaVivo());
    }

    /*
     Pre: Ninguna.
     Post: Devuelve una copia del vector de enemigos actuales para mantener el encapsulamiento.
     */
    public Vector<Personaje> obtenerEnemigos() {
        return new Vector<>(enemigos); 
    }

    /*
     Pre: Ninguna.
     Post: Devuelve true si hay al menos un enemigo en el vector.
     */
    public boolean quedanEnemigos() {
        return !enemigos.isEmpty();
    }

    /*
     Pre: Ninguna.
     Post: Devuelve la cantidad entera de enemigos en el vector.
     */
    public int cantidadEnemigos() {
        return enemigos.size();
    }
}