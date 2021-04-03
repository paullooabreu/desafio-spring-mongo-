package com.desafioviavarejo.desafiospringmongo.app.core.usecase;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApoliceEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.exception.BusinessException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ApoliceGateway;
import org.junit.Assert;
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
public class BuscarRelApolicePorNumeroUseCaseTest {

    @InjectMocks
    private BuscarRelApolicePorNumeroUseCase buscarRelApolicePorNumeroUseCase;

    @Mock
    private ApoliceGateway apoliceGateway;

    @Test
    public void buscarClientePorNumero() {

        when(apoliceGateway.buscarApolicePorNumero(any())).thenReturn(obterApoliceNaoVencida());

        ApoliceEntity result = buscarRelApolicePorNumeroUseCase.buscarPorNumero("id");

        Assert.assertNotNull(result);
        Assert.assertEquals(obterApoliceAVencer(), result);

        verify(apoliceGateway, times(1)).buscarApolicePorNumero(any());

    }

    @Test
    public void buscarClientePorNumeroVencida() {

        when(apoliceGateway.buscarApolicePorNumero(any())).thenReturn(obterApoliceVencida());

        ApoliceEntity result = buscarRelApolicePorNumeroUseCase.buscarPorNumero("id");

        Assert.assertNotNull(result);
        Assert.assertEquals(obterApoliceAVencida(), result);

        verify(apoliceGateway, times(1)).buscarApolicePorNumero(any());

    }

    @Test(expected = BusinessException.class)
    public void buscarClientePorNumeroException() {

        when(apoliceGateway.buscarApolicePorNumero(any())).thenThrow(ServiceUnavailableException.class);

        buscarRelApolicePorNumeroUseCase.buscarPorNumero("id");

        verify(apoliceGateway, times(1)).buscarApolicePorNumero(any());

    }

    private ApoliceEntity obterApoliceNaoVencida() {
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

    private ApoliceEntity obterApoliceVencida() {
        return ApoliceEntity.builder()
                .idApolice(idApolice)
                .idCliente(idCliente)
                .placaVeiculo(placaVeiculo)
                .numeroApolice(numeroApolice)
                .inicioVigencia(inicioVigencia)
                .fimVigencia(inicioVigencia.minusDays(3))
                .valorApolice(valorApolice)
                .build();
    }

    private ApoliceEntity obterApoliceAVencer() {
        return ApoliceEntity.builder()
                .idApolice(idApolice)
                .idCliente(idCliente)
                .placaVeiculo(placaVeiculo)
                .numeroApolice(numeroApolice)
                .inicioVigencia(inicioVigencia)
                .fimVigencia(fimVigencia)
                .valorApolice(valorApolice)
                .descricaoApolice("Faltam 8 para vencer a Apólice!")
                .build();
    }

    private ApoliceEntity obterApoliceAVencida() {
        return ApoliceEntity.builder()
                .idApolice(idApolice)
                .idCliente(idCliente)
                .placaVeiculo(placaVeiculo)
                .numeroApolice(numeroApolice)
                .inicioVigencia(inicioVigencia)
                .fimVigencia(inicioVigencia.minusDays(3))
                .valorApolice(valorApolice)
                .apoliceVencida(true)
                .descricaoApolice("A apólice está 3 dias vencida!")
                .build();
    }

    private static final String idApolice = "JKFGSGFKSG";
    private static final String idCliente = "KJGFHGHK";
    private static final String placaVeiculo = "AAA9999";
    private static final String numeroApolice = "8527419";
    private static final LocalDate inicioVigencia = LocalDate.of(2021, Month.APRIL, 2);
    private static final LocalDate fimVigencia = LocalDate.of(2021, Month.APRIL, 10);
    private static final BigDecimal valorApolice = BigDecimal.TEN;
}
