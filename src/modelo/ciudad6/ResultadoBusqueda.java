package modelo.ciudad6;

import java.util.List;

/*
 * Clase contenedora para devolver el resultado de una búsqueda
 * junto con el registro del paso a paso algorítmico.
 */
public class ResultadoBusqueda {
    private int valorEncontrado;
    private List<String> pasosExplicativos;

    /*
     * Constructor del contenedor de resultados.
     * * @pre La lista de pasos explicativos no debe ser nula.
     * @post Se crea un objeto que empaqueta el valor hallado y la bitácora de pasos.
     */
    public ResultadoBusqueda(int valorEncontrado, List<String> pasosExplicativos) {
        this.valorEncontrado = valorEncontrado;
        this.pasosExplicativos = pasosExplicativos;
    }

    /*
     * @pre Ninguna.
     * @post Devuelve el valor entero encontrado (-1 si no se halló).
     */
    public int getValorEncontrado() {
        return this.valorEncontrado;
    }

    /*
     * @pre Ninguna.
     * @post Devuelve la lista con el desglose del paso a paso del algoritmo.
     */
    public List<String> getPasosExplicativos() {
        return this.pasosExplicativos;
    }
}