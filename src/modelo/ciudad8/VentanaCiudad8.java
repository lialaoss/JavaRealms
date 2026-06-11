package modelo.ciudad8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import minijuego.Ciudad8Minijuego;

public class VentanaCiudad8 extends JFrame {
    private LogicaHanoi motorHanoi;
    private List<MovimientoHanoi> movimientos;
    private int pasoActual;
    private int discosSeleccionados;
    private Ciudad8Minijuego minijuego;

    private List<Integer> torreA;
    private List<Integer> torreB;
    private List<Integer> torreC;

    private JTextArea areaTorreA;
    private JTextArea areaTorreB;
    private JTextArea areaTorreC;
    private JTextArea areaExplicacion;
    private JLabel etiquetaEstado;
    private JButton botonSiguiente;
    private JComboBox<String> comboDiscos;

    public VentanaCiudad8(Ciudad8Minijuego minijuego) {
        this.minijuego = minijuego;
        this.motorHanoi = new LogicaHanoi();
        this.discosSeleccionados = 3;
        configurarVentana();
        inicializarComponentes();
        reiniciarJuego();
    }

    private void configurarVentana() {
        setTitle("JavaRealms - ¡Desafío de la Ciudad 8: Las Torres de Hanoi!");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void inicializarComponentes() {
        JPanel panelMision = new JPanel(new BorderLayout());
        panelMision.setBackground(new Color(44, 62, 80));
        panelMision.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        String instrucciones = "<html><font color='#F1C40F'><b>DESAFÍO - CIUDAD 8: EL RITUAL DE HANOI</b></font><br>"
                + "<font color='white'>El tiempo se ha detenido. Para reactivarlo debes guiar la ejecución de la pila recursiva.<br>"
                + "Tu objetivo es trasladar todos los discos desde la <b>Torre A</b> hasta la <b>Torre C</b>.<br>"
                + "Presiona 'Avanzar Paso Recursivo' para observar cómo el procesador ejecuta el algoritmo matemático.</font><hr></html>";

        JLabel etiquetaFija = new JLabel(instrucciones);
        etiquetaFija.setFont(new Font("SansSerif", Font.PLAIN, 13));
        panelMision.add(etiquetaFija, BorderLayout.NORTH);

        this.etiquetaEstado = new JLabel();
        this.etiquetaEstado.setFont(new Font("SansSerif", Font.BOLD, 13));
        this.etiquetaEstado.setForeground(Color.WHITE);
        panelMision.add(this.etiquetaEstado, BorderLayout.CENTER);
        add(panelMision, BorderLayout.NORTH);

        JPanel panelTorresGlobal = new JPanel(new GridLayout(1, 3, 10, 10));
        panelTorresGlobal.setBorder(BorderFactory.createTitledBorder("Altares de Poder (Estado de las Torres)"));

        this.areaTorreA = crearAreaTorre("Torre A (Origen)");
        this.areaTorreB = crearAreaTorre("Torre B (Auxiliar)");
        this.areaTorreC = crearAreaTorre("Torre C (Destino)");

        panelTorresGlobal.add(this.areaTorreA);
        panelTorresGlobal.add(this.areaTorreB);
        panelTorresGlobal.add(this.areaTorreC);
        add(panelTorresGlobal, BorderLayout.CENTER);

        JPanel panelControl = new JPanel(new BorderLayout(5, 5));
        panelControl.setPreferredSize(new Dimension(420, 450));

        JPanel panelOpciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelOpciones.setBorder(BorderFactory.createTitledBorder("Configuración del Ritual"));
        panelOpciones.add(new JLabel("Cantidad de Discos:"));
        String[] opciones = {"3 Discos (Fácil - 7 pasos)", "5 Discos (Intermedio - 31 pasos)", "8 Discos (Leyenda - 255 pasos)"};
        this.comboDiscos = new JComboBox<>(opciones);
        panelOpciones.add(this.comboDiscos);
        panelControl.add(panelOpciones, BorderLayout.NORTH);

        this.areaExplicacion = new JTextArea();
        this.areaExplicacion.setEditable(false);
        this.areaExplicacion.setFont(new Font("Monospaced", Font.PLAIN, 12));
        this.areaExplicacion.setBackground(new Color(245, 245, 245));
        JScrollPane scrollLog = new JScrollPane(this.areaExplicacion);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Análisis de la Pila de Ejecución (Recursión)"));
        panelControl.add(scrollLog, BorderLayout.CENTER);

        this.botonSiguiente = new JButton("Avanzar Paso Recursivo");
        this.botonSiguiente.setFont(new Font("SansSerif", Font.BOLD, 14));
        this.botonSiguiente.setBackground(new Color(52, 152, 219));
        this.botonSiguiente.setForeground(Color.WHITE);
        panelControl.add(this.botonSiguiente, BorderLayout.SOUTH);
        add(panelControl, BorderLayout.EAST);

        this.botonSiguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                avanzarPasoAlgoritmo();
            }
        });

        this.comboDiscos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) comboDiscos.getSelectedItem();
                if (seleccion.startsWith("3")) {
                    discosSeleccionados = 3;
                } else if (seleccion.startsWith("5")) {
                    discosSeleccionados = 5;
                } else {
                    discosSeleccionados = 8;
                }
                reiniciarJuego();
            }
        });
    }

    private JTextArea crearAreaTorre(String titulo) {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.BOLD, 14));
        area.setBackground(new Color(236, 240, 241));
        area.setBorder(BorderFactory.createTitledBorder(titulo));
        return area;
    }

    private void reiniciarJuego() {
        this.pasoActual = 0;
        this.botonSiguiente.setEnabled(true);
        this.torreA = new ArrayList<>();
        this.torreB = new ArrayList<>();
        this.torreC = new ArrayList<>();

        for (int i = this.discosSeleccionados; i >= 1; i--) {
            this.torreA.add(i);
        }

        this.motorHanoi.resolverHanoi(this.discosSeleccionados, 'A', 'B', 'C');
        this.movimientos = this.motorHanoi.getListaMovimientos();
        this.etiquetaEstado.setText("Estado: Ritual listo. " + this.discosSeleccionados + " discos (" + this.movimientos.size() + " movimientos).");
        mostrarExplicacionInicial();
        actualizarGraficoTorres();
    }

    private void mostrarExplicacionInicial() {
        this.areaExplicacion.setText("ÁRBOL DE LLAMADAS GENERADO:\n");
        for (String linea : this.motorHanoi.getBitacoraRecursiva()) {
            this.areaExplicacion.append(linea + "\n");
        }
        this.areaExplicacion.setCaretPosition(0);
    }

    private void avanzarPasoAlgoritmo() {
        if (this.pasoActual >= this.movimientos.size()) {
            return;
        }

        MovimientoHanoi mov = this.movimientos.get(pasoActual);
        List<Integer> origenLista = obtenerListaTorre(mov.getOrigen());
        int disco = origenLista.remove(origenLista.size() - 1);
        List<Integer> destinoLista = obtenerListaTorre(mov.getDestino());
        destinoLista.add(disco);

        this.pasoActual++;
        actualizarGraficoTorres();
        this.etiquetaEstado.setText("Movimiento " + this.pasoActual + "/" + this.movimientos.size()
                + ". Disco " + mov.getDisco() + " de " + mov.getOrigen() + " -> " + mov.getDestino());
        this.areaExplicacion.append("\n[PASO " + this.pasoActual + "]: Mover Disco " + mov.getDisco()
                + " de " + mov.getOrigen() + " -> " + mov.getDestino());

        if (this.pasoActual == this.movimientos.size()) {
            this.etiquetaEstado.setText("<html><font color='#2ECC71'><b>¡CIUDAD 8 GANADA!</b></font></html>");
            this.botonSiguiente.setEnabled(false);
            JOptionPane.showMessageDialog(this, "¡Completaste las Torres de Hanoi!", "¡Victoria!", JOptionPane.INFORMATION_MESSAGE);
            if (this.minijuego != null) {
                this.minijuego.notificarVictoria();
            }
        }
    }

    private List<Integer> obtenerListaTorre(char nombre) {
        if (nombre == 'A') { return this.torreA; }
        if (nombre == 'B') { return this.torreB; }
        return this.torreC;
    }

    private void actualizarGraficoTorres() {
        dibujarTorreEspecifica(this.areaTorreA, this.torreA);
        dibujarTorreEspecifica(this.areaTorreB, this.torreB);
        dibujarTorreEspecifica(this.areaTorreC, this.torreC);
    }

    private void dibujarTorreEspecifica(JTextArea area, List<Integer> torre) {
        area.setText("\n");
        int altoGrafico = Math.max(this.discosSeleccionados, 8);
        for (int i = altoGrafico - 1; i >= 0; i--) {
            if (i < torre.size()) {
                int tamanioDisco = torre.get(i);
                String cuerpoDisco = "=".repeat(tamanioDisco * 2 + 1);
                String bloqueDisco = "[" + cuerpoDisco + "]";
                int espaciosLaterales = 10 - tamanioDisco;
                String sangria = " ".repeat(Math.max(0, espaciosLaterales));
                area.append(sangria + bloqueDisco + sangria + "\n");
            } else {
                area.append("           |           \n");
            }
        }
        area.append("=======================");
    }
}