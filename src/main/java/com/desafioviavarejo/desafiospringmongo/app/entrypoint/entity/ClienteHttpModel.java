package com.desafioviavarejo.desafiospringmongo.app.entrypoint.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteHttpModel {

    private String idCliente;

    @NotBlank(message = "O campo Nome é obrigatório")
    private String nomeCliente;

    @NotBlank(message = "O campo CPF é obrigatório")
    @Size(min = 11, message = "O campo CPF deve conter 11 caracteres")
    private String cpfCliente;

    @NotBlank(message = "O campo Cidade é obrigatório")
    private String cidadeCliente;

    @NotBlank(message = "O campo UF é obrigatório")
    private String ufCliente;
}
