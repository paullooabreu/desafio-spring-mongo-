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
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SalvarClienteUseCaseTest {

    @InjectMocks
    private SalvarClienteUseCase salvarClienteUseCase;

    @Mock
    private ClienteGateway clienteGateway;

    @Test
    public void salvarCliente() {
        when(clienteGateway.salvarCliente(Mockito.any())).thenReturn(obterClienteSalvo());
        when(clienteGateway.obterClientePorCpf(Mockito.any()))
                .thenReturn(Collections.emptyList());

        ClienteEntity result = salvarClienteUseCase.salvar(obterCliente());

        Assert.assertNotNull(result);
        Assert.assertEquals(obterClienteSalvo(), result);

        verify(clienteGateway, times(1)).obterClientePorCpf(Mockito.any());
        verify(clienteGateway, times(1)).salvarCliente(Mockito.any());
    }

    @Test(expected = BusinessException.class)
    public void salvarClienteException() {
        when(clienteGateway.salvarCliente(Mockito.any())).thenThrow(ServiceUnavailableException.class);
        when(clienteGateway.obterClientePorCpf(Mockito.any()))
                .thenReturn(Collections.emptyList());

        salvarClienteUseCase.salvar(obterCliente());

        verify(clienteGateway, times(1)).obterClientePorCpf(Mockito.any());
        verify(clienteGateway, times(1)).salvarCliente(Mockito.any());
    }

    @Test(expected = BusinessException.class)
    public void salvarClienteObterClientePorCpfNaoVazioException() {
        when(clienteGateway.obterClientePorCpf(Mockito.any()))
                .thenReturn(Collections.singletonList(ClienteEntity.builder().build()));

        salvarClienteUseCase.salvar(obterCliente());

        verify(clienteGateway, times(1)).salvarCliente(Mockito.any());
    }

    @Test(expected = BusinessException.class)
    public void salvarClienteObterClientePorCpfException() {
        when(clienteGateway.obterClientePorCpf(Mockito.any())).thenThrow(ServiceUnavailableException.class);

        salvarClienteUseCase.salvar(obterCliente());

        verify(clienteGateway, times(1)).obterClientePorCpf(Mockito.any());
    }

    @Test(expected = BusinessException.class)
    public void salvarClienteCpfInvalidoException() {
        salvarClienteUseCase.salvar(obterClienteCpfInvalido());
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

    private ClienteEntity obterCliente() {
        return ClienteEntity.builder()
                .cidadeCliente("Curitiba")
                .cpfCliente("59701859065")
                .nomeCliente("Via")
                .ufCliente("PR")
                .build();
    }

    private ClienteEntity obterClienteCpfInvalido() {
        return ClienteEntity.builder()
                .cidadeCliente("Curitiba")
                .cpfCliente("11111111111")
                .nomeCliente("Via")
                .ufCliente("PR")
                .build();
    }
}
