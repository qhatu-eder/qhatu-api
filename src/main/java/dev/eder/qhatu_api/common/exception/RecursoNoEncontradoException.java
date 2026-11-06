package dev.eder.qhatu_api.common.exception;

// 404: lo que se busca no existe
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
