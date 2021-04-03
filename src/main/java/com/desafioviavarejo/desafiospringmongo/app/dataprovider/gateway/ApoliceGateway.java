package com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApoliceEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApolicePageResult;

public interface ApoliceGateway {
    ApoliceEntity salvarApolice(ApoliceEntity apolice);

    ApoliceEntity buscarApolicePorNumero(String numeroApolice);

    ApoliceEntity buscarApolicePorId(String idApolice);

    ApolicePageResult buscarApolices(Integer offset, Integer limit);

    void atualizarApolice(ApoliceEntity apoliceNova);

    void excluirApolice(String idApolice);
}
