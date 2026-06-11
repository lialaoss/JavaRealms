package ui;

import java.awt.Color;
import java.awt.Graphics2D;

public class NodoCiudad {
    private int x, y;
    private int size = 50;
    private int id;
    
    /**
     * Les ire poniendo sets y getters si necesito jj
     * @param x
     * @param y
     * @param id
     */
    public NodoCiudad(int x, int y, int id) {
    	this.x = x;
    	this.y = y;
    	setId(id);
    }

    public void dibujar(Graphics2D g2) {
		g2.setColor(Color.BLUE);
        g2.fillRect(x, y, size, size);
    }

    public boolean contiene(int mx, int my) {
        return mx >= x && mx <= x + size &&
               my >= y && my <= y + size;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
    
}
