package modelo;

/**
 * Interfaz que define el contrato para observar los cambios en la Ciudad 1 (Recolección).
 * Post: permite desacoplar la lógica del mapa 3D y la partida de su representación visual.
 */
public interface ObservadorRecoleccion {

    /**
     * Pre: partida no es nula.
     * Post: notifica a la vista que el estado del jugador (posición o radio de visión) 
     * ha cambiado para que se redibuje el mapa y la niebla.
     * @param partida El estado actual de la partida en la Ciudad 1.
     */
    void actualizarVista(Partida partida);

    /**
     * Pre: el elemento no es nulo.
     * Post: avisa a la interfaz que se ha recolectado un objeto para mostrar 
     * un mensaje o actualizar el panel de la mochila.
     * @param item El elemento que ha sido recogido del mapa.
     */
    void objetoRecolectado(Elemento item);
}