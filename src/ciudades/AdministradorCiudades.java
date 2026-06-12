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
	
	public AdministradorCiudades() {
		cargarDatosCiudades();
		this.grafo = new ConexionesCiudades(ciudades, ID_CIUDAD_INICIAL);
		cargarDatosVecinos();
	}
	
	// ========================= CARGA DE CIUDADES =========================
	
	/**
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
	 * Agrega un arista en el grafo para poder conectar una ciudad a otra
	 * agregandoa su vez el peso, el cual serian los puntos necesarios para
	 * poder ingresar a la ciudad
	 * @param datos
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
