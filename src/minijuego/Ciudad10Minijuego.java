package minijuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;

import ciudades.Ciudad;
import ciudades.EstadoCiudad;
import entidad.Jugador;
import logica.KeyHandler;
import modelo.ciudad10.EcuacionRecurrencia;
import modelo.ciudad10.ExpansorEcuacion;
import modelo.ciudad10.ParserEcuacion;
import modelo.ciudad10.SolucionadorTeoremaMaestro;

public class Ciudad10Minijuego implements Minijuego {

    private Ciudad ciudad;
    private Jugador jugador;

    // Estado del minijuego
    private String inputUsuario = "";
    private String resultado = "";
    private List<String> pasos = null;
    private String error = "";
    private boolean ganado = false;

    public Ciudad10Minijuego(Ciudad ciudad, Jugador jugador) {
        this.ciudad = ciudad;
        this.jugador = jugador;
    }

    @Override
    public void iniciar() {
        // nada que inicializar
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 1152, 576);

        g2.setFont(new Font("Monospaced", Font.BOLD, 16));
        g2.setColor(Color.YELLOW);
        g2.drawString("Ciudad 10 - Complejidad Algorítmica", 50, 40);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced", Font.PLAIN, 14));
        g2.drawString("Ingresá una ecuación. Ejemplo: T(n) = 2T(n/2) + O(n)", 50, 70);
        g2.drawString("Entrada: " + inputUsuario + "_", 50, 100);

        if (!error.isEmpty()) {
            g2.setColor(Color.RED);
            g2.drawString("Error: " + error, 50, 130);
        }

        if (!resultado.isEmpty()) {
            g2.setColor(Color.GREEN);
            g2.drawString("Resultado: " + resultado, 50, 160);
        }

        if (pasos != null) {
            g2.setColor(Color.CYAN);
            g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
            int y = 190;
            for (String paso : pasos) {
                if (y > 550) { break; }
                g2.drawString(paso, 50, y);
                y += 18;
            }
        }

        if (ganado) {
            g2.setColor(Color.GREEN);
            g2.setFont(new Font("Monospaced", Font.BOLD, 18));
            g2.drawString("¡Ciudad completada! Q para volver.", 50, 540);
        } else {
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
            g2.drawString("ENTER para resolver | BACKSPACE para borrar | Q para volver", 50, 555);
        }
    }

    /**
     * Recibe input de teclado — llamar desde Panel.update()
     */
    public void procesarCaracter(char c) {
        if (c == '\n' || c == '\r') {
            resolver();
        } else if (c == '\b') {
            if (!inputUsuario.isEmpty()) {
                inputUsuario = inputUsuario.substring(0, inputUsuario.length() - 1);
            }
        } else {
            inputUsuario += c;
        }
    }

    private void resolver() {
        error = "";
        resultado = "";
        pasos = null;

        try {
            ParserEcuacion parser = new ParserEcuacion();
            EcuacionRecurrencia ec = parser.parsear(inputUsuario);

            SolucionadorTeoremaMaestro solucionador = new SolucionadorTeoremaMaestro();
            resultado = solucionador.resolver(ec);

            ExpansorEcuacion expansor = new ExpansorEcuacion();
            pasos = expansor.expandirPasoAPaso(ec, 4);

            ganado = true;
            resultadoPartida();

        } catch (Exception e) {
            error = e.getMessage();
        }
    }

    @Override
    public void resultadoPartida() {
        if (ganado) {
            desbloquearVecinos();
        }
    }

    @Override
    public void desbloquearVecinos() {
        ciudad.setEstado(EstadoCiudad.COMPLETADA);
    }

    public String getInputUsuario() {
        return inputUsuario;
    }
}