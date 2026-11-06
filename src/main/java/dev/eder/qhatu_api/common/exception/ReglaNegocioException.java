package dev.eder.qhatu_api.common.exception;

// 409: los datos están bien escritos, pero el negocio no lo permite (stock, caja cerrada...)

public class ReglaNegocioException extends RuntimeException {

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
