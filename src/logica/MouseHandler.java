package logica;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ui.Panel;
import ui.RenderizarMenu;

public class MouseHandler extends MouseAdapter {
	
	private AdministradorJuego admin;
	private RenderizarMenu menu;
	private Panel panel;
	
	/*
	 * Pre: El administrador del juego, el menú y el panel deben estar inicializados (no ser nulos).
	 * Post: Crea el "escuchador" de mouse y guarda las referencias para poder interactuar con las pantallas y la lógica del juego.
	 */
	public MouseHandler(AdministradorJuego admin, RenderizarMenu menu, Panel panel) {
	    this.admin = admin;
	    this.menu = menu;
	    this.panel = panel;
	}
	
	/*
	 * Pre: El usuario hace un click con el mouse en alguna parte de la ventana.
	 * Post: Captura en qué posición exacta (X, Y) se hizo el click y, dependiendo de en qué pantalla estemos (menú, mapa o algún minijuego específico de la ciudad 3, 4 o 5), le avisa a la clase correspondiente para que active la acción.
	 */
	@Override
	public void mousePressed(MouseEvent e) {

	    int mouseX = e.getX();
	    int mouseY = e.getY();

	    switch (admin.getEstado()) {
	        case MENU_PRINCIPAL:
	        	menu.procesarClickMenuConTransicion(mouseX, mouseY, panel);
	            break;
	        case MAPA_GENERAL:
	            menu.procesarClickMapa(mouseX, mouseY);
	            break;
	        case FIN_DEL_JUEGO:
	            menu.procesarClickFinal(mouseX, mouseY);
	        default:
	            break;
	    }
	    
	    if(admin.getJuegoActual() != null) {
		    switch (admin.getCiudadActual().getId()) {
		    	case 3:
		    	    admin.getJuegoActual().procesarClick(mouseX, mouseY);
		    	    break;
		    	case 4:
		    	    admin.getJuegoActual().procesarClick(mouseX, mouseY);
		    	    break;
		    	case 5:
		    	    admin.getJuegoActual().procesarClick(mouseX, mouseY);
		    	    break;
		    	case 6:
		    		admin.getJuegoActual().procesarClick(mouseX, mouseY);
		    		break;
		    	case 9:
		    	    admin.getJuegoActual().procesarClick(mouseX, mouseY);
		    	    break;
		    	default:
		    		break;
		    }
	    }
	}
}