package com.desafioviavarejo.desafiospringmongo.app.core.exception.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiError {
    private final String codigo;
    private final String mensagem;
}
