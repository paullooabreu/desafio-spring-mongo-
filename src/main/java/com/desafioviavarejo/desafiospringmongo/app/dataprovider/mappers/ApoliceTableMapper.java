package com.desafioviavarejo.desafiospringmongo.app.dataprovider.mappers;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApoliceEntity;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.entity.ApoliceTable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ApoliceTableMapper {

    ApoliceTableMapper INSTANCE = Mappers.getMapper(ApoliceTableMapper.class);

    ApoliceTable toTable(ApoliceEntity entity);


    @Mapping(target = "apoliceVencida", ignore = true)
    @Mapping(target = "descricaoApolice", ignore = true)
    ApoliceEntity toEntity(ApoliceTable entity);

}
