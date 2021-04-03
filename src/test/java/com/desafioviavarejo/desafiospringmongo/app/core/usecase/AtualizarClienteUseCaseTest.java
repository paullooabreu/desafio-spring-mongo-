package com.desafioviavarejo.desafiospringmongo.app.core.usecase;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClienteEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.exception.BusinessException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ClienteGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AtualizarClienteUseCaseTest {

    @InjectMocks
    private AtualizarClienteUseCase atualizarClienteUseCase;

    @Mock
    private ClienteGateway clienteGateway;

    private static final String idCliente = "JHFKGJ";

    @Test
    public void atualizarCliente() {
        doNothing().when(clienteGateway).atualizarCliente(any());
        when(clienteGateway.buscarClientePorId(any())).thenReturn(obterClienteSalvo());

        atualizarClienteUseCase.atualizarCliente(obterClienteSalvo(), idCliente);

        verify(clienteGateway, times(1)).atualizarCliente(any());
        verify(clienteGateway, times(1)).buscarClientePorId(any());
    }

    @Test(expected = BusinessException.class)
    public void atualizarClienteException() {
        doThrow(ServiceUnavailableException.class).when(clienteGateway).atualizarCliente(any());
        when(clienteGateway.buscarClientePorId(any())).thenReturn(obterClienteSalvo());

        atualizarClienteUseCase.atualizarCliente(obterClienteSalvo(), idCliente);

        verify(clienteGateway, times(1)).atualizarCliente(any());
        verify(clienteGateway, times(1)).buscarClientePorId(any());

    }

    @Test(expected = BusinessException.class)
    public void atualizarClientebuscarClientePorIdErroException() {
        when(clienteGateway.buscarClientePorId(any())).thenThrow(ServiceUnavailableException.class);

        atualizarClienteUseCase.atualizarCliente(obterClienteSalvo(), idCliente);

        verify(clienteGateway, times(1)).buscarClientePorId(any());
    }

    @Test(expected = BusinessException.class)
    public void atualizarClienteobterClientePorCpfErroException() {
        when(clienteGateway.obterClientePorCpf(any())).thenReturn(Collections.singletonList(obterClienteSalvo()));

        atualizarClienteUseCase.atualizarCliente(obterClienteSalvo(), idCliente);

        verify(clienteGateway, times(1)).obterClientePorCpf(any());
    }

    @Test(expected = BusinessException.class)
    public void atualizarClienteobterClientePorCpfException() {
        when(clienteGateway.obterClientePorCpf(any())).thenThrow(ServiceUnavailableException.class);

        atualizarClienteUseCase.atualizarCliente(obterClienteSalvo(), idCliente);

        verify(clienteGateway, times(1)).obterClientePorCpf(any());
    }



    private ClienteEntity obterClienteSalvoCpfInvalido() {
        return ClienteEntity.builder()
                .cidadeCliente("Curitiba")
                .cpfCliente("11111111111")
                .idCliente("GHFJJJJJJJ")
                .nomeCliente("Via")
                .ufCliente("PR")
                .build();
    }

    private ClienteEntity obterClienteSalvo() {
        return ClienteEntity.builder()
                .cidadeCliente("Curitiba")
                .cpfCliente("20300305036")
                .idCliente("GHFJJJJJJJ")
                .nomeCliente("Via")
                .ufCliente("PR")
                .build();
    }
}
