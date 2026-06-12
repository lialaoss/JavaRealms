package ciudad9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.Random;

/*
  Proporciona una pregunta de opción múltiple cargada desde un archivo.
 */
public class Pregunta {
    private final String enunciado;
    private final Vector<String> opciones; 
    private final int indiceCorrecto;
    
  
    private static final Vector<Pregunta> bancoPreguntas = new Vector<>();
    private static final Vector<Pregunta> preguntasDisponibles = new Vector<>();

    /*
     Pre: 'opciones' no debe ser nulo y debe tener al menos una opción.
     Post: Se instancia una Pregunta con los parámetros dados.
     */
    public Pregunta(String enunciado, Vector<String> opciones, int indiceCorrecto) {
        this.enunciado = enunciado;
        this.opciones = opciones;
        this.indiceCorrecto = indiceCorrecto;
    }

    public String getEnunciado() { return enunciado; }
    public Vector<String> getOpciones() { return opciones; }
    public int getIndiceCorrecto() { return indiceCorrecto; }

    /*
     Pre: 'rutaArchivo' debe ser un path válido a un archivo TXT.
     Post: Se cargan las preguntas del archivo y se almacenan en las listas internas, quedando listas para ser utilizadas.
     */
    public static void cargarDesdeArchivo(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                
                String[] partes = linea.split(";");
                if (partes.length >= 3) {
                    String enunciado = partes[0];
                    int indiceCorrecto = Integer.parseInt(partes[partes.length - 1].trim());
                    
                    Vector<String> opcionesTemp = new Vector<>();
                    for(int i = 1; i < partes.length - 1; i++) {
                        opcionesTemp.add(partes[i].trim());
                    }
                    
                    Pregunta nuevaPregunta = new Pregunta(enunciado, opcionesTemp, indiceCorrecto);
                    bancoPreguntas.add(nuevaPregunta);
                    preguntasDisponibles.add(nuevaPregunta);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al cargar el archivo de preguntas: " + e.getMessage());
        }
    }

    /*
     Pre: Las preguntas deben haber sido cargado.
     Poat: Devuelve una pregunta al azar, removiéndola de las disponibles. Si no hay más disponibles, las reinicia.
     */
    public static Pregunta obtenerAleatoria() {
        if (bancoPreguntas.isEmpty()) {
            Vector<String> opcError = new Vector<>();
            opcError.add("Sí"); opcError.add("No");
            return new Pregunta("¿Falta el archivo TXT?", opcError, 0);
        }
        
        if (preguntasDisponibles.isEmpty()) {
            preguntasDisponibles.addAll(bancoPreguntas);
        }
        
        Random rand = new Random();
        int indiceElegido = rand.nextInt(preguntasDisponibles.size());
        
        return preguntasDisponibles.remove(indiceElegido);
    }
}