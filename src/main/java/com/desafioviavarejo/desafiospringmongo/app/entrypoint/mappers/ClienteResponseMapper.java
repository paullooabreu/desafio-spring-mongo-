package com.desafioviavarejo.desafiospringmongo.app.entrypoint.mappers;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClientePageResult;
import com.desafioviavarejo.desafiospringmongo.app.entrypoint.entity.ClienteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClienteResponseMapper {

    ClienteResponseMapper INSTANCE = Mappers.getMapper(ClienteResponseMapper.class);

    ClienteResponse toResponse(ClientePageResult pageResult);
}
