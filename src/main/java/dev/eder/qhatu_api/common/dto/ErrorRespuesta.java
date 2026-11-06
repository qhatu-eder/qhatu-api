package dev.eder.qhatu_api.common.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorRespuesta(

        int estado,
        String mensaje,
        Map<String, String> detalles,
        LocalDateTime fecha) {

}
