package com.desafioviavarejo.desafiospringmongo.app.core.usecase;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApoliceEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.exception.BusinessException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ApoliceGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.desafioviavarejo.desafiospringmongo.app.core.exception.ExceptionDetails.*;
import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.ATUALIZAR_APOLICE_FIM;
import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.ATUALIZAR_APOLICE_INICIO;

@Slf4j
@Component
public class AtualizarApoliceUseCase {

    private final ApoliceGateway apoliceGateway;

    public AtualizarApoliceUseCase(ApoliceGateway apoliceGateway) {
        this.apoliceGateway = apoliceGateway;
    }

    public void atualizarApolice(ApoliceEntity apolice, String idApolice) {
        log.info(ATUALIZAR_APOLICE_INICIO);

        ApoliceEntity apoliceSalva = obterApoliceId(idApolice);

        validarApolice(apoliceSalva);

        validarDatas(apoliceSalva, apolice);

        ApoliceEntity apoliceNova = montarApolice(apoliceSalva, apolice);

        atualizarApolice(apoliceNova);
    }

    private void validarApolice(ApoliceEntity apoliceSalva) {
        if (apoliceSalva == null)
            throw  new  BusinessException(ERRO_APOLICE_NAO_EXISTE.getCodigo(), ERRO_APOLICE_NAO_EXISTE.getMensagem());
    }

    private void atualizarApolice(ApoliceEntity apoliceNova) {
        try {
            this.apoliceGateway.atualizarApolice(apoliceNova);
        } catch (ServiceUnavailableException e) {
            log.info(ATUALIZAR_APOLICE_FIM);
            throw new BusinessException(ERRO_ATUALIZAR_APOLICE.getCodigo(), ERRO_ATUALIZAR_APOLICE.getMensagem());
        }
    }

    private ApoliceEntity obterApoliceId(String idApolice) {
        try {
            return apoliceGateway.buscarApolicePorId(idApolice);
        } catch (ServiceUnavailableException e) {
            throw new BusinessException(ERRO_OBTER_APOLICE_ID.getCodigo(), ERRO_OBTER_APOLICE_ID.getMensagem());
        }
    }

    private ApoliceEntity montarApolice(ApoliceEntity apoliceSalva, ApoliceEntity apolice) {
        if (apolice.getInicioVigencia() != null)
            apoliceSalva.setInicioVigencia(apolice.getInicioVigencia());
        if (apolice.getFimVigencia() != null)
            apoliceSalva.setFimVigencia(apolice.getFimVigencia());
        if (apolice.getPlacaVeiculo() != null)
            apoliceSalva.setPlacaVeiculo(apolice.getPlacaVeiculo().trim());
        if (apolice.getValorApolice() != null)
            apoliceSalva.setValorApolice(apolice.getValorApolice());
        return apoliceSalva;
    }

    private void validarDatas(ApoliceEntity apoliceSalva, ApoliceEntity apolice) {
        LocalDate dataInicial = apolice.getInicioVigencia();
        LocalDate dataFinal = apolice.getFimVigencia();

        if (dataInicial != null && dataFinal != null) {
            if (dataInicial.isAfter(dataFinal))
                throw new BusinessException(ERRO_DATA_APOLICE.getCodigo(), ERRO_DATA_APOLICE.getMensagem());
        } else if (dataInicial != null) {
            if (dataInicial.isAfter(apoliceSalva.getFimVigencia()))
                throw new BusinessException(ERRO_DATA_APOLICE.getCodigo(), ERRO_DATA_APOLICE.getMensagem());
        } else if (dataFinal != null) {
            if (apoliceSalva.getInicioVigencia().isAfter(dataFinal))
                throw new BusinessException(ERRO_DATA_APOLICE.getCodigo(), ERRO_DATA_APOLICE.getMensagem());
        }
    }
}
