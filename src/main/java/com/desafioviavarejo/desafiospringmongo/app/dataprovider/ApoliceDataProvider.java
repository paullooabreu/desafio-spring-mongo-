package com.desafioviavarejo.desafiospringmongo.app.dataprovider;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApoliceEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApolicePageResult;
import com.desafioviavarejo.desafiospringmongo.app.core.exception.BusinessException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.entity.ApoliceTable;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ApoliceGateway;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.mappers.ApoliceTableMapper;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.repository.ApoliceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static com.desafioviavarejo.desafiospringmongo.app.core.exception.ExceptionDetails.*;
import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.*;

/**
 * Acesso as Dados de Apolices
 *
 * @author Paulo Abreu
 */
@Slf4j
@Component
public class ApoliceDataProvider implements ApoliceGateway {

    private final ApoliceRepository apoliceRepository;

    @Autowired
    public ApoliceDataProvider(ApoliceRepository apoliceRepository) {
        this.apoliceRepository = apoliceRepository;
    }

    @Override
    public ApoliceEntity salvarApolice(ApoliceEntity apolice) {
        ApoliceTable apoliceTable;
        try {
            log.info(SALVAR_APOLICE_INICIO);
            apoliceTable = apoliceRepository.save(ApoliceTableMapper.INSTANCE.toTable(apolice));
        } catch (Exception e) {
            log.info(SALVAR_APOLICE_FIM);
            throw new ServiceUnavailableException(ERRO_SALVAR_APOLICE.getCodigo(), ERRO_SALVAR_APOLICE.getMensagem(), e);
        }
        return ApoliceTableMapper.INSTANCE.toEntity(apoliceTable);
    }

    @Override
    public ApoliceEntity  buscarApolicePorNumero(String numeroApolice) {
        ApoliceTable apoliceTable;
        try {
            log.info(BUSCAR_APOLICE_NUMERO_INICIO);
            apoliceTable = apoliceRepository.findByNumeroApolice(numeroApolice);
        } catch (Exception e) {
            log.info(BUSCAR_APOLICE_NUMERO_FIM);
            throw new ServiceUnavailableException(ERRO_BUSCAR_APOLICE_NUMERO.getCodigo(), ERRO_BUSCAR_APOLICE_NUMERO.getMensagem(), e);
        }
        return ApoliceTableMapper.INSTANCE.toEntity(apoliceTable);
    }

    @Override
    public ApoliceEntity buscarApolicePorId(String idApolice) {
        ApoliceTable apolice;
        try {
            log.info(BUSCAR_APOLICE_ID_INICIO);
            apolice = this.apoliceRepository.findByIdApolice(idApolice);
        } catch (Exception e) {
            log.info(BUSCAR_APOLICE_ID_FIM);
            throw new ServiceUnavailableException(ERRO_OBTER_CLIENTE_ID.getCodigo(), ERRO_OBTER_CLIENTE_ID.getMensagem(), e);
        }
        return ApoliceTableMapper.INSTANCE.toEntity(apolice);
    }

    @Override
    public ApolicePageResult buscarApolices(Integer offset, Integer limit) {
        Page<ApoliceTable> apolices;
        try {
            log.info(BUSCAR_APOLICES_INICIO);
            apolices = this.apoliceRepository.findAll(PageRequest.of(offset, limit));
        } catch (Exception e) {
            log.info(BUSCAR_APOLICES_FIM);
            throw new ServiceUnavailableException(ERRO_OBTER_APOLICES.getCodigo(), ERRO_OBTER_APOLICES.getMensagem(), e);
        }
        return gerarResultado(offset, apolices);
    }


    @Override
    public void atualizarApolice(ApoliceEntity apolice) {
        try {
            log.info(ATUALIZAR_APOLICE_INICIO);
            this.apoliceRepository.save(ApoliceTableMapper.INSTANCE.toTable(apolice));
        } catch (Exception e) {
            log.info(ATUALIZAR_APOLICE_FIM);
            throw new ServiceUnavailableException(ERRO_ATUALIZAR_APOLICE.getCodigo(), ERRO_ATUALIZAR_APOLICE.getMensagem(), e);
        }
    }

    @Override
    public void excluirApolice(String idApolice) {
        try {
            log.info(EXCLUIR_APOLICE_INICIO);
            this.apoliceRepository.deleteByIdApolice(idApolice);
        } catch (Exception e) {
            log.info(EXCLUIR_APOLICE_FIM);
            throw new ServiceUnavailableException(ERRO_EXCLUIR_APOLICE.getCodigo(), ERRO_EXCLUIR_APOLICE.getMensagem(), e);
        }
    }

    private ApolicePageResult gerarResultado(Integer offset, Page<ApoliceTable> apolices) {
        return ApolicePageResult.builder()
                .offset(offset.longValue())
                .limit((long) apolices.getTotalPages())
                .records(apolices.getTotalElements())
                .apolices(apolices.stream().map(ApoliceTableMapper.INSTANCE::toEntity).collect(Collectors.toList()))
                .build();
    }
}
