package com.desafioviavarejo.desafiospringmongo.app.core.exception;

public class BusinessException extends RuntimeException {
    private String codigo;

    public BusinessException(String codigo, String mensagem, Throwable causa) {
        super(mensagem, causa);
        this.codigo = codigo;
    }

    public BusinessException(String codigo, String mensagem) {
        super(mensagem);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return this.codigo;
    }
}
