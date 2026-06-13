package modelo.ciudad5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase encargada de la lectura, limpieza y transformación de archivos
 * para la indexación de datos en las estructuras de búsqueda.
 */

public class TransformadorTXT {
    /**
     * Lee un archivo de texto, limpia sus palabras y las guarda en el árbol y la lista.
     * * PRE: rutaArchivo != null y debe apuntar a un archivo existente.
     * arbol != null y lista != null (instancias creadas en memoria).
     * POST: El archivo se lee por completo. Las palabras se pasan a minúsculas,
     * se limpian de signos y se guardan con su línea y posición exacta.
     * * @param rutaArchivo Dirección o nombre del archivo de texto de origen.
     * @param arbol Estructura de tipo Árbol Binario para la Ardilla 1.
     * @param lista Estructura de tipo Lista Dinámica para la Ardilla 2.
     * @return boolean true si la carga fue exitosa, false si hubo algún error.
     */
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
