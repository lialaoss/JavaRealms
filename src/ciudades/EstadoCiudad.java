package ciudades;

/*
 *  Pre: Ninguna.
 * Post: Define los cuatro estados posibles que puede tener una ciudad en el mapa durante la partida.
 */
public enum EstadoCiudad {
	DESBLOQUEADA, // El jugador ya puede entrar a jugar en esta ciudad.
	BLOQUEADA,    // Aún no se descubrió el camino o no se puede acceder.
	COMPLETADA,   // El jugador ya superó el desafío de esta ciudad.
	PERDIDA       // El jugador intentó el desafío pero fracasó.
}