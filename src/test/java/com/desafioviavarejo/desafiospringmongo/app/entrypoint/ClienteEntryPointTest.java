package com.desafioviavarejo.desafiospringmongo.app.entrypoint;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClienteEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClientePageResult;
import com.desafioviavarejo.desafiospringmongo.app.core.usecase.*;
import com.desafioviavarejo.desafiospringmongo.app.entrypoint.entity.ClienteHttpModel;
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

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ClienteEntryPointTest {

    @InjectMocks
    private ClienteEntryPoint clienteEntryPoint;

    @Mock
    private SalvarClienteUseCase salvarClienteUseCase;
    @Mock
    private BuscarClientePorIdUseCase buscarClientePorIdUseCase;
    @Mock
    private BuscarClientesUseCase buscarClientesUseCase;
    @Mock
    private AtualizarClienteUseCase atualizarClienteUseCase;
    @Mock
    private ExcluirClienteUseCase excluirClienteUseCase;

    private static final String idCliente = "KJFKFKHFKJ";
    private static final String ufCliente = "PR";
    private static final String nomeCliente = "Via";
    private static final String cpfCliente = "59701859065";
    private static final String cidadeCliente = "Curitiba";

    private MockMvc mockMvc;

    private final String URL_API = "/desafio";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.clienteEntryPoint).build();
    }

    @Test
    public void salvarCliente() throws Exception {
        when(salvarClienteUseCase.salvar(any())).thenReturn(obterCliente());

        mockMvc.perform(MockMvcRequestBuilders.post(URL_API + "/clientes")
                .content(jsonString(obterClienteHttpModel()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        Mockito.verify(salvarClienteUseCase, times(1)).salvar(any());
    }

    @Test
    public void buscarClientePorId() throws Exception {
        when(buscarClientePorIdUseCase.buscarPorId(any())).thenReturn(obterCliente());

        mockMvc.perform(MockMvcRequestBuilders.get(URL_API + "/clientes/1")
                .param("idCliente", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(buscarClientePorIdUseCase, times(1)).buscarPorId(any());
    }

    @Test
    public void buscarClientePorIdNull() throws Exception {
        when(buscarClientePorIdUseCase.buscarPorId(any())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get(URL_API + "/clientes/1")
                .param("idCliente", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(buscarClientePorIdUseCase, times(1)).buscarPorId(any());
    }

    @Test
    public void buscarClientes() throws Exception {
        when(buscarClientesUseCase.buscarClientes(any(), any())).thenReturn(obterClientePage());

        mockMvc.perform(MockMvcRequestBuilders.get(URL_API + "/clientes")
                .param("offset", "0")
                .param("limit", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(buscarClientesUseCase, times(1)).buscarClientes(any(), any());
    }

    @Test
    public void buscarClientesNull() throws Exception {
        when(buscarClientesUseCase.buscarClientes(any(), any())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get(URL_API + "/clientes")
                .param("offset", "0")
                .param("limit", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(buscarClientesUseCase, times(1)).buscarClientes(any(), any());
    }

    @Test
    public void atualizarCliente() throws Exception {
        doNothing().when(atualizarClienteUseCase).atualizarCliente(any(), any());

        mockMvc.perform(MockMvcRequestBuilders.patch(URL_API + "/clientes/1")
                .param("idCliente", "1")
                .content(jsonString(obterClienteHttpModel()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(atualizarClienteUseCase, times(1)).atualizarCliente(any(), any());
    }

    @Test
    public void excluirClientes() throws Exception {
        doNothing().when(excluirClienteUseCase).excluirCliente(any());

        mockMvc.perform(MockMvcRequestBuilders.delete(URL_API + "/clientes/1")
                .param("idClientes", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(excluirClienteUseCase, times(1)).excluirCliente(any());
    }

    private ClientePageResult obterClientePage() {
        return ClientePageResult.builder()
                .clientes(Collections.singletonList(obterCliente()))
                .limit(10L)
                .offset(0L)
                .records(1L)
                .build();
    }

    private static String jsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ClienteEntity obterCliente() {
        return ClienteEntity.builder()
                .idCliente(idCliente)
                .ufCliente(ufCliente)
                .nomeCliente(nomeCliente)
                .cpfCliente(cpfCliente)
                .cidadeCliente(cidadeCliente)
                .build();
    }

    private ClienteHttpModel obterClienteHttpModel() {
        return ClienteHttpModel.builder()
                .ufCliente(ufCliente)
                .nomeCliente(nomeCliente)
                .cpfCliente(cpfCliente)
                .cidadeCliente(cidadeCliente)
                .build();
    }
}
