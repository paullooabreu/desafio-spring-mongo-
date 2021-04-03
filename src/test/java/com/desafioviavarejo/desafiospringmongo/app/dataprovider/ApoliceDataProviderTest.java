package com.desafioviavarejo.desafiospringmongo.app.dataprovider;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApoliceEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApolicePageResult;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.entity.ApoliceTable;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.repository.ApoliceRepository;
import com.mongodb.MongoQueryException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ApoliceDataProviderTest {

    @InjectMocks
    private ApoliceDataProvider apoliceDataProvider;

    @Mock
    private ApoliceRepository apoliceRepository;

    private static final String idApolice = "JKFGSGFKSG";
    private static final String idCliente = "KJGFHGHK";
    private static final String placaVeiculo = "AAA9999";
    private static final String numeroApolice = "8527419";
    private static final LocalDate inicioVigencia = LocalDate.of(2021, Month.APRIL, 2);
    private static final LocalDate fimVigencia = LocalDate.of(2021, Month.APRIL, 10);
    private static final BigDecimal valorApolice = BigDecimal.TEN;
    private static final int offset = 0;
    private static final int limit = 1;
    private Page<ApoliceTable> apoliceTablePage = new PageImpl<>(Collections.singletonList(obterApoliceTable()));

    @Test
    public void salvarApolice() {
        when(apoliceRepository.save(any())).thenReturn(obterApoliceTable());

        ApoliceEntity result = apoliceDataProvider.salvarApolice(obterApolice());

        assertNotNull(result);
        assertEquals(obterApolice(), result);

        verify(apoliceRepository, times(1)).save(any());
    }

    @Test(expected = ServiceUnavailableException.class)
    public void salvarApoliceException() {
        when(apoliceRepository.save(any())).thenThrow(MongoQueryException.class);

        apoliceDataProvider.salvarApolice(obterApolice());

        verify(apoliceRepository, times(1)).save(any());
    }

    @Test
    public void buscarApolicePorId() {
        when(apoliceRepository.findByIdApolice(any())).thenReturn(obterApoliceTable());

        ApoliceEntity result = apoliceDataProvider.buscarApolicePorId(idApolice);

        assertNotNull(result);
        assertEquals(obterApolice(), result);

        verify(apoliceRepository, times(1)).findByIdApolice(any());
    }

    @Test(expected = ServiceUnavailableException.class)
    public void buscarApolicePorIdException() {
        when(apoliceRepository.findByIdApolice(any())).thenThrow(MongoQueryException.class);

        apoliceDataProvider.buscarApolicePorId(idApolice);

        verify(apoliceRepository, times(1)).findByIdApolice(any());
    }

    @Test
    public void buscarApolices() {

        when(apoliceRepository.findAll(eq(PageRequest.of(offset, limit)))).thenReturn(apoliceTablePage);

        ApolicePageResult result = apoliceDataProvider.buscarApolices(offset, limit);

        assertNotNull(result);
        assertEquals(obterApolicePageResult(), result);

        verify(apoliceRepository, times(1)).findAll(eq(PageRequest.of(offset, limit)));
    }

    private ApolicePageResult obterApolicePageResult() {
        return ApolicePageResult.builder()
                .offset((long) offset)
                .limit((long) apoliceTablePage.getTotalPages())
                .records(apoliceTablePage.getTotalElements())
                .apolices(Collections.singletonList(obterApolice()))
                .build();
    }

    @Test(expected = ServiceUnavailableException.class)
    public void buscarApolicesException() {
        when(apoliceRepository.findAll(eq(PageRequest.of(offset, limit)))).thenThrow(MongoQueryException.class);

        apoliceDataProvider.buscarApolices(offset, limit);

        verify(apoliceRepository, times(1)).findAll(eq(PageRequest.of(offset, limit)));
    }

    @Test
    public void buscarApolicePorNumero() {
        when(apoliceRepository.findByNumeroApolice(any())).thenReturn(obterApoliceTable());

        ApoliceEntity result = apoliceDataProvider.buscarApolicePorNumero(numeroApolice);

        assertNotNull(result);
        assertEquals(obterApolice(), result);

        verify(apoliceRepository, times(1)).findByNumeroApolice(any());
    }

    @Test(expected = ServiceUnavailableException.class)
    public void buscarApolicePorNumeroException() {
        when(apoliceRepository.findByNumeroApolice(any())).thenThrow(MongoQueryException.class);

        apoliceDataProvider.buscarApolicePorNumero(numeroApolice);

        verify(apoliceRepository, times(1)).findByNumeroApolice(any());
    }

    @Test
    public void atualizarApolice() {
        when(apoliceRepository.save(any())).thenReturn(obterApoliceTable());

        apoliceDataProvider.atualizarApolice(obterApolice());

        verify(apoliceRepository, times(1)).save(any());
    }

    @Test(expected = ServiceUnavailableException.class)
    public void atualizarApoliceException() {
        when(apoliceRepository.save(any())).thenThrow(MongoQueryException.class);

        apoliceDataProvider.atualizarApolice(obterApolice());

        verify(apoliceRepository, times(1)).save(any());
    }

    @Test
    public void excluirApolice() {
        doNothing().when(apoliceRepository).deleteByIdApolice(any());

        apoliceDataProvider.excluirApolice(idApolice);

        verify(apoliceRepository, times(1)).deleteByIdApolice(any());
    }

    @Test(expected = ServiceUnavailableException.class)
    public void excluirApoliceException() {
        doThrow(MongoQueryException.class).when(apoliceRepository).deleteByIdApolice(any());

        apoliceDataProvider.excluirApolice(idApolice);

        verify(apoliceRepository, times(1)).deleteByIdApolice(any());
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

    private ApoliceTable obterApoliceTable() {
        return ApoliceTable.builder()
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
