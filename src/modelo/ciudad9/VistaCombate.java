package modelo.ciudad9;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import utiles.ObservadorVictoria;

import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.BorderLayout;

/**
 * Interfaz gráfica principal del combate. 
 * Separa la lógica de presentación del modelo de datos.
 */
public class VistaCombate extends JFrame {
    private final JTextArea areaTexto;

    /**
     * Pre: Ninguna.
     * Post: Se inicializa la ventana principal de Swing, estableciendo sus dimensiones, layout y haciéndola visible en pantalla.
     */
    public VistaCombate(ObservadorVictoria observador) {
        setTitle("Al-Quest - Desafío Ciudad 9");
        setSize(550, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 13));
        
        JScrollPane scroll = new JScrollPane(areaTexto);
        add(scroll, BorderLayout.CENTER);

        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Pre: 'combate' no debe ser nulo y debe contener el estado actualizado de la partida.
     * Post: El área de texto de la ventana se actualiza mostrando la vida del jugador, los enemigos activos y el historial de sucesos.
     */
    public void mostrarEstado(ControladorCombate combate) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("==================================================\n");
        sb.append("                BATALLA: CIUDAD 9                 \n");
        sb.append("==================================================\n");
        sb.append(String.format(" Jugador: %-12s | HP: %-3d/100\n", 
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

    /**
     * Pre: 'accionActual' y 'totalAcciones' deben ser enteros positivos mayores a 0.
     * Post: Retorna un entero (1, 2 o 3) ingresado por el usuario mediante un cuadro de diálogo. Si el usuario cancela, el programa finaliza. Si ingresa texto inválido, retorna 2 (Defensa) por defecto.
     */
    public int solicitarAccion(int accionActual, int totalAcciones) {
        String mensajeMenu = "Acción [" + accionActual + " de " + totalAcciones + "]\n\n"
                           + "1 -> Ataque\n"
                           + "2 -> Defensa\n"
                           + "3 -> Habilidad\n\n"
                           + "Seleccione una opción (1-3):";
        
        String inputOpcion = JOptionPane.showInputDialog(this, mensajeMenu, "Turno del Héroe", JOptionPane.QUESTION_MESSAGE);
        
        if (inputOpcion == null) {
            System.exit(0); 
        }

        try {
            return Integer.parseInt(inputOpcion);
        } catch (NumberFormatException e) {
            return 2; 
        }
    }

    /**
     * Pre: 'listaEnemigos' no debe ser nula y debe contener al menos un enemigo vivo.
     * Post: Retorna el índice del enemigo seleccionado. Si queda un solo enemigo, retorna 0 automáticamente sin mostrar el diálogo. Ante una entrada inválida, retorna 0.
     */
    public int solicitarObjetivo(ListaEnemigos listaEnemigos) {
        int cantidadVivos = listaEnemigos.cantidadEnemigos();
        
        if (cantidadVivos == 1) {
            return 0; 
        }

        StringBuilder sbMenuEnemigos = new StringBuilder("Seleccione el objetivo de su ataque:\n\n");
        for (int i = 0; i < cantidadVivos; i++) {
            Personaje e = listaEnemigos.obtenerEnemigos().get(i);
            sbMenuEnemigos.append(i + 1).append(") ").append(e.getNombre()).append(" (HP: ").append(e.getVida()).append(")\n");
        }
        
        String inputObjetivo = JOptionPane.showInputDialog(this, sbMenuEnemigos.toString(), "Selección de Objetivo", JOptionPane.QUESTION_MESSAGE);
        
        if (inputObjetivo != null) {
            try {
                return Integer.parseInt(inputObjetivo) - 1;
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    /**
     * Pre: El combate debe haber concluido (estado de victoria o derrota confirmado).
     * Post: Muestra un cuadro de diálogo final informando el resultado al jugador.
     */
    public void mostrarMensajeFin(boolean victoria) {
        if (victoria) {
            JOptionPane.showMessageDialog(this, "¡VICTORIA EN CIUDAD 9!\nHas superado el desafío de estructuras de datos.", "Fin de la Partida", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "GAME OVER\nEl Héroe ha caído en la Ciudad 9.", "Fin de la Partida", JOptionPane.ERROR_MESSAGE);
        }
    }
}
