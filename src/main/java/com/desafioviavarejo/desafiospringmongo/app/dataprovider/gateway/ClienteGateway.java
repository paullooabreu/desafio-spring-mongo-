package com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClienteEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClientePageResult;
import com.desafioviavarejo.desafiospringmongo.app.entrypoint.entity.ClienteHttpModel;

import java.util.List;

public interface ClienteGateway {

    ClienteEntity salvarCliente(ClienteEntity cliente);

    List<ClienteEntity> obterClientePorCpf(String cpf);

    ClienteEntity buscarClientePorId(String idCliente);

    ClientePageResult buscarClientes(Integer offset, Integer limit);

    void atualizarCliente(ClienteEntity cliente);

    void excluirCliente(String idCliente);
}
