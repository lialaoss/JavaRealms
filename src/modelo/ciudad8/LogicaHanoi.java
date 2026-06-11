package modelo.ciudad8;

import java.util.ArrayList;
import java.util.List;

/**
 * Se encarga de resolver las Torres de Hanoi de manera recursiva
 * y almacenar el historial de llamadas y movimientos.
 */
public class LogicaHanoi {
    private List<MovimientoHanoi> listaMovimientos;
    private List<String> bitacoraRecursiva;

    /**
     * @pre Ninguna.
     * @post Inicializa las listas para almacenar la solución del algoritmo.
     */
    public LogicaHanoi() {
        this.listaMovimientos = new ArrayList<>();
        this.bitacoraRecursiva = new ArrayList<>();
    }

    /**
     * Método público para iniciar la resolución del problema.
     * @pre numeroDiscos > 0.
     * @post Genera la lista completa de movimientos óptimos y la bitácora teórica.
     */
    public void resolverHanoi(int numeroDiscos, char origen, char auxiliar, char destino) {
        this.listaMovimientos.clear();
        this.bitacoraRecursiva.clear();
        this.bitacoraRecursiva.add("=== INICIANDO ÁRBOL DE RECURSIVIDAD (Hanoi para " + numeroDiscos + " discos) ===");
        calcularHanoiRecursivo(numeroDiscos, origen, auxiliar, destino, 1);
    }

    /**
     * Algoritmo recursivo clásico de Torres de Hanoi.
     * @pre Ninguna.
     * @post Añade de forma ordenada los movimientos y el rastro de la pila de ejecución.
     */
    private void calcularHanoiRecursivo(int n, char desde, char aux, char hasta, int nivel) {
        String sangria = "  ".repeat(nivel); // Para graficar el árbol visualmente en la consola
        
        this.bitacoraRecursiva.add(sangria + "• Llamada recursiva: Hanoi(n=" + n + ", " + desde + " -> " + hasta + " usando " + aux + ")");

        if (n == 1) {
            this.bitacoraRecursiva.add(sangria + "  [Caso Base] Mover disco 1 directamente de " + desde + " a " + hasta);
            this.listaMovimientos.add(new MovimientoHanoi(1, desde, hasta));
            return;
        }

        // Paso 1: Mover n-1 discos de Origen a Auxiliar
        calcularHanoiRecursivo(n - 1, desde, hasta, aux, nivel + 1);

        // Paso 2: Mover el disco grande que quedó libre a Destino
        this.bitacoraRecursiva.add(sangria + "  [Paso Central] Mover disco grande " + n + " de " + desde + " a " + hasta);
        this.listaMovimientos.add(new MovimientoHanoi(n, desde, hasta));

        // Paso 3: Mover los n-1 discos que dejamos en Auxiliar hacia el Destino
        calcularHanoiRecursivo(n - 1, aux, desde, hasta, nivel + 1);
    }

    public List<MovimientoHanoi> getListaMovimientos() {
        return this.listaMovimientos;
    }

    public List<String> getBitacoraRecursiva() {
        return this.bitacoraRecursiva;
    }
}