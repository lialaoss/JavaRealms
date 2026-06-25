package render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import modelo.ciudad1.Elemento;
import ui.ConfiguracionPantalla;

public class RenderElementos {
    private final int TILE_SIZE = ConfiguracionPantalla.TILE_SIZE;
    private int x, y, z;
    private Elemento elemento;
    private boolean recolectado;
    private Image imagen;
    

    /*
     * Pre: Las coordenadas 'x', 'y', 'z' deben ser posiciones válidas en el mapa. El 'elemento' lógico no debe ser nulo.
     * Post: Crea el objeto visual en las coordenadas indicadas, lo vincula con su elemento lógico correspondiente y lo marca inicialmente como no recolectado.
     */
    public RenderElementos(int x, int y, int z, Elemento elemento) {

    public RenderElementos(int x, int y, int z, Elemento elemento, Image imagen) {

    	this.x = x;
    	this.y = y;
    	this.z = z;
    	setElemento(elemento);
    	setImagen(imagen);
    	this.recolectado = false;
    }

    /*
     * Pre: El motor gráfico 'g2' no debe ser nulo. 'centroX' y 'centroY' deben ser las coordenadas de origen de la cámara.
     * Post: Calcula la posición exacta en la pantalla basándose en el tamaño de los azulejos (tiles) y dibuja el elemento como un cuadrado de color azul.
     */
    public void dibujar(Graphics2D g2, int centroX, int centroY) {
    	if (imagen != null) {
    		int pantallaX = centroX + x * TILE_SIZE;
            int pantallaY = centroY + y * TILE_SIZE;
            g2.drawImage(imagen, pantallaX, pantallaY, TILE_SIZE, TILE_SIZE, null);
        } else {
            g2.setColor(Color.GRAY);
            g2.fillRect(x, y, 80, 80);
        }
    }
    
    /*
     * Pre: Ninguna.
     * Post: Cambia el estado del elemento a recolectado (marcando que ya fue encontrado por el jugador).
     */
    public void elementoEncontrado() {
    	this.recolectado = true;
    }
    
    /*
     * Pre: Ninguna.
     * Post: Devuelve true si el elemento ya fue recolectado por el jugador, o false si todavía sigue en el mapa.
     */
    public boolean getEncontrado() {
    	return recolectado;
    }

    /*
     * Pre: Ninguna.
     * Post: Devuelve el elemento lógico (datos y tipo) que está asociado a este objeto visual.
     */
	public Elemento getElemento() {
		return elemento;
	}
	
	private void setImagen(Image imagen) {
		this.imagen = imagen;
	}

	/*
     * Pre: El 'elemento' lógico a asignar no debe ser nulo.
     * Post: Actualiza o vincula un nuevo elemento lógico a este componente de renderizado.
     */
	public void setElemento(Elemento elemento) {
		this.elemento = elemento;
	}

	/*
     * Pre: Ninguna.
     * Post: Devuelve la coordenada 'z' (la capa o piso del mapa) en la que se encuentra este elemento.
     */
	public int getZ() {
		return z;
	}

	/*
     * Pre: La capa 'z' debe ser un número válido dentro de los límites de altura del mapa.
     * Post: Modifica la capa o piso en la que está ubicado el elemento.
     */
	public void setZ(int z) {
		this.z = z;
	}
    
    
}
