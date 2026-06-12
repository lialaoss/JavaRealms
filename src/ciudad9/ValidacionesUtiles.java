package ciudad9;

/*
  Clase para centralizar las validaciones del sistema.
 */
public class ValidacionesUtiles {
    
    /*
      Pre: Ninguna.
      Post: Devuelve true si el objeto recibido es nulo, false en caso contrario.
     */
    public static boolean esNulo(Object obj) {
        return obj == null;
    }

    /*
      Pre: Ninguna.
      Post: Devuelve el valor original si es mayor o igual a 0. Si es negativo, retorna 0.
     */
    public static int asegurarPositivo(int valor) {
        return Math.max(0, valor);
    }
}