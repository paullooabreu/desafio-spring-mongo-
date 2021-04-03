package com.desafioviavarejo.desafiospringmongo.app.entrypoint;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApoliceEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.usecase.BuscarRelApolicePorNumeroUseCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class RelatorioApoliceEntryPointTest {

    @InjectMocks
    private RelatorioApoliceEntryPoint relatorioApoliceEntryPoint;

    @Mock
    private BuscarRelApolicePorNumeroUseCase buscarRelApolicePorNumeroUseCase;
    private MockMvc mockMvc;

    private final String URL_API = "/desafio";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.relatorioApoliceEntryPoint).build();
    }

    @Test
    public void buscarRelApolicePorNumero() throws Exception {
        when(buscarRelApolicePorNumeroUseCase.buscarPorNumero(any())).thenReturn(obterApolice());

        mockMvc.perform(MockMvcRequestBuilders.get(URL_API + "/relatorio-apolices/1234567")
                .param("numeroApolice", "1234567")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(buscarRelApolicePorNumeroUseCase, times(1)).buscarPorNumero(any());
    }

    @Test
    public void buscarRelApolicePorNumeroNull() throws Exception {
        when(buscarRelApolicePorNumeroUseCase.buscarPorNumero(any())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get(URL_API + "/relatorio-apolices/1234567")
                .param("numeroApolice", "1234567")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(buscarRelApolicePorNumeroUseCase, times(1)).buscarPorNumero(any());
    }

    private static final String idApolice = "JKFGSGFKSG";
    private static final String idCliente = "KJGFHGHK";
    private static final String placaVeiculo = "AAA9999";
    private static final String numeroApolice = "8527419";
    private static final LocalDate inicioVigencia = LocalDate.of(2021, Month.APRIL, 2);
    private static final LocalDate fimVigencia = LocalDate.of(2021, Month.APRIL, 10);
    private static final BigDecimal valorApolice = BigDecimal.TEN;

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
