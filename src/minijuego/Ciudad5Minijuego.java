package minijuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.io.File; 
import java.util.Random;
import javax.swing.JFileChooser; 
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter; 

import ciudades.Ciudad;
import ciudades.EstadoCiudad;
import entidad.Jugador;
import modelo.ciudad5.ArbolABB;
import modelo.ciudad5.EstadoMinijuego5;
import modelo.ciudad5.GanadorCarrera;
import modelo.ciudad5.ListaDinamica;
import modelo.ciudad5.NodoArbol;
import modelo.ciudad5.NodoLista;
import modelo.ciudad5.TransformadorTXT;
import render.FinMinijuegoPantalla;
import ui.Boton;
import ui.ConfiguracionPantalla;
import ui.GestorRecursos;

public class Ciudad5Minijuego implements Minijuego {

    private String[] palabras = {"árbol", "ardillas", "búsqueda", "elemento", "binario"};
    private String palabraBuscada;
    private Ciudad ciudad;
    private Jugador jugador;
    
    private FinMinijuegoPantalla pantallaFinal = new FinMinijuegoPantalla();
    
    private boolean ganado = false;
    private GestorRecursos recursos;
    
    private ArbolABB miArbol;
    private ListaDinamica miLista;
    private TransformadorTXT transformador;
    
    private EstadoMinijuego5 estado = EstadoMinijuego5.INICIO;
    private GanadorCarrera ganador;

    private boolean datosCargados = false;
    private boolean eleccionArbol = true;
    
    private String textoMostrado;
    
    private int pasosArbol, pasosLista;
    private NodoLista resultadoLista;
    private NodoArbol resultadoArbol;

    private long tiempoArbol;
    private long tiempoLista;
    
    private Boton botonBuscar, botonArbol, botonLista;
    
    public Ciudad5Minijuego(Ciudad ciudad, Jugador jugador, GestorRecursos recursos) {
        this.ciudad = ciudad;
        this.jugador = jugador;
        this.recursos = recursos;
        crearBotones();
    }
    
    @Override
    public void iniciar() {
        miArbol = new ArbolABB();
        miLista = new ListaDinamica();
        transformador = new TransformadorTXT();
        elegirPalabraAleatoria();
    }
    
