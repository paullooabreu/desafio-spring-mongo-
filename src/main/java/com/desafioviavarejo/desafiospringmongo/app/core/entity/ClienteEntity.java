package com.desafioviavarejo.desafiospringmongo.app.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEntity {
    private String idCliente;
    private String nomeCliente;
    private String cpfCliente;
    private String cidadeCliente;
    private String ufCliente;
}
