package ciudad9;
/**
 * Encargada de mostrar información al usuario.
 */
public class VistaCombate {

    public void mostrarEstado(
            Personaje jugador,
            ListaEnemigos listaEnemigos) {

        System.out.println();
        System.out.println("===== COMBATE =====");

        System.out.println(
                jugador.getNombre()
                        + " HP: "
                        + jugador.getVida());

        for (Personaje enemigo :
                listaEnemigos.obtenerEnemigos()) {

            System.out.println(
                    enemigo.getNombre()
                            + " HP: "
                            + enemigo.getVida());
        }

        System.out.println("===================");
        System.out.println();
    }
}