package com.desafioviavarejo.desafiospringmongo.app.entrypoint.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponse {
    private Long offset;
    private Long limit;
    private Long records;
    private List<ClienteHttpModel> clientes;
}
