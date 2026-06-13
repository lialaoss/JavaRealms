package modelo.ciudad5;
import java.util.Scanner;

public class Main {
	/*
     * Pre: El archivo de texto que se va a ingresar debe existir en la ruta indicada y tener un formato legible.
     * Post: Inicia el programa de simulación "Árbol vs Lista", carga los datos del texto y permite al usuario apostar de forma interactiva por una de las dos estructuras (ardillas) para ver cuál busca una palabra en menos tiempo y con menos operaciones.
     */
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ArbolABB miArbol = new ArbolABB(); //
        ListaDinamica miLista = new ListaDinamica(); //
        TransformadorTXT transformador = new TransformadorTXT(); //

        System.out.println("=================================================");
        System.out.println("JUEGO ÁRBOL VS LISTA");
        System.out.println("=================================================");
        System.out.print("Ingrese el nombre o ruta del archivo de texto (ej: src/texto.txt): ");
        String rutaArchivo = teclado.nextLine();

        boolean lecturaExitosa = transformador.cargarDatos(rutaArchivo, miArbol, miLista);

        if (lecturaExitosa) {
            System.out.println("Archivo cargado ");
            
            int opcion = 0;
            while (opcion != 3) {
                System.out.println("\n--- OPCIONES ---");
                System.out.println("1 - Apostar por una Ardilla y Buscar Palabra");
                System.out.println("3 - Salir");
                System.out.print("Seleccione una opción: ");
                
                if (teclado.hasNextInt()) {
                    opcion = teclado.nextInt();
                    teclado.nextLine(); 

                    if (opcion == 1) {
                        int eleccionArdilla = 0;
                        while (eleccionArdilla != 1 && eleccionArdilla != 2) {
                            System.out.println("\n¿Por cuál Ardilla quieres apostar?");
                            System.out.println("1 - Ardilla Rayada (Árbol ABB - Índice)");
                            System.out.println("2 - Ardilla Voladora (Lista Dinámica - Lineal)");
                            System.out.print("Elige tu competidora (1 o 2): ");
                            if (teclado.hasNextInt()) {
                                eleccionArdilla = teclado.nextInt();
                            }
                            teclado.nextLine(); 
                            if (eleccionArdilla != 1 && eleccionArdilla != 2) {
                                System.out.println("Error: Opción inválida. Elige 1 o 2.");
                            }
                        }

                        System.out.print("\nIngrese la palabra a buscar en el texto: ");
                        String palabraBuscada = teclado.nextLine();

                        miArbol.buscar(palabraBuscada);
                        miLista.buscarLineal(palabraBuscada);

                        long inicioLista = System.nanoTime();
                        NodoLista resultadoLista = miLista.buscarLineal(palabraBuscada);
                        long finLista = System.nanoTime();
                        long tiempoLista = finLista - inicioLista;

                        long inicioArbol = System.nanoTime();
                        NodoArbol resultadoArbol = miArbol.buscar(palabraBuscada);
                        long finArbol = System.nanoTime();
                        long tiempoArbol = finArbol - inicioArbol;

                        int pasosArbol = miArbol.getOperacionesUltimaBusqueda();
                        int pasosLista = miLista.getOperacionesUltimaBusqueda();

                        System.out.println("\n--- RESULTADOS DE LA CARRERA ---");
                        
                        if (resultadoArbol != null && resultadoLista != null) {
                            System.out.println("Palabra encontrada por ambas ardillas!");
                            System.out.println("Ubicación: Línea " + resultadoArbol.linea + ", Posición " + resultadoArbol.posicion);
                        } else {
                            System.out.println("La palabra no existe en el texto (ambas llegaron al final).");
                        }

                        System.out.println("-----------------------------------------");
                        System.out.println("Ardilla Rayada (Árbol ABB):");
                        System.out.println("  - Operaciones: " + pasosArbol);
                        System.out.println("  - Tiempo:      " + tiempoArbol + " ns.");
                        System.out.println("-----------------------------------------");
                        System.out.println("Ardilla Voladora (Lista Dinámica):");
                        System.out.println("  - Operaciones: " + pasosLista);
                        System.out.println("  - Tiempo:      " + tiempoLista + " ns.");
                        System.out.println("-----------------------------------------");

                        int ardillaGanadora = 0; 
                        
                        if (tiempoArbol < tiempoLista) {
                            long ventaja = tiempoLista - tiempoArbol;
                            System.out.println("¡GANÓ LA ARDILLA RAYADA (Árbol)! Fue " + ventaja + " ns más rápida.");
                            ardillaGanadora = 1;
                        } else if (tiempoLista < tiempoArbol) {
                            long ventaja = tiempoArbol - tiempoLista;
                            System.out.println("¡GANÓ LA ARDILLA VOLADORA (Lista)! Fue " + ventaja + " ns más rápida.");
                            ardillaGanadora = 2;
                        } else {
                            System.out.println("¡Empate exacto! Ambas registraron " + tiempoArbol + " ns.");
                            ardillaGanadora = 0;
                        }

                        System.out.println("-----------------------------------------");
                        if (ardillaGanadora == 0) {
                            System.out.println("Hubo un empate! Nadie gana ni pierde la apuesta.");
                        } else if (eleccionArdilla == ardillaGanadora) {
                            System.out.println("¡GANASTE LA APUESTA! Le atinaste a la ardilla más veloz.");
                        } else {
                            System.out.println("¡PERDISTE LA APUESTA! Tu ardilla se quedó sin fuerzas esta vez.");
                        }
                        System.out.println("-----------------------------------------");

                    } else if (opcion != 3) {
                        System.out.println("Esa opción no existe");
                    }
                } else {
                    System.out.println("Por favor, ingrese un número válido.");
                    teclado.next(); 
                }
            }
            System.out.println("\nCerrando simulación...");
        } else {
            System.out.println("No se pudo iniciar porque el archivo '" + rutaArchivo + "' no fue encontrado.");
        }
        
        teclado.close(); 
    }
}