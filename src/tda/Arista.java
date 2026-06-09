package tda;

/*
* Representa una Arista (borde) en el grafo.
* @param <T> El tipo del valor almacenado en los vértices.
* @param <U> El tipo del peso almacenado en la arista.
*/
public class Arista<T, U> {
   private U peso;
   private Vertice<T, U> destino;

   public Arista(U peso, Vertice<T, U> destino) {
       this.peso = peso;
       this.destino = destino;
   }

   public U getPeso() {
       return peso;
   }

   public Vertice<T, U> getDestino() {
       return destino;
   }

   @Override
   public String toString() {
       // Muestra el destino y el peso
       return "-> [" + destino.getValor() + "](" + peso + ")";
   }
}
