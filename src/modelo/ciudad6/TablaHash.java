package modelo.ciudad6;

import java.util.ArrayList;
import java.util.List;

/**
 * Lógica de la Tabla Hash optimizada para jugabilidad.
 * Utiliza una función hash basada en la posición de los caracteres y suma ASCII,
 * facilitando el cálculo mental para el jugador.
 */
public class TablaHash {
    private static final int TAMANIO_INICIAL = 11; 
    private CeldaHash[] tabla;
    private int cantidadElementos;

    public TablaHash() {
        this.tabla = new CeldaHash[TAMANIO_INICIAL];
        this.cantidadElementos = 0;
    }

    /**
     * Función Hash diseñada para el juego: Sumatoria de (ASCII * Posición).
     * @pre pasos No debe ser nulo. La clave no debe ser nula ni vacía.
     * @post Devuelve el índice base entre 0 y 10.
     */
    private int calcularYExplicarHash(String clave, List<String> pasos) {
        pasos.add("Clave recibida: '" + clave + "'");
        pasos.add("1. FÓRMULA DEL TEMPLO: Sumatoria de (Código ASCII × Posición)");
        
        int sumaTotal = 0;
        for (int i = 0; i < clave.length(); i++) {
            char caracter = clave.charAt(i);
            int ascii = (int) caracter;
            int posicionLetra = i + 1; // Posición humana: 1, 2, 3...
            int subtotal = ascii * posicionLetra;
            
            sumaTotal += subtotal;
            
            pasos.add("  • '" + caracter + "' (ASCII " + ascii + ") × Posic. " + posicionLetra + " = " + subtotal + " [Acumulado: " + sumaTotal + "]");
        }
        
        int indiceBase = sumaTotal % TAMANIO_INICIAL;
        
        pasos.add("\n2. MÉTODO DE LA DIVISIÓN (Tamaño del Vector = 11):");
        pasos.add("  • Operación Módulo: " + sumaTotal + " % 11 = " + indiceBase);
        pasos.add("=> ¡Posición Base de Destino: " + indiceBase + "!");
        pasos.add("----------------------------------------------------------------------");
        
        return indiceBase;
    }

    /**
     * Inserta un elemento en la tabla resolviendo colisiones por Linear Probing.
     */
    public List<String> insertar(String clave, int valor) {
        List<String> pasos = new ArrayList<>();
        
        int indiceBase = calcularYExplicarHash(clave, pasos);
        int indiceActual = indiceBase;
        int intento = 0;

        while (this.tabla[indiceActual] != null) {
            if (this.tabla[indiceActual].getClave().equals(clave)) {
                this.tabla[indiceActual] = new CeldaHash(clave, valor);
                pasos.add("-> La clave '" + clave + "' ya existía en la posición " + indiceActual + ". Se actualizó su valor.");
                return pasos;
            }
            
            pasos.add("[COLISION DETECTADA] La posición " + indiceActual + " ya está ocupada por '" + this.tabla[indiceActual].getClave() + "'.");
            intento++;
            
            if (intento >= TAMANIO_INICIAL) {
                pasos.add("[ERROR] El Oráculo está colapsado. No queda espacio libre.");
                throw new IllegalStateException("La tabla hash está completamente llena.");
            }
            
            indiceActual = (indiceBase + intento) % TAMANIO_INICIAL;
            pasos.add("  • Aplicando Linear Probing: (" + indiceBase + " + intento " + intento + ") % 11 = posición " + indiceActual);
        }

        this.tabla[indiceActual] = new CeldaHash(clave, valor);
        this.cantidadElementos++;
        pasos.add("\n[ÉXITO] Casillero libre encontrado. Guardado en la posición: " + indiceActual);
        
        return pasos;
    }

    /**
     * Busca una clave detallando el recorrido realizado.
     */
    public ResultadoBusqueda buscar(String clave) {
        List<String> pasos = new ArrayList<>();
        int indiceBase = calcularYExplicarHash(clave, pasos);
        int indiceActual = indiceBase;
        int intento = 0;

        pasos.add("3. INICIANDO BÚSQUEDA EN MEMORIA:");

        while (this.tabla[indiceActual] != null) {
            pasos.add("  • Inspeccionando índice " + indiceActual + "...");
            
            if (this.tabla[indiceActual].getClave().equals(clave)) {
                pasos.add("-> ¡COINCIDENCIA ENCONTRADA! Clave '" + clave + "' localizada. Valor: " + this.tabla[indiceActual].getValor());
                return new ResultadoBusqueda(this.tabla[indiceActual].getValor(), pasos);
            }
            
            pasos.add("    Contiene la clave '" + this.tabla[indiceActual].getClave() + "'. Avanzando...");
            intento++;
            
            if (intento >= TAMANIO_INICIAL) {
                break;
            }
            
            indiceActual = (indiceBase + intento) % TAMANIO_INICIAL;
            pasos.add("  • Aplicando Linear Probing: Evaluando posición " + indiceActual);
        }

        pasos.add("\n[FIN] La clave '" + clave + "' no habita en este oráculo.");
        return new ResultadoBusqueda(-1, pasos); 
    }

    public CeldaHash[] getTabla() {
        return this.tabla;
    }
}