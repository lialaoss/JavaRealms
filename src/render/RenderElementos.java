package render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import modelo.ciudad1.Elemento;
import ui.ConfiguracionPantalla;

public class RenderElementos {
    private final int TILE_SIZE = ConfiguracionPantalla.TILE_SIZE;
    private int x, y, z;
    private Elemento elemento;
    private boolean recolectado;
    private Image imagen;
    
    public RenderElementos(int x, int y, int z, Elemento elemento, Image imagen) {
    	this.x = x;
    	this.y = y;
    	this.z = z;
    	setElemento(elemento);
    	setImagen(imagen);
    	this.recolectado = false;
    }

    public void dibujar(Graphics2D g2, int centroX, int centroY) {
    	if (imagen != null) {
    		int pantallaX = centroX + x * TILE_SIZE;
            int pantallaY = centroY + y * TILE_SIZE;
            g2.drawImage(imagen, pantallaX, pantallaY, TILE_SIZE, TILE_SIZE, null);
        } else {
            g2.setColor(Color.GRAY);
            g2.fillRect(x, y, 80, 80);
        }
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
	
	private void setImagen(Image imagen) {
		this.imagen = imagen;
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
