package com.desafioviavarejo.desafiospringmongo.app.core.usecase;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClienteEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.exception.BusinessException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ClienteGateway;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BuscarClientePorIdUseCaseTest {

    @InjectMocks
    private BuscarClientePorIdUseCase buscarClientePorIdUseCase;

    @Mock
    private ClienteGateway clienteGateway;

    @Test
    public void buscarClientePorId() {

        when(clienteGateway.buscarClientePorId(any())).thenReturn(obterClienteSalvo());

        ClienteEntity result = buscarClientePorIdUseCase.buscarPorId("id");

        Assert.assertNotNull(result);
        Assert.assertEquals(obterClienteSalvo(), result);

        verify(clienteGateway, times(1)).buscarClientePorId(any());

    }

    @Test(expected = BusinessException.class)
    public void buscarClientePorIdException() {

        when(clienteGateway.buscarClientePorId(any())).thenThrow(ServiceUnavailableException.class);

        buscarClientePorIdUseCase.buscarPorId("id");

        verify(clienteGateway, times(1)).buscarClientePorId(any());

    }


    private ClienteEntity obterClienteSalvo() {
        return ClienteEntity.builder()
                .cidadeCliente("Curitiba")
                .cpfCliente("5970189065")
                .idCliente("GHFJJJJJJJ")
                .nomeCliente("Via")
                .ufCliente("PR")
                .build();
    }

}
