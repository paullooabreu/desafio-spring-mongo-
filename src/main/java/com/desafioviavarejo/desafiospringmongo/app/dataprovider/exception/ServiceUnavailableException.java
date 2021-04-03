package com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception;

public class ServiceUnavailableException extends RuntimeException {

    private String codigo;

    public ServiceUnavailableException(String codigo, String mensagem, Throwable causa) {
        super(mensagem, causa);
        this.codigo = codigo;
    }

    public ServiceUnavailableException(String codigo, String mensagem) {
        super(mensagem);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return this.codigo;
    }
}