    private void seleccionarYCargarArchivo() {
        JFileChooser selectorArchivo = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de Texto (.txt)", "txt");
        selectorArchivo.setFileFilter(filtro);
        
        int resultado = selectorArchivo.showOpenDialog(null);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = selectorArchivo.getSelectedFile();
            String rutaAbsoluta = archivoSeleccionado.getAbsolutePath();
            
            try {
                datosCargados = transformador.cargarDatos(rutaAbsoluta, miArbol, miLista);
                textoMostrado = transformador.getTextoOriginal();
                
                // --- NUEVO: Solicitar al usuario la palabra a buscar ---
                String palabraUsuario = JOptionPane.showInputDialog(
                    null, 
                    "Escribe la palabra que deseas buscar en el archivo:", 
                    "Buscar Palabra", 
                    JOptionPane.QUESTION_MESSAGE
                );
                
                // Si el usuario escribió algo y no canceló la ventana, asignamos su palabra
                if (palabraUsuario != null && !palabraUsuario.trim().isEmpty()) {
                    palabraBuscada = palabraUsuario.trim();
                } else {
                    // Si lo dejó vacío o canceló, el juego usa una por defecto de la lista para no romperse
                    elegirPalabraAleatoria();
                    JOptionPane.showMessageDialog(null, "No ingresaste una palabra. Se usará una aleatoria: '" + palabraBuscada + "'");
                }
                
                // Si todo sale bien, avanzamos al estado de APUESTA
                estado = EstadoMinijuego5.APUESTA;
                
            } catch (Exception e) {
                System.out.println("Error al cargar el archivo seleccionado: " + e.getMessage());
            }
        } else {
            System.out.println("El usuario cerró o canceló la ventana de selección.");
        }
    }
    
    private void elegirPalabraAleatoria() {
        Random random = new Random();
        palabraBuscada = palabras[random.nextInt(palabras.length)];
    }
    
    private void ejecutarBusqueda() {
        if(!datosCargados) {
            throw new RuntimeException("No se pudieron cargar los datos.");
        }
        long inicioLista = System.nanoTime();
        resultadoLista = miLista.buscarLineal(palabraBuscada);
        long finLista = System.nanoTime();

        long inicioArbol = System.nanoTime();
        resultadoArbol = miArbol.buscar(palabraBuscada);
        long finArbol = System.nanoTime();
        
        pasosArbol = miArbol.getOperacionesUltimaBusqueda();
        pasosLista = miLista.getOperacionesUltimaBusqueda();

        tiempoLista = finLista - inicioLista;
        tiempoArbol = finArbol - inicioArbol;

        if (tiempoArbol < tiempoLista) {
            ganador = GanadorCarrera.ARBOL;
        } else if (tiempoLista < tiempoArbol) {
            ganador = GanadorCarrera.LISTA;
        } else {
            ganador = GanadorCarrera.EMPATE;
        }
    }
    
    // ========================== RENDER ====================================
    
    private void crearBotones() {
        int anchoBoton = 300;
        int altoBoton = 130;

        int centroX = (ConfiguracionPantalla.SCREEN_WIDTH - anchoBoton) / 2;
        int centroY = (ConfiguracionPantalla.SCREEN_HEIGHT - altoBoton) / 2 - 100;
        
        botonBuscar = new Boton(recursos.getBotonMenu1(), centroX, centroY, anchoBoton, altoBoton);
        
        int separacion = 50;

        int anchoTotal = anchoBoton * 2 + separacion;
        int inicialX = (ConfiguracionPantalla.SCREEN_WIDTH - anchoTotal) / 2;

        int botonesY = ConfiguracionPantalla.SCREEN_HEIGHT - 150;

        botonArbol = new Boton(recursos.getBotonArbol(), inicialX, botonesY, anchoBoton, altoBoton);

        botonLista = new Boton(recursos.getBotonLista(), inicialX + anchoBoton + separacion, botonesY,
            anchoBoton, altoBoton);
    }

    @Override
    public void render(Graphics2D g2) {
        dibujarFondo(g2);

        switch (estado) {
            case INICIO:
                renderInicio(g2);
                break;
            case APUESTA:
                renderApuesta(g2);
                break;
            case RESULTADOS:
                renderResultados(g2);
                break;
        }
    }
    
    private void dibujarFondo(Graphics2D g2) {
        g2.setColor(Color.GRAY);
        g2.fillRect(
            0,
            0,
            ConfiguracionPantalla.SCREEN_WIDTH,
            ConfiguracionPantalla.SCREEN_HEIGHT
        );

        g2.setFont(new Font("Arial", Font.PLAIN, 28));
        g2.setColor(Color.WHITE);
    }
    
    private void renderInicio(Graphics2D g2) {
        dibujarTitulo(g2, "Iniciar partida");
        botonBuscar.dibujar(g2);
    }
    
    private void renderApuesta(Graphics2D g2) {
        dibujarTitulo(g2, "¿Por cuál Ardilla quieres apostar?");

        g2.drawString("Palabra a buscar: " + palabraBuscada, 50, 120);

        int y = 160;

        String[] lineas = textoMostrado.split("\n");

        for (String linea : lineas) {
            g2.drawString(linea, 50, y);
            y += 25;
        }

        botonArbol.dibujar(g2);
        botonLista.dibujar(g2);
    }
    
    private void dibujarTitulo(Graphics2D g2, String titulo) {
        FontMetrics fm = g2.getFontMetrics();
        int tx = (ConfiguracionPantalla.SCREEN_WIDTH - fm.stringWidth(titulo)) / 2;
        g2.drawString(titulo, tx, 80);
    }
    
    private void renderResultados(Graphics2D g2) {
        if (resultadoArbol != null && resultadoLista != null) {
            g2.drawString("Palabra encontrada por ambas ardillas!", 100, 430);
            g2.drawString("Ubicación: Línea " + resultadoArbol.linea + ", Posición " + 
                    resultadoArbol.posicion, 100, 460);
        } else {
            g2.drawString("La palabra no existe en el texto.", 100, 430);
        }

        dibujarEstadisticas(g2);
        dibujarGanador(g2);
        dibujarResultadoApuesta(g2);
    }
    
    private void dibujarEstadisticas(Graphics2D g2) {
        g2.drawString("----------------------------------------------", 80, 60);
        g2.drawString("RESULTADOS DE LA CARRERA", 100, 100);

        g2.drawString("Ardilla Rayada (Árbol ABB):", 100, 140);
        g2.drawString("  - Operaciones: " + pasosArbol, 100, 170);
        g2.drawString("  - Tiempo: " + tiempoArbol + " ns", 100, 200);

        g2.drawString("Ardilla Voladora (Lista Dinámica):", 100, 250);
        g2.drawString("  - Operaciones: " + pasosLista, 100, 280);
        g2.drawString("  - Tiempo: " + tiempoLista + " ns", 100, 310);

        g2.drawString("----------------------------------------------", 80, 340);
    }
    
    private void dibujarGanador(Graphics2D g2) {
        switch (ganador) {
            case ARBOL:
                g2.setColor(Color.YELLOW);
                g2.drawString("¡GANÓ LA ARDILLA RAYADA (ÁRBOL)!", 100, 380);
                break;
            case LISTA:
                g2.setColor(Color.CYAN);
                g2.drawString("¡GANÓ LA ARDILLA VOLADORA (LISTA)!", 100, 380);
                break;
            default:
                g2.setColor(Color.WHITE);
                g2.drawString("¡EMPATE! Ambas ardillas llegaron igual.", 100, 380);
                break;
        }
        g2.setColor(Color.WHITE);
    }
    
    private void dibujarResultadoApuesta(Graphics2D g2) {
        g2.drawString("----------------------------------------------", 80, 500);

        if (ganador == GanadorCarrera.EMPATE) {
            g2.drawString("Hubo un empate! Nadie gana ni pierde la apuesta.", 100, 540);
            return;
        }

        boolean acerto = (eleccionArbol && ganador == GanadorCarrera.ARBOL) ||
                (!eleccionArbol && ganador == GanadorCarrera.LISTA);

        if (acerto) {
            g2.setColor(Color.GREEN);
            g2.drawString("¡GANASTE LA APUESTA! Le atinaste a la ardilla más veloz.", 100, 540);

            ganado = true;
            pantallaFinal.mostrarResultados(g2, ciudad);
        } else {
            g2.setColor(Color.RED);
            g2.drawString("¡PERDISTE LA APUESTA! Tu ardilla se quedó sin fuerzas esta vez.", 100, 540);
        }
        g2.setColor(Color.WHITE);
    }

    @Override
    public void procesarClick(int mouseX, int mouseY) {
        switch (estado) {
            case INICIO:
                if (botonBuscar.contiene(mouseX, mouseY)) {
                    seleccionarYCargarArchivo(); 
                }
                break;

            case APUESTA:
                if (botonArbol.contiene(mouseX, mouseY)) {
                    eleccionArbol = true;
                    ejecutarBusqueda();
                    estado = EstadoMinijuego5.RESULTADOS;
                } else if (botonLista.contiene(mouseX, mouseY)) {
                    eleccionArbol = false;
                    ejecutarBusqueda();
                    estado = EstadoMinijuego5.RESULTADOS;
                }
                break;

            case RESULTADOS:
                break;
        }
    }

    @Override
    public void resultadoPartida() {
        if(ganado) {
            ciudad.setEstado(EstadoCiudad.COMPLETADA);
            jugador.sumarPuntos(ciudad.getPuntosDeExperiencia());
        }
    }
}
