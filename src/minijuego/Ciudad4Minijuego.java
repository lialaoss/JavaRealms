package minijuego;

import java.awt.Color;
import java.awt.Graphics2D;

import ciudades.Ciudad;
import ciudades.EstadoCiudad;
import entidad.Jugador;
import ui.GestorRecursos;
import modelo.ciudad4.BubbleSort;
import modelo.ciudad4.ObservadorOrdenamiento;

public class Ciudad4Minijuego implements Minijuego, ObservadorOrdenamiento {

    private Ciudad ciudad;
    private Jugador jugador;
    private GestorRecursos recursos;
    
    private int[] vectorActual;
    private int indiceA = -1;
    private int indiceB = -1;
    private int pivote = -1;
    private boolean completado = false;
    
    private enum Fase { INPUT_VECTOR, INPUT_ALGORITMO, ORDENANDO }
    private Fase fase = Fase.INPUT_VECTOR;
    private String inputActual = "";
    private String error = "";
    
    private String algoritmoSeleccionado = "Seleccion de Algoritmo";

    public Ciudad4Minijuego(Ciudad ciudad, Jugador jugador, GestorRecursos recursos) {
        this.ciudad = ciudad;
        this.jugador = jugador;
        this.recursos = recursos;
    }

    @Override
    public void iniciar() {
        this.completado = false;
        this.fase = Fase.INPUT_VECTOR;
    }

    @Override
    public void notificarCambio(int[] vector, int indiceA, int indiceB, int pivote) {
        this.vectorActual = vector.clone();
        this.indiceA = indiceA;
        this.indiceB = indiceB;
        this.pivote = pivote;

        // Cumplimiento del contrato: generamos una pausa para permitir la animacion
        try {
            Thread.sleep(500); // Pausa de 500 milisegundos para que la interfaz dibuje
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 1152, 576);

        g2.setColor(Color.YELLOW);
        g2.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 16));
        g2.drawString("Ciudad 4 - Ordenamiento", 50, 35);

        g2.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 14));
        g2.setColor(Color.WHITE);

        if (fase == Fase.INPUT_VECTOR) {
            g2.drawString("Ingresa los números a ordenar separados por espacios:", 50, 70);
            g2.drawString("Ejemplo: 34 12 5 89 56 21 7", 50, 95);
            g2.drawString("> " + inputActual + "_", 50, 120);
        } else if (fase == Fase.INPUT_ALGORITMO) {
            g2.drawString("Seleccioná el algoritmo:", 50, 70);
            g2.drawString("1 - Bubble Sort", 50, 100);
            g2.drawString("2 - QuickSort", 50, 125);
            g2.drawString("> " + inputActual + "_", 50, 155);
        } else if (fase == Fase.ORDENANDO) {
            g2.drawString("Algoritmo: " + algoritmoSeleccionado, 50, 70);
            if (vectorActual != null) {
                for (int i = 0; i < vectorActual.length; i++) {
                    if (i == pivote && pivote != -1) {
                        g2.setColor(Color.GREEN);
                    } else if (i == indiceA || i == indiceB) {
                        g2.setColor(Color.RED);
                    } else {
                        g2.setColor(Color.WHITE);
                    }
                    int barHeight = vectorActual[i] * 3;
                    g2.fillRect(50 + (i * 80), 400 - barHeight, 60, barHeight);
                    g2.setColor(Color.WHITE);
                    g2.drawString(String.valueOf(vectorActual[i]), 70 + (i * 80), 420);
                }
            }
            if (completado) {
                g2.setColor(Color.CYAN);
                g2.drawString("¡Vector ordenado! Ciudad superada.", 50, 460);
            } else {
                g2.setColor(Color.YELLOW);
                g2.drawString("Ordenando...", 50, 460);
            }
        }

        if (!error.isEmpty()) {
            g2.setColor(Color.RED);
            g2.drawString("Error: " + error, 50, 555);
        }
        g2.setColor(Color.GRAY);
        g2.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        g2.drawString("Q para volver al mapa", 50, 570);
    }


    public void procesarCaracter(char c) {
        error = "";
        if (c == '\n' || c == '\r') {
            procesarEnter();
        } else if (c == '\b') {
            if (!inputActual.isEmpty()) {
                inputActual = inputActual.substring(0, inputActual.length() - 1);
            }
        } else {
            inputActual += c;
        }
    }
    
    private void procesarEnter() {
        if (fase == Fase.INPUT_VECTOR) {
            String[] partes = inputActual.trim().split("\\s+");
            if (partes.length < 2) {
                error = "Ingresá al menos 2 números.";
                return;
            }
            try {
                vectorActual = new int[partes.length];
                for (int i = 0; i < partes.length; i++) {
                    vectorActual[i] = Integer.parseInt(partes[i]);
                }
                inputActual = "";
                fase = Fase.INPUT_ALGORITMO;
            } catch (NumberFormatException e) {
                error = "Solo se permiten números enteros.";
            }
        } else if (fase == Fase.INPUT_ALGORITMO) {
            if (inputActual.trim().equals("1")) {
                algoritmoSeleccionado = "Bubble Sort";
            } else if (inputActual.trim().equals("2")) {
                algoritmoSeleccionado = "QuickSort";
            } else {
                error = "Ingresá 1 o 2.";
                return;
            }
            inputActual = "";
            fase = Fase.ORDENANDO;
            arrancarOrdenamiento();
        }
    }
    
    private void arrancarOrdenamiento() {
        Thread hilo = new Thread(() -> {
            if (algoritmoSeleccionado.equals("Bubble Sort")) {
                new modelo.ciudad4.BubbleSort().ordenar(vectorActual, Ciudad4Minijuego.this);
            } else {
                new modelo.ciudad4.QuickSort().ordenar(vectorActual, Ciudad4Minijuego.this);
            }
            resultadoPartida();
        });
        hilo.start();
    }

    @Override
    public void resultadoPartida() {
        this.completado = true;
        desbloquearVecinos();
    }

    @Override
    public void desbloquearVecinos() {
        ciudad.setEstado(EstadoCiudad.COMPLETADA);
    }
    
    public void setAlgoritmoSeleccionado(String nombre) {
        this.algoritmoSeleccionado = nombre;
    }

	@Override
	public void procesarClick(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}
}