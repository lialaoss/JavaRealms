# ⚔️ AI-Quest (JavaRealms) - Aventura Algorítmica

**Universidad de Buenos Aires (UBA)** **Licenciatura en Análisis de Sistemas** **Trabajo Práctico 2: Algoritmos y Estructuras de Datos**

---

## 📖 Descripción del Proyecto
**AI-Quest** es un entorno interactivo y gamificado diseñado para resolver y visualizar problemas algorítmicos complejos. El sistema simula un mundo dividido en "Ciudades", conectadas mediante un TDA Grafo, donde cada nodo representa un minijuego o desafío matemático distinto. 

Para avanzar en el mapa, el jugador debe superar retos que abarcan desde el recorrido de laberintos y ordenamiento de vectores, hasta el cálculo asintótico y el ruteo de flujos en redes.

## 🏗️ Arquitectura General
El motor principal del juego fue desarrollado desde cero utilizando **Java Swing** y **Graphics2D** sin motores gráficos de terceros. 
* **Game Loop Robusto:** La clase `Panel` implementa un hilo (`Thread`) dedicado que actualiza la lógica matemática y renderiza los gráficos a **60 FPS** constantes.
* **Patrón State / MVC:** Control centralizado mediante la clase `AdministradorJuego`, permitiendo transiciones fluidas entre el Menú, el Mapa General y los Minijuegos.
* **Patrón Observer:** Ampliamente utilizado a lo largo del proyecto (`ObservadorOrdenamiento`, `ObservadorRecoleccion`, `ObservadorVictoria`) para desacoplar la algoritmia pesada de la renderización gráfica.

## 🏰 Módulos y Ciudades Integradas

El mapa cuenta con 10 ciudades, cada una con un enfoque algorítmico específico:

* **Ciudad 1: Exploración y Recolección (Matrices 3D)**
  * Exploración de un `Mapa3D` con gestión de "Niebla de Guerra".
  * Uso de polimorfismo para objetos recolectables (Bengalas, Antorchas, Radares).
* **Ciudad 2: El Problema de las N-Reinas**
  * Motor de resolución de tableros $N \times N$ utilizando el paradigma de **Backtracking** (Pilas/DFS).
* **Ciudad 3: Escape del Laberinto**
  * Algoritmos de búsqueda de caminos no informados (**BFS** y **DFS**).
  * Renderizado del historial de exploración exportado fotograma a fotograma en formato `.bmp`.
* **Ciudad 4: Módulo de Ordenamiento**
  * Implementación visual y paralela de algoritmos como **Bubble Sort** y **Quick Sort** (Divide y Vencerás).
* **Ciudad 6: El Oráculo Hash**
  * Implementación de una `TablaHash` didáctica con resolución de colisiones mediante *Linear Probing*.
* **Ciudad 7: Redes de Energía (Grafos y Flujos)**
  * TDA `GrafoFlujo` que calcula caminos mínimos y cuellos de botella implementando el algoritmo de **Ford-Fulkerson**.
* **Ciudad 8: Torres de Hanoi**
  * Árbol de recursividad gráfica para resolver el clásico problema matemático paso a paso.
* **Ciudad 9: Sistema de Combate (Pilas, Colas y Listas)**
  * Batalla por turnos orquestada por una `ColaTurnos` (FIFO) para las iniciativas, una `PilaAcciones` (LIFO) para acumular combos, y una `ListaEnemigos` dinámica.
* **Ciudad 10: El Laboratorio del Teorema Maestro**
  * Procesador de texto (*Parser*) y motor matemático para resolver ecuaciones de recurrencia y graficar asintóticamente la notación $\Theta$ (Big-Theta) en archivos `.bmp`.

## 💻 Tecnologías Utilizadas
* **Lenguaje:** Java 8+
* **Framework Gráfico:** `javax.swing` y `java.awt` nativo.
* **Procesamiento de Imágenes:** `javax.imageio.ImageIO` (Generación Headless de BMPs).

## 🚀 Instrucciones de Ejecución
1. Clonar el repositorio localmente.
2. Abrir el proyecto en un IDE compatible (IntelliJ IDEA, Eclipse, etc.).
3. Ubicar la clase de entrada en el paquete `main`: **`src/main/Main.java`**.
4. Ejecutar el método `main()`. El juego cargará los *assets* (`GestorRecursos`) e iniciará en el Menú Principal.
5. Los archivos de solución estáticos (`.bmp`) se generarán en la carpeta raíz a medida que el jugador resuelva las Ciudades 3 y 10.
