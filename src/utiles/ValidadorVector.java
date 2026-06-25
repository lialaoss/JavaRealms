package utiles;

public class ValidadorVector {

    private static final int MIN_ELEMENTOS = 3;
    private static final int MAX_ELEMENTOS = 10;

    public static int[] parsear(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("El input no puede estar vacio.");
        }

        String[] partes = input.trim().split(",");

        if (partes.length < MIN_ELEMENTOS) {
            throw new IllegalArgumentException("Minimo " + MIN_ELEMENTOS + " elementos.");
        }

        if (partes.length > MAX_ELEMENTOS) {
            throw new IllegalArgumentException("Maximo " + MAX_ELEMENTOS + " elementos.");
        }

        int[] vector = new int[partes.length];

        for (int i = 0; i < partes.length; i++) {
            String parte = partes[i].trim();
            if (parte.isEmpty()) {
                throw new IllegalArgumentException("Hay una coma sin valor en la posicion " + (i + 1) + ".");
            }
            try {
                vector[i] = Integer.parseInt(parte);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("'" + parte + "' no es un numero entero valido.");
            }
        }

        return vector;
    }
}
