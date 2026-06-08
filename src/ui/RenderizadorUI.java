package ui;

import java.awt.Graphics2D;

import ciudades.Ciudad;
import logica.AdministradorJuego;
import logica.EstadoJuego;

public class RenderizadorUI {

	// ATRIBUTOS
	private int screenWidth;
	private int screenHeight;
	
	private RenderizarMenu renderMenus;
	private GestorRecursos recursos;
	private AdministradorJuego admin;

	// CONSTRUCTOR
	/**
	 * Se crea el apartado en donde se decidira que va a renderizarse
	 * mediante el juego vaya avanzando
	 * @param screenWidth : ancho de la pantalla
	 * @param screenHeight : alto de la pantalla
	 */
	public RenderizadorUI(int screenWidth, int screenHeight, GestorRecursos recursos, AdministradorJuego admin) {
		setScreenWidth(screenWidth);
		setScreenHeight(screenHeight);
		setRecursos(recursos);
		setAdmin(admin);
		setRenderMenus(new RenderizarMenu(this.screenWidth, this.screenHeight, this.recursos, this.admin));
	}
	
	// METODOS
	
	/**
	 * Se renderiza el menu y demas segun el estado en el que se
	 * encuentre el juego.
	 * @param estado : estado del juego a renderizar
	 * @param g2
	 */
	public void renderizarPorEstado(EstadoJuego estado, Graphics2D g2) {
		switch (estado) {
			case MENU_PRINCIPAL:
				this.renderMenus.renderizarMenuPrincipal(g2);
				break;
			case MENU_INSTRUCCIONES:
				this.renderMenus.renderizarMenuInstrucciones(g2);
				break;
			case MENU_EN_PAUSA:
				this.renderMenus.renderizarMenuEnPausa(g2);
				break;
			case MAPA_GENERAL:
				this.renderMenus.renderizarMapaGeneral(g2);
				break;
			case FIN_DEL_JUEGO:
				this.renderMenus.renderizarFin(g2);
				break;
			default:
				break;
		}
	}

	// GETTERS
	
	public RenderizarMenu getRenderMenus() {
		return renderMenus;
	}

	// SETTERS

	private void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	private void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	private void setRenderMenus(RenderizarMenu renderMenus) {
		this.renderMenus = renderMenus;
	}

	private void setRecursos(GestorRecursos recursos) {
		this.recursos = recursos;
	}

	private void setAdmin(AdministradorJuego admin) {
		this.admin = admin;
	}

}
