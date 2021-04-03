package com.desafioviavarejo.desafiospringmongo.app.entrypoint.mappers;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApoliceEntity;
import com.desafioviavarejo.desafiospringmongo.app.entrypoint.entity.ApoliceHttpModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ApoliceHttpModelMapper {

    ApoliceHttpModelMapper INSTANCE = Mappers.getMapper(ApoliceHttpModelMapper.class);

    ApoliceHttpModel toHttpModel(ApoliceEntity entity);


    @Mapping(target = "apoliceVencida", ignore = true)
    @Mapping(target = "descricaoApolice", ignore = true)
    ApoliceEntity toEntity(ApoliceHttpModel httpModel);
}
