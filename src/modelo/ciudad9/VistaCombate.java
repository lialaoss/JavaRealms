package modelo.ciudad9;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;

public class VistaCombate extends JFrame {
    private final JTextArea areaTexto;
    private final JLabel labelJugador; // Espacio exclusivo del héroe
    private final JLabel labelEnemigo; // Espacio exclusivo del monstruo

    public VistaCombate() {
        setTitle("Al-Quest - Desafío Ciudad 9");
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- PANEL DE SPRITES (Dividido en 2 columnas: Héroe | Enemigo) ---
        JPanel panelSprites = new JPanel(new GridLayout(1, 2, 20, 0));
        panelSprites.setBackground(Color.DARK_GRAY); // Color de fondo base para que no sea gris aburrido
        panelSprites.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        labelJugador = new JLabel();
        labelJugador.setHorizontalAlignment(JLabel.CENTER);

        labelEnemigo = new JLabel();
        labelEnemigo.setHorizontalAlignment(JLabel.CENTER);

        panelSprites.add(labelJugador);
        panelSprites.add(labelEnemigo);
        add(panelSprites, BorderLayout.NORTH); 

        // --- PANEL DE TEXTO ---
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 13));
        
        JScrollPane scroll = new JScrollPane(areaTexto);
        add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }

    // Métodos específicos para actualizar a cada uno por separado
    public void actualizarJugador(BufferedImage img) {
        if (img != null) {
            labelJugador.setIcon(new ImageIcon(img));
            labelJugador.repaint();
        }
    }

    public void actualizarEnemigo(BufferedImage img) {
        if (img != null) {
            labelEnemigo.setIcon(new ImageIcon(img));
            labelEnemigo.repaint();
        }
    }

    public void mostrarEstado(ControladorCombate combate) {
        StringBuilder sb = new StringBuilder();
        sb.append("==================================================\n");
        sb.append("                BATALLA: CIUDAD 9                 \n");
        sb.append("==================================================\n");
        sb.append(String.format(" Jugador: %-12s | HP: %-3d/150\n", 
                combate.getJugador().getNombre(), combate.getJugador().getVida()));
        sb.append(" Combo: [").append(combate.getExperienciaCombo()).append("/")
          .append(ControladorCombate.MAX_COMBO).append("] ")
          .append(combate.isComboDisponible() ? "★ ¡COMBO LISTO! ★" : "").append("\n");
        sb.append("--------------------------------------------------\n");
        sb.append(" Enemigos Activos:\n");
        
        for (int i = 0; i < combate.getListaEnemigos().cantidadEnemigos(); i++) {
            Personaje e = combate.getListaEnemigos().obtenerEnemigos().get(i);
            sb.append(String.format("   %d. %-12s -> HP: %-3d\n", (i + 1), e.getNombre(), e.getVida()));
        }
        sb.append("--------------------------------------------------\n");
        sb.append(" HISTORIAL DE SUCESOS:\n");
        for (String suceso : combate.getHistorialSucesos()) {
            sb.append(" • ").append(suceso).append("\n");
        }
        sb.append("==================================================\n");
        areaTexto.setText(sb.toString());
    }

    public int solicitarAccion(int accionActual, int totalAcciones) {
        String mensaje = "Acción [" + accionActual + " de " + totalAcciones + "]\n\n¿Qué hará el Héroe?";
        String[] opcionesBotones = {"Ataque", "Defensa", "Habilidad"};
        int seleccion = JOptionPane.showOptionDialog(this, mensaje, "Turno del Héroe",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcionesBotones, opcionesBotones[0]);
        if (seleccion == JOptionPane.CLOSED_OPTION) System.exit(0); 
        return seleccion + 1;
    }

    public int solicitarObjetivo(ListaEnemigos listaEnemigos) {
        int cantidadVivos = listaEnemigos.cantidadEnemigos();
        if (cantidadVivos == 1) return 0; 
        String[] opcionesBotones = new String[cantidadVivos];
        for (int i = 0; i < cantidadVivos; i++) {
            Personaje e = listaEnemigos.obtenerEnemigos().get(i);
            opcionesBotones[i] = e.getNombre() + " (HP: " + e.getVida() + ")";
        }
        int seleccion = JOptionPane.showOptionDialog(this, "Seleccione el objetivo:", "Objetivo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcionesBotones, opcionesBotones[0]);
        return (seleccion == JOptionPane.CLOSED_OPTION) ? 0 : seleccion;
    }

    public boolean hacerPreguntaEstructuras(Pregunta pregunta) {
        Object[] opcionesArray = pregunta.getOpciones().toArray();
        int seleccion = JOptionPane.showOptionDialog(this, "Responde:\n\n" + pregunta.getEnunciado(),
                "¡Desafío!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcionesArray, opcionesArray[0]);
        return seleccion == pregunta.getIndiceCorrecto();
    }

    public void mostrarMensajeFin(boolean victoria) {
        if (victoria) {
            JOptionPane.showMessageDialog(this, "¡VICTORIA EN CIUDAD 9!", "Fin", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "GAME OVER", "Fin", JOptionPane.ERROR_MESSAGE);
        }
    }
}