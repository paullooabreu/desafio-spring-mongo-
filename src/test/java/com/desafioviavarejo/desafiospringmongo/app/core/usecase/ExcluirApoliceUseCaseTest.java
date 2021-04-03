package com.desafioviavarejo.desafiospringmongo.app.core.usecase;

import com.desafioviavarejo.desafiospringmongo.app.core.exception.BusinessException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ApoliceGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExcluirApoliceUseCaseTest {

    @InjectMocks
    private ExcluirApoliceUseCase excluirApoliceUseCase;

    @Mock
    private ApoliceGateway apoliceGateway;

    private static final String idApolice = "JHFKGJ";

    @Test
    public void excluirApolice() {
        doNothing().when(apoliceGateway).excluirApolice(any());

        excluirApoliceUseCase.excluirApolice(idApolice);

        verify(apoliceGateway, times(1)).excluirApolice(any());
    }

    @Test(expected = BusinessException.class)
    public void excluirApoliceException() {
        doThrow(ServiceUnavailableException.class).when(apoliceGateway).excluirApolice(any());

        excluirApoliceUseCase.excluirApolice(idApolice);

        verify(apoliceGateway, times(1)).excluirApolice(any());
    }
}
