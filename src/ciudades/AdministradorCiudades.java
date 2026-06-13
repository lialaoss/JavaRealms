package ciudades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import utiles.Validaciones;

public class AdministradorCiudades {
	
	// CONSTANTES
	private final String RUTA_CIUDADES = "/datosJuego/Ciudades.txt";
	private final String RUTA_VECINOS = "/datosJuego/Vecinos.txt";
	private final String SEPARADOR = ";";
	private final int ID_CIUDAD_INICIAL = 1; // ...
	
	// ATRIBUTOS
	private Map<Integer, Ciudad> ciudades = new HashMap<>();
	private ConexionesCiudades grafo;

	// CONSTRUCTOR
	
	/*
	 *  Pre: Los archivos de texto de ciudades y vecinos deben existir en la carpeta correcta del proyecto.
	 * Post: Crea el administrador, lee los archivos de texto, guarda las ciudades en la memoria y arma el grafo con todos los caminos conectados.
	 */
	public AdministradorCiudades() {
		cargarDatosCiudades();
		this.grafo = new ConexionesCiudades(ciudades, ID_CIUDAD_INICIAL);
		cargarDatosVecinos();
	}
	
	// ========================= CARGA DE CIUDADES =========================
	
	/*
	 *  Pre: El archivo de ciudades debe existir y cada renglón debe respetar el formato (NombreCiudad;Puntos;ID).
	 * Post: Lee el archivo línea por línea y guarda todas las ciudades encontradas en la colección del juego.
	 */
	private void cargarDatosCiudades() {
		
		try (BufferedReader br = abrirArchivo(RUTA_CIUDADES)) {

	        String line;

	        while ((line = br.readLine()) != null) {
				String[] datos = line.split(SEPARADOR);
				agregarCiudad(datos);
	        }
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	}
	
	/*
	 *  Pre: El arreglo "datos" no puede ser nulo y debe tener justo el nombre, los puntos y el ID de la ciudad.
	 * Post: Construye el objeto Ciudad con esa información y lo guarda en el mapa de ciudades usando su ID como clave.
	 */
	private void agregarCiudad(String[] datos) {
		Validaciones.esDistintoDeNull(datos, "datos");
		String nombre = datos[0];
		Integer puntosDeExperiencia = Integer.parseInt(datos[1]);
		Integer id = Integer.parseInt(datos[2]);
		
		Ciudad ciudad = new Ciudad(nombre, puntosDeExperiencia, id);
		this.ciudades.put(id, ciudad);
	}
	
	// ========================= CARGA DE VECINOS =========================
	
	/*
	 *  Pre: El archivo de vecinos debe existir y cada renglón debe respetar el formato (ID_Ciudad;PesoArista;ID_Vecino).
	 * Post: Lee el archivo y va creando en el grafo los caminos que unen a las ciudades.
	 */
	private void cargarDatosVecinos() {
	    try (BufferedReader br = abrirArchivo(RUTA_VECINOS)) {

	        String line;

	        while ((line = br.readLine()) != null) {
	            String[] datos = line.split(SEPARADOR);
	            agregarVecino(datos);
	        }
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/*
	 *  Pre: El arreglo "datos" no puede ser nulo, y las dos ciudades indicadas en los datos ya deben existir en el juego.
	 * Post: Busca las dos ciudades y crea un camino entre ellas en el grafo, indicando cuántos puntos cuesta cruzarlo (el peso).
	 */
	private void agregarVecino(String[] datos) {
		Validaciones.esDistintoDeNull(datos, "datos");
		
		Integer idCiudad = Integer.parseInt(datos[0]);
		Integer peso = Integer.parseInt(datos[1]);
		Integer idVecino = Integer.parseInt(datos[2]);

		Ciudad ciudad = ciudades.get(idCiudad);
		Ciudad vecino = ciudades.get(idVecino);
		
		if (ciudad == null || vecino == null) {
		    throw new IllegalStateException("Ciudad o vecino no existe");
		}
		
		grafo.crearConexion(ciudad, vecino, peso);
		
	}

    // ========================= UTIL =========================

	/* 
	 * Pre: La ruta ingresada no puede ser nula y el archivo tiene que estar en ese lugar.
	 * Post: Abre el archivo y devuelve una herramienta (BufferedReader) lista para que el programa pueda leer sus textos.
	 */
    private BufferedReader abrirArchivo(String ruta) {
    	Validaciones.esDistintoDeNull(ruta, "ruta");
        InputStream is = getClass().getResourceAsStream(ruta);

        if (is == null) {
            throw new RuntimeException("No se encontró el archivo: " + ruta);
        }

        return new BufferedReader(new InputStreamReader(is));
    }
	
	// GETTERS

	public Map<Integer, Ciudad> getCiudades() {
		return ciudades;
	}

	public ConexionesCiudades getGrafo() {
		return grafo;
	}
}