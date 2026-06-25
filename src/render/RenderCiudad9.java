package render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import ciudades.Ciudad;
import ui.ConfiguracionPantalla;
import modelo.ciudad9.GestorRecursosCiudad9; // Importación de tu nuevo gestor local

public class RenderCiudad9 {
    
    private GestorRecursosCiudad9 recursosLocales;

    /*
     * Pre: El gestor local no debe ser nulo y debe haber invocado su carga de texturas.
     * Post: Instancia el motor de renderizado vinculando el gestor específico de la Ciudad 9.
     */
    public RenderCiudad9(GestorRecursosCiudad9 recursosLocales) {
        this.recursosLocales = recursosLocales;
    }

    /*
     * Pre: Los componentes gráficos no deben ser nulos. 'fondoElegido' debe ser un índice válido (0 a 3).
     * Post: Dibuja el fondo pixel art consumido del gestor local de la Ciudad 9 seleccionado al azar.
     */
    public void dibujar(Graphics2D g2, Ciudad ciudad, FinMinijuegoPantalla pantallaFinal, boolean ganado, int fondoElegido) {
        
        // Dibujamos el fondo que nos mandó el minijuego desde el gestor local
        if (recursosLocales.getFondosCiudad9() != null && recursosLocales.getFondosCiudad9().length > fondoElegido) {
            BufferedImage fondoBatalla = recursosLocales.getFondosCiudad9()[fondoElegido];
            g2.drawImage(fondoBatalla, 0, 0, ConfiguracionPantalla.SCREEN_WIDTH, ConfiguracionPantalla.SCREEN_HEIGHT, null);
        } else {
            // Fondo negro de respaldo en caso de que falle la lectura de los archivos
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, ConfiguracionPantalla.SCREEN_WIDTH, ConfiguracionPantalla.SCREEN_HEIGHT);
        }

        // Textos descriptivos por encima del fondo pixel art
        g2.setColor(Color.WHITE);
        g2.drawString("Ciudad 9 - Desafío de Estructuras", 50, 50);
        g2.drawString("Respondé las preguntas en la ventana emergente para atacar.", 50, 80);
        g2.drawString("Presioná 'Q' para escapar al mapa mundial.", 50, 110);
        
        // Si el minijuego ya se ganó, dibuja el cartel de victoria final
        if (ganado) {
            pantallaFinal.mostrarResultados(g2, ciudad);
        }
    }
}