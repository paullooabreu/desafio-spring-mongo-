package com.desafioviavarejo.desafiospringmongo.app.dataprovider.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "apolice")
public class ApoliceTable {

    @Id
    private String idApolice;

    private String idCliente;

    private String numeroApolice;

    private LocalDate inicioVigencia;

    private LocalDate fimVigencia;

    private String placaVeiculo;

    private BigDecimal valorApolice;

}
