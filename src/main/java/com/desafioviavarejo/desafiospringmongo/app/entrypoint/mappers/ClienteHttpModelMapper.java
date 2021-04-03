package com.desafioviavarejo.desafiospringmongo.app.entrypoint.mappers;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClienteEntity;
import com.desafioviavarejo.desafiospringmongo.app.entrypoint.entity.ClienteHttpModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClienteHttpModelMapper {

    ClienteHttpModelMapper INSTANCE = Mappers.getMapper(ClienteHttpModelMapper.class);

    ClienteHttpModel toHttpModel(ClienteEntity entity);

    ClienteEntity toEntity(ClienteHttpModel http);
}
