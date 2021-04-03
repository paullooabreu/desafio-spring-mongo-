package com.desafioviavarejo.desafiospringmongo.app.dataprovider.repository;

import com.desafioviavarejo.desafiospringmongo.app.dataprovider.entity.ApoliceTable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApoliceRepository extends MongoRepository<ApoliceTable, String> {
    ApoliceTable findByNumeroApolice(String numeroApolice);

    ApoliceTable findByIdApolice(String idApolice);

    void deleteByIdApolice(String idApolice);
}
