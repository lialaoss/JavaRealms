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
	/**
	 * Crea el administrador de ciudades, cargando las ciudades
	 * y sus conexiones desde los archivos correspondientes.
	 * Pre: Los archivos de ciudades y vecinos deben existir.
	 * Post:
	 * - Las ciudades son cargadas en memoria.
	 * - El grafo de conexiones queda inicializado.
	 * - Las relaciones de vecindad quedan establecidas.
	 */
	public AdministradorCiudades() {
		cargarDatosCiudades();
		this.grafo = new ConexionesCiudades(ciudades, ID_CIUDAD_INICIAL);
		cargarDatosVecinos();
	}
	
	// ========================= CARGA DE CIUDADES =========================
	
	/**
	 *
	 * Carga las ciudades desde el archivo de datos.
	 * Pre: El archivo de ciudades debe existir y respetar
	 * el formato establecido.
	 * Post: Todas las ciudades del archivo son agregadas a
	 * la colección de ciudades.
	 * 
	 * formato linea:
	 * NombreCiudad;Puntos;ID
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
	
	/**
	 * Crea una ciudad a partir de los datos recibidos y la
	 * agrega a la colección de ciudades.
	 * Pre: 
	 *  - datos != null
	 *  - datos debe contener nombre, experiencia e id.
	 * Post: La ciudad creada queda almacenada en la colección.
	 * @param datos información de la ciudad.
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
	

	/**
	 * Carga las conexiones entre ciudades desde el archivo
	 * de vecinos.
	 * Pre: El archivo de vecinos debe existir y respetar
	 * el formato establecido.
	 * Post: Todas las conexiones definidas son agregadas al grafo.
	 * 
	 * formato linea:
	 * NombreCiudad;PesoArista;IDVecino
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
	
	/**
	 * Agrega una conexión entre dos ciudades en el grafo, indicando el
	 * costo necesario para acceder a ella.
	 * Pre:
	 * - datos != null.
	 * - Las ciudades indicadas deben existir.
	 * - datos debe contener idCiudad, peso e idVecino.
	 * Post:
	 * - Se crea una conexión entre ambas ciudades con el peso especificado.
	 * @param datos: información de la conexión.
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

	/**
	 * Abre un archivo de recursos y devuelve un lector
	 * para acceder a su contenido.
	 * Pre:
	 * - ruta != null.
	 * - El archivo debe existir en la ruta indicada.
	 * Post: Se devuelve un BufferedReader asociado al archivo.
	 *
	 * @param ruta ubicación del archivo.
	 * @return lector para acceder al contenido.
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
