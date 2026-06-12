package modelo.ciudad5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TransformadorTXT {
    public boolean cargarDatos(String rutaArchivo, ArbolABB arbol, ListaDinamica lista) {
        boolean exito = true;

        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try {
            fileReader = new FileReader(rutaArchivo);
            bufferedReader = new BufferedReader(fileReader);

            String lineaTexto;
            int numeroLinea = 1;

            while ((lineaTexto = bufferedReader.readLine()) != null) {
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
        return exito;
    }
}
