package minijuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;

import ciudades.Ciudad;
import ciudades.EstadoCiudad;
import entidad.Jugador;
import modelo.ciudad10.EcuacionRecurrencia;
import modelo.ciudad10.ExpansorEcuacion;
import modelo.ciudad10.ParserEcuacion;
import modelo.ciudad10.SolucionadorTeoremaMaestro;
import render.FinMinijuegoPantalla;

public class Ciudad10Minijuego implements Minijuego {

    private Ciudad ciudad;
    private Jugador jugador;
    private String inputUsuario = "";
    private String resultado = "";
    private List<String> pasos = null;
    private String error = "";
    private boolean ganado = false;

    private FinMinijuegoPantalla pantallaFinal = new FinMinijuegoPantalla();

    /*
     * Pre: La ciudad y el jugador ya deben estar creados y no ser nulos.
     * Post: Crea el minijuego de la Ciudad 10 guardando los datos para usarlos cuando el jugador gane.
     */
    public Ciudad10Minijuego(Ciudad ciudad, Jugador jugador) {
        this.ciudad = ciudad;
        this.jugador = jugador;
    }

    /*
     * Pre: Ninguna.
     * Post: Método requerido por la interfaz Minijuego. Acá arranca vacío porque este juego en particular se maneja a medida que el jugador tipea.
     */
    @Override
    public void iniciar() {
    }

    /* 
     * Pre: El motor gráfico de Java (Graphics2D) debe estar inicializado.
     * Post: Dibuja toda la pantalla negra, las instrucciones en blanco, y va mostrando en tiempo real lo que el jugador escribe. Si gana, dibuja la pantalla de victoria.
     */
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
			pantallaFinal.mostrarResultados(g2, ciudad);
        } else {
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
            g2.drawString("ENTER para resolver | BACKSPACE para borrar | Q para volver", 50, 555);
        }
    }

    /* 
     * Pre: El usuario presiona una tecla válida en su teclado.
     * Post: Si toca ENTER, manda a resolver la ecuación. Si toca BACKSPACE, borra la última letra. Si toca cualquier otra cosa, la suma al texto que aparece en pantalla.
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
    
    /* 
     * Pre: El jugador tiene que haber apretado ENTER después de escribir algo.
     * Post: Intenta traducir la ecuación que escribió el jugador y la resuelve con el Teorema Maestro. Si está todo bien, muestra los pasos y le da la victoria. Si escribió cualquier cosa, tira un error en pantalla.
     */
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
    
    /* 
     * Pre: El jugador resolvió bien la ecuación.
     * Post: Si la variable ganado es true, cambia el estado de la ciudad a COMPLETADA y le regala los puntos de experiencia al jugador.
     */
    @Override
    public void resultadoPartida() {
        if (ganado) {
            ciudad.setEstado(EstadoCiudad.COMPLETADA);
			jugador.sumarPuntos(ciudad.getPuntosDeExperiencia());
        }
    }
    
    /* 
     * Pre: Ninguna.
     * Post: Devuelve el texto exacto que el jugador lleva escrito hasta el momento.
     */
    public String getInputUsuario() {
        return inputUsuario;
    }

    /* 
     * Pre: El usuario hace click en la pantalla.
     * Post: Por ahora no hace nada, porque este minijuego específico se maneja 100% con el teclado. Queda vacío para cumplir con la interfaz.
     */
	@Override
	public void procesarClick(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}
}