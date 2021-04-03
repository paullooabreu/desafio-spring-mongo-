package com.desafioviavarejo.desafiospringmongo.app.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApolicePageResult {
    private Long offset;
    private Long limit;
    private Long records;
    private List<ApoliceEntity> apolices;
}
