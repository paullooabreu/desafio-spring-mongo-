package com.desafioviavarejo.desafiospringmongo.app.core.usecase;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApoliceEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApolicePageResult;
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
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BuscarApolicesUseCaseTest {

    @InjectMocks
    private BuscarApolicesUseCase buscarApolicesUseCase;

    @Mock
    private ApoliceGateway apoliceGateway;

    private static final int offset = 5;
    private static final int limit = 0;
    private static final String idApolice = "JKFGSGFKSG";
    private static final String idCliente = "KJGFHGHK";
    private static final String placaVeiculo = "AAA9999";
    private static final String numeroApolice = "8527419";
    private static final LocalDate inicioVigencia = LocalDate.of(2021, Month.APRIL, 2);
    private static final LocalDate fimVigencia = LocalDate.of(2021, Month.APRIL, 10);
    private static final BigDecimal valorApolice = BigDecimal.TEN;

    @Test
    public void buscarClientes() {
        when(apoliceGateway.buscarApolices(any(), any())).thenReturn(obterApolicePage());

        ApolicePageResult result = buscarApolicesUseCase.buscarApolices(offset, limit);

        Assert.assertNotNull(result);
        Assert.assertEquals(obterApolicePage(), result);

        verify(apoliceGateway).buscarApolices(any(), any());
    }

    @Test(expected = BusinessException.class)
    public void buscarClientesExcption() {
        when(apoliceGateway.buscarApolices(any(), any())).thenThrow(ServiceUnavailableException.class);

        buscarApolicesUseCase.buscarApolices(offset, limit);

        verify(apoliceGateway).buscarApolices(any(), any());
    }

    private ApolicePageResult obterApolicePage() {
        return ApolicePageResult.builder()
                .apolices(Collections.singletonList(obterApolice()))
                .limit(10L)
                .offset(0L)
                .records(1L)
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

}
