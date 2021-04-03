package com.desafioviavarejo.desafiospringmongo.app.entrypoint;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApoliceEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApolicePageResult;
import com.desafioviavarejo.desafiospringmongo.app.core.usecase.*;
import com.desafioviavarejo.desafiospringmongo.app.entrypoint.entity.ApoliceHttpModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ApoliceEntryPointTest {

    @InjectMocks
    private ApoliceEntryPoint apoliceEntryPoint;

    @Mock
    private SalvarApoliceUseCase salvarApoliceUseCase;
    @Mock
    private BuscarApolicePorIdUseCase buscarApolicePorIdUseCase;
    @Mock
    private BuscarApolicesUseCase buscarApolicesUseCase;
    @Mock
    private AtualizarApoliceUseCase atualizarApoliceUseCase;
    @Mock
    private ExcluirApoliceUseCase excluirApoliceUseCase;

    private MockMvc mockMvc;

    private final String URL_API = "/desafio";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.apoliceEntryPoint).build();
    }

    private static final String idApolice = "JKFGSGFKSG";
    private static final String idCliente = "KJGFHGHK";
    private static final String placaVeiculo = "AAA9999";
    private static final String numeroApolice = "8527419";
    private static final LocalDate inicioVigencia = LocalDate.of(2021, Month.APRIL, 2);
    private static final LocalDate fimVigencia = LocalDate.of(2021, Month.APRIL, 10);
    private static final BigDecimal valorApolice = BigDecimal.TEN;

    @Test
    public void salvarApolice() throws Exception {
        when(salvarApoliceUseCase.salvar(any())).thenReturn(obterApolice());

        mockMvc.perform(MockMvcRequestBuilders.post(URL_API + "/apolices")
                .content(jsonString(obterHttpModel()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        Mockito.verify(salvarApoliceUseCase, times(1)).salvar(any());
    }

    @Test
    public void buscarApolicePorId() throws Exception {
        when(buscarApolicePorIdUseCase.buscarPorId(any())).thenReturn(obterApolice());

        mockMvc.perform(MockMvcRequestBuilders.get(URL_API + "/apolices/1")
                .param("idApolice", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(buscarApolicePorIdUseCase, times(1)).buscarPorId(any());
    }

    @Test
    public void buscarApolicePorIdNull() throws Exception {
        when(buscarApolicePorIdUseCase.buscarPorId(any())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get(URL_API + "/apolices/1")
                .param("idApolice", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(buscarApolicePorIdUseCase, times(1)).buscarPorId(any());
    }

    @Test
    public void buscarApolices() throws Exception {
        when(buscarApolicesUseCase.buscarApolices(any(), any())).thenReturn(obterApolicePage());

        mockMvc.perform(MockMvcRequestBuilders.get(URL_API + "/apolices")
                .param("offset", "0")
                .param("limit", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(buscarApolicesUseCase, times(1)).buscarApolices(any(), any());
    }

    @Test
    public void buscarApolicesNull() throws Exception {
        when(buscarApolicesUseCase.buscarApolices(any(), any())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get(URL_API + "/apolices")
                .param("offset", "0")
                .param("limit", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(buscarApolicesUseCase, times(1)).buscarApolices(any(), any());
    }

    @Test
    public void atualizarApolice() throws Exception {
        doNothing().when(atualizarApoliceUseCase).atualizarApolice(any(), any());

        mockMvc.perform(MockMvcRequestBuilders.patch(URL_API + "/apolices/1")
                .param("idApolice", "1")
                .content(jsonString(obterHttpModel()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(atualizarApoliceUseCase, times(1)).atualizarApolice(any(), any());
    }

    @Test
    public void excluirApolice() throws Exception {
        doNothing().when(excluirApoliceUseCase).excluirApolice(any());

        mockMvc.perform(MockMvcRequestBuilders.delete(URL_API + "/apolices/1")
                .param("idApolice", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(excluirApoliceUseCase, times(1)).excluirApolice(any());
    }

    private ApolicePageResult obterApolicePage() {
        return ApolicePageResult.builder()
                .apolices(Collections.singletonList(obterApolice()))
                .limit(10L)
                .offset(0L)
                .records(1L)
                .build();
    }

    private static String jsonString(Object obj) throws JsonProcessingException {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    private ApoliceHttpModel obterHttpModel() {
        return ApoliceHttpModel.builder()
                .idCliente(idCliente)
                .placaVeiculo(placaVeiculo)
                .inicioVigencia(inicioVigencia)
                .fimVigencia(fimVigencia)
                .valorApolice(valorApolice)
                .build();
    }
}
