package ui;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import logica.AdministradorJuego;
import logica.EstadoJuego;
import logica.KeyHandler;
import logica.MouseHandler;
import minijuego.Ciudad10Minijuego;
import minijuego.Ciudad1Minijuego;
import minijuego.Minijuego;

public class Panel extends JPanel implements Runnable {
	
	// CONSTANTES
	
	/**
	 * Configuracion de pantalla:
	 * originalTileSize : tamaño de tiles
	 * maxScreenCol : max cantidad de columnas para nuestra ventana (ancho)
	 * maxScreenRow : max cantidad de filas para nuestra ventana (altura)
	 * screenWidth : ancho de pantalla
	 * screenHeight : alto pantalla
	 */
	private final int originalTileSize = 16; // 16x16 tile
	private final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; // 48x48 tile
	public final int maxScreenCol = 24;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 1152 px
	public final int screenHeight = tileSize * maxScreenRow; // 576 px

	private final int FPS = 60; // FPS del juego dea

	// ATRIBUTOS
	private AdministradorJuego admin;
	private RenderizadorUI renderUI;
	private GestorRecursos recursos;
	
	private KeyHandler keyH = new KeyHandler();; // con esto detectamos el tecladoo jeje
	private MouseHandler mouseH; // con esto detectamos el mouse...
	
	private Thread gameThread; // un Thread es algo que se puede iniciar y detener
	// Una vez se inicia, el programa sigue funcionando hasta que lo detienes o se hace algo en particular jeje
	
	private boolean enTransicion = false;
	private float alpha = 0f;
	private EstadoJuego estadoDestino;

	// CONSTRUCTOR
	
	public Panel(AdministradorJuego admin){
		setAdmin(admin);
		setRecursos(admin.getRecursos());
		setRenderUI(new RenderizadorUI(this.screenWidth, this.screenHeight, this.recursos, this.admin));
		
		mouseH = new MouseHandler(this.admin, this.renderUI.getRenderMenus(), this);
		 
		this.setPreferredSize(new Dimension(this.screenWidth, this.screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.addMouseListener(mouseH);
		this.setFocusable(true);
	}
	

	// METODOS
	
	/**
	 * instanciamos el Thread
	 */
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start(); // llama al metodo run
	}
	
	/**
	 * jeje, actualiza la logica y los "dibujos" 60 veces por segundo (fps) dea
	 */
	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		long drawCount = 0;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				// me gusto tener esto para ver que el programa este actualizandose ahre
				System.out.println("FPS" + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	/**
	 * Se encarga de dibujaaar
	 */
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D) g;

	    g2.setColor(Color.RED);
	    g2.drawString("RENDERING: " + admin.getEstado(), 50, 50);

	    EstadoJuego estado = admin.getEstado();

	    if (estado == EstadoJuego.EN_PROGRESO) {
	        Minijuego juego = admin.getJuegoActual();
	        if (juego != null) {
	            juego.render(g2);
	        }
	    } else {
	        // ESTO FALTABA
	        renderUI.renderizarPorEstado(estado, g2);
	    }
	    
	    if (enTransicion) {
	        g2.setComposite(java.awt.AlphaComposite.getInstance(
	            java.awt.AlphaComposite.SRC_OVER, alpha));
	        g2.setColor(java.awt.Color.BLACK);
	        g2.fillRect(0, 0, screenWidth, screenHeight);
	        g2.setComposite(java.awt.AlphaComposite.getInstance(
	            java.awt.AlphaComposite.SRC_OVER, 1f));
	    }
	}
	
	/**
	 * podremos actualizar inforamcion del juegooo (creo q lo de abajo de admin le metere en otro lado
	 * y le hare update desde aqui jeje)
	 */
	public void update() {
		
		admin.update();
		
		if(admin.getEstado() == EstadoJuego.MENU_INSTRUCCIONES ||
				admin.getEstado() == EstadoJuego.MAPA_GENERAL) {
			if(keyH.QPressed == true) {
				
					admin.setEstado(EstadoJuego.MENU_PRINCIPAL);
					keyH.QPressed = false;
			}
		}
		
		
		if(admin.getEstado() == EstadoJuego.EN_PROGRESO) {
			if(keyH.QPressed == true) {
					
				admin.setEstado(EstadoJuego.MAPA_GENERAL);
				keyH.QPressed = false;
				admin.getJuegoActual().resultadoPartida();
				admin.limpiarJuegoActual();
			}
		}
		
		if (enTransicion) {
		    alpha += 0.05f;
		    if (alpha >= 1f) {
		        admin.setEstado(estadoDestino);
		        alpha = 0f;
		        enTransicion = false;
		    }
		    return;
		}
		
	    Minijuego juego = admin.getJuegoActual();
	        if(juego instanceof Ciudad1Minijuego) {
	            Ciudad1Minijuego c1 = (Ciudad1Minijuego) juego;
	            if(keyH.upPressed)    { c1.mover(0, -1, 0); keyH.upPressed = false; }
	            if(keyH.downPressed)  { c1.mover(0,  1, 0); keyH.downPressed = false; }
	            if(keyH.leftPressed)  { c1.mover(-1, 0, 0); keyH.leftPressed = false; }
	            if(keyH.rightPressed) { c1.mover( 1, 0, 0); keyH.rightPressed = false; }
	        }
	        
	        if(juego instanceof Ciudad10Minijuego) {
	    	    Ciudad10Minijuego c10 = (Ciudad10Minijuego) juego;
	    	    if(keyH.ultimoCaracter != 0) {
	    	        c10.procesarCaracter(keyH.ultimoCaracter);
	    	        keyH.ultimoCaracter = 0;
	    	    }
	    	}
	    }
	
	
	public void iniciarTransicion(EstadoJuego destino) {
	    this.estadoDestino = destino;
	    this.enTransicion = true;
	    this.alpha = 0f;
	}
	

	//SETTERS
	private void setAdmin(AdministradorJuego admin) {
		this.admin = admin;
	}


	private void setRenderUI(RenderizadorUI renderUI) {
		this.renderUI = renderUI;
	}


	private void setRecursos(GestorRecursos recursos) {
		this.recursos = recursos;
	}
	
	
}
