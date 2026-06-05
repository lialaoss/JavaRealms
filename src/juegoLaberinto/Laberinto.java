package juegoLaberinto;

import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Laberinto {
//ATRIBUTOS ---------------------------------------------------------------------------------------------------------------------------------------------------------------
	private char[][] mapa;
	private Nodo inicio;
    private Nodo fin;

//CONSTRUCTORES ----------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Representa un laberinto cargado desde un archivo de texto.
     *
     * Formato:
     * # = pared
     * . = camino
     * I = inicio
     * F = fin
     *
     * Precondiciones:
     * - El laberinto debe estar rodeado por paredes.
     * - Inicio y fin no deben ubicarse en los bordes.
     */
    public Laberinto(String rutaArchivo) throws IOException {

        List<String> lineas = Files.readAllLines(Paths.get(rutaArchivo));
        if (lineas.isEmpty()) {
            throw new IllegalArgumentException(
                "El laberinto está vacío."
            );
        }

        validarLongitudFilas(lineas);

        int filas = lineas.size();
        int columnas = lineas.get(0).length();

        this.mapa = new char[filas][columnas];

        int cantidadInicio = 0;
        int cantidadFin = 0;

        for (int fila = 0; fila < filas; fila++) {

            String lineaActual = lineas.get(fila);

            for (int columna = 0; columna < columnas; columna++) {

                char celda = lineaActual.charAt(columna);

                this.mapa[fila][columna] = celda;

                switch (celda) {

                    case '#':
                    case '.':
                        break;

                    case 'I':
                        cantidadInicio++;
                        this.inicio = new Nodo(fila, columna);
                        break;

                    case 'F':
                        cantidadFin++;
                        this.fin = new Nodo(fila, columna);
                        break;

                    default:
                        throw new IllegalArgumentException(
                            "Caracter inválido: " + celda
                        );
                }
            }
        }

        validarInicioYFin(cantidadInicio, cantidadFin);
    }
    
//METODOS -----------------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Muestra el laberinto por consola.
     *
     * Precondiciones:
     * - El laberinto debe haber sido construido correctamente.
     *
     * Postcondiciones:
     * - Se imprime el contenido completo del mapa por consola.
     * - No modifica el estado del objeto.
     */
    public void mostrarMapa() {
        for (int fila = 0; fila < mapa.length; fila++) {

            for (int columna = 0; columna < mapa[fila].length; columna++) {
                System.out.print(mapa[fila][columna]);
            }
            System.out.println();
        }
    }

    /**
     * Verifica que todas las filas del archivo tengan la misma longitud.
     *
     * Precondiciones:
     * - lineas no es null.
     * - lineas contiene al menos una fila.
     *
     * Postcondiciones:
     * - Si todas las filas tienen la misma longitud, finaliza normalmente.
     * - En caso contrario lanza IllegalArgumentException.
     */
    private void validarLongitudFilas(List<String> lineas) {

        int columnas = lineas.get(0).length();

        for (String linea : lineas) {
            if (linea.length() != columnas) {
                throw new IllegalArgumentException(
                    "Todas las filas deben tener la misma longitud."
                );
            }
        }
    }
    
    /**
     * Verifica que exista exactamente un inicio y un fin.
     *
     * Precondiciones:
     * - cantidadInicio >= 0.
     * - cantidadFin >= 0.
     *
     * Postcondiciones:
     * - Si existe exactamente un inicio y un fin, finaliza normalmente.
     * - En caso contrario lanza IllegalArgumentException.
     */
    private void validarInicioYFin(int cantidadInicio, int cantidadFin) {

        if (cantidadInicio != 1) {
            throw new IllegalArgumentException(
                "Debe existir exactamente un inicio."
            );
        }

        if (cantidadFin != 1) {
            throw new IllegalArgumentException(
                "Debe existir exactamente un fin."
            );
        }
    }
    
    
    public List<Nodo> obtenerVecinos(Nodo n) {

        List<Nodo> vecinos = new java.util.ArrayList<>();

        int fila = n.getFila();
        int col = n.getColumna();

        // arriba
        if (esTransitable(fila - 1, col)) {
            vecinos.add(new Nodo(fila - 1, col));
        }

        // abajo
        if (esTransitable(fila + 1, col)) {
            vecinos.add(new Nodo(fila + 1, col));
        }

        // izquierda
        if (esTransitable(fila, col - 1)) {
            vecinos.add(new Nodo(fila, col - 1));
        }

        // derecha
        if (esTransitable(fila, col + 1)) {
            vecinos.add(new Nodo(fila, col + 1));
        }

        return vecinos;
    }
    
    
    private boolean esTransitable(int f, int c) {

        if (f < 0 || f >= mapa.length || c < 0 || c >= mapa[0].length) {
            return false;
        }

        return mapa[f][c] != '#';
    }

//GETTERS -----------------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Devuelve el nodo de inicio del laberinto.
     */
    public Nodo getInicio() {
        return this.inicio;
    }

    /**
     * Devuelve el nodo final del laberinto.
     */
    public Nodo getFin() {
        return this.fin;
    }
    
    
    public char[][] getMapa() {
        return mapa;
    }

}
