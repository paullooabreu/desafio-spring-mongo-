package com.desafioviavarejo.desafiospringmongo.app.util;

import lombok.Data;

@Data
public class MensagensLog {

    //Cliente
    public static final String SALVAR_CLIENTE_INICIO = "Salvar cliente - Iniciado";
    public static final String SALVAR_CLIENTE_FIM = "Salvar cliente - Excluir";
    public static final String BUSCAR_CLIENTE_ID_INICIO = "Buscar cliente por id - Iniciado";
    public static final String BUSCAR_CLIENTE_ID_FIM = "Buscar cliente por id - Excluir";
    public static final String BUSCAR_CLIENTES_INICIO = "Buscar clientes - Iniciado";
    public static final String BUSCAR_CLIENTES_FIM = "Buscar clientes - Excluir";
    public static final String ATUALIZAR_CLIENTE_INICIO = "Atualizar Cliente - Iniciado";
    public static final String ATUALIZAR_CLIENTE_FIM = "Atualizar cliente - Excluir";
    public static final String EXCLUIR_CLIENTE_INICIO = "Excluir cliente - Iniciado";
    public static final String EXCLUIR_CLIENTE_FIM = "Excluir clientes - Excluir";
    public static final String OBTER_CLIENTE_CPF_INICIO = "Obter cliente por cpf - Iniciado";
    public static final String OBTER_CLIENTE_CPF_FIM = "Excluir clientes por cpf - Excluir";

    //Apolice
    public static final String SALVAR_APOLICE_INICIO = "Salvar apolice - Iniciado";
    public static final String SALVAR_APOLICE_FIM = "Salvar apolice - Excluir";
    public static final String BUSCAR_APOLICE_ID_INICIO = "Buscar apolice por Id - Iniciado";
    public static final String BUSCAR_APOLICE_ID_FIM = "Buscar apolice por Id - Excluir";
    public static final String BUSCAR_APOLICES_INICIO = "Buscar apolices - Iniciado";
    public static final String BUSCAR_APOLICES_FIM = "Buscar apolices - Excluir";
    public static final String ATUALIZAR_APOLICE_INICIO = "Atualizar apolice - Iniciado";
    public static final String ATUALIZAR_APOLICE_FIM = "Atualizar apolice - Excluir";
    public static final String EXCLUIR_APOLICE_INICIO = "Excluir apolice - Iniciado";
    public static final String EXCLUIR_APOLICE_FIM = "Excluir apolice - Excluir";

    //Relatorio de Apolice
    public static final String BUSCAR_APOLICE_NUMERO_INICIO = "Buscar apolice por numero - Iniciado";
    public static final String BUSCAR_APOLICE_NUMERO_FIM = "Buscar apolice por numero - Excluir";

}
