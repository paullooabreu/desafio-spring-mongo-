package com.desafioviavarejo.desafiospringmongo.app.dataprovider;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClienteEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClientePageResult;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.entity.ClienteTable;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ClienteGateway;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.mappers.ClienteTableMapper;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.desafioviavarejo.desafiospringmongo.app.core.exception.ExceptionDetails.*;
import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.*;

/**
 * Acesso as Dados de Clientes
 *
 * @author Paulo Abreu
 */
@Slf4j
@Component
public class ClienteDataProvider implements ClienteGateway {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteDataProvider(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteEntity salvarCliente(ClienteEntity cliente) {
        ClienteTable clienteSalvo;
        try {
            log.info(SALVAR_CLIENTE_INICIO);
            clienteSalvo = this.clienteRepository.save(ClienteTableMapper.INSTANCE.toTable(cliente));
        } catch (Exception e) {
            log.info(SALVAR_CLIENTE_FIM);
            throw new ServiceUnavailableException(ERRO_SALVAR_CLIENTE.getCodigo(), ERRO_SALVAR_CLIENTE.getMensagem(), e);
        }
        return ClienteTableMapper.INSTANCE.toEntity(clienteSalvo);
    }

    @Override
    public List<ClienteEntity> obterClientePorCpf(String cpf) {
        List<ClienteTable> clientes;
        try {
            log.info(OBTER_CLIENTE_CPF_INICIO);
            clientes = this.clienteRepository.findByCpfCliente(cpf);
        } catch (Exception e) {
            log.info(OBTER_CLIENTE_CPF_FIM);
            throw new ServiceUnavailableException(ERRO_OBTER_CLIENTE_CPF.getCodigo(), ERRO_OBTER_CLIENTE_CPF.getMensagem(), e);
        }
        return clientes.stream().map(ClienteTableMapper.INSTANCE::toEntity).collect(Collectors.toList());
    }

    @Override
    public ClienteEntity buscarClientePorId(String idCliente) {
        ClienteTable cliente;
        try {
            log.info(BUSCAR_CLIENTE_ID_INICIO);
            cliente = this.clienteRepository.findByIdCliente(idCliente);
        } catch (Exception e) {
            log.info(BUSCAR_CLIENTE_ID_FIM);
            throw new ServiceUnavailableException(ERRO_OBTER_CLIENTE_ID.getCodigo(), ERRO_OBTER_CLIENTE_ID.getMensagem(), e);
        }
        return ClienteTableMapper.INSTANCE.toEntity(cliente);
    }

    @Override
    public ClientePageResult buscarClientes(Integer offset, Integer limit) {
        Page<ClienteTable> clientes;
        try {
            log.info(BUSCAR_CLIENTES_INICIO);
            clientes = this.clienteRepository.findAll(PageRequest.of(offset, limit));
        } catch (Exception e) {
            log.info(BUSCAR_CLIENTES_FIM);
            throw new ServiceUnavailableException(ERRO_OBTER_CLIENTES.getCodigo(), ERRO_OBTER_CLIENTES.getMensagem(), e);
        }
        return gerarResultado(offset, clientes);
    }

    @Override
    public void atualizarCliente(ClienteEntity cliente) {
        try {
            log.info(ATUALIZAR_CLIENTE_INICIO);
            this.clienteRepository.save(ClienteTableMapper.INSTANCE.toTable(cliente));
        } catch (Exception e) {
            log.info(ATUALIZAR_CLIENTE_FIM);
            throw new ServiceUnavailableException(ERRO_ATUALIZAR_CLIENTE.getCodigo(), ERRO_ATUALIZAR_CLIENTE.getMensagem(), e);
        }
    }

    @Override
    public void excluirCliente(String idCliente) {
        try {
            log.info(EXCLUIR_CLIENTE_INICIO);
            this.clienteRepository.deleteById(idCliente);
        } catch (Exception e) {
            log.info(EXCLUIR_CLIENTE_FIM);
            throw new ServiceUnavailableException(ERRO_EXCLUIR_CLIENTE.getCodigo(), ERRO_EXCLUIR_CLIENTE.getMensagem(), e);
        }
    }

    private ClientePageResult gerarResultado(Integer offset, Page<ClienteTable> clientes) {
        return ClientePageResult.builder()
                .offset(offset.longValue())
                .limit((long) clientes.getTotalPages())
                .records(clientes.getTotalElements())
                .clientes(clientes.stream().map(ClienteTableMapper.INSTANCE::toEntity).collect(Collectors.toList()))
                .build();
    }
}
