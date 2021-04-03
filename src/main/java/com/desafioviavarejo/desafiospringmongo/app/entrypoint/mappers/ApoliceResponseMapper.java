package com.desafioviavarejo.desafiospringmongo.app.entrypoint.mappers;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApolicePageResult;
import com.desafioviavarejo.desafiospringmongo.app.entrypoint.entity.ApoliceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ApoliceResponseMapper {

    ApoliceResponseMapper INSTANCE = Mappers.getMapper(ApoliceResponseMapper.class);

    ApoliceResponse toResponse(ApolicePageResult page);

    ApolicePageResult toPage(ApoliceResponse response);

}
