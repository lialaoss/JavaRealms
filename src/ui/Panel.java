package ui;

import java.awt.AlphaComposite;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import ciudades.EstadoCiudad;
import logica.AdministradorJuego;
import logica.DireccionJugador;
import logica.EstadoJuego;
import logica.KeyHandler;
import logica.MouseHandler;
import minijuego.Ciudad10Minijuego;
import minijuego.Ciudad1Minijuego;
import minijuego.Ciudad2Minijuego;
import minijuego.Ciudad7Minijuego;
import minijuego.Minijuego;

public class Panel extends JPanel implements Runnable {
	
	// CONSTANTES
	private final int FPS = ConfiguracionPantalla.FPS;

	// ATRIBUTOS
	public int screenWidth = ConfiguracionPantalla.SCREEN_WIDTH;
	public int screenHeight = ConfiguracionPantalla.SCREEN_HEIGHT;
	
	private AdministradorJuego admin;
	private RenderizadorUI renderUI;
	private GestorRecursos recursos;
	
	private KeyHandler keyH = new KeyHandler();
	private MouseHandler mouseH;
	
	private Thread gameThread;
	
	private boolean enTransicion = false;
	private float alpha = 0f;
	private EstadoJuego estadoDestino;

	// CONSTRUCTOR
	
	public Panel(AdministradorJuego admin){
		setAdmin(admin);
		setRecursos(admin.getRecursos());
		setRenderUI(new RenderizadorUI(this.recursos, this.admin));
		
		mouseH = new MouseHandler(this.admin, this.renderUI.getRenderMenus(), this);
		 
		this.setPreferredSize(new Dimension(this.screenWidth, this.screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.addMouseListener(mouseH);
		this.setFocusable(true);
	}
	

	// METODOS
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
			}
			
			if(timer >= 1000000000) {
				timer = 0;
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D) g;

	    g2.setColor(Color.RED);
	    g2.drawString("RENDERING: " + admin.getEstado(), 50, screenHeight - 20);

	    EstadoJuego estado = admin.getEstado();

	    if (estado == EstadoJuego.EN_PROGRESO) {
	        Minijuego juego = admin.getJuegoActual();
	        if (juego != null) {
	            juego.render(g2);
	        }
	    } else {
	        renderUI.renderizarPorEstado(estado, g2);
	    }
	    
	    if (enTransicion) {
	        g2.setComposite(AlphaComposite.getInstance(
	            AlphaComposite.SRC_OVER, alpha));
	        g2.setColor(Color.BLACK);
	        g2.fillRect(0, 0, screenWidth, screenHeight);
	        g2.setComposite(AlphaComposite.getInstance(
	            AlphaComposite.SRC_OVER, 1f));
	    }
	}
	
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
		        keyH.ultimoCaracter = 0;
		        keyH.enterPressed = false;
		        admin.getJuegoActual().resultadoPartida();
		        if(admin.getCiudadActual().getEstado() == EstadoCiudad.COMPLETADA) {
		        	admin.desbloquearVecinos(admin.getCiudadActual());
		        }
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
	            if(keyH.upPressed == true || keyH.downPressed == true 
	    				|| keyH.leftPressed == true || keyH.rightPressed == true) {
		            if(keyH.upPressed)    {
		            	c1.mover(0, -1, 0);
		            	admin.getJugador().setDireccion(DireccionJugador.UP);
		            	keyH.upPressed = false;
		            }
		            if(keyH.downPressed)  {
		            	c1.mover(0,  1, 0);
		            	admin.getJugador().setDireccion(DireccionJugador.DOWN);
		            	keyH.downPressed = false;
		            }
		            if(keyH.leftPressed)  {
		            	c1.mover(-1, 0, 0);
		            	admin.getJugador().setDireccion(DireccionJugador.LEFT);
		            	keyH.leftPressed = false;
		            }
		            if(keyH.rightPressed) {
		            	c1.mover( 1, 0, 0);
		            	admin.getJugador().setDireccion(DireccionJugador.RIGHT);
		            	keyH.rightPressed = false;
		            }
		            
					admin.getJugador().contadorSprite++;
					
					if(admin.getJugador().numeroDeSprite == 1) {
						admin.getJugador().numeroDeSprite = 2;
					} else if (admin.getJugador().numeroDeSprite == 2) {
						admin.getJugador().numeroDeSprite = 1;
					}
					admin.getJugador().contadorSprite = 0;
					
	            }

	        }
	        
	        if(juego instanceof Ciudad10Minijuego) {
	    	    Ciudad10Minijuego c10 = (Ciudad10Minijuego) juego;
	    	    if(keyH.ultimoCaracter != 0) {
	    	        c10.procesarCaracter(keyH.ultimoCaracter);
	    	        keyH.ultimoCaracter = 0;
	    	    }
	    	}
	        
	        if(juego instanceof Ciudad2Minijuego) {
	            Ciudad2Minijuego c2 = (Ciudad2Minijuego) juego;
	            if(keyH.ultimoCaracter != 0) {
	                c2.procesarCaracter(keyH.ultimoCaracter);
	                keyH.ultimoCaracter = 0;
	            }
	            if(keyH.enterPressed) {
	                c2.avanzarFrame();
	                keyH.enterPressed = false;
	            }
	        }
	        
	        if(juego instanceof Ciudad7Minijuego) {
	            Ciudad7Minijuego c7 = (Ciudad7Minijuego) juego;
	            if(keyH.ultimoCaracter != 0) {
	                c7.procesarCaracter(keyH.ultimoCaracter);
	                keyH.ultimoCaracter = 0;
	            }
	            if(keyH.enterPressed) {
	                c7.avanzarFrame();
	                keyH.enterPressed = false;
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
