package com.desafioviavarejo.desafiospringmongo.app.core.usecase;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApoliceEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClienteEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.exception.BusinessException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ApoliceGateway;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ClienteGateway;
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
public class SalvarApoliceUseCaseTest {

    @InjectMocks
    private SalvarApoliceUseCase salvarApoliceUseCase;

    @Mock
    private ApoliceGateway apoliceGateway;

    @Mock
    private ClienteGateway clienteGateway;

    private static final String idApolice = "JKFGSGFKSG";
    private static final String idCliente = "KJGFHGHK";
    private static final String placaVeiculo = "AAA9999";
    private static final String numeroApolice = "8527419";
    private static final LocalDate inicioVigencia = LocalDate.of(2021, Month.APRIL, 2);
    private static final LocalDate fimVigencia = LocalDate.of(2021, Month.APRIL, 10);
    private static final BigDecimal valorApolice = BigDecimal.TEN;


    @Test
    public void salvarApolice() {
        when(apoliceGateway.salvarApolice(any())).thenReturn(obterApolice());
        when(clienteGateway.buscarClientePorId(any())).thenReturn(obterCliente());
        when(apoliceGateway.buscarApolicePorNumero(any())).thenReturn(null);

        ApoliceEntity result = salvarApoliceUseCase.salvar(obterApolice());

        Assert.assertNotNull(result);
        Assert.assertEquals(obterApolice(), result);

        verify(apoliceGateway, times(1)).salvarApolice(any());
        verify(clienteGateway, times(1)).buscarClientePorId(any());
        verify(apoliceGateway, times(1)).buscarApolicePorNumero(any());
    }

    @Test(expected = BusinessException.class)
    public void salvarApolicebuscarApolicePorNumeroException() {
        when(clienteGateway.buscarClientePorId(any())).thenReturn(obterCliente());
        when(apoliceGateway.buscarApolicePorNumero(any())).thenThrow(ServiceUnavailableException.class);

        salvarApoliceUseCase.salvar(obterApolice());

        verify(clienteGateway, times(1)).buscarClientePorId(any());
        verify(apoliceGateway, times(1)).buscarApolicePorNumero(any());
    }

    @Test(expected = BusinessException.class)
    public void salvarApoliceExcpetion() {
        when(apoliceGateway.salvarApolice(any())).thenThrow(ServiceUnavailableException.class);
        when(clienteGateway.buscarClientePorId(any())).thenReturn(obterCliente());
        when(apoliceGateway.buscarApolicePorNumero(any())).thenReturn(null);

        salvarApoliceUseCase.salvar(obterApoliceSalvar());

        verify(apoliceGateway, times(1)).salvarApolice(any());
        verify(clienteGateway, times(1)).buscarClientePorId(any());
        verify(apoliceGateway, times(1)).buscarApolicePorNumero(any());
    }

    @Test(expected = BusinessException.class)
    public void salvarApoliceBuscarClientePorIdNullExcpetion() {
        when(clienteGateway.buscarClientePorId(any())).thenReturn(null);

        salvarApoliceUseCase.salvar(obterApoliceSalvar());

        verify(clienteGateway, times(1)).buscarClientePorId(any());
    }

    @Test(expected = BusinessException.class)
    public void salvarApoliceBuscarClientePorIdExcpetion() {
        when(clienteGateway.buscarClientePorId(any())).thenThrow(ServiceUnavailableException.class);

        salvarApoliceUseCase.salvar(obterApoliceSalvar());

        verify(clienteGateway, times(1)).buscarClientePorId(any());
    }

    @Test(expected = BusinessException.class)
    public void salvarApoliceDatasException() {

        salvarApoliceUseCase.salvar(obterApoliceDatasErradas());

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


    private ApoliceEntity obterApoliceSalvar() {
        return ApoliceEntity.builder()
                .idCliente(idCliente)
                .placaVeiculo(placaVeiculo)
                .numeroApolice(numeroApolice)
                .inicioVigencia(inicioVigencia)
                .fimVigencia(fimVigencia)
                .valorApolice(valorApolice)
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

}
