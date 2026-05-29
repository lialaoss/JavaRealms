package ciudad9;
public class Main {

    public static void main(String[] args) {

        ControladorCombate combate =
                new ControladorCombate();

        VistaCombate vista =
                new VistaCombate();

        combate.agregarAccionJugador("ATAQUE");
        combate.agregarAccionJugador("DEFENSA");
        combate.agregarAccionJugador("HABILIDAD");

        while (!combate.victoria()
                && !combate.derrota()) {

            vista.mostrarEstado(
                    combate.getJugador(),
                    combate.getListaEnemigos());

            combate.ejecutarTurno();
        }

        if (combate.victoria()) {

            System.out.println(
                    "Todos los enemigos fueron derrotados.");
        } else {

            System.out.println(
                    "El jugador ha sido derrotado.");
        }
    }
}