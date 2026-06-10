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
    
    private String algoritmoSeleccionado = "Seleccion de Algoritmo";

    public Ciudad4Minijuego(Ciudad ciudad, Jugador jugador, GestorRecursos recursos) {
        this.ciudad = ciudad;
        this.jugador = jugador;
        this.recursos = recursos;
    }

    @Override
    public void iniciar() {
        this.completado = false;
        // Vector de prueba para validar la conexion
        this.vectorActual = new int[]{34, 12, 5, 89, 56, 21, 7};
        this.algoritmoSeleccionado = "Bubble Sort";

        // Aislamos la ejecucion en un hilo secundario para no bloquear la vista
        Thread hiloOrdenamiento = new Thread(new Runnable() {
            @Override
            public void run() {
                BubbleSort bubble = new BubbleSort();
                // Ciudad4Minijuego.this pasa la referencia del observador
                bubble.ordenar(vectorActual, Ciudad4Minijuego.this);
                resultadoPartida();
            }
        });
        
        hiloOrdenamiento.start();
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
        g2.setColor(Color.WHITE);
        g2.drawString("Ciudad 4 - Modulo de Ordenamiento", 50, 50);
        g2.drawString("Algoritmo en ejecucion: " + algoritmoSeleccionado, 50, 80);

        if (vectorActual != null) {
            for (int i = 0; i < vectorActual.length; i++) {
                
                if (i == pivote && pivote != -1) {
                    g2.setColor(Color.GREEN); 
                } else if (i == indiceA || i == indiceB) {
                    g2.setColor(Color.RED); 
                } else {
                    g2.setColor(Color.WHITE); 
                }
                
                g2.drawRect(50 + (i * 60), 120, 50, 50);
                g2.drawString(String.valueOf(vectorActual[i]), 70 + (i * 60), 150);
            }
        }

        g2.setColor(Color.WHITE);
        if (completado) {
            g2.setColor(Color.CYAN);
            g2.drawString("Estado: Vector ordenado. Ciudad superada.", 50, 220);
        } else {
            g2.setColor(Color.YELLOW);
            g2.drawString("Procesando iteraciones del algoritmo...", 50, 220);
        }
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