package ciudad9;

import java.util.ArrayList;
import java.util.List;

/**
 * Motor del combate. Orquesta el flujo de turnos, acciones y estructuras.
 */
public class ControladorCombate {
    public static final int MAX_COMBO = 3;

    private final Personaje jugador;
    private final ListaEnemigos listaEnemigos;
    private final ColaTurnos colaTurnos;
    private final PilaAcciones pilaAcciones;

    private int experienciaCombo;
    private boolean comboDisponible;
    private final List<String> historialSucesos;

    /**
     * Pre: Ninguna.
     * Post: Se inicializan las estructuras, se crean los personajes y se encolan para iniciar el combate.
     */
    public ControladorCombate() {
        this.jugador = new Personaje("Heroe", 100, 25, true);
        this.listaEnemigos = new ListaEnemigos();
        this.colaTurnos = new ColaTurnos();
        this.pilaAcciones = new PilaAcciones();
        this.experienciaCombo = 0;
        this.comboDisponible = false;
        this.historialSucesos = new ArrayList<>();
        
        registrarSuceso("¡Comienza la batalla en Ciudad 9!");
        inicializarCombate();
    }

    private void inicializarCombate() {
        Personaje tanque = new Personaje("Tanque", 100, 8, false);
        Personaje guerrero = new Personaje("Guerrero", 70, 12, false);
        Personaje mago = new Personaje("Mago", 45, 20, false);

        listaEnemigos.agregarEnemigo(tanque);
        listaEnemigos.agregarEnemigo(guerrero);
        listaEnemigos.agregarEnemigo(mago);

        colaTurnos.encolar(jugador);
        colaTurnos.encolar(tanque);
        colaTurnos.encolar(guerrero);
        colaTurnos.encolar(mago);
    }

    /**
     * Pre: 'suceso' no debe ser nulo.
     * Post: El suceso se agrega al historial. Si el historial supera los 12 elementos, se elimina el más antiguo.
     */
    public void registrarSuceso(String suceso) {
        this.historialSucesos.add(suceso);
        if (this.historialSucesos.size() > 12) {
            this.historialSucesos.remove(0);
        }
    }

    public List<String> getHistorialSucesos() { return historialSucesos; }

    /**
     * Pre: La cola de turnos debe estar inicializada.
     * Post: Retorna true si el jugador es el próximo en desencolarse, false en caso contrario.
     */
    public boolean esTurnoJugador() {
        return !colaTurnos.estaVacia() && colaTurnos.espiar() == jugador;
    }

    /**
     * Pre: 'tipoAccion' debe ser una constante válida de la clase Accion.
     * Post: La acción elegida queda registrada como la última acción pendiente a ejecutar en el turno.
     */
    public void agregarAccionJugador(String tipoAccion) {
        if (tipoAccion == null) return;
        pilaAcciones.apilarAccion(new Accion(tipoAccion));
    }

    /**
     * Pre: El jugador acaba de finalizar su turno correctamente.
     * Post: Aumenta la experiencia de combo. Si alcanza MAX_COMBO, comboDisponible pasa a true.
     */
    private void aumentarCombo() {
        if (!comboDisponible) {
            experienciaCombo++;
            if (experienciaCombo >= MAX_COMBO) {
                experienciaCombo = MAX_COMBO;
                comboDisponible = true;
            }
        }
    }

    /**
     * Pre: 'objetivo' debe ser el índice del enemigo seleccionado (0 si es automático).
     * Post: Se desencola al personaje actual y se ejecuta su acción. Los personajes derrotados son eliminados. Los personajes vivos vuelven al final de la cola.
     */
    public void ejecutarTurno(int objetivo) {
        Personaje actual = colaTurnos.desencolar();
        if (actual == null || !actual.estaVivo()) return;

        if (actual == jugador) {
            ejecutarTurnoJugador(objetivo);
            aumentarCombo(); 
        } else {
            ejecutarTurnoEnemigo(actual);
        }

        listaEnemigos.eliminarDerrotados();
        if (actual.estaVivo()) {
            colaTurnos.encolar(actual);
        }
    }

    /**
     * Pre: La pila de acciones contiene al menos una acción apilada.
     * Post: Se desapilan y ejecutan LIFO las acciones correspondientes (1 normal, o 2 si el combo está activo).
     */
    private void ejecutarTurnoJugador(int objetivo) {
        int accionesAEjecutar = comboDisponible ? 2 : 1;
        
        if (comboDisponible) {
            registrarSuceso("★ ¡COMBO ACTIVADO! El Héroe canaliza una ráfaga de doble acción.");
            comboDisponible = false;
            experienciaCombo = 0;
        }

        for (int i = 0; i < accionesAEjecutar; i++) {
            Accion accion = pilaAcciones.desapilarAccion();
            if (accion == null) break;

            switch (accion.getTipo()) {
                case Accion.ATAQUE:
                    realizarAtaque(objetivo);
                    break;
                case Accion.DEFENSA:
                    realizarDefensa();
                    break;
                case Accion.HABILIDAD:
                    usarHabilidad();
                    break;
            }
        }
    }

    private void realizarAtaque(int objetivo) {
        if (!listaEnemigos.quedanEnemigos()) return;
        if (objetivo < 0 || objetivo >= listaEnemigos.cantidadEnemigos()) {
            objetivo = 0;
        }
        Personaje enemigo = listaEnemigos.obtenerEnemigos().get(objetivo);
        enemigo.recibirDanio(jugador.getAtaque());
        registrarSuceso("[Acción] Héroe atacó a " + enemigo.getNombre() + " infligiendo " + jugador.getAtaque() + " de daño.");
    }

    private void realizarDefensa() {
        jugador.activarDefensa();
        registrarSuceso("[Acción] Héroe se puso en guardia. Mitigará el 50% del próximo ataque.");
    }

    private void usarHabilidad() {
        jugador.curar(30);
        registrarSuceso("[Acción] Héroe usó Sanación y recuperó +30 HP.");
    }

    private void ejecutarTurnoEnemigo(Personaje enemigo) {
        int vidaAntes = jugador.getVida();
        jugador.recibirDanio(enemigo.getAtaque());
        int danioReal = vidaAntes - jugador.getVida(); 
        registrarSuceso("[Turno Enemigo] " + enemigo.getNombre() + " atacó al Héroe infligiendo " + danioReal + " de daño.");
    }

    /** Pre: Ninguna. 
     *  Post: Retorna true si la lista de enemigos está vacía.
    **/
    public boolean victoria() { return !listaEnemigos.quedanEnemigos(); }

    /** Pre: Ninguna.
     *  Post: Retorna true si la vida del jugador es 0.
    **/
    public boolean derrota() { return !jugador.estaVivo(); }

    public Personaje getJugador() { return jugador; }
    
    public ListaEnemigos getListaEnemigos() { return listaEnemigos; }
    
    public int getExperienciaCombo() { return experienciaCombo; }
    
    public boolean isComboDisponible() { return comboDisponible; }
}