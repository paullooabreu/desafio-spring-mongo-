package com.desafioviavarejo.desafiospringmongo.app.core.usecase;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClienteEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.exception.BusinessException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ClienteGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.desafioviavarejo.desafiospringmongo.app.core.exception.ExceptionDetails.*;
import static com.desafioviavarejo.desafiospringmongo.app.core.util.ValidaCPF.isCPF;
import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.SALVAR_CLIENTE_FIM;
import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.SALVAR_CLIENTE_INICIO;

/**
 * Responsável por salvar um Cliente
 *
 * @author Paulo Abreu
 */
@Component
@Slf4j
public class SalvarClienteUseCase {

    private final ClienteGateway clienteGateway;

    @Autowired
    public SalvarClienteUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public ClienteEntity salvar(ClienteEntity cliente) {
        log.info(SALVAR_CLIENTE_INICIO);

        verificaCpf(cliente);

        return salvarCliente(cliente);
    }

    private ClienteEntity salvarCliente(ClienteEntity cliente) {
        try {
            return this.clienteGateway.salvarCliente(cliente);
        } catch (ServiceUnavailableException e) {
            log.info(SALVAR_CLIENTE_FIM);
            throw new BusinessException(ERRO_SALVAR_CLIENTE.getCodigo(), ERRO_SALVAR_CLIENTE.getMensagem(), e);
        }
    }

    private void verificaCpf(ClienteEntity cliente) {
        if (!isCPF(cliente.getCpfCliente()))
            throw new BusinessException(ERRO_CPF_INVALIDO.getCodigo(), ERRO_CPF_INVALIDO.getMensagem());

        List<ClienteEntity> clientes = obterClientesPorCpf(cliente);

        if (!clientes.isEmpty())
            throw new BusinessException(ERRO_CADASTRADO.getCodigo(), ERRO_CADASTRADO.getMensagem());
    }

    private List<ClienteEntity> obterClientesPorCpf(ClienteEntity cliente) {
        try {
            return this.clienteGateway.obterClientePorCpf(cliente.getCpfCliente());
        } catch (ServiceUnavailableException e) {
            throw new BusinessException(ERRO_OBTER_CLIENTE_CPF.getCodigo(), ERRO_OBTER_CLIENTE_CPF.getMensagem(), e);
        }
    }
}
