package com.desafioviavarejo.desafiospringmongo.app.core.usecase;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApoliceEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.exception.BusinessException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ApoliceGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.desafioviavarejo.desafiospringmongo.app.core.exception.ExceptionDetails.ERRO_OBTER_APOLICE_ID;
import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.*;

/**
 * Responsável por buscar uma apolice por Id
 *
 * @author Paulo Abreu
 */
@Slf4j
@Component
public class BuscarApolicePorIdUseCase {

    private final ApoliceGateway apoliceGateway;

    @Autowired
    public BuscarApolicePorIdUseCase(ApoliceGateway apoliceGateway) {
        this.apoliceGateway = apoliceGateway;
    }

    public ApoliceEntity buscarPorId(String idApolice) throws BusinessException {
        try {
            log.info(BUSCAR_APOLICE_ID_INICIO);
            return this.apoliceGateway.buscarApolicePorId(idApolice);
        } catch (ServiceUnavailableException e) {
            log.info(BUSCAR_APOLICE_ID_FIM);
            throw new BusinessException(ERRO_OBTER_APOLICE_ID.getCodigo(), ERRO_OBTER_APOLICE_ID.getMensagem(), e);
        }
    }
}
