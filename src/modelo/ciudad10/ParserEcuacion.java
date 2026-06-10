package modelo.ciudad10;

/**
 * Clase funcional encargada de interpretar (parsear) el texto ingresado
 * por el usuario y convertirlo en una instancia del TDA EcuacionRecurrencia.
 */
public class ParserEcuacion {

    /**
     * Procesa una cadena de texto y extrae los coeficientes matemáticos correspondientes.
     * @pre El parámetro 'entrada' no debe ser nulo ni estar vacío.
     * @post Se instancia y devuelve un objeto EcuacionRecurrencia inmutable y validado.
     * @param entrada Texto ingresado por el usuario a través de la interfaz gráfica.
     * @return Una instancia válida de EcuacionRecurrencia.
     */
    public EcuacionRecurrencia parsear(String entrada) {
        // 1. Delegamos la limpieza y el corte a un método helper (¡Modularización!)
        String[] partes = normalizarYDividir(entrada);

        String parteRecursiva = partes[0];
        String parteCosto = partes[1];

        validarFinTerminoRecursivo(parteRecursiva);

        EcuacionRecurrencia.TipoEcuacion tipoDetectado;
        if (parteRecursiva.contains("/")) {
            tipoDetectado = EcuacionRecurrencia.TipoEcuacion.DIVISION;
        } else if (parteRecursiva.contains("-")) {
            tipoDetectado = EcuacionRecurrencia.TipoEcuacion.SUSTRACCION;
        } else {
            throw new IllegalArgumentException("Error: Falta el operador '/' o '-' en el término recursivo.");
        }

        int parametroA = extraerParametroA(parteRecursiva);
        int parametroB = extraerParametroB(parteRecursiva, tipoDetectado);
        int gradoK = extraerGradoK(parteCosto);

        return new EcuacionRecurrencia(tipoDetectado, parametroA, parametroB, gradoK);
    }

    /**
     * Limpia el texto de entrada y lo separa en los dos términos principales de la ecuación.
     * @param entrada Texto crudo del usuario.
     * @return Arreglo de 2 posiciones: [0] Término recursivo, [1] Término de costo.
     */
    private String[] normalizarYDividir(String entrada) {
        if (entrada == null || entrada.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: La ecuación ingresada está vacía.");
        }

        String textoNormalizado = entrada.replaceAll("\\s+", "").toUpperCase();

        if (textoNormalizado.startsWith("T(N)=")) {
            textoNormalizado = textoNormalizado.substring(5);
        }

        String[] partes = textoNormalizado.split("\\+O\\(");
        if (partes.length != 2) {
            throw new IllegalArgumentException("Error: Formato inválido. Use '+O(' para separar el término recursivo del costo.");
        }

        return partes;
    }

    /**
     * Verifica estructuralmente que no existan caracteres adicionales no evaluados al final de la llamada recursiva.
     * * @param parteRecursiva La subcadena de texto que contiene el llamado recursivo.
     * @throws IllegalArgumentException Si se encuentra texto basura luego del paréntesis de cierre.
     */
    private void validarFinTerminoRecursivo(String parteRecursiva) {
        int indiceParentesisCierre = parteRecursiva.indexOf(')');
        if (indiceParentesisCierre == -1) {
            throw new IllegalArgumentException("Error: Falta el paréntesis de cierre ')' en la llamada recursiva.");
        }
        if (parteRecursiva.length() > indiceParentesisCierre + 1) {
            throw new IllegalArgumentException("Error: Se detectaron caracteres extraños después de la llamada recursiva.");
        }
    }

    /**
     * Aísla y extrae el coeficiente 'a' de la subcadena recursiva.
     * * @param parteRecursiva La subcadena de texto que contiene el llamado recursivo.
     * @return El coeficiente de cantidad de subproblemas procesado como entero.
     * @throws IllegalArgumentException Si el valor extraído no puede ser casteado a número entero.
     */
    private int extraerParametroA(String parteRecursiva) {
        int indiceT = parteRecursiva.indexOf('T');
        if (indiceT == -1) {
            throw new IllegalArgumentException("Error: Falta la letra 'T' en el término recursivo.");
        }

        if (indiceT == 0) {
            return 1;
        }

        String valorA = parteRecursiva.substring(0, indiceT);
        try {
            return Integer.parseInt(valorA);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: El coeficiente 'a' (" + valorA + ") no es un número entero válido.");
        }
    }

    /**
     * Aísla y extrae el valor 'b' de reducción matemática, dependiendo del operador.
     * * @param parteRecursiva La subcadena de texto que contiene el llamado recursivo.
     * @param tipo El tipo de ecuación para determinar el símbolo a buscar ('/' o '-').
     * @return El factor de reducción procesado como entero.
     * @throws IllegalArgumentException Si el valor extraído no es numérico.
     */
    private int extraerParametroB(String parteRecursiva, EcuacionRecurrencia.TipoEcuacion tipo) {
        char operador = (tipo == EcuacionRecurrencia.TipoEcuacion.DIVISION) ? '/' : '-';
        int indiceOperador = parteRecursiva.indexOf(operador);
        int indiceParentesisCierre = parteRecursiva.indexOf(')');

        String valorB = parteRecursiva.substring(indiceOperador + 1, indiceParentesisCierre);
        try {
            return Integer.parseInt(valorB);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: El parámetro 'b' (" + valorB + ") no es un número entero válido.");
        }
    }

    /**
     * Extrae de forma inteligente el grado máximo 'k' del costo de combinación polinómico.
     * * @param parteCosto La subcadena de texto que contiene la notación Big-O.
     * @return El grado asintótico procesado como entero.
     * @throws IllegalArgumentException Si el grado de la ecuación está malformado.
     */
    private int extraerGradoK(String parteCosto) {
        if (!parteCosto.endsWith(")")) {
            throw new IllegalArgumentException("Error: Falta el paréntesis de cierre ')' en la notación O().");
        }

        String funcionInterna = parteCosto.substring(0, parteCosto.length() - 1);
        int indicePotencia = funcionInterna.indexOf('^');

        if (indicePotencia != -1) {
            String exponente = funcionInterna.substring(indicePotencia + 1);

            int finExponente = exponente.indexOf('+');
            if (finExponente == -1) {
                finExponente = exponente.indexOf('-');
            }
            if (finExponente != -1) {
                exponente = exponente.substring(0, finExponente);
            }

            try {
                return Integer.parseInt(exponente);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error: El grado 'k' (" + exponente + ") no es válido.");
            }

        } else if (funcionInterna.contains("N")) {
            return 1;
        } else {
            try {
                int finConstante = funcionInterna.indexOf('+');
                if (finConstante == -1) {
                    finConstante = funcionInterna.indexOf('-');
                }
                String constanteConstatada = (finConstante != -1) ? funcionInterna.substring(0, finConstante) : funcionInterna;

                Integer.parseInt(constanteConstatada);
                return 0;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error: El costo O(" + funcionInterna + ") no es válido.");
            }
        }
    }
}
