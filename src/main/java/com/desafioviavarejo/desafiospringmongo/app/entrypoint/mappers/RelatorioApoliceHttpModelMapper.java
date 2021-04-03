package com.desafioviavarejo.desafiospringmongo.app.entrypoint.mappers;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApoliceEntity;
import com.desafioviavarejo.desafiospringmongo.app.entrypoint.entity.RelatorioApoliceHttpModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RelatorioApoliceHttpModelMapper {

    RelatorioApoliceHttpModelMapper INSTANCE = Mappers.getMapper(RelatorioApoliceHttpModelMapper.class);

    RelatorioApoliceHttpModel toRelatorio(ApoliceEntity entity);
}
