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
import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.ATUALIZAR_CLIENTE_FIM;
import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.ATUALIZAR_CLIENTE_INICIO;

/**
 * Responsável por atualizar um Cliente
 *
 * @author Paulo Abreu
 */
@Slf4j
@Component
public class AtualizarClienteUseCase {
    private final ClienteGateway clienteGateway;

    @Autowired
    public AtualizarClienteUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public void atualizarCliente(ClienteEntity cliente, String idCliente) {
        log.info(ATUALIZAR_CLIENTE_INICIO);

        verificaCpf(cliente);

        ClienteEntity clienteSalvo = obterClienteId(idCliente);

        ClienteEntity clienteNovo = montarCliente(clienteSalvo, cliente);

        atualizarCliente(clienteNovo);
    }

    private void verificaCpf(ClienteEntity cliente) {
        if (cliente.getCpfCliente() != null) {
            if (!isCPF(cliente.getCpfCliente()))
                throw new BusinessException(ERRO_CPF_INVALIDO.getCodigo(), ERRO_CPF_INVALIDO.getMensagem());

            List<ClienteEntity> clientes = obterClientesPorCpf(cliente);

            if (!clientes.isEmpty())
                throw new BusinessException(ERRO_CADASTRADO.getCodigo(), ERRO_CADASTRADO.getMensagem());
        }
    }

    private void atualizarCliente(ClienteEntity clienteNovo) {
        try {
            this.clienteGateway.atualizarCliente(clienteNovo);
        } catch (ServiceUnavailableException e) {
            log.info(ATUALIZAR_CLIENTE_FIM);
            throw new BusinessException(ERRO_ATUALIZAR_CLIENTE.getCodigo(), ERRO_ATUALIZAR_CLIENTE.getMensagem());
        }
    }

    private ClienteEntity obterClienteId(String idCliente) {
        try {
            return clienteGateway.buscarClientePorId(idCliente);
        } catch (ServiceUnavailableException e) {
            throw new BusinessException(ERRO_OBTER_CLIENTE_ID.getCodigo(), ERRO_OBTER_CLIENTE_ID.getMensagem());
        }
    }

    private ClienteEntity montarCliente(ClienteEntity clienteSalvo, ClienteEntity cliente) {
        if (cliente.getCpfCliente() != null)
            clienteSalvo.setCpfCliente(cliente.getCpfCliente().trim());
        if (cliente.getCidadeCliente() != null)
            clienteSalvo.setCidadeCliente(cliente.getCidadeCliente().trim());
        if (cliente.getNomeCliente() != null)
            clienteSalvo.setNomeCliente(cliente.getNomeCliente().trim());
        if (cliente.getUfCliente() != null)
            clienteSalvo.setUfCliente(cliente.getUfCliente().trim());
        return clienteSalvo;
    }

    private List<ClienteEntity> obterClientesPorCpf(ClienteEntity cliente) {
        try {
            return this.clienteGateway.obterClientePorCpf(cliente.getCpfCliente());
        } catch (ServiceUnavailableException e) {
            throw new BusinessException(ERRO_OBTER_CLIENTE_CPF.getCodigo(), ERRO_OBTER_CLIENTE_CPF.getMensagem(), e);
        }
    }

}
