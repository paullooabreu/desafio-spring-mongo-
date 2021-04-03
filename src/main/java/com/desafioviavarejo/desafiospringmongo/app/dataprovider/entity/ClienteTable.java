package com.desafioviavarejo.desafiospringmongo.app.dataprovider.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cliente")
public class ClienteTable {

    @Id
    private String idCliente;
    private String nomeCliente;
    private String cpfCliente;
    private String cidadeCliente;
    private String ufCliente;
}
