package modelo.ciudad3;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) throws IOException, InterruptedException {

        // limpia frames anteriores
        File dir = new File("frames/");
        if (dir.exists()) {
            for (File f : dir.listFiles()) {
                f.delete();
            }
        } 
        else {
            dir.mkdirs();
        }

        // carga laberinto
        Laberinto lab = new Laberinto("laberintos/lab2.txt");

        lab.mostrarMapa();

        System.out.println("Inicio: " + lab.getInicio());
        System.out.println("Fin: " + lab.getFin());

        Scanner sc = new Scanner(System.in);

        // selección de algoritmo
        System.out.println("\n=== MÉTODO DE BÚSQUEDA ===");
        System.out.println("1 - BFS");
        System.out.println("2 - DFS");
        System.out.print("Opción: ");

        int metodo = sc.nextInt();
        sc.nextLine();

        List<Snapshot> frames;

        long inicioTiempo = System.nanoTime();

        if (metodo == 1) {
            BFS bfs = new BFS();
            frames = bfs.buscar(lab);
        } 
        else if (metodo == 2) {
            DFS dfs = new DFS();
            frames = dfs.buscar(lab);
        } 
        else {
            System.out.println("Opción inválida.");
            sc.close();
            return;
        }
        long finTiempo = System.nanoTime();
        double tiempoMs =
                (finTiempo - inicioTiempo) / 1_000_000.0;
        System.out.printf("Tiempo de ejecución: %.3f ms%n",tiempoMs);

        System.out.println("Frames generados: " + frames.size());

        // selección de visualización
        System.out.println("\n=== MODO DE VISUALIZACIÓN ===");
        System.out.println("1 - Animación automática");
        System.out.println("2 - Paso a paso");
        System.out.print("Opción: ");

        int op = sc.nextInt();
        sc.nextLine();

        System.out.println("\nIniciando visualización...");

        // animación de frames
        for (int i = 0; i < frames.size(); i++) {
            RenderizadorBMP.escribirBMP(frames.get(i).estado,"current.bmp");
            if (op == 1) {
                Thread.sleep(100);
            } 
            else {
                System.out.println("Enter = siguiente | q = salir");
                String input = sc.nextLine();

                if (input.equalsIgnoreCase("q")) {
                    System.out.println("Volviendo al menú...");
                    break;
                }
            }
        }
        System.out.println("Listo.");
        sc.close();
    }
}