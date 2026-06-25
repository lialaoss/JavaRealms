package render;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import ciudades.Ciudad;
import ui.ConfiguracionPantalla;
import ui.GestorRecursos;

public class FinMinijuegoPantalla {
    private BufferedImage fondoVictoria;
    private float[] particulasX;
    private float[] particulasY;
    private float[] particulasVel;
    private float[] particulasTamanio;
    private int[] particulasColor;
    private boolean particulasInicializadas = false;
    private int tickVictoria = 0;

    public void setFondoVictoria(BufferedImage fondo) {
        this.fondoVictoria = fondo;
    }
    
    private void inicializarParticulas() {
        int cantidad = 60;
        particulasX = new float[cantidad];
        particulasY = new float[cantidad];
        particulasVel = new float[cantidad];
        particulasTamanio = new float[cantidad];
        particulasColor = new int[cantidad];

        for (int i = 0; i < cantidad; i++) {
            particulasX[i] = (float)(Math.random() * ConfiguracionPantalla.SCREEN_WIDTH);
            particulasY[i] = (float)(Math.random() * ConfiguracionPantalla.SCREEN_HEIGHT);
            particulasVel[i] = (float)(1.5 + Math.random() * 2.5);
            particulasTamanio[i] = (float)(4 + Math.random() * 6);
            particulasColor[i] = (int)(Math.random() * 3);
        }

        particulasInicializadas = true;
    }

    public void mostrarResultados(Graphics2D g2, Ciudad ciudad) {
        if (!particulasInicializadas) {
            inicializarParticulas();
        }

        tickVictoria++;

        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, ConfiguracionPantalla.SCREEN_WIDTH, ConfiguracionPantalla.SCREEN_HEIGHT);

        if (fondoVictoria != null) {
            int anchoImg = ConfiguracionPantalla.SCREEN_WIDTH / 2;
            int altoImg = ConfiguracionPantalla.SCREEN_HEIGHT / 2;
            int xImg = (ConfiguracionPantalla.SCREEN_WIDTH - anchoImg) / 2;
            int yImg = (ConfiguracionPantalla.SCREEN_HEIGHT - altoImg) / 2 - 40;
            g2.drawImage(fondoVictoria, xImg, yImg, anchoImg, altoImg, null);
        }

        for (int i = 0; i < particulasX.length; i++) {
            particulasY[i] += particulasVel[i];
            particulasX[i] += (float)(Math.sin(tickVictoria * 0.05 + i) * 0.8);

            if (particulasY[i] > ConfiguracionPantalla.SCREEN_HEIGHT) {
                particulasY[i] = -10;
                particulasX[i] = (float)(Math.random() * ConfiguracionPantalla.SCREEN_WIDTH);
            }

            float brillo = (float)(Math.sin(tickVictoria * 0.1 + i) * 0.5 + 0.5);
            int alpha = (int)(150 + brillo * 100);
            int tamanio = (int)particulasTamanio[i];

            Color color;
            switch (particulasColor[i]) {
                case 0: color = new Color(255, 215, 0, alpha); break;
                case 1: color = new Color(255, 255, 150, alpha); break;
                default: color = new Color(200, 160, 0, alpha); break;
            }

            g2.setColor(color);
            g2.fillRect((int)particulasX[i], (int)particulasY[i], tamanio, tamanio);

            g2.setColor(new Color(255, 255, 200, alpha / 2));
            g2.fillRect((int)particulasX[i] - 1, (int)particulasY[i] - 1, tamanio + 2, tamanio + 2);
        }

        String texto = "Puntos de experiencia ganados : " + ciudad.getPuntosDeExperiencia() + " ptos !!!";
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics fm = g2.getFontMetrics();
        int x = (ConfiguracionPantalla.SCREEN_WIDTH - fm.stringWidth(texto)) / 2;
        int y = ConfiguracionPantalla.SCREEN_HEIGHT - 60;
        g2.drawString(texto, x, y);
    }
}
