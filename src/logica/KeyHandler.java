package logica;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import ui.RenderizarMenu;

public class KeyHandler implements KeyListener {

	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean QPressed;
	public char ultimoCaracter = 0;
	public boolean enterPressed = false;

	/* 
	 * Pre: El usuario presiona y suelta una tecla que genera un texto o símbolo.
	 * Post: Guarda en la variable el último carácter exacto que se tecleó.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		ultimoCaracter = e.getKeyChar();
		
	}

	/*
	 * Pre: El usuario presiona una tecla del teclado.
	 * Post: Identifica si la tecla apretada es W, A, S, D, Q o Enter, y pone su respectiva variable en true para que el juego sepa que la acción está activa.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_ENTER) { enterPressed = true; }
	
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;	
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if(code == KeyEvent.VK_Q) {
			QPressed = true;
		}
	}

	/* 
	 * Pre: El usuario suelta una tecla que estaba apretando.
	 * Post: Identifica qué tecla se soltó y vuelve a poner su variable en false, avisando al juego que se detenga el movimiento o la acción.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;	
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}

		if(code == KeyEvent.VK_Q) {
			QPressed = false;
		}
	}

}