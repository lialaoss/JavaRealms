package modelo.ciudad9;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.BorderLayout;

/*
 * Interfaz gráfica principal del combate (Vista).
 */
public class VistaCombate extends JFrame {
    private final JTextArea areaTexto;

    /*
     Pre: Ninguna.
     Post: Se configura y se hace visible la ventana de combate en Swing.
     */
    public VistaCombate() {
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
    }

    /*
     Pre: 'combate' no debe ser nulo.
     Post: El área de texto se repinta reflejando los puntos de vida actuales y el historial.
     */
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

    /*
     Pre: 'accionActual' y 'totalAcciones' son valores positivos.
     Post: Muestra botones gráficos y retorna el número asociado a la acción elegida (1, 2 o 3).
     */
    public int solicitarAccion(int accionActual, int totalAcciones) {
        String mensaje = "Acción [" + accionActual + " de " + totalAcciones + "]\n\n¿Qué hará el Héroe?";
        String[] opcionesBotones = {"Ataque", "Defensa", "Habilidad"};
        
        int seleccion = JOptionPane.showOptionDialog(this,
                mensaje,
                "Turno del Héroe",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionesBotones,
                opcionesBotones[0]);
        
        if (seleccion == JOptionPane.CLOSED_OPTION) {
            System.exit(0); 
        }
        return seleccion + 1;
    }

    /*
     Pre: 'listaEnemigos' no es nulo.
     Post: Retorna el índice del enemigo elegido mediante botones. Si queda uno, lo autoselecciona (retorna 0).
     */
    public int solicitarObjetivo(ListaEnemigos listaEnemigos) {
        int cantidadVivos = listaEnemigos.cantidadEnemigos();
        if (cantidadVivos == 1) return 0; 

        String[] opcionesBotones = new String[cantidadVivos];
        for (int i = 0; i < cantidadVivos; i++) {
            Personaje e = listaEnemigos.obtenerEnemigos().get(i);
            opcionesBotones[i] = e.getNombre() + " (HP: " + e.getVida() + ")";
        }
        
        int seleccion = JOptionPane.showOptionDialog(this,
                "Seleccione el objetivo de su ataque:",
                "Selección de Objetivo",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionesBotones,
                opcionesBotones[0]);
        
        if (seleccion == JOptionPane.CLOSED_OPTION) return 0;
        return seleccion;
    }

    /*
     Pre: 'pregunta' es un objeto Pregunta válido instanciado.
     Post: Despliega el desafío choice. Retorna true si el usuario clickeó el botón correcto.
     */
    public boolean hacerPreguntaEstructuras(Pregunta pregunta) {
        // La API gráfica nativa exige Array de Objetos para el ploteo de botones.
        Object[] opcionesArray = pregunta.getOpciones().toArray();
        
        int seleccion = JOptionPane.showOptionDialog(this,
                "Para ejecutar la acción con éxito, responde:\n\n" + pregunta.getEnunciado(),
                "¡Desafío del Profesor!",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionesArray,
                opcionesArray[0]);

        return seleccion == pregunta.getIndiceCorrecto();
    }

    /*
     Pre: El juego determinó un ganador o perdedor.
     Post: Renderiza una ventana de diálogo final anunciando el resultado.
     */
    public void mostrarMensajeFin(boolean victoria) {
        if (victoria) {
            JOptionPane.showMessageDialog(this, "¡VICTORIA EN CIUDAD 9!\nHas superado el desafío de estructuras de datos.", "Fin de la Partida", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "GAME OVER\nEl Héroe ha caído en la Ciudad 9.", "Fin de la Partida", JOptionPane.ERROR_MESSAGE);
        }
    }
}
