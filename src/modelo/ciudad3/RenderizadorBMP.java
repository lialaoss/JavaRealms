package modelo.ciudad3;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class RenderizadorBMP {
	/*
     * Pre: La lista de 'frames' no debe ser nula y debe contener los estados del laberinto.
     * Post: Recorre todos los snapshots y genera un archivo físico de imagen con formato .bmp para cada uno de ellos, numerados secuencialmente.
     */
	public static void generar(List<Snapshot> frames) throws IOException {

        for (int i = 0; i < frames.size(); i++) {
            escribirBMP(frames.get(i).estado, "frame_" + i + ".bmp");
        }
    }
	
	/*
     * Pre: La matriz de caracteres 'mapa' debe estar correctamente inicializada y el 'nombre' del archivo no debe estar vacío.
     * Post: Crea un archivo de imagen BMP en el disco, escribe su encabezado correspondiente y traduce cada caracter del mapa en píxeles de colores de abajo hacia arriba, aplicando el relleno (padding) necesario para cumplir con el formato BMP.
     */
	public static void escribirBMP(char[][] mapa, String nombre) throws IOException {

	    int ancho = mapa[0].length;
	    int alto = mapa.length;

	    FileOutputStream fos = new FileOutputStream(nombre);

	    int fileSize = 54 + 3 * ancho * alto;

	    byte[] bmpHeader = crearHeader(fileSize, ancho, alto);

	    fos.write(bmpHeader);

	    for (int y = alto - 1; y >= 0; y--) {
	        for (int x = 0; x < ancho; x++) {

	            byte[] color = colorDe(mapa[y][x]);

	            fos.write(color);
	        }

	        int rowSize = ancho * 3;
	        int padding = (4 - (rowSize % 4)) % 4;

	        for (int p = 0; p < padding; p++) {
	            fos.write(0);
	        }
	    }

	    fos.close();
	}
	
	/*
     * Pre: Ninguna.
     * Post: Devuelve un arreglo de 3 bytes (formato RGB) que representa el color asignado a cada caracter especial del mapa (como paredes, caminos, inicio, fin o visitados).
     */
	private static byte[] colorDe(char c) {

	    switch (c) {

	        case '#': return new byte[]{0, 0, 0};         // negro
	        case '.': return new byte[]{(byte)255,(byte)255,(byte)255}; // blanco
	        case 'I': return new byte[]{0, (byte)255, 0}; // verde
	        case 'F': return new byte[]{0, 0, (byte)255}; // rojo
	        case '*': return new byte[]{(byte)0, (byte)255, (byte)255}; // celeste
	        case 'A': return new byte[]{(byte)255, 0, (byte)255}; // magenta
	        
	        case 'P':
	            return new byte[]{
	                0,
	                (byte)165,
	                (byte)255
	            };

	        default:  return new byte[]{(byte)200,(byte)200,(byte)200};
	    }
	}
	
	/*
     * Pre: 'fileSize', 'width' (ancho) y 'height' (alto) deben ser valores mayores a cero y coherentes con la imagen a crear.
     * Post: Construye y devuelve un arreglo de 54 bytes con la estructura de cabecera obligatoria que requiere cualquier archivo BMP de 24 bits para que la computadora pueda leerlo como una imagen válida.
     */
	private static byte[] crearHeader(int fileSize, int width, int height) {

	    byte[] header = new byte[54];

	    header[0] = 'B';
	    header[1] = 'M';

	    intToBytes(fileSize, header, 2);
	    intToBytes(54, header, 10);
	    intToBytes(40, header, 14);
	    intToBytes(width, header, 18);
	    intToBytes(height, header, 22);
	    header[26] = 1;
	    header[28] = 24;
	    
	    header[30] = 0;

	    return header;
	}
	
	/*
     * Pre: El arreglo 'arr' debe estar inicializado y tener el tamaño suficiente para soportar la escritura a partir de la posición 'offset'.
     * Post: Descompone un número entero de 32 bits en 4 bytes individuales usando operaciones de desplazamiento de bits (shifting) y los guarda en el orden correspondiente (Little Endian).
     */
	private static void intToBytes(int value, byte[] arr, int offset) {

	    arr[offset] = (byte)(value);
	    arr[offset + 1] = (byte)(value >> 8);
	    arr[offset + 2] = (byte)(value >> 16);
	    arr[offset + 3] = (byte)(value >> 24);
	}
}