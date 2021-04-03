package com.desafioviavarejo.desafiospringmongo.app.core.entity;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClienteEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientePageResult {

    private Long offset;
    private Long limit;
    private Long records;
    private List<ClienteEntity> clientes;
}
