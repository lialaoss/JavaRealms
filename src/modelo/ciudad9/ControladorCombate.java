package modelo.ciudad9;

import java.util.Vector;
import utiles.ValidacionesUtiles;

/*
  Motor del combate. Maneja el flujo de turnos, acciones y estructuras.
 */
public class ControladorCombate {
    public static final int MAX_COMBO = 3;

    private final Personaje jugador;
    private final ListaEnemigos listaEnemigos;
    private final ColaTurnos colaTurnos;
    private final PilaAcciones pilaAcciones;

    private int experienciaCombo;
    private boolean comboDisponible;
    private final Vector<String> historialSucesos;

    /*
     Pre: Ninguna.
     Post: Se inicializan las estructuras, se limpia el historial y se inicia el combate con los valores predeterminados.
     */
    public ControladorCombate() {
        this.jugador = new Personaje("Heroe", 150, 30, true);
        this.listaEnemigos = new ListaEnemigos();
        this.colaTurnos = new ColaTurnos();
        this.pilaAcciones = new PilaAcciones();
        this.experienciaCombo = 0;
        this.comboDisponible = false;
        this.historialSucesos = new Vector<>();
        
        registrarSuceso("¡Comienza la batalla en Ciudad 9!");
        inicializarCombate();
    }

    /*
     Pre: El combate no debe haber sido inicializado previamente y no deben existir personajes registrados.
     Post: Se crean los enemigos y se encolan junto al jugador en la Cola de Turnos.
     */
    private void inicializarCombate() {
        Personaje tanque = new Personaje("Dragon", 100, 8, false);
        Personaje guerrero = new Personaje("Demonio", 70, 12, false);
        Personaje mago = new Personaje("Genio", 45, 20, false);

        listaEnemigos.agregarEnemigo(tanque);
        listaEnemigos.agregarEnemigo(guerrero);
        listaEnemigos.agregarEnemigo(mago);

        colaTurnos.encolar(jugador);
        colaTurnos.encolar(tanque);
        colaTurnos.encolar(guerrero);
        colaTurnos.encolar(mago);
    }

    /*
     Pre: 'suceso' no debe ser nulo.
     Post: Añade el suceso al historial. Si excede 12 elementos, borra el más antiguo.
     */
    public void registrarSuceso(String suceso) {
        if (ValidacionesUtiles.esNulo(suceso)) return;
        this.historialSucesos.add(suceso);
        if (this.historialSucesos.size() > 12) {
            this.historialSucesos.remove(0);
        }
    }

    /*
     Pre: Ninguna.
     Posr: Devuelve el vector con el historial de eventos recientes.
     */
    public Vector<String> getHistorialSucesos() { return historialSucesos; }

    /*
     Pre: Ninguna.
     Post: Devuelve true si el próximo personaje en la cola de turnos es el jugador.
     */
    public boolean esTurnoJugador() {
        return !colaTurnos.estaVacia() && colaTurnos.espiar() == jugador;
    }

    /*
     Pre: 'tipoAccion' debe ser válido.
     Poat: Apila la acción seleccionada en la pila del jugador.
     */
    public void agregarAccionJugador(String tipoAccion) {
        if (ValidacionesUtiles.esNulo(tipoAccion)) return;
        pilaAcciones.apilarAccion(new Accion(tipoAccion));
    }

    /*
     Pre: El turno del jugador finalizó correctamente.
     Poat: Incrementa la experiencia de combo. Si llega al máximo, se activa el combo.
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

    /*
     Pre: 'objetivo' es un índice válido y 'aciertoPregunta' indica si el jugador respondió bien la trivia.
     Post: Desencola al personaje activo y procesa su turno. Si sigue vivo, lo vuelve a encolar.
     */
    public void ejecutarTurno(int objetivo, boolean aciertoPregunta) {
        Personaje actual = colaTurnos.desencolar();
        if (actual == null || !actual.estaVivo()) return;

        if (actual == jugador) {
            ejecutarTurnoJugador(objetivo, aciertoPregunta);
            aumentarCombo(); 
        } else {
            ejecutarTurnoEnemigo(actual);
        }

        listaEnemigos.eliminarDerrotados();
        if (actual.estaVivo()) {
            colaTurnos.encolar(actual);
        }
    }

