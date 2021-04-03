package com.desafioviavarejo.desafiospringmongo.app.dataprovider;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClienteEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClientePageResult;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.entity.ClienteTable;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.repository.ClienteRepository;
import com.mongodb.MongoQueryException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClienteDataProviderTest {

    @InjectMocks
    private ClienteDataProvider clienteDataProvider;

    @Mock
    private ClienteRepository clienteRepository;

    private static final String idCliente = "KJFKFKHFKJ";
    private static final String ufCliente = "PR";
    private static final String nomeCliente = "Via";
    private static final String cpfCliente = "59701859065";
    private static final String cidadeCliente = "Curitiba";
    private static final int offset = 0;
    private static final int limit = 1;
    private Page<ClienteTable> clienteTablePage = new PageImpl<>(Collections.singletonList(obterClienteTable()));

    @Test
    public void salvarCliente() {
        when(clienteRepository.save(any())).thenReturn(obterClienteTable());

        ClienteEntity result = clienteDataProvider.salvarCliente(obterCliente());

        assertNotNull(result);
        assertEquals(obterCliente(), result);

        verify(clienteRepository, times(1)).save(any());
    }

    @Test(expected = ServiceUnavailableException.class)
    public void salvarClienteException() {
        when(clienteRepository.save(any())).thenThrow(MongoQueryException.class);

        clienteDataProvider.salvarCliente(obterCliente());

        verify(clienteRepository, times(1)).save(any());
    }

    @Test
    public void buscarClientePorId() {
        when(clienteRepository.findByIdCliente(any())).thenReturn(obterClienteTable());

        ClienteEntity result = clienteDataProvider.buscarClientePorId(idCliente);

        assertNotNull(result);
        assertEquals(obterCliente(), result);

        verify(clienteRepository, times(1)).findByIdCliente(any());
    }

    @Test(expected = ServiceUnavailableException.class)
    public void buscarClientePorIdException() {
        when(clienteRepository.findByIdCliente(any())).thenThrow(MongoQueryException.class);

        clienteDataProvider.buscarClientePorId(idCliente);

        verify(clienteRepository, times(1)).findByIdCliente(any());
    }

    @Test
    public void buscarClientes() {

        when(clienteRepository.findAll(eq(PageRequest.of(offset, limit)))).thenReturn(clienteTablePage);

        ClientePageResult result = clienteDataProvider.buscarClientes(offset, limit);

        assertNotNull(result);
        assertEquals(obterClientesPageResult(), result);

        verify(clienteRepository, times(1)).findAll(eq(PageRequest.of(offset, limit)));
    }

    private ClientePageResult obterClientesPageResult() {
        return ClientePageResult.builder()
                .offset((long) offset)
                .limit((long) clienteTablePage.getTotalPages())
                .records(clienteTablePage.getTotalElements())
                .clientes(Collections.singletonList(obterCliente()))
                .build();
    }

    @Test(expected = ServiceUnavailableException.class)
    public void buscarClientesException() {
        when(clienteRepository.findAll(eq(PageRequest.of(offset, limit)))).thenThrow(MongoQueryException.class);

        clienteDataProvider.buscarClientes(offset, limit);

        verify(clienteRepository, times(1)).findAll(eq(PageRequest.of(offset, limit)));
    }

    @Test
    public void atualizarCliente() {
        when(clienteRepository.save(any())).thenReturn(obterClienteTable());

        clienteDataProvider.atualizarCliente(obterCliente());

        verify(clienteRepository, times(1)).save(any());
    }

    @Test(expected = ServiceUnavailableException.class)
    public void atualizarClienteException() {
        when(clienteRepository.save(any())).thenThrow(MongoQueryException.class);

        clienteDataProvider.atualizarCliente(obterCliente());

        verify(clienteRepository, times(1)).save(any());
    }

    @Test
    public void excluirCliente() {
        doNothing().when(clienteRepository).deleteById(any());

        clienteDataProvider.excluirCliente(idCliente);

        verify(clienteRepository, times(1)).deleteById(any());
    }

    @Test(expected = ServiceUnavailableException.class)
    public void excluirClienteeException() {
        doThrow(MongoQueryException.class).when(clienteRepository).deleteById(any());

        clienteDataProvider.excluirCliente(idCliente);

        verify(clienteRepository, times(1)).deleteById(any());
    }

    private ClienteEntity obterCliente() {
        return ClienteEntity.builder()
                .idCliente(idCliente)
                .ufCliente(ufCliente)
                .nomeCliente(nomeCliente)
                .cpfCliente(cpfCliente)
                .cidadeCliente(cidadeCliente)
                .build();
    }

    private ClienteTable obterClienteTable() {
        return ClienteTable.builder()
                .idCliente(idCliente)
                .ufCliente(ufCliente)
                .nomeCliente(nomeCliente)
                .cpfCliente(cpfCliente)
                .cidadeCliente(cidadeCliente)
                .build();
    }
}
