package ciudad9;

/*
  Punto de entrada del módulo Ciudad 9.
 */
public class Main {
    
    /*
     Pre: El archivo 'preguntas.txt' debe estar ubicado en la raíz del proyecto.
     Poat: Lee el archivo, maneja la interacción entre MVC, y gestiona el combate hasta finalizar en victoria o derrota.
     */
    public static void main(String[] args) {

        Pregunta.cargarDesdeArchivo("preguntas.txt");
        
        ControladorCombate combate = new ControladorCombate();
        VistaCombate vista = new VistaCombate();

        while (!combate.victoria() && !combate.derrota()) {
            vista.mostrarEstado(combate);

            if (combate.esTurnoJugador()) {
                int accionesRequeridas = combate.isComboDisponible() ? 2 : 1;
                boolean requiereSeleccionarObjetivo = false;

                for (int i = 0; i < accionesRequeridas; i++) {
                    int opcion = vista.solicitarAccion(i + 1, accionesRequeridas);
                    String tipoAccion;

                    switch (opcion) {
                        case 1:
                            tipoAccion = Accion.ATAQUE;
                            requiereSeleccionarObjetivo = true;
                            break;
                        case 2:
                            tipoAccion = Accion.DEFENSA;
                            break;
                        case 3:
                            tipoAccion = Accion.HABILIDAD;
                            break;
                        default:
                            tipoAccion = Accion.DEFENSA;
                            break;
                    }
                    combate.agregarAccionJugador(tipoAccion);
                }

                int objetivoElegido = 0;
                if (requiereSeleccionarObjetivo && combate.getListaEnemigos().quedanEnemigos()) {
                    objetivoElegido = vista.solicitarObjetivo(combate.getListaEnemigos());
                }

                Pregunta preguntaAleatoria = Pregunta.obtenerAleatoria();
                boolean respondioBien = vista.hacerPreguntaEstructuras(preguntaAleatoria);

                combate.ejecutarTurno(objetivoElegido, respondioBien);

            } else {

                combate.ejecutarTurno(0, true);
                vista.mostrarEstado(combate);

                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        vista.mostrarEstado(combate);
        vista.mostrarMensajeFin(combate.victoria());
    }
}