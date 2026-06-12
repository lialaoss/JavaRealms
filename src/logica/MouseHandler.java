package logica;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ui.Panel;
import ui.RenderizarMenu;

public class MouseHandler extends MouseAdapter {
	
	private AdministradorJuego admin;
	private RenderizarMenu menu;
	private ui.Panel panel;
	
	public MouseHandler(AdministradorJuego admin, RenderizarMenu menu, Panel panel) {
	    this.admin = admin;
	    this.menu = menu;
	    this.panel = panel;
	}
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
	        default:
	            break;
	    }
	    
	    // Diria que podemos agregar en este switch solo aquellas ciudades q necesiten al cursor
	    if(admin.getJuegoActual() != null) {
		    switch (admin.getCiudadActual().getId()) {
		    	case 3:
		    	    admin.getJuegoActual().procesarClick(mouseX, mouseY);
		    	    break;
		    	case 4:
		    	    admin.getJuegoActual().procesarClick(mouseX, mouseY);
		    	    break;
		    	default:
		    		break;
		    }
	    }
	}
}