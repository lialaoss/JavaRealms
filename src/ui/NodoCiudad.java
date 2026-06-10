package ui;

import java.awt.Color;
import java.awt.Graphics2D;

public class NodoCiudad {
    private int x, y;
    private int ancho = 80;
    private int alto = 80;
    private int id;
    private java.awt.Image imagen;
    private String nombre;

    public NodoCiudad(int x, int y, int id, java.awt.Image imagen, String nombre) {
        this.x = x;
        this.y = y;
        setId(id);
        this.imagen = imagen;
        this.nombre = nombre;
    }

    public void dibujar(Graphics2D g2) {
        if (imagen != null) {
            int imgAncho = imagen.getWidth(null);
            int imgAlto = imagen.getHeight(null);
            // escalar manteniendo proporción dentro de un tamaño máximo
            int maxSize = 80;
            int drawAncho, drawAlto;
            if (imgAncho > imgAlto) {
                drawAncho = maxSize;
                drawAlto = (imgAlto * maxSize) / imgAncho;
            } else {
                drawAlto = maxSize;
                drawAncho = (imgAncho * maxSize) / imgAlto;
            }
            g2.drawImage(imagen, x, y, drawAncho, drawAlto, null);
        } else {
            g2.setColor(Color.BLUE);
            g2.fillRect(x, y, 80, 80);
        }
        
        if (nombre != null) {
            g2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
            java.awt.FontMetrics fm = g2.getFontMetrics();
            int tx = x + (80 - fm.stringWidth(nombre)) / 2;
            // sombra
            g2.setColor(java.awt.Color.BLACK);
            g2.drawString(nombre, tx + 1, y + 96);
            // texto
            g2.setColor(new java.awt.Color(255, 255, 255));
            g2.drawString(nombre, tx, y + 95);
        }
    }

    public boolean contiene(int mx, int my) {
        return mx >= x && mx <= x + ancho &&
               my >= y && my <= y + alto;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
    
}
