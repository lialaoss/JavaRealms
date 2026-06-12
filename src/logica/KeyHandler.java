package logica;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import ui.RenderizarMenu;

public class KeyHandler implements KeyListener {

	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean QPressed;
	public char ultimoCaracter = 0;
	public boolean enterPressed = false;

	@Override
	public void keyTyped(KeyEvent e) {
		ultimoCaracter = e.getKeyChar();
		
	}

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
