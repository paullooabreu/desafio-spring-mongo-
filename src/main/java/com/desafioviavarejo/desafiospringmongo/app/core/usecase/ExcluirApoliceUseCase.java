package com.desafioviavarejo.desafiospringmongo.app.core.usecase;

import com.desafioviavarejo.desafiospringmongo.app.core.exception.BusinessException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ApoliceGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.desafioviavarejo.desafiospringmongo.app.core.exception.ExceptionDetails.ERRO_EXCLUIR_APOLICE;
import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.*;

/**
 * Responsável por excluir uma apolice
 *
 * @author Paulo Abreu
 */
@Slf4j
@Component
public class ExcluirApoliceUseCase {

    private final ApoliceGateway apoliceGateway;

    @Autowired
    public ExcluirApoliceUseCase(ApoliceGateway apoliceGateway) {
        this.apoliceGateway = apoliceGateway;
    }

    public void excluirApolice(String idApolice) {
        try {
            log.info(EXCLUIR_APOLICE_INICIO);
            this.apoliceGateway.excluirApolice(idApolice);
        } catch (ServiceUnavailableException e) {
            log.info(EXCLUIR_APOLICE_FIM);
            throw new BusinessException(ERRO_EXCLUIR_APOLICE.getCodigo(), ERRO_EXCLUIR_APOLICE.getMensagem());
        }
    }
}
