package com.desafioviavarejo.desafiospringmongo.app.core.usecase;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApolicePageResult;
import com.desafioviavarejo.desafiospringmongo.app.core.exception.BusinessException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ApoliceGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.desafioviavarejo.desafiospringmongo.app.core.exception.ExceptionDetails.ERRO_OBTER_APOLICES;
import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.BUSCAR_APOLICES_FIM;
import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.BUSCAR_APOLICES_INICIO;

/**
 * Responsável por buscar apolices
 *
 * @author Paulo Abreu
 */
@Slf4j
@Component
public class BuscarApolicesUseCase {

    private final ApoliceGateway apoliceGateway;

    @Autowired
    public BuscarApolicesUseCase(ApoliceGateway apoliceGateway) {
        this.apoliceGateway = apoliceGateway;
    }

    public ApolicePageResult buscarApolices(Integer offset, Integer limit) {
        try {
            log.info(BUSCAR_APOLICES_INICIO);
            return this.apoliceGateway.buscarApolices(offset, limit);
        } catch (ServiceUnavailableException e) {
            log.info(BUSCAR_APOLICES_FIM);
            throw new BusinessException(ERRO_OBTER_APOLICES.getCodigo(), ERRO_OBTER_APOLICES.getMensagem(), e);
        }
    }
}
