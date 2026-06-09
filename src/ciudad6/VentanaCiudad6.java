package ciudad6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Interfaz Gráfica de la Ciudad 6 transformada en un Desafío de Juego (Al-Quest).
 * El jugador debe cumplir ciertos objetivos algorítmicos para ganar la ciudad.
 */
public class VentanaCiudad6 extends JFrame {
    private TablaHash tablaHash;
    private JPanel panelTabla;
    private JTextArea areaPasos;
    private JTextField campoClave;
    private JTextField campoValor;
    private JLabel[] etiquetasCeldas;

    // --- VARIABLES DEL JUEGO ---
    private int colisionesLogradas = 0;
    private final int COLISIONES_REQUERIDAS = 3;
    private boolean busquedaColisionadaExitosa = false;
    private boolean ciudadGanada = false;

    // Componentes de la interfaz del juego
    private JLabel etiquetaMision;
    private JButton botonInsertar;
    private JButton botonBuscar;

    public VentanaCiudad6() {
        this.tablaHash = new TablaHash();
        this.etiquetasCeldas = new JLabel[11]; 
        
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("JavaRealms - ¡Desafío de la Ciudad 6: El Oráculo Hash!");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void inicializarComponentes() {
        // --- PANEL DE CONSIGNAS (SUPERIOR) ---
        JPanel panelMision = new JPanel(new BorderLayout());
        panelMision.setBackground(new Color(44, 62, 80));
        panelMision.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        
        // Texto fijo con las consignas originales que pidieron mantener arriba
        String textoConsignas = "<html><font color='#F1C40F'><b>⚔️ DESAFÍO - CIUDAD 6: EL ORÁCULO HASH ⚔️</b></font><br>"
                + "<font color='white'>El guardián ha bloqueado el camino. Para descifrar la clave de salida y avanzar debes:<br>"
                + "1. Provocar al menos <b>3 colisiones</b> de memoria usando Linear Probing.<br>"
                + "2. Buscar y <b>encontrar con éxito</b> una clave que haya sido desplazada por una colisión.<br>"
                + "<i>Consejo: ¡Prueba ingresando claves con caracteres similares para forzar el mismo índice!</i></font><hr></html>";
        
        JLabel etiquetaFija = new JLabel(textoConsignas);
        etiquetaFija.setFont(new Font("SansSerif", Font.PLAIN, 13));
        panelMision.add(etiquetaFija, BorderLayout.NORTH);

        // Esta es la etiqueta que SÓLO va a manejar las variables y el estado dinámico
        this.etiquetaMision = new JLabel();
        this.etiquetaMision.setFont(new Font("SansSerif", Font.BOLD, 13));
        panelMision.add(this.etiquetaMision, BorderLayout.CENTER);
        
        // Llamada inicial para que pinte el estado 0/3 apenas abre el juego
        actualizarTextoEstado();
        
        add(panelMision, BorderLayout.NORTH);

        // --- PANEL IZQUIERDO: Estructura Visual del Vector ---
        this.panelTabla = new JPanel();
        this.panelTabla.setLayout(new GridLayout(11, 1, 5, 5));
        this.panelTabla.setBorder(BorderFactory.createTitledBorder("Estado de la Tabla Hash"));
        this.panelTabla.setPreferredSize(new Dimension(380, 450));

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
        panelControles.setBorder(BorderFactory.createTitledBorder("Panel de Control del Héroe"));

        panelControles.add(new JLabel("Clave:"));
        this.campoClave = new JTextField(8);
        panelControles.add(this.campoClave);

        panelControles.add(new JLabel("Valor (Nro):"));
        this.campoValor = new JTextField(5);
        panelControles.add(this.campoValor);

        this.botonInsertar = new JButton("Insertar Elemento");
        this.botonBuscar = new JButton("Buscar Clave");
        panelControles.add(this.botonInsertar);
        panelControles.add(this.botonBuscar);

        panelDerecho.add(panelControles, BorderLayout.NORTH);

        this.areaPasos = new JTextArea();
        this.areaPasos.setEditable(false);
        this.areaPasos.setFont(new Font("Monospaced", Font.PLAIN, 13));
        this.areaPasos.setBackground(new Color(245, 245, 245));
        JScrollPane scrollPasos = new JScrollPane(this.areaPasos);
        scrollPasos.setBorder(BorderFactory.createTitledBorder("Bitácora del Oráculo (Explicación Algorítmica)"));
        
        panelDerecho.add(scrollPasos, BorderLayout.CENTER);
        add(panelDerecho, BorderLayout.CENTER);

        this.botonInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarInsercion();
            }
        });

        this.botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarBusqueda();
            }
        });
    }
    
    private void actualizarTextoEstado() {
        String estadoMision = "<html><font color='#E67E22'><b>ESTADO DE LA MISIÓN ACTUAL:</b></font><br>"
                + "<font color='white'>• Colisiones provocadas: <b>" + this.colisionesLogradas + "/" + COLISIONES_REQUERIDAS + "</b><br>"
                + "• ¿Encontraste un elemento colisionado?: <b>" + (this.busquedaColisionadaExitosa ? "SÍ" : "NO") + "</b></font></html>";
        this.etiquetaMision.setText(estadoMision);
    }

    
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
            
            // CONTAR COLISIONES: Revisamos los pasos impresos buscando la palabra "[COLISIÓN]"
            for (String paso : pasos) {
                if (paso.contains("[COLISIÓN]")) {
                    this.colisionesLogradas++;
                }
            }
            
            actualizarConsolaPasos(pasos);
            actualizarGraficoTabla();
            verificarCondicionVictoria();
            limpiarCampos();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El valor debe ser un número entero.", "Error de Tipo", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, "¡Memoria Llena! No puedes introducir más elementos.", "Tabla Hash Llena", JOptionPane.ERROR_MESSAGE);
            this.areaPasos.append("\n[SISTEMA] Operación cancelada: Estructura al 100%.\n");
        }
    }

    private void ejecutarBusqueda() {
        String clave = this.campoClave.getText().trim();

        if (clave.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa una clave para buscar.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ResultadoBusqueda resultado = this.tablaHash.buscar(clave);
        actualizarConsolaPasos(resultado.getPasosExplicativos());
        
        if (resultado.getValorEncontrado() != -1) {
            // VERIFICAR VICTORIA DE BÚSQUEDA: Si se encontró y en los pasos hubo un Linear Probing
            boolean sufrioColision = false;
            for (String paso : resultado.getPasosExplicativos()) {
                if (paso.contains("Aplicando Linear Probing")) {
                    sufrioColision = true;
                }
            }
            
            if (sufrioColision) {
                this.busquedaColisionadaExitosa = true;
                this.areaPasos.append("\n[¡LOGRO!] Has localizado un elemento que fue desplazado por colisión.\n");
            }
            
            JOptionPane.showMessageDialog(this, "¡Elemento encontrado!\nValor: " + resultado.getValorEncontrado(), "Resultado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "La clave '" + clave + "' no existe.", "No Encontrado", JOptionPane.ERROR_MESSAGE);
        }
        
        verificarCondicionVictoria();
        limpiarCampos();
    }

    /**
     * Evalúa si el usuario cumplió los requisitos lógicos para ganar la ciudad.
     */
    private void verificarCondicionVictoria() {
        if (this.ciudadGanada) {
            return;
        }

        // Actualizamos dinámicamente la barra de la misión para dar feedback
        String estadoMision = "<html><font color='white'><b>ESTADO DE LA MISIÓN:</b><br>"
                + "• Colisiones provocadas: <b>" + this.colisionesLogradas + "/" + COLISIONES_REQUERIDAS + "</b><br>"
                + "• ¿Encontraste un elemento colisionado?: <b>" + (this.busquedaColisionadaExitosa ? "SÍ" : "NO") + "</b></font></html>";
        this.etiquetaMision.setText(estadoMision);

        // Si se cumplen ambas condiciones: ¡Victoria!
        if (this.colisionesLogradas >= COLISIONES_REQUERIDAS && this.busquedaColisionadaExitosa) {
            this.ciudadGanada = true;
            this.etiquetaMision.setText("<html><font color='#2ECC71'><b>¡CIUDAD 6 GANADA! +100 PUNTOS</b><br>"
                    + "Has dominado el algoritmo de Hashing y Linear Probing de forma perfecta. El camino está libre.</font></html>");
            
            // Deshabilitamos los controles para cerrar el juego limpiamente
            this.botonInsertar.setEnabled(false);
            this.botonBuscar.setEnabled(false);
            this.campoClave.setEnabled(false);
            this.campoValor.setEnabled(false);
            
            JOptionPane.showMessageDialog(this, "¡Felicidades, ganaste la Ciudad 6!\nHas descifrado el comportamiento de la Tabla Hash.", "¡Victoria!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

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

    private void actualizarConsolaPasos(List<String> pasos) {
        this.areaPasos.setText("");
        for (String paso : pasos) {
            this.areaPasos.append(paso + "\n");
        }
        // Desplaza el scroll automáticamente hacia arriba de todo para empezar a leer desde el inicio del cálculo
        this.areaPasos.setCaretPosition(0);
    }

    private void limpiarCampos() {
        this.campoClave.setText("");
        this.campoValor.setText("");
        this.campoClave.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaCiudad6().setVisible(true);
            }
        });
    }
}