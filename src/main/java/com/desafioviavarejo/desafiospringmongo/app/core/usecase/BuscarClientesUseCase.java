package com.desafioviavarejo.desafiospringmongo.app.core.usecase;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClientePageResult;
import com.desafioviavarejo.desafiospringmongo.app.core.exception.BusinessException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ClienteGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.desafioviavarejo.desafiospringmongo.app.core.exception.ExceptionDetails.ERRO_OBTER_CLIENTES;
import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.BUSCAR_CLIENTES_FIM;
import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.BUSCAR_CLIENTES_INICIO;

/**
 * Responsável por buscar clientes
 *
 * @author Paulo Abreu
 */
@Slf4j
@Component
public class BuscarClientesUseCase {

    private final ClienteGateway clienteGateway;

    @Autowired
    public BuscarClientesUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public ClientePageResult buscarClientes(Integer offset, Integer limit) {
        try {
            log.info(BUSCAR_CLIENTES_INICIO);
            return this.clienteGateway.buscarClientes(offset, limit);
        } catch (ServiceUnavailableException e) {
            log.info(BUSCAR_CLIENTES_FIM);
            throw new BusinessException(ERRO_OBTER_CLIENTES.getCodigo(), ERRO_OBTER_CLIENTES.getMensagem(), e);
        }
    }
}
