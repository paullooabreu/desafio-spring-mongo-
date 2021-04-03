package com.desafioviavarejo.desafiospringmongo.app.core.usecase;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApoliceEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.exception.BusinessException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ApoliceGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AtualizarApoliceUseCaseTest {

    @InjectMocks
    private AtualizarApoliceUseCase atualizarApoliceUseCase;

    @Mock
    private ApoliceGateway apoliceGateway;

    private static final String idApolice = "JKFGSGFKSG";
    private static final String idCliente = "KJGFHGHK";
    private static final String placaVeiculo = "AAA9999";
    private static final String numeroApolice = "8527419";
    private static final LocalDate inicioVigencia = LocalDate.of(2021, Month.APRIL, 2);
    private static final LocalDate fimVigencia = LocalDate.of(2021, Month.APRIL, 10);
    private static final BigDecimal valorApolice = BigDecimal.TEN;


    @Test
    public void atualizarApolice() {
        when(apoliceGateway.buscarApolicePorId(any())).thenReturn(obterApolice());

        doNothing().when(apoliceGateway).atualizarApolice(any());

        atualizarApoliceUseCase.atualizarApolice(obterApolice(), idApolice);

        verify(apoliceGateway, timeout(1)).buscarApolicePorId(any());
        verify(apoliceGateway, timeout(1)).atualizarApolice(any());

    }

    @Test(expected = BusinessException.class)
    public void atualizarApoliceException() {
        when(apoliceGateway.buscarApolicePorId(any())).thenReturn(obterApolice());
        doThrow(ServiceUnavailableException.class).when(apoliceGateway).atualizarApolice(any());

        atualizarApoliceUseCase.atualizarApolice(obterApolice(), idApolice);

        verify(apoliceGateway, timeout(1)).buscarApolicePorId(any());
        verify(apoliceGateway, timeout(1)).atualizarApolice(any());
    }

    @Test(expected = BusinessException.class)
    public void atualizarApolicebuscarApolicePorIdException() {
        when(apoliceGateway.buscarApolicePorId(any())).thenThrow(ServiceUnavailableException.class);

        atualizarApoliceUseCase.atualizarApolice(obterApolice(), idApolice);

        verify(apoliceGateway, timeout(1)).buscarApolicePorId(any());
    }

    @Test(expected = BusinessException.class)
    public void atualizarApoliceDatasErradasException() {
        when(apoliceGateway.buscarApolicePorId(any())).thenReturn(obterApolice());

        atualizarApoliceUseCase.atualizarApolice(obterApoliceDatasErradas(), idApolice);

        verify(apoliceGateway, timeout(1)).buscarApolicePorId(any());
    }

    @Test(expected = BusinessException.class)
    public void atualizarApoliceDataErroFimException() {
        when(apoliceGateway.buscarApolicePorId(any())).thenReturn(obterApolice());

        atualizarApoliceUseCase.atualizarApolice(obterApoliceDataErroFim(), idApolice);

        verify(apoliceGateway, timeout(1)).buscarApolicePorId(any());
    }

    @Test(expected = BusinessException.class)
    public void atualizarApoliceDataErroInicioException() {
        when(apoliceGateway.buscarApolicePorId(any())).thenReturn(obterApolice());

        atualizarApoliceUseCase.atualizarApolice(obterApoliceDataErroInicio(), idApolice);

        verify(apoliceGateway, timeout(1)).buscarApolicePorId(any());
    }

    private ApoliceEntity obterApoliceDataErroFim() {
        return ApoliceEntity.builder()
                .idApolice(idApolice)
                .idCliente(idCliente)
                .placaVeiculo(placaVeiculo)
                .numeroApolice(numeroApolice)
                .fimVigencia(inicioVigencia.minusDays(1))
                .valorApolice(valorApolice)
                .build();
    }

    private ApoliceEntity obterApoliceDataErroInicio() {
        return ApoliceEntity.builder()
                .idApolice(idApolice)
                .idCliente(idCliente)
                .placaVeiculo(placaVeiculo)
                .numeroApolice(numeroApolice)
                .inicioVigencia(fimVigencia.plusDays(1))
                .valorApolice(valorApolice)
                .build();
    }

    private ApoliceEntity obterApolice() {
        return ApoliceEntity.builder()
                .idApolice(idApolice)
                .idCliente(idCliente)
                .placaVeiculo(placaVeiculo)
                .numeroApolice(numeroApolice)
                .inicioVigencia(inicioVigencia)
                .fimVigencia(fimVigencia)
                .valorApolice(valorApolice)
                .build();
    }

    private ApoliceEntity obterApoliceDatasErradas() {
        return ApoliceEntity.builder()
                .idApolice(idApolice)
                .idCliente(idCliente)
                .placaVeiculo(placaVeiculo)
                .numeroApolice(numeroApolice)
                .inicioVigencia(fimVigencia)
                .fimVigencia(inicioVigencia)
                .valorApolice(valorApolice)
                .build();
    }

}
