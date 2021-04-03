package com.desafioviavarejo.desafiospringmongo.app.dataprovider.mappers;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClienteEntity;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.entity.ClienteTable;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClienteTableMapper {

    ClienteTableMapper INSTANCE = Mappers.getMapper(ClienteTableMapper.class);

    ClienteTable toTable(ClienteEntity entity);

    ClienteEntity toEntity(ClienteTable table);
}
