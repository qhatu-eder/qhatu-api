package dev.eder.qhatu_api.common.util;

public final class ValidadorDocumento {

    // Pesos oficiales para los 10 primeros dígitos del RUC
    private static final int[] PESOS_RUC = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};

    private ValidadorDocumento() {
    }

    public static boolean esDniValido(String dni) {
        return dni != null && dni.matches("^\\d{8}$");
    }

    // 11 dígitos, empieza con 10, 15, 17 o 20, y el último cuadra con el módulo 11
    public static boolean esRucValido(String ruc) {
        if (ruc == null || !ruc.matches("^(10|15|17|20)\\d{9}$")) {
            return false;
        }
        int suma = 0;
        for (int i = 0; i < 10; i++) {
            suma += Character.getNumericValue(ruc.charAt(i)) * PESOS_RUC[i];
        }
        int esperado = 11 - (suma % 11);
        if (esperado == 10) {
            esperado = 0;
        } else if (esperado == 11) {
            esperado = 1;
        }
        return esperado == Character.getNumericValue(ruc.charAt(10));
    }
}