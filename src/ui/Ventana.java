package ui;

import javax.swing.JFrame;
import javax.swing.JPanel; // Importa JPanel para aceptar cualquier panel
import logica.AdministradorJuego;

public class Ventana extends JFrame {
    
    private JPanel panelActual;

    public Ventana(AdministradorJuego admin){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("AI-Quest");
        
        this.panelActual = new Panel(admin);
        this.add(panelActual);
        
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        if (panelActual instanceof Panel) {
            ((Panel) panelActual).startGameThread();
        }
    }

}