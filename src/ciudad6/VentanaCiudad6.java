package ciudad6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Interfaz Gráfica de usuario desarrollada en Swing para representar la Ciudad 6.
 * Controla la interacción del usuario y renderiza en tiempo real el estado de la estructura.
 */
public class VentanaCiudad6 extends JFrame {
    private TablaHash tablaHash;
    private JPanel panelTabla;
    private JTextArea areaPasos;
    private JTextField campoClave;
    private JTextField campoValor;
    private JLabel[] etiquetasCeldas;

    /**
     * Constructor de la ventana gráfica.
     * * @pre Ninguna.
     * @post Crea e inicializa el Frame de la ventana junto con todos sus paneles y listeners.
     */
    public VentanaCiudad6() {
        this.tablaHash = new TablaHash();
        this.etiquetasCeldas = new JLabel[11]; 
        
        configurarVentana();
        inicializarComponentes();
    }

    /**
     * Define los parámetros globales de la ventana de Swing.
     * * @pre Ninguna.
     * @post La ventana queda configurada con tamaño, título y posicionamiento relativo centrado.
     */
    private void configurarVentana() {
        setTitle("JavaRealms - Ciudad 6: Tabla Hash");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    /**
     * Instancia y acopla los componentes visuales (Paneles, Botones, TextAreas).
     * * @pre El array de etiquetasCeldas debe estar instanciado con tamaño 11.
     * @post La interfaz queda completamente armada con sus respectivos Listeners asignados.
     */
    private void inicializarComponentes() {
        // --- PANEL IZQUIERDO: Estructura Visual del Vector ---
        this.panelTabla = new JPanel();
        this.panelTabla.setLayout(new GridLayout(11, 1, 5, 5));
        this.panelTabla.setBorder(BorderFactory.createTitledBorder("Estado de la Tabla Hash"));
        this.panelTabla.setPreferredSize(new Dimension(380, 500));

        for (int i = 0; i < this.etiquetasCeldas.length; i++) {
            this.etiquetasCeldas[i] = new JLabel(" [ " + i + " ] -> Vacío", SwingConstants.CENTER);
            this.etiquetasCeldas[i].setOpaque(true);
            this.etiquetasCeldas[i].setBackground(Color.LIGHT_GRAY);
            this.etiquetasCeldas[i].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            this.panelTabla.add(this.etiquetasCeldas[i]);
        }
        add(this.panelTabla, BorderLayout.WEST);

        // --- PANEL DERECHO: Consola y Cuadro de Controles ---
        JPanel panelDerecho = new JPanel(new BorderLayout(5, 5));
        
        JPanel panelControles = new JPanel(new FlowLayout());
        panelControles.setBorder(BorderFactory.createTitledBorder("Operaciones del Jugador"));

        panelControles.add(new JLabel("Clave:"));
        this.campoClave = new JTextField(8);
        panelControles.add(this.campoClave);

        panelControles.add(new JLabel("Valor (Nro):"));
        this.campoValor = new JTextField(5);
        panelControles.add(this.campoValor);

        JButton botonInsertar = new JButton("Insertar Elemento");
        JButton botonBuscar = new JButton("Buscar Clave");
        panelControles.add(botonInsertar);
        panelControles.add(botonBuscar);

        panelDerecho.add(panelControles, BorderLayout.NORTH);

        // Consola de Texto para el paso a paso
        this.areaPasos = new JTextArea();
        this.areaPasos.setEditable(false);
        this.areaPasos.setFont(new Font("Monospaced", Font.PLAIN, 13));
        this.areaPasos.setBackground(new Color(245, 245, 245));
        JScrollPane scrollPasos = new JScrollPane(this.areaPasos);
        scrollPasos.setBorder(BorderFactory.createTitledBorder("Explicación del Algoritmo"));
        
        panelDerecho.add(scrollPasos, BorderLayout.CENTER);
        add(panelDerecho, BorderLayout.CENTER);

        // Vincular acciones de los botones
        botonInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarInsercion();
            }
        });

        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarBusqueda();
            }
        });
    }

    /**
     * Recoge los datos del formulario de inserción, valida tipos y los envía a la estructura.
     * Ataja desbordes de memoria mediante bloques try-catch.
     * * @pre Ninguna.
     * @post Si los datos son válidos, inserta la celda, refresca el panel gráfico e imprime los pasos.
     */
    private void ejecutarInsercion() {
        String clave = this.campoClave.getText().trim();
        String valorStr = this.campoValor.getText().trim();

        if (clave.isEmpty() || valorStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa ambos campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int valor = Integer.parseInt(valorStr);
            List<String> pasos = this.tablaHash.insertar(clave, valor);
            
            actualizarConsolaPasos(pasos);
            actualizarGraficoTabla();
            limpiarCampos();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El valor debe ser un número entero.", "Error de Tipo", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, "¡Memoria Llena! No se pueden introducir más elementos en esta ciudad.", "Tabla Hash Llena", JOptionPane.ERROR_MESSAGE);
            this.areaPasos.append("\n[SISTEMA] Operación cancelada: Estructura al 100% de capacidad.\n");
        }
    }

    /**
     * Recoge la clave del formulario e invoca la búsqueda secuencial en la estructura.
     * * @pre Ninguna.
     * @post Imprime el camino de búsqueda en la consola lateral y despliega un cuadro emergente indicando el resultado.
     */
    private void ejecutarBusqueda() {
        String clave = this.campoClave.getText().trim();

        if (clave.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa una clave para buscar.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ResultadoBusqueda resultado = this.tablaHash.buscar(clave);
        actualizarConsolaPasos(resultado.getPasosExplicativos());
        
        if (resultado.getValorEncontrado() != -1) {
            JOptionPane.showMessageDialog(this, "¡Elemento encontrado!\nValor: " + resultado.getValorEncontrado(), "Resultado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "La clave '" + clave + "' no existe.", "No Encontrado", JOptionPane.ERROR_MESSAGE);
        }
        limpiarCampos();
    }

    /**
     * Sincroniza el estado lógico del array de la Tabla Hash con las etiquetas gráficas del panel izquierdo.
     * * @pre Ninguna.
     * @post Modifica los textos y colores de fondo de las celdas ocupadas frente a las vacías.
     */
    private void actualizarGraficoTabla() {
        CeldaHash[] celdas = this.tablaHash.getTabla();
        for (int i = 0; i < celdas.length; i++) {
            if (celdas[i] != null) {
                this.etiquetasCeldas[i].setText(" [ " + i + " ] -> Clave: '" + celdas[i].getClave() + "' | Valor: " + celdas[i].getValor());
                this.etiquetasCeldas[i].setBackground(new Color(174, 214, 241)); 
            } else {
                this.etiquetasCeldas[i].setText(" [ " + i + " ] -> Vacío");
                this.etiquetasCeldas[i].setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    /**
     * Vuelca la lista de explicaciones del algoritmo en el JTextArea derecho.
     * * @pre La lista de pasos no debe ser nula.
     * @post El panel de texto se limpia y se rellena con la nueva bitácora de ejecución.
     */
    private void actualizarConsolaPasos(List<String> pasos) {
        this.areaPasos.setText("");
        for (String paso : pasos) {
            this.areaPasos.append(paso + "\n");
        }
    }

    /**
     * Resetea el contenido de las cajas de texto de la UI.
     * * @pre Ninguna.
     * @post Cajas vacías y el foco de escritura reasignado a la caja de la clave.
     */
    private void limpiarCampos() {
        this.campoClave.setText("");
        this.campoValor.setText("");
        this.campoClave.requestFocus();
    }

    /**
     * Punto de entrada principal (main) para lanzar la aplicación de manera autónoma en Eclipse.
     * * @pre Ninguna.
     * @post Despliega y hace visible la ventana gráfica de la Ciudad 6.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaCiudad6().setVisible(true);
            }
        });
    }
}