package ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BotonMenu {

    private int x;
    private int y;
    private int width;
    private int height;

    private BufferedImage imagen;

    /**
     * 
     * @param imagen
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public BotonMenu(
            BufferedImage imagen,
            int x,
            int y,
            int width,
            int height) {

        this.imagen = imagen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void dibujar(Graphics2D g2) {
        g2.drawImage(
                imagen,
                x,
                y,
                width,
                height,
                null);
    }

    public boolean contiene(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width &&
               mouseY >= y && mouseY <= y + height;
    }

}