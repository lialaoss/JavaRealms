package ciudad9;
/**
 * Contiene toda la lógica del combate.
 */
public class ControladorCombate {

    private Personaje jugador;

    private ListaEnemigos listaEnemigos;
    private ColaTurnos colaTurnos;
    private PilaAcciones pilaAcciones;

    public ControladorCombate() {

        jugador = new Personaje("Heroe", 100, 20);

        listaEnemigos = new ListaEnemigos();
        colaTurnos = new ColaTurnos();
        pilaAcciones = new PilaAcciones();

        inicializarCombate();
    }

    private void inicializarCombate() {

        Personaje goblin = new Personaje("Goblin", 50, 10);
        Personaje orco = new Personaje("Orco", 80, 15);

        listaEnemigos.agregarEnemigo(goblin);
        listaEnemigos.agregarEnemigo(orco);

        colaTurnos.encolar(jugador);
        colaTurnos.encolar(goblin);
        colaTurnos.encolar(orco);
    }

    public void agregarAccionJugador(String tipoAccion) {

        pilaAcciones.apilarAccion(
                new Accion(tipoAccion));
    }

    public void ejecutarTurno() {

        Personaje actual = colaTurnos.desencolar();

        if (actual == null) {
            return;
        }

        if (!actual.estaVivo()) {
            return;
        }

        if (actual == jugador) {

            ejecutarTurnoJugador();

        } else {

            ejecutarTurnoEnemigo(actual);
        }

        listaEnemigos.eliminarDerrotados();

        if (actual.estaVivo()) {
            colaTurnos.encolar(actual);
        }
    }

    private void ejecutarTurnoJugador() {

        Accion accion =
                pilaAcciones.desapilarAccion();

        if (accion == null) {

            System.out.println(
                    "El jugador no tiene acciones cargadas.");
            return;
        }

        switch (accion.getTipo()) {

            case "ATAQUE":

                realizarAtaque();
                break;

            case "DEFENSA":

                realizarDefensa();
                break;

            case "HABILIDAD":

                usarHabilidad();
                break;

            default:

                System.out.println(
                        "Accion desconocida.");
        }
    }

    private void realizarAtaque() {

        if (!listaEnemigos.quedanEnemigos()) {
            return;
        }

        Personaje enemigo =
                listaEnemigos.obtenerEnemigos().get(0);

        enemigo.recibirDanio(
                jugador.getAtaque());

        System.out.println(
                jugador.getNombre()
                        + " ataca a "
                        + enemigo.getNombre());
    }

    private void realizarDefensa() {

        jugador.activarDefensa();

        System.out.println(
                jugador.getNombre()
                        + " adopta posicion defensiva.");
    }

    private void usarHabilidad() {

        jugador.curar(15);

        System.out.println(
                jugador.getNombre()
                        + " utiliza Curacion.");
    }

    private void ejecutarTurnoEnemigo(
            Personaje enemigo) {

        jugador.recibirDanio(
                enemigo.getAtaque());

        System.out.println(
                enemigo.getNombre()
                        + " ataca al jugador.");
    }

    public boolean victoria() {
        return !listaEnemigos.quedanEnemigos();
    }

    public boolean derrota() {
        return !jugador.estaVivo();
    }

    public Personaje getJugador() {
        return jugador;
    }

    public ListaEnemigos getListaEnemigos() {
        return listaEnemigos;
    }

    public PilaAcciones getPilaAcciones() {
        return pilaAcciones;
    }
}