package modelo.ciudad1;

import logica.DireccionJugador;
import utiles.ValidacionesUtiles;


/**
 * TDA que representa los datos basicos de un jugador.
 * Post: define la identidad del usuario de manera independiente a la partida.
 */

public class Jugador {

    private String nombre;
    
    /**
     * Pre: el nombre no debe ser nulo ni estar vacio.
     * Post: inicializa al jugador con el nombre indicado.
     * @param nombre Nombre identificatorio del jugador.
     */

    public Jugador(String nombre) {
        ValidacionesUtiles.validarNoNulo(nombre, "El nombre del jugador no puede ser nulo");
        if (nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        this.nombre = nombre;
    }
    
    
    /**
     * Post: devuelve el nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    
    /**
     * Pre: el nuevo nombre no debe ser nulo ni vacio.
     * Post: actualiza el nombre del jugador.
     */
    public void setNombre(String nombre) {
        ValidacionesUtiles.validarNoNulo(nombre, "El nombre no puede ser nulo");
        if (nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        this.nombre = nombre;
    }

	@Override
    public String toString() {
        return "Jugador: " + nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Jugador other = (Jugador) obj;
        return nombre.equals(other.nombre);
    }
}