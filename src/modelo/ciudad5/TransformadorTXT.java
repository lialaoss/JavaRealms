package modelo.ciudad5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TransformadorTXT {
	private String textoOriginal;
	
	/*
     * Pre: Ninguna.
     * Post: Devuelve un texto largo que contiene todo el contenido original del archivo que se leyó.
     */
	public String getTextoOriginal() {
	    return textoOriginal;
	}
	
	/*
     * Pre: La 'rutaArchivo' debe ser válida. El 'arbol' y la 'lista' pasados por parámetro no deben ser nulos.
     * Post: Intenta abrir y leer el archivo línea por línea. Limpia cada palabra (quitando signos de puntuación y pasándola a minúsculas) y la guarda tanto en el árbol como en la lista con su ubicación exacta. Devuelve true si pudo leer todo con éxito o false si hubo algún error con el archivo.
     */
    public boolean cargarDatos(String rutaArchivo, ArbolABB arbol, ListaDinamica lista) {
    	StringBuilder textoCompleto = new StringBuilder();
    	
        boolean exito = true;

        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try {
            fileReader = new FileReader(rutaArchivo);
            bufferedReader = new BufferedReader(fileReader);

            String lineaTexto;
            int numeroLinea = 1;

            while ((lineaTexto = bufferedReader.readLine()) != null) {
            	textoCompleto.append(lineaTexto);
                textoCompleto.append("\n");
                
                String[] palabrasDeLaLinea = lineaTexto.split("\\s+");
                int posicionPalabra = 1;

                for (int i = 0; i < palabrasDeLaLinea.length; i++) {
                    String palabraSucia = palabrasDeLaLinea[i];
                    String palabraLimpia = palabraSucia.replaceAll("[^a-zA-Z0-9áéíóúÁÉÍÓÚñÑ]", "").toLowerCase().trim();
                
                    if (!palabraLimpia.isEmpty()) {
                        arbol.insertar(palabraLimpia, numeroLinea, posicionPalabra);
                        lista.insertar(palabraLimpia, numeroLinea, posicionPalabra);
                        posicionPalabra++;
                    }
                }
                numeroLinea++;
            } 
        } catch (IOException e) {
            System.out.println("Error al leer el archivo, " + e.getMessage());
            exito = false;
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                System.out.println("No se pudieron cerrar los flujos del archivo" + e.getMessage());
            }
        }
        textoOriginal = textoCompleto.toString();
        
        return exito;
    }
}
