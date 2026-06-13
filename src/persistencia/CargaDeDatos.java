package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import ciudades.Ciudad;
import ciudades.EstadoCiudad;
import logica.AdministradorJuego;

public class CargaDeDatos {
	
	// CONSTANTES
	private final String RUTA_DATOS = "DatosGuardados.txt";
	
	// ATRIBUTOS
	private AdministradorJuego admin;
	
	/*
	 * Pre: El 'admin' (administrador del juego) no debe ser nulo y tiene que estar correctamente inicializado.
	 * Post: Crea el cargador de datos vinculándolo al administrador e intenta leer el archivo guardado para restaurar el progreso del jugador inmediatamente.
	 */
	public CargaDeDatos(AdministradorJuego admin) {
		this.admin = admin;
		cargarDatosJuego();
	}
	
	// ================ CARGA DE DATOS DEL JUEGO ================
	
	/*
	 * Pre: Debe existir el archivo "DatosGuardados.txt" con el formato correcto (primera línea con el puntaje y las siguientes con las IDs de las ciudades completadas).
	 * Post: Lee el archivo línea por línea, le asigna los puntos de experiencia guardados al jugador, marca como completadas las ciudades que correspondan y desbloquea sus ciudades vecinas en el mapa del juego.
	 */
	private void cargarDatosJuego() {
		try (BufferedReader br = new BufferedReader(new FileReader(RUTA_DATOS))) {
		    
	        String line;
	        
	        if((line = br.readLine()) != null) {
				System.out.println("Dato puntaje jugador: " + line);
				admin.getJugador().setPuntosExperiencia(Integer.parseInt(line));
	        }

	        while ((line = br.readLine()) != null) {
				System.out.println("Dato ciudad: " + line);
				Ciudad ciudad = admin.getAdminCiudades().getCiudades().get(Integer.parseInt(line));
				ciudad.setEstado(EstadoCiudad.COMPLETADA);
				admin.desbloquearVecinos(ciudad);
	        }
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	}
	
	// ========== ACTUALIZACION DE DATOS DEL JUEGO ==============
	
	/*
	 * Pre: El administrador debe tener acceso válido a los datos del jugador y a la lista de ciudades.
	 * Post: Sobrescribe el archivo de guardado guardando los puntos de experiencia actuales en la primera línea, y luego escribe el identificador (ID) de cada una de las ciudades que el jugador ya haya completado.
	 */
	public void actualizarArchivoDeDatos() {
		System.out.println(new File(RUTA_DATOS).getAbsolutePath());
		System.out.println(new File(RUTA_DATOS).exists());
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_DATOS))) {

	        bw.write(String.valueOf(admin.getJugador().getPuntosExperiencia()));
	        bw.newLine();
	        Map<Integer, Ciudad> ciudades = admin.getAdminCiudades().getCiudades();
	        for (Integer id : ciudades.keySet()) {
	        	
	        	if(ciudades.get(id).getEstado() == EstadoCiudad.COMPLETADA) {
		        	bw.write(String.valueOf(ciudades.get(id).getId()));
		            bw.newLine();
	        	}
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	// =============== ELIMINACION DE DATOS DEL JUEGO ===================
	
	/*
	 * Pre: Ninguna.
	 * Post: Limpia o resetea el archivo de guardado, dejando únicamente un "0" escrito en él, ideal para comenzar una nueva partida desde cero.
	 */
	public void eliminarDatosDeArchivo() {
		System.out.println(new File(RUTA_DATOS).getAbsolutePath());
		System.out.println(new File(RUTA_DATOS).exists());
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_DATOS))) {
	        bw.write("0");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
}
