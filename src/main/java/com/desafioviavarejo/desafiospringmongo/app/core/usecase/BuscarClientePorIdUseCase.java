package com.desafioviavarejo.desafiospringmongo.app.core.usecase;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClienteEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.exception.BusinessException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ClienteGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.desafioviavarejo.desafiospringmongo.app.core.exception.ExceptionDetails.ERRO_OBTER_CLIENTE_ID;
import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.*;

/**
 * Responsável por buscar cliente por Id
 *
 * @author Paulo Abreu
 */
@Slf4j
@Component
public class BuscarClientePorIdUseCase {

    private final ClienteGateway clienteGateway;

    @Autowired
    public BuscarClientePorIdUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public ClienteEntity buscarPorId(String idCliente) {
        try {
            log.info(BUSCAR_CLIENTE_ID_INICIO);
            return this.clienteGateway.buscarClientePorId(idCliente);
        } catch (ServiceUnavailableException e) {
            log.info(BUSCAR_CLIENTE_ID_FIM);
            throw new BusinessException(ERRO_OBTER_CLIENTE_ID.getCodigo(), ERRO_OBTER_CLIENTE_ID.getMensagem(), e);
        }
    }
}
