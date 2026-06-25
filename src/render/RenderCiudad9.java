package render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import ciudades.Ciudad;
import modelo.ciudad9.ControladorCombate;
import modelo.ciudad9.Personaje;
import modelo.ciudad9.Pregunta;
import ui.ConfiguracionPantalla;
import ui.GestorRecursos;

public class RenderCiudad9 {
    
    private GestorRecursos recursosGlobales;

    public RenderCiudad9(GestorRecursos recursosGlobales) {
        this.recursosGlobales = recursosGlobales;
    }

    public void dibujar(Graphics2D g2, Ciudad ciudad, FinMinijuegoPantalla pantallaFinal, boolean ganado, 
                        ControladorCombate combate, BufferedImage spriteJugador, 
                        BufferedImage efectoAtaque, int objetivoImpacto, String mensaje,
                        String faseActual, Pregunta preguntaActual) {
        

        BufferedImage fondoBatalla = recursosGlobales.getFondoBatalla();
        if (fondoBatalla != null) {
            g2.drawImage(fondoBatalla, 0, 0, ConfiguracionPantalla.SCREEN_WIDTH, ConfiguracionPantalla.SCREEN_HEIGHT, null);
        } else {
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, ConfiguracionPantalla.SCREEN_WIDTH, ConfiguracionPantalla.SCREEN_HEIGHT);
        }

        int playerSize = 140;
        int playerX = ConfiguracionPantalla.SCREEN_WIDTH - 180; 
        int playerY = 220; 


        if (spriteJugador != null) {
            g2.drawImage(spriteJugador, playerX, playerY, playerSize, playerSize, null);
        }
        
        int[][] datosEnemigos = new int[4][4]; 

        if (combate != null) {
            java.util.List<Personaje> vivos = combate.getListaEnemigos().obtenerEnemigos();
            int index = 0;
            
            for (Personaje e : vivos) {
                if (index >= 3) break; 
                
                String n = e.getNombre().toLowerCase();
                int ex = 0, ey = 0, ew = 0, eh = 0;
                BufferedImage sprite = null;

                if (n.contains("dragon") || n.contains("dragón")) {
                    sprite = recursosGlobales.getDragon();
                    ex = 260; ey = 20; ew = 280; eh = 280; 
                } else if (n.contains("demon") || n.contains("demonio")) {
                    sprite = recursosGlobales.getDemon();
                    ex = 190; ey = 150; ew = 210; eh = 210; 
                } else if (n.contains("jinn") || n.contains("genio")) {
                    sprite = recursosGlobales.getJinn();
                    ex = 310; ey = 230; ew = 160; eh = 160; 
                } else {
                    if (index == 0) { sprite = recursosGlobales.getDragon(); ex = 260; ey = 20; ew = 280; eh = 280; }
                    else if (index == 1) { sprite = recursosGlobales.getDemon(); ex = 190; ey = 150; ew = 210; eh = 210; }
                    else { sprite = recursosGlobales.getJinn(); ex = 310; ey = 230; ew = 160; eh = 160; }
                }

                datosEnemigos[index][0] = ex;
                datosEnemigos[index][1] = ey;
                datosEnemigos[index][2] = ew;
                datosEnemigos[index][3] = eh;

                if (sprite != null) {
                    g2.drawImage(sprite, ex, ey, ew, eh, null);
                }
                index++;
            }
        }

        if (efectoAtaque != null) {
            if (objetivoImpacto == 3) { // Impacto al jugador
                g2.drawImage(efectoAtaque, playerX - 10, playerY - 10, playerSize + 20, playerSize + 20, null);
            } 
            else if (objetivoImpacto == 10) { // Impacto al Dragón (coordenadas e independientes fijas)
                g2.drawImage(efectoAtaque, 260, 20, 280, 280, null);
            } 
            else if (objetivoImpacto == 11) { // Impacto al Demonio
                g2.drawImage(efectoAtaque, 190, 150, 210, 210, null);
            } 
            else if (objetivoImpacto == 12) { // Impacto al Genio
                g2.drawImage(efectoAtaque, 310, 230, 160, 160, null);
            }
            else if (objetivoImpacto >= 0 && objetivoImpacto < 3) { // Sistema de respaldo dinámico
                int targetX = datosEnemigos[objetivoImpacto][0];
                int targetY = datosEnemigos[objetivoImpacto][1];
                int targetW = datosEnemigos[objetivoImpacto][2];
                int targetH = datosEnemigos[objetivoImpacto][3];
                if (targetW > 0) {
                    g2.drawImage(efectoAtaque, targetX, targetY, targetW, targetH, null);
                }
            }
        }

