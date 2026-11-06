package dev.eder.qhatu_api.common.dto;

// Todas las respuestas OK tienen la misma forma: { "mensaje": "...", "data": ... }
public record ApiRespuesta<T>(
        String mensaje,
        T data) {

}
