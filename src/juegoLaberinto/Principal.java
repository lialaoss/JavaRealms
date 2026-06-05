package juegoLaberinto;

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
        } else {
            dir.mkdirs();
        }

        // carga laberinto
        Laberinto lab = new Laberinto("laberintos/lab1.txt");

        lab.mostrarMapa();

        System.out.println("Inicio: " + lab.getInicio());
        System.out.println("Fin: " + lab.getFin());

        // ejecuta el algoritmo de busqueda
        BFS bfs = new BFS();
        List<Snapshot> frames = bfs.buscar(lab);

        System.out.println("Frames generados: " + frames.size());

        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== MODO DE VISUALIZACIÓN ===");
        System.out.println("1 - Animación automática");
        System.out.println("2 - Paso a paso");
        System.out.print("Opción: ");

        int op = sc.nextInt();
        sc.nextLine(); // limpiar buffer

        System.out.println("\nIniciando visualización...");

        // Animacion de frames
        for (int i = 0; i < frames.size(); i++) {

            RenderizadorBMP.escribirBMP(frames.get(i).estado, "current.bmp");

            if (op == 1) {
                Thread.sleep(100);
            } else {

                System.out.println("Enter = siguiente | q = salir al menú");

                String input = sc.nextLine();

                if (input.equalsIgnoreCase("q")) {
                    System.out.println("Volviendo al menú...");
                    break;
                }
            }
        }

        System.out.println("Listo.");
    }
}