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
public class BuscarApolicePorIdUseCaseTest {

    @InjectMocks
    private BuscarApolicePorIdUseCase buscarApolicePorIdUseCase;

    @Mock
    private ApoliceGateway apoliceGateway;

    @Test
    public void buscarClientePorId() {

        when(apoliceGateway.buscarApolicePorId(any())).thenReturn(obterApolice());

        ApoliceEntity result = buscarApolicePorIdUseCase.buscarPorId("id");

        Assert.assertNotNull(result);
        Assert.assertEquals(obterApolice(), result);

        verify(apoliceGateway, times(1)).buscarApolicePorId(any());

    }

    @Test(expected = BusinessException.class)
    public void buscarClientePorIdException() {

        when(apoliceGateway.buscarApolicePorId(any())).thenThrow(ServiceUnavailableException.class);

        buscarApolicePorIdUseCase.buscarPorId("id");

        verify(apoliceGateway, times(1)).buscarApolicePorId(any());

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

    private static final String idApolice = "JKFGSGFKSG";
    private static final String idCliente = "KJGFHGHK";
    private static final String placaVeiculo = "AAA9999";
    private static final String numeroApolice = "8527419";
    private static final LocalDate inicioVigencia = LocalDate.of(2021, Month.APRIL, 2);
    private static final LocalDate fimVigencia = LocalDate.of(2021, Month.APRIL, 10);
    private static final BigDecimal valorApolice = BigDecimal.TEN;

}
