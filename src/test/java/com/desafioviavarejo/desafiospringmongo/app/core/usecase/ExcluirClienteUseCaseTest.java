package com.desafioviavarejo.desafiospringmongo.app.core.usecase;

import com.desafioviavarejo.desafiospringmongo.app.core.exception.BusinessException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ClienteGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExcluirClienteUseCaseTest {

    private static final String idCliente = "JHFKGJ";

    @InjectMocks
    private ExcluirClienteUseCase excluirClienteUseCase;

    @Mock
    private ClienteGateway clienteGateway;

    @Test
    public void excluirCliente() {
        doNothing().when(clienteGateway).excluirCliente(any());

        excluirClienteUseCase.excluirCliente(idCliente);

        verify(clienteGateway, times(1)).excluirCliente(any());
    }

    @Test(expected = BusinessException.class)
    public void excluirClienteException() {
        doThrow(ServiceUnavailableException.class).when(clienteGateway).excluirCliente(any());

        excluirClienteUseCase.excluirCliente(idCliente);

        verify(clienteGateway, times(1)).excluirCliente(any());
    }
}
