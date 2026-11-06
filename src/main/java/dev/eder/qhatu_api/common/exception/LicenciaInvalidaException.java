package dev.eder.qhatu_api.common.exception;

public class LicenciaInvalidaException extends RuntimeException{

    // 402: no hay licencia o ya venció
    public LicenciaInvalidaException (String mensaje){
        super(mensaje);
    }
}