    /*
     Pre: El jugador tiene acciones encoladas en su pila.
     Post: Desapila y ejecuta sus acciones. Aplica los penalizadores si aciertoPregunta es false.
     */
    private void ejecutarTurnoJugador(int objetivo, boolean aciertoPregunta) {
        int accionesAEjecutar = comboDisponible ? 2 : 1;
        
        if (comboDisponible) {
            registrarSuceso("★ ¡COMBO ACTIVADO! El Héroe canaliza una ráfaga de doble acción.");
            comboDisponible = false;
            experienciaCombo = 0;
        }

        if (aciertoPregunta) {
            registrarSuceso("✓ ¡Respuesta Correcta! El Héroe actúa con máxima eficacia.");
        } else {
            registrarSuceso("✗ Respuesta Incorrecta... El Héroe titubea y pierde efectividad.");
        }

        for (int i = 0; i < accionesAEjecutar; i++) {
            Accion accion = pilaAcciones.desapilarAccion();
            if (accion == null) break;

            switch (accion.getTipo()) {
                case Accion.ATAQUE:
                    realizarAtaque(objetivo, aciertoPregunta);
                    break;
                case Accion.DEFENSA:
                    realizarDefensa(aciertoPregunta);
                    break;
                case Accion.HABILIDAD:
                    usarHabilidad(aciertoPregunta);
                    break;
            }
        }
    }

    /*
     Pre: El jugador eligió Atacar.
     Post: Calcula el daño con o sin penalización y lo inflige al enemigo objetivo.
     */
    private void realizarAtaque(int objetivo, boolean acierto) {
        if (!listaEnemigos.quedanEnemigos()) return;
        if (objetivo < 0 || objetivo >= listaEnemigos.cantidadEnemigos()) objetivo = 0;
        
        Personaje enemigo = listaEnemigos.obtenerEnemigos().get(objetivo);
        int danioAInfligir = acierto ? jugador.getAtaque() : jugador.getAtaque() / 2;
        
        enemigo.recibirDanio(danioAInfligir);
        registrarSuceso("[Acción] Héroe atacó a " + enemigo.getNombre() + " infligiendo " + danioAInfligir + " de daño.");
    }

    /*
     Pre: El jugador eligió Defender.
     Post: Activa la defensa del jugador si respondió bien la pregunta.
     */
    private void realizarDefensa(boolean acierto) {
        if (acierto) {
            jugador.activarDefensa();
            registrarSuceso("[Acción] Héroe se puso en guardia perfecta. Mitigará daño.");
        } else {
            registrarSuceso("[Acción] Héroe intentó defenderse pero tropezó. ¡Queda expuesto!");
        }
    }

    /*
     Pre: El jugador eligió Habilidad.
     Post: Cura al jugador. Si respondió mal la trivia, la curación es mucho menor.
     */
    private void usarHabilidad(boolean acierto) {
        int cura = acierto ? 30 : 10;
        jugador.curar(cura);
        registrarSuceso("[Acción] Héroe usó Sanación y recuperó +" + cura + " HP.");
    }

    /*
     Pre: Es el turno de un enemigo.
     Post: El enemigo ataca al jugador aplicando daño según su atributo de ataque.
     */
    private void ejecutarTurnoEnemigo(Personaje enemigo) {
        int vidaAntes = jugador.getVida();
        jugador.recibirDanio(enemigo.getAtaque());
        int danioReal = vidaAntes - jugador.getVida(); 
        registrarSuceso("[Turno Enemigo] " + enemigo.getNombre() + " atacó al Héroe infligiendo " + danioReal + " de daño.");
    }

    /*
     Pre: Ninguna.
     Post: Devuelve true si no quedan enemigos vivos.
     */
    public boolean victoria() { return !listaEnemigos.quedanEnemigos(); }

    /*
     Pre: Ninguna.
     Post: Devuelve true si la vida del jugador llegó a 0.
     */
    public boolean derrota() { return !jugador.estaVivo(); }

    /*
     Pre: Ninguna.
     Post: Devuelve la instancia del personaje Jugador.
     */
    public Personaje getJugador() { return jugador; }

    /*
     Pre: Ninguna.
     Post: Devuelve la colección de enemigos.
     */
    public ListaEnemigos getListaEnemigos() { return listaEnemigos; }

    /*
     Pre: Ninguna.
     Post: Devuelve el contador de experiencia para el combo.
     */
    public int getExperienciaCombo() { return experienciaCombo; }

    /*
     Pre: Ninguna.
     Post: Devuelve true si el jugador tiene su combo cargado y listo.
     */
    public boolean isComboDisponible() { return comboDisponible; }
}