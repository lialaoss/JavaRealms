package ciudad6;

import java.util.ArrayList;
import java.util.List;

/**
 * Lógica de la Tabla Hash con direccionamiento abierto (Linear Probing).
 * Resuelve colisiones buscando el siguiente casillero contiguo disponible.
 */
public class TablaHash {
    private static final int TAMANIO_INICIAL = 11; 
    private CeldaHash[] tabla;
    private int cantidadElementos;

    /**
     * Constructor de la tabla.
     * * @pre Ninguna.
     * @post Se inicializa el array interno con un tamaño fijo de 11 y el contador en 0.
     */
    public TablaHash() {
        this.tabla = new CeldaHash[TAMANIO_INICIAL];
        this.cantidadElementos = 0;
    }

    /**
     * Transforma una cadena de texto en un índice de la tabla mediante 
     * el método polinómico (multiplicador 31) y el método de la división.
     * * @pre La clave no debe ser nula.
     * @post Devuelve un número entero positivo comprendido entre 0 y 10 (inclusive).
     */
    private int funcionHash(String clave) {
        int hash = 0;
        for (int i = 0; i < clave.length(); i++) {
            hash = (hash * 31) + clave.charAt(i);
        }
        return Math.abs(hash) % TAMANIO_INICIAL;
    }

    /**
     * Inserta o actualiza un elemento en la tabla asociando la clave con su valor.
     * Resuelve colisiones de manera lineal.
     * * @pre La clave no debe ser nula ni vacía. La tabla no debe estar al 100% de capacidad si la clave es nueva.
     * @post Se añade la celda en la primera posición libre encontrada o se actualiza su valor. 
     * Devuelve la lista con los logs del recorrido.
     * @throws IllegalStateException Si la tabla hash está completamente llena (factor de carga = 1.0) y se intenta insertar una clave nueva.
     */
    public List<String> insertar(String clave, int valor) {
        List<String> pasos = new ArrayList<>();
        
        int indiceBase = funcionHash(clave);
        int indiceActual = indiceBase;
        int intento = 0;
        
        pasos.add("Clave: '" + clave + "' -> Hash calculado: " + indiceBase);

        while (this.tabla[indiceActual] != null) {
            if (this.tabla[indiceActual].getClave().equals(clave)) {
                this.tabla[indiceActual] = new CeldaHash(clave, valor);
                pasos.add("La clave ya existía en la posición " + indiceActual + ". Se actualizó su valor.");
                return pasos;
            }
            
            pasos.add("[COLISIÓN] Posición " + indiceActual + " ocupada por '" + this.tabla[indiceActual].getClave() + "'.");
            intento++;
            
            if (intento >= TAMANIO_INICIAL) {
                pasos.add("[ERROR CRÍTICO] Se recorrió toda la tabla y no hay espacio disponible.");
                throw new IllegalStateException("La tabla hash está completamente llena.");
            }
            
            indiceActual = (indiceBase + intento) % TAMANIO_INICIAL;
            pasos.add("Probando siguiente posición (Linear Probing): " + indiceActual);
        }

        this.tabla[indiceActual] = new CeldaHash(clave, valor);
        this.cantidadElementos++;
        pasos.add("¡Éxito! Elemento guardado en la posición libre: " + indiceActual);
        
        return pasos;
    }

    /**
     * Busca un elemento en la tabla recorriendo secuencialmente a partir del hash indexado.
     * * @pre La clave no debe ser nula ni vacía.
     * @post Devuelve un objeto ResultadoBusqueda con el valor (-1 si no existe) y la bitácora de pasos.
     */
    public ResultadoBusqueda buscar(String clave) {
        List<String> pasos = new ArrayList<>();
        int indiceBase = funcionHash(clave);
        int indiceActual = indiceBase;
        int intento = 0;

        pasos.add("Buscando '" + clave + "'. Hash inicial: " + indiceBase);

        while (this.tabla[indiceActual] != null) {
            pasos.add("Verificando posición " + indiceActual + "...");
            
            if (this.tabla[indiceActual].getClave().equals(clave)) {
                pasos.add("¡Encontrado en posición " + indiceActual + "! Valor asociado: " + this.tabla[indiceActual].getValor());
                return new ResultadoBusqueda(this.tabla[indiceActual].getValor(), pasos);
            }
            
            pasos.add("Posición " + indiceActual + " contiene otra clave. Aplicando Linear Probing.");
            intento++;
            
            if (intento >= TAMANIO_INICIAL) {
                break;
            }
            
            indiceActual = (indiceBase + intento) % TAMANIO_INICIAL;
        }

        pasos.add("Se encontró una celda vacía o se recorrió toda la tabla. La clave '" + clave + "' no existe.");
        return new ResultadoBusqueda(-1, pasos); 
    }

    /**
     * @pre Ninguna.
     * @post Devuelve el arreglo interno de celdas para su lectura gráfica.
     */
    public CeldaHash[] getTabla() {
        return this.tabla;
    }
}