package ui;

import javax.swing.JFrame;

import logica.AdministradorJuego;

public class Ventana extends JFrame {
	
	private Panel panel; // usamos nuestro TDA Panel

	public Ventana(AdministradorJuego admin){
		JFrame ventana = new JFrame();

		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setResizable(false);
		ventana.setTitle("AI-Quest"); // creo que era asi
		
		panel = new Panel(admin);
		
		ventana.add(panel);
		ventana.pack(); // Adecua el contenido de la ventana
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
		panel.startGameThread();
		
	}

}