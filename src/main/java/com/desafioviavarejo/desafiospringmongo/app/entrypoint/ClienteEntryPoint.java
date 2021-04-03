package com.desafioviavarejo.desafiospringmongo.app.entrypoint;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClienteEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClientePageResult;
import com.desafioviavarejo.desafiospringmongo.app.core.usecase.*;
import com.desafioviavarejo.desafiospringmongo.app.entrypoint.entity.ClienteHttpModel;
import com.desafioviavarejo.desafiospringmongo.app.entrypoint.entity.ClienteResponse;
import com.desafioviavarejo.desafiospringmongo.app.entrypoint.mappers.ClienteHttpModelMapper;
import com.desafioviavarejo.desafiospringmongo.app.entrypoint.mappers.ClienteResponseMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.*;

/**
 * Api Clientes
 *
 * @author Paulo Abreu
 */
@Slf4j
@RestController
@RequestMapping("/desafio")
public class ClienteEntryPoint {

    private final SalvarClienteUseCase salvarClienteUseCase;
    private final BuscarClientePorIdUseCase buscarClientePorIdUseCase;
    private final BuscarClientesUseCase buscarClientesUseCase;
    private final AtualizarClienteUseCase atualizarClienteUseCase;
    private final ExcluirClienteUseCase excluirClienteUseCase;

    @Autowired
    public ClienteEntryPoint(SalvarClienteUseCase salvarClienteUseCase,
                             BuscarClientePorIdUseCase buscarClientePorIdUseCase,
                             BuscarClientesUseCase buscarClientesUseCase,
                             AtualizarClienteUseCase atualizarClienteUseCase,
                             ExcluirClienteUseCase excluirClienteUseCase) {
        this.salvarClienteUseCase = salvarClienteUseCase;
        this.buscarClientePorIdUseCase = buscarClientePorIdUseCase;
        this.buscarClientesUseCase = buscarClientesUseCase;
        this.atualizarClienteUseCase = atualizarClienteUseCase;
        this.excluirClienteUseCase = excluirClienteUseCase;
    }

    @ApiOperation(value = "API responsavel por cadastrar um Cliente", response = ClienteHttpModel.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created")})
    @PostMapping(path = "/clientes")
    public ResponseEntity<ClienteHttpModel> salvarCliente(@RequestBody @Valid ClienteHttpModel cliente) {
        log.info(SALVAR_CLIENTE_INICIO);
        ClienteEntity result = this.salvarClienteUseCase.salvar(ClienteHttpModelMapper.INSTANCE.toEntity(cliente));

        return ResponseEntity.created(URI.create("clientes")).body(ClienteHttpModelMapper.INSTANCE.toHttpModel(result));
    }

    @ApiOperation(value = "API responsavel por buscar um Cliente por Id", response = ClienteHttpModel.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 204, message = "No Content")})
    @GetMapping(path = "/clientes/{idCliente}")
    public ResponseEntity<ClienteHttpModel> buscarClientePorId(@PathVariable @NotNull String idCliente) {
        log.info(BUSCAR_CLIENTE_ID_INICIO);
        ClienteEntity result = this.buscarClientePorIdUseCase.buscarPorId(idCliente);

        if (result == null)
            return ResponseEntity.noContent().build();


        return ResponseEntity.ok(ClienteHttpModelMapper.INSTANCE.toHttpModel(result));
    }

    @ApiOperation(value = "API responsavel por buscar todos os Clientes", response = ClienteResponse.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 204, message = "No Content")})
    @GetMapping(path = "/clientes")
    public ResponseEntity<ClienteResponse> buscarClientes(@RequestParam(defaultValue = "0") Integer offset,
                                                          @RequestParam(defaultValue = "100") Integer limit) {
        log.info(BUSCAR_CLIENTES_INICIO);
        ClientePageResult result = this.buscarClientesUseCase.buscarClientes(offset, limit);

        if (result == null || result.getClientes() == null || result.getClientes().isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(ClienteResponseMapper.INSTANCE.toResponse(result));
    }

    @ApiOperation(value = "API responsavel por atualizar um Cliente")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Sucesso")})
    @PatchMapping(path = "/clientes/{idCliente}")
    public ResponseEntity<Void> atualizarCliente(@RequestBody @NotNull ClienteHttpModel cliente,
                                                 @PathVariable @NotNull String idCliente) {
        log.info(ATUALIZAR_CLIENTE_INICIO);
        this.atualizarClienteUseCase.atualizarCliente(ClienteHttpModelMapper.INSTANCE.toEntity(cliente), idCliente);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "API responsavel por excluir um Cliente")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Sucesso")})
    @DeleteMapping(path = "/clientes/{idCliente}")
    public ResponseEntity<Void> excluirCliente(@PathVariable String idCliente) {
        log.info(EXCLUIR_CLIENTE_INICIO);
        this.excluirClienteUseCase.excluirCliente(idCliente);

        return ResponseEntity.ok().build();
    }
}