        g2.setColor(new Color(0, 0, 0, 180)); 
        g2.fillRect(20, 20, 250, 180); 
        g2.setColor(Color.WHITE);
        g2.drawRect(20, 20, 250, 180);

        g2.setFont(new Font("Arial", Font.BOLD, 14));
        if (combate != null) {
            g2.drawString("Héroe HP: " + combate.getJugador().getVida() + " / 150", 30, 45);
            g2.drawString("Combo: " + combate.getExperienciaCombo() + " / 3", 30, 65);
            
            g2.drawString("--- ENEMIGOS ---", 30, 95);
            java.util.List<Personaje> vivos = combate.getListaEnemigos().obtenerEnemigos();
            int yText = 115;
            for (Personaje e : vivos) {
                g2.drawString(e.getNombre() + " HP: " + e.getVida(), 30, yText);
                yText += 20; 
            }
        }

        if (faseActual != null && !faseActual.equals("ESPERA") && !ganado) {
            g2.setColor(new Color(0, 0, 0, 220)); 
            g2.fillRect(0, 400, ConfiguracionPantalla.SCREEN_WIDTH, 110);
            g2.setColor(Color.WHITE);
            g2.drawRect(0, 400, ConfiguracionPantalla.SCREEN_WIDTH, 110);

            g2.setFont(new Font("Arial", Font.BOLD, 14));

            if (faseActual.equals("ACCION")) {
                g2.drawString("Elige tu acción:", 50, 425);
                dibujarBoton(g2, "Ataque", 50, 440, 170, 40);
                dibujarBoton(g2, "Defensa", 240, 440, 170, 40);
                dibujarBoton(g2, "Habilidad", 430, 440, 170, 40);
            } 
            else if (faseActual.equals("OBJETIVO")) {
                g2.drawString("¿A quién atacar?", 50, 425);
                java.util.List<Personaje> vivos = combate.getListaEnemigos().obtenerEnemigos();
                int xBtn = 50;
                for (Personaje e : vivos) {
                    dibujarBoton(g2, e.getNombre(), xBtn, 440, 170, 40);
                    xBtn += 190; 
                }
            } 
            else if (faseActual.equals("PREGUNTA") && preguntaActual != null) {
                g2.setColor(Color.YELLOW);
                g2.drawString(preguntaActual.getEnunciado(), 50, 425);
                Object[] opciones = preguntaActual.getOpciones().toArray();
                int xBtn = 30; 
                for (Object opcion : opciones) {
                    dibujarBoton(g2, String.valueOf(opcion), xBtn, 440, 170, 40); 
                    xBtn += 185; 
                }
            }
        }

        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, ConfiguracionPantalla.SCREEN_HEIGHT - 60, ConfiguracionPantalla.SCREEN_WIDTH, 60);
        g2.setColor(Color.YELLOW);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString(mensaje != null ? mensaje : "", 50, ConfiguracionPantalla.SCREEN_HEIGHT - 25);
        
        if (ganado) {
            pantallaFinal.mostrarResultados(g2, ciudad);
        }
    }

    private void dibujarBoton(Graphics2D g2, String texto, int x, int y, int w, int h) {
        g2.setColor(new Color(50, 50, 50)); 
        g2.fillRect(x, y, w, h);
        g2.setColor(Color.WHITE);
        g2.drawRect(x, y, w, h);
        g2.drawString(texto, x + 10, y + 25); 
    }
}