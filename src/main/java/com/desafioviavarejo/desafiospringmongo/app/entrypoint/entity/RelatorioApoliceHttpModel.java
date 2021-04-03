package com.desafioviavarejo.desafiospringmongo.app.entrypoint.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioApoliceHttpModel {
    private String idApolice;

    private String idCliente;

    private String numeroApolice;

    private LocalDate inicioVigencia;

    private LocalDate fimVigencia;

    private String placaVeiculo;

    private BigDecimal valorApolice;

    private boolean apoliceVencida;

    private String descricaoApolice;
}
