package render;

import java.awt.Color;
import java.awt.Graphics2D;

import modelo.ciudad1.Elemento;
import ui.ConfiguracionPantalla;

public class RenderElementos {
    private final int TILE_SIZE = ConfiguracionPantalla.TILE_SIZE;
    private int x, y, z;
    private Elemento elemento;
    private boolean recolectado;
    
    /**
     * @param x
     * @param y
     * @param elemento
     */
    public RenderElementos(int x, int y, int z, Elemento elemento) {
    	this.x = x;
    	this.y = y;
    	this.z = z;
    	setElemento(elemento);
    	this.recolectado = false;
    }

    public void dibujar(Graphics2D g2, int centroX, int centroY) {
		g2.setColor(Color.BLUE);
		
		int pantallaX = centroX + x * TILE_SIZE;
        int pantallaY = centroY + y * TILE_SIZE;

        g2.fillRect(pantallaX, pantallaY, TILE_SIZE, TILE_SIZE);
    }
    
    public void elementoEncontrado() {
    	this.recolectado = true;
    }
    
    public boolean getEncontrado() {
    	return recolectado;
    }

	public Elemento getElemento() {
		return elemento;
	}

	public void setElemento(Elemento elemento) {
		this.elemento = elemento;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
    
    
}
