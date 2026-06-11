package utiles;

public class ValidacionesUtilesCiudad7 {

    /**
     * Pre: mensaje no es nulo.
     * Post: lanza IllegalArgumentException si el objeto es nulo.
     */
    public static void validarNoNulo(Object obj, String mensaje) {
        if (obj == null) {
            throw new IllegalArgumentException(mensaje + " no puede ser nulo.");
        }
    }

    /**
     * Pre: mensaje no es nulo.
     * Post: lanza IllegalArgumentException si el String es nulo o vacío.
     */
    public static void validarNoVacio(String texto, String mensaje) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje + " no puede ser nulo ni vacío.");
        }
    }

    /**
     * Pre: mensaje no es nulo.
     * Post: lanza IllegalArgumentException si el valor no es estrictamente mayor a cero.
     */
    public static void validarMayorACero(int valor, String mensaje) {
        if (valor <= 0) {
            throw new IllegalArgumentException(mensaje + " debe ser mayor a cero. Valor recibido: " + valor);
        }
    }

    /**
     * Pre: mensaje no es nulo.
     * Post: lanza IllegalArgumentException si el valor es negativo.
     */
    public static void validarNoNegativo(int valor, String mensaje) {
        if (valor < 0) {
            throw new IllegalArgumentException(mensaje + " no puede ser negativo. Valor recibido: " + valor);
        }
    }

    /**
     * Pre: min <= max, mensaje no es nulo.
     * Post: lanza IllegalArgumentException si el valor está fuera del rango [min, max].
     */
    public static void validarRango(int valor, int min, int max, String mensaje) {
        if (valor < min || valor > max) {
            throw new IllegalArgumentException(mensaje + " debe estar entre " + min + " y " + max + ". Valor recibido: " + valor);
        }
    }
}
