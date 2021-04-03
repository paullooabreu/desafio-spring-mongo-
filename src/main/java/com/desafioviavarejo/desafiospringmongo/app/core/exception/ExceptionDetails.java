package com.desafioviavarejo.desafiospringmongo.app.core.exception;

import lombok.Getter;

@Getter
public enum ExceptionDetails {
    ERRO_SALVAR_CLIENTE("VIA-0001 ", "Erro ao salvar o cliente"),
    ERRO_CPF_INVALIDO("VIA-0002 ", "O CPF é Inválido"),
    ERRO_CADASTRADO("VIA-0003 ", "O CPF já está cadastrado"),
    ERRO_OBTER_CLIENTE_CPF("VIA-0004 ", "Erro ao obter Clientes por CPF"),
    ERRO_OBTER_CLIENTE_ID("VIA-0005 ", "Erro ao obter Cliente por Id"),
    ERRO_OBTER_CLIENTES("VIA-0006 ", "Erro ao obter Clientes"),
    ERRO_ATUALIZAR_CLIENTE("VIA-0006 ", "Erro ao atualizar o Cliente"),
    ERRO_EXCLUIR_CLIENTE("VIA-0007 ", "Erro ao excluir o Cliente"),
    ERRO_SALVAR_APOLICE("VIA-0008 ", "Erro ao salvar a Apolice"),
    ERRO_DATA_APOLICE("VIA-0009 ", "A Data Inicial deve ser menor que a Data Final"),
    ERRO_ID_CLIENTE("VIA-0010 ", "O Id do cliente é Obrigatório"),
    ERRO_CLIENTE_NULL("VIA-0011 ", "O cliente não existe!"),
    ERRO_BUSCAR_APOLICE_NUMERO("VIA-0012 ", "O ao buscar a apolice pelo numero"),
    ERRO_OBTER_APOLICE_ID("VIA-0013 ", "Erro ao obter Apolice por Id"),
    ERRO_OBTER_APOLICES("VIA-0014 ", "Erro ao obter Apolices"),
    ERRO_ATUALIZAR_APOLICE("VIA-0015 ", "Erro ao Atualizar a Apolice"),
    ERRO_EXCLUIR_APOLICE("VIA-0016 ", "Erro ao Excluir a Apolice"),
    ERRO_APOLICE_NAO_EXISTE("VIA-0016 ", "Erro apolice não existe");

    ExceptionDetails(String codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }

    private String codigo;
    private String mensagem;
}
