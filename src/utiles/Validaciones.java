package utiles;

public class Validaciones {
	
	/**
	 * Si el valor es menor a 0, lanza una excepcion
	 * @param valor dato a validar
	 * @param nombre titulo a mostrar en el error
	 */
	public static void validarMayorACero(double valor, String nombre) {		
		if (valor <= 0) {
			throw new RuntimeException("El " + nombre + " debe ser mayor a 0. Se ingreso " + valor);
		}
	}
	
	public static void validarMayorAUno(double valor, String nombre) {		
		if (valor <= 1) {
			throw new RuntimeException("El " + nombre + " debe ser mayor a 0. Se ingreso " + valor);
		}
	}
	
	public static void validarMayorOIgualACero(double valor, String nombre) {		
		if (valor < 0) {
			throw new RuntimeException("El " + nombre + " debe ser mayor o igual a 0. Se ingreso " + valor);
		}
	}
	
	/**
	 * Valida la longitud del texto este entre desde y hasta (inclusive). 
	 * @param valor el valor a validar
	 * @param desde cantidad minima de caracteres (inclusive). Puede ser 0 o mayor.
	 * @param hasta cantidad maxima de caracteres (inclusive). Puede ser 0 o mayor.
	 * @param nombreDelAtributo nombre del atributo a informar
	 * @return lanza excepcion sino cumple la validacion
	 */
    public static void validarLongitudDeTexto(String valor, int desde, Integer hasta, String nombreDelAtributo) {
        if ((valor == null) ||
           ((desde > 0) && valor.trim().isEmpty()) ||
           (valor.length() < desde) ||
           ((hasta != null) && valor.length() > hasta)) {
        	throw new RuntimeException("El " + nombreDelAtributo + " debe estar entre " + desde + " y " + hasta + " caracteres. Se ingreso " + valor);
        }
    }

    /**
     * Valida que el texto (valor) tenga letras o espacios.
     * @param valor una cadena entre a-z y A-Z con espacios.
     * @param nombreDelAtributo nombre del atributo a informar
     * @return lanza excepcion sino cumple la validacion
     */
    public static void validarCaracteresAlfabeticos(String valor, String nombreDelAtributo) {
        // 3. Verificar que contenga solo letras y espacios
        // [a-zA-Z ]+ significa: cualquier caracter de la 'a' a la 'z',
        // mayúscula o minúscula, más el espacio. El '+' indica "uno o más".
        if (!valor.matches("[a-zA-Z ]+")) {
        	throw new RuntimeException("El " + nombreDelAtributo + " debe tener solo letras y espacios. Se ingreso " + valor);
        }
    }

    /**
     * Valida que valor sea verdadero
     * @param valor
     * @param texto
     */
	public static void validarFalso(boolean valor, String texto) {
		if (valor) {
			throw new RuntimeException(texto);
		}		
	}

	/**
	 * Valida que el parametro sea distinto de nulo
	 * @param object
	 * @param texto
	 */
	public static void esDistintoDeNull(Object object, String texto) {
		if (object == null) {
			throw new RuntimeException("El " + texto + " no puede ser nulo");
		}		
	}
	
	public static void esIgualDeNull(Object object, String texto) {
		if (object != null) {
			throw new RuntimeException("El " + texto + " no puede ser distinto nulo");
		}		
	}

	public static void validarVerdadero(boolean valor, String texto) {
		validarFalso(!valor, texto);
	}
    
	@SuppressWarnings("unchecked")
	public static <E> void validarRangoDeEnum(E valorAChequear, E... valoresPosibles) {
		if (valorAChequear == null) {
			throw new RuntimeException("El valor a chequear es nulo");
		}
		if (valoresPosibles != null) {
			for(E valor: valoresPosibles) {
				if (valor.equals(valorAChequear)) {
					return;
				}
			}
		}
		throw new RuntimeException("El valor " + valorAChequear + " no esta en " + valoresPosibles);
	}
	
	/**
	 * Valida el rango de enteros, inclusive
	 * @param desde
	 * @param hasta
	 * @param valor
	 */
    public static void validarRangoNumerico(int valor, int desde, int hasta, String texto){
        if(valor > hasta || valor < desde){
            throw new RuntimeException("El valor de " + texto + " no esta en el rango " + desde + "-" + hasta + " inclusive. Vale " + valor);
        }
    }

    /**
     * Valida que el rango de valor, este entre el limite inicial y final (inclusive)
     * @param valor
     * @param limiteInicial
     * @param limiteFinal
     * @param texto
     */
	public static void validarRango(double valor, double limiteInicial, double limiteFinal, String texto) {
		if (valor < limiteInicial) {
			throw new RuntimeException(texto + " no respeta el limite inicial " + limiteInicial);
		}
		if (valor > limiteFinal) {
			throw new RuntimeException(texto + " no respeta el limite final " + limiteFinal);
		}
	}
    
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
//METODOS DE CONSULTA DE ESTADO ---------------------------------------------------------------------------	
//GETTERS REDEFINIDOS -------------------------------------------------------------------------------------
//GETTERS INICIALIZADOS -----------------------------------------------------------------------------------
//GETTERS COMPLEJOS ---------------------------------------------------------------------------------------
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS COMPLEJOS----------------------------------------------------------------------------------------	
//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}
