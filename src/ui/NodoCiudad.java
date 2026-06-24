package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Graphics2D;

import ciudades.Ciudad;
import utiles.ValidacionesUtiles;

public class NodoCiudad {
    private int x, y;
    private int ancho = 80;
    private int alto = 80;
    private int id;
    private Image imagen;
    private String nombre;

    public NodoCiudad(int x, int y, Image imagen, Ciudad ciudad) {
        this.x = x;
        this.y = y;
        setImagen(imagen);
        setId(ciudad.getId());
        this.nombre = ciudad.getNombre();
    }

    public void dibujar(Graphics2D g2) {
        if (imagen != null) {
            int imgAncho = imagen.getWidth(null);
            int imgAlto = imagen.getHeight(null);

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
        	imprimirTexto(g2, nombre);
        }
    }
    
    private void imprimirTexto(Graphics2D g2, String texto) {
    	g2.setFont(new Font("Arial", Font.BOLD, 18));
        FontMetrics fm = g2.getFontMetrics();
        int tx = x + (80 - fm.stringWidth(texto)) / 2;

        g2.setColor(Color.BLACK);
        g2.drawString(texto, tx + 1, y + 96);

        g2.setColor(new Color(255, 255, 255));
        g2.drawString(texto, tx, y + 96);
    }

    public boolean contiene(int mx, int my) {
        return mx >= x && mx <= x + ancho &&
               my >= y && my <= y + alto;
    }

	public int getId() {
		return id;
	}

	private void setId(int id) {
		ValidacionesUtiles.validarMayorACero(id, "id");
		this.id = id;
	}
    
    private void setImagen(Image imagen) {
    	ValidacionesUtiles.validarNoNulo(imagen, "imagen");
    	this.imagen = imagen;
    }
}
