package com.desafioviavarejo.desafiospringmongo.app.core.usecase;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClienteEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClientePageResult;
import com.desafioviavarejo.desafiospringmongo.app.core.exception.BusinessException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ClienteGateway;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BuscarClientesUseCaseTest {

    @InjectMocks
    private BuscarClientesUseCase buscarClientesUseCase;

    @Mock
    private ClienteGateway clienteGateway;

    private static final String idCliente = "KJFKFKHFKJ";
    private static final String ufCliente = "PR";
    private static final String nomeCliente = "Via";
    private static final String cpfCliente = "59701859065";
    private static final String cidadeCliente = "Curitiba";

    private static final int offset = 5;
    private static final int limit = 0;

    @Test
    public void buscarClientes() {
        when(clienteGateway.buscarClientes(any(), any())).thenReturn(obterClientePage());

        ClientePageResult result = buscarClientesUseCase.buscarClientes(offset, limit);

        Assert.assertNotNull(result);
        Assert.assertEquals(obterClientePage(), result);

        verify(clienteGateway).buscarClientes(any(), any());
    }

    @Test(expected = BusinessException.class)
    public void buscarClientesExcption() {
        when(clienteGateway.buscarClientes(any(), any())).thenThrow(ServiceUnavailableException.class);

        buscarClientesUseCase.buscarClientes(offset, limit);

        verify(clienteGateway).buscarClientes(any(), any());
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

    private ClientePageResult obterClientePage() {
        return ClientePageResult.builder()
                .clientes(Collections.singletonList(obterCliente()))
                .limit(10L)
                .offset(0L)
                .records(1L)
                .build();
    }
}
