package dev.eder.qhatu_api.common.util;

// Limpieza de textos que llegan del front o del Excel

public final class Textos {

    private Textos(){}

    // "  martillo   de  acero " -> "MARTILLO DE ACERO"
    public static String mayuscula(String texto){
        if (texto == null) {
            return null;
        }
        return texto.trim().replaceAll("\\s+", "").toUpperCase();
    }

    // "" o "   " -> null (para no guardar textos vacíos)
    public static String vacioNull(String texto){
        if (texto == null || texto.isBlank()) {
            return null;
        }
        return texto.trim();
    }

    // Deja solo los dígitos: " 2060-1234565 " -> "20601234565"
    public static String soloDigitos(String texto){
        return texto == null ? "":texto.replace("\\D", "");
    }
}
