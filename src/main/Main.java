package main;

import logica.AdministradorJuego;

public class Main {

	/* 
	 * Pre: Ninguna.
	 * Post: Es el punto de arranque del programa. Crea el administrador general (que a su vez carga los archivos y los datos) y le da la orden de iniciar el juego para que aparezca la ventana en pantalla.
	 */
	public static void main(String[] args) {
		
		AdministradorJuego admin = new AdministradorJuego();
		admin.iniciarJuego();

	}

}