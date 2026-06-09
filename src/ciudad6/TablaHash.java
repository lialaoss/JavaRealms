package ciudad6;

import java.util.ArrayList;
import java.util.List;

/**
 * Lógica de la Tabla Hash con direccionamiento abierto (Linear Probing).
 * Muestra el desglose matemático de las operaciones para el usuario.
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
     * Calcula el hash e inyecta en la lista de pasos el desglose matemático detallado.
     * @pre pasos No debe ser nulo. La clave no debe ser nula.
     * @post Devuelve el índice base entre 0 y 10.
     */
    private int calcularYExplicarHash(String clave, List<String> pasos) {
        pasos.add("Clave recibida: '" + clave + "'");
        pasos.add("1. PROCESANDO CARACTERES (Multiplicador Primo 31):");
        
        int hash = 0;
        for (int i = 0; i < clave.length(); i++) {
            char caracter = clave.charAt(i);
            int ascii = (int) caracter;
            int hashAnterior = hash;
            
            hash = (hash * 31) + ascii;
            
            if (i == 0) {
                pasos.add("  • '" + caracter + "' (ASCII " + ascii + ") -> Hash inicial = " + hash);
            } else {
                pasos.add("  • '" + caracter + "' (ASCII " + ascii + ") -> (" + hashAnterior + " * 31) + " + ascii + " = " + hash);
            }
        }
        
        int hashAbsoluto = Math.abs(hash);
        int indiceBase = hashAbsoluto % TAMANIO_INICIAL;
        
        pasos.add("2. MÉTODO DE LA DIVISIÓN (Tamaño de Tabla = 11):");
        pasos.add("  • Valor absoluto del Hash: " + hashAbsoluto);
        pasos.add("  • Operación Módulo: " + hashAbsoluto + " % 11 = " + indiceBase);
        pasos.add("=> ¡Posición Base de Destino: " + indiceBase + "!");
        pasos.add("----------------------------------------------------------------------");
        
        return indiceBase;
    }

    /**
     * Inserta un elemento desglosando las operaciones y las colisiones detectadas.
     */
    public List<String> insertar(String clave, int valor) {
        List<String> pasos = new ArrayList<>();
        
        // Obtenemos el índice base y dejamos guardado el cálculo en la bitácora
        int indiceBase = calcularYExplicarHash(clave, pasos);
        int indiceActual = indiceBase;
        int intento = 0;

        while (this.tabla[indiceActual] != null) {
            if (this.tabla[indiceActual].getClave().equals(clave)) {
                this.tabla[indiceActual] = new CeldaHash(clave, valor);
                pasos.add("-> La clave '" + clave + "' ya existía en la posición " + indiceActual + ". Se actualizó su valor.");
                return pasos;
            }
            
            pasos.add("[COLISIÓN DETECTADA] La posición " + indiceActual + " ya contiene a '" + this.tabla[indiceActual].getClave() + "'.");
            intento++;
            
            if (intento >= TAMANIO_INICIAL) {
                pasos.add("[ERROR] Tabla colapsada. No queda espacio libre en el vector.");
                throw new IllegalStateException("La tabla hash está completamente llena.");
            }
            
            indiceActual = (indiceBase + intento) % TAMANIO_INICIAL;
            pasos.add("  • Aplicando Linear Probing: (" + indiceBase + " + intento " + intento + ") % 11 = posición " + indiceActual);
        }

        this.tabla[indiceActual] = new CeldaHash(clave, valor);
        this.cantidadElementos++;
        pasos.add("\n[ÉXITO] Casillero libre encontrado. Guardado en posición: " + indiceActual);
        
        return pasos;
    }

    /**
     * Busca una clave explicando al usuario el recorrido de celdas y Linear Probing en caso de colisión.
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
            
            pasos.add("    Contiene la clave '" + this.tabla[indiceActual].getClave() + "'. No es lo que buscamos. Avanzando...");
            intento++;
            
            if (intento >= TAMANIO_INICIAL) {
                break;
            }
            
            indiceActual = (indiceBase + intento) % TAMANIO_INICIAL;
            pasos.add("  • Aplicando Linear Probing: Evaluando posición " + indiceActual);
        }

        pasos.add("\n[FIN] Se llegó a una celda vacía o se barrió la tabla. La clave '" + clave + "' no habita en este oráculo.");
        return new ResultadoBusqueda(-1, pasos); 
    }

    public CeldaHash[] getTabla() {
        return this.tabla;
    }
}