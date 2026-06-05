package juegoLaberinto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class RenderizadorBMP {
	public static void generar(List<Snapshot> frames) throws IOException {

        for (int i = 0; i < frames.size(); i++) {
            escribirBMP(frames.get(i).estado, "frame_" + i + ".bmp");
        }
    }
	
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
	
	
	private static byte[] colorDe(char c) {

	    switch (c) {

	        case '#': return new byte[]{0, 0, 0};         // negro
	        case '.': return new byte[]{(byte)255,(byte)255,(byte)255}; // blanco
	        case 'I': return new byte[]{0, (byte)255, 0}; // verde
	        case 'F': return new byte[]{0, 0, (byte)255}; // rojo
	        case '*': return new byte[]{(byte)0, (byte)255, (byte)255}; // celeste
	        case 'A': return new byte[]{(byte)255, 0, (byte)255}; // magenta

	        default:  return new byte[]{(byte)200,(byte)200,(byte)200};
	    }
	}
	
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
	
	private static void intToBytes(int value, byte[] arr, int offset) {

	    arr[offset] = (byte)(value);
	    arr[offset + 1] = (byte)(value >> 8);
	    arr[offset + 2] = (byte)(value >> 16);
	    arr[offset + 3] = (byte)(value >> 24);
	}
}