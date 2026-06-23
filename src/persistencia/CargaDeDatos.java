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
	private Map<Integer, Ciudad> ciudades;
	
	// CONSTRUCTOR
	public CargaDeDatos(AdministradorJuego admin) {
		this.admin = admin;
		this.ciudades = admin.getAdminCiudades().getCiudades();
		cargarDatosJuego();
	}
	
	// ================ CARGA DE DATOS DEL JUEGO ================
	
	
	private void cargarDatosJuego() {
		try (BufferedReader br = new BufferedReader(new FileReader(RUTA_DATOS))) {
		    
	        String line;
	        
	        if((line = br.readLine()) != null) {
				admin.getJugador().setPuntosExperiencia(Integer.parseInt(line));
	        }

	        while ((line = br.readLine()) != null) {
				Ciudad ciudad = admin.getAdminCiudades().getCiudades().get(Integer.parseInt(line));
				ciudad.setEstado(EstadoCiudad.COMPLETADA);
				admin.desbloquearVecinos(ciudad);
	        }
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	}
	
	// ========== ACTUALIZACION DE DATOS DEL JUEGO ==============
	
	public void actualizarArchivoDeDatos() {
		System.out.println(new File(RUTA_DATOS).getAbsolutePath());
		System.out.println(new File(RUTA_DATOS).exists());
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_DATOS))) {

	        bw.write(String.valueOf(admin.getJugador().getPuntosExperiencia()));
	        bw.newLine();
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
	
	public void eliminarDatosDeArchivo() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_DATOS))) {
	        bw.write("0");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	// ============= RESTAURAR ULTIMOS DATOS DEL JUEGO ==================
	
	public void restaurarUltimosDatos() {
        for (Integer id : ciudades.keySet()) {
        	ciudades.get(id).setEstado(EstadoCiudad.BLOQUEADA);
        }
        cargarDatosJuego();
        if(this.ciudades.get(1).getEstado() != EstadoCiudad.COMPLETADA) {
			this.ciudades.get(1).setEstado(EstadoCiudad.DESBLOQUEADA);
		}
	}
}
