package com.desafioviavarejo.desafiospringmongo.app.core.usecase;

import com.desafioviavarejo.desafiospringmongo.app.core.exception.BusinessException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ClienteGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.desafioviavarejo.desafiospringmongo.app.core.exception.ExceptionDetails.ERRO_EXCLUIR_CLIENTE;
import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.EXCLUIR_CLIENTE_FIM;
import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.EXCLUIR_CLIENTE_INICIO;

/**
 * Responsável por excluir um Cliente
 *
 * @author Paulo Abreu
 */
@Slf4j
@Component
public class ExcluirClienteUseCase {
    private final ClienteGateway clienteGateway;

    @Autowired
    public ExcluirClienteUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public void excluirCliente(String idCliente) {
        try {
            log.info(EXCLUIR_CLIENTE_INICIO);
            this.clienteGateway.excluirCliente(idCliente);
        } catch (ServiceUnavailableException e) {
            log.info(EXCLUIR_CLIENTE_FIM);
            throw new BusinessException(ERRO_EXCLUIR_CLIENTE.getCodigo(), ERRO_EXCLUIR_CLIENTE.getMensagem());
        }
    }
}
