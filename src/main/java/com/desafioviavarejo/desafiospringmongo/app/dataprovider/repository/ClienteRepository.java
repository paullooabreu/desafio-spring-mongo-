package com.desafioviavarejo.desafiospringmongo.app.dataprovider.repository;

import com.desafioviavarejo.desafiospringmongo.app.dataprovider.entity.ClienteTable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends MongoRepository<ClienteTable, String> {
    List<ClienteTable> findByCpfCliente(String cpf);

    ClienteTable findByIdCliente(String idCliente);
}
