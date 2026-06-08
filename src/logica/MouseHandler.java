package logica;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ui.RenderizarMenu;

public class MouseHandler extends MouseAdapter {
	
	private AdministradorJuego admin;
	private RenderizarMenu menu;

	public MouseHandler(AdministradorJuego admin, RenderizarMenu menu) {
	    this.admin = admin;
	    this.menu = menu;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {

	    int mouseX = e.getX();
	    int mouseY = e.getY();

	    switch (admin.getEstado()) {
	        case MENU_PRINCIPAL:
	            menu.procesarClickMenu(mouseX, mouseY, admin);
	            break;
	        case MAPA_GENERAL:
	            menu.procesarClickMapa(mouseX, mouseY, admin);
	            break;
	        // ...
	        default:
	            break;
	    }
	}
}