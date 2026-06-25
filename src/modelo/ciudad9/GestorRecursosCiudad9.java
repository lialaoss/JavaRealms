package modelo.ciudad9;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class GestorRecursosCiudad9 {

    private BufferedImage[] demonIdle;
    private BufferedImage[] demonAttack;
    private BufferedImage[] demonDeath;

    private BufferedImage[] dragonAttack;
    private BufferedImage[] dragonFireAttack;
    private BufferedImage[] dragonDeath;

    private BufferedImage[] jinnIdle;
    private BufferedImage[] jinnAttack;      
    private BufferedImage[] jinnMagicAttack; 
    private BufferedImage[] jinnDeath;       

    private BufferedImage[] fondosCiudad9;

    /*
     * Pre: Las carpetas de recursos en 'src/sprites.ciudad9' deben existir y contener las imágenes numeradas.
     * Post: Inicializa y carga en memoria todos los recursos gráficos específicos de la Ciudad 9.
     */
    public void cargarRecursos() {
        try {
            // --- 1. CARGA DEL DEMONIO ---
            demonIdle = new BufferedImage[3]; 
            for (int i = 0; i < demonIdle.length; i++) {
                demonIdle[i] = ImageIO.read(getClass().getResourceAsStream("/sprites.ciudad9/demon/Idle" + (i + 1) + ".png"));
            }
            
            demonAttack = new BufferedImage[4]; 
            for (int i = 0; i < demonAttack.length; i++) {
                demonAttack[i] = ImageIO.read(getClass().getResourceAsStream("/sprites.ciudad9/demon/Attack" + (i + 1) + ".png"));
            }
            
            demonDeath = new BufferedImage[6]; 
            for (int i = 0; i < demonDeath.length; i++) {
                demonDeath[i] = ImageIO.read(getClass().getResourceAsStream("/sprites.ciudad9/demon/Death" + (i + 1) + ".png"));
            }

            // --- 2. CARGA DEL DRAGÓN ---
            dragonAttack = new BufferedImage[4]; 
            for (int i = 0; i < dragonAttack.length; i++) {
                dragonAttack[i] = ImageIO.read(getClass().getResourceAsStream("/sprites.ciudad9/dragon/Attack" + (i + 1) + ".png"));
            }
            
            dragonFireAttack = new BufferedImage[4]; 
            for (int i = 0; i < dragonFireAttack.length; i++) {
                dragonFireAttack[i] = ImageIO.read(getClass().getResourceAsStream("/sprites.ciudad9/dragon/Fire_Attack" + (i + 1) + ".png"));
            }
            
            dragonDeath = new BufferedImage[5]; 
            for (int i = 0; i < dragonDeath.length; i++) {
                dragonDeath[i] = ImageIO.read(getClass().getResourceAsStream("/sprites.ciudad9/dragon/Death" + (i + 1) + ".png"));
            }

            // --- 3. CARGA DEL JINN ---
            jinnIdle = new BufferedImage[3]; 
            for (int i = 0; i < jinnIdle.length; i++) {
                jinnIdle[i] = ImageIO.read(getClass().getResourceAsStream("/sprites.ciudad9/jinn_animation/Idle" + (i + 1) + ".png"));
            }
            
            jinnAttack = new BufferedImage[4]; 
            for (int i = 0; i < jinnAttack.length; i++) {
                jinnAttack[i] = ImageIO.read(getClass().getResourceAsStream("/sprites.ciudad9/jinn_animation/Attack" + (i + 1) + ".png"));
            }
            
            jinnMagicAttack = new BufferedImage[23]; 
            for (int i = 0; i < jinnMagicAttack.length; i++) {
                jinnMagicAttack[i] = ImageIO.read(getClass().getResourceAsStream("/sprites.ciudad9/jinn_animation/Magic_Attack" + (i + 1) + ".png"));
            }
            
            jinnDeath = new BufferedImage[6]; 
            for (int i = 0; i < jinnDeath.length; i++) {
                jinnDeath[i] = ImageIO.read(getClass().getResourceAsStream("/sprites.ciudad9/jinn_animation/Death" + (i + 1) + ".png"));
            }

            // --- 4. CARGA DE FONDOS DE BATALLA ---
            fondosCiudad9 = new BufferedImage[4]; 
            for (int i = 0; i < fondosCiudad9.length; i++) {
                fondosCiudad9[i] = ImageIO.read(getClass().getResourceAsStream("/sprites.ciudad9/Battleground" + (i + 1) + ".png"));
            }

        } catch (Exception e) {
            System.err.println("Error crítico en el gestor local de Ciudad 9: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- GETTERS ---
    public BufferedImage[] getDemonIdle() { return demonIdle; }
    public BufferedImage[] getDemonAttack() { return demonAttack; }
    public BufferedImage[] getDemonDeath() { return demonDeath; }
    public BufferedImage[] getDragonAttack() { return dragonAttack; }
    public BufferedImage[] getDragonFireAttack() { return dragonFireAttack; }
    public BufferedImage[] getDragonDeath() { return dragonDeath; }
    public BufferedImage[] getJinnIdle() { return jinnIdle; }
    public BufferedImage[] getJinnAttack() { return jinnAttack; }
    public BufferedImage[] getJinnMagicAttack() { return jinnMagicAttack; }
    public BufferedImage[] getJinnDeath() { return jinnDeath; }
    public BufferedImage[] getFondosCiudad9() { return fondosCiudad9; }
}