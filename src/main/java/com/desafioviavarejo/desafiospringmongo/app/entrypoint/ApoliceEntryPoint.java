package com.desafioviavarejo.desafiospringmongo.app.entrypoint;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApoliceEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApolicePageResult;
import com.desafioviavarejo.desafiospringmongo.app.core.usecase.*;
import com.desafioviavarejo.desafiospringmongo.app.entrypoint.entity.ApoliceHttpModel;
import com.desafioviavarejo.desafiospringmongo.app.entrypoint.entity.ApoliceResponse;
import com.desafioviavarejo.desafiospringmongo.app.entrypoint.mappers.ApoliceHttpModelMapper;
import com.desafioviavarejo.desafiospringmongo.app.entrypoint.mappers.ApoliceResponseMapper;
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
 * Api Apolices
 *
 * @author Paulo Abreu
 */
@Slf4j
@RestController
@RequestMapping("/desafio")
public class ApoliceEntryPoint {

    private final SalvarApoliceUseCase salvarApoliceUseCase;
    private final BuscarApolicePorIdUseCase buscarApolicePorIdUseCase;
    private final BuscarApolicesUseCase buscarApolicesUseCase;
    private final AtualizarApoliceUseCase atualizarApoliceUseCase;
    private final ExcluirApoliceUseCase excluirApoliceUseCase;

    @Autowired
    public ApoliceEntryPoint(SalvarApoliceUseCase salvarApoliceUseCase,
                             BuscarApolicePorIdUseCase buscarApolicePorIdUseCase,
                             BuscarApolicesUseCase buscarApolicesUseCase,
                             AtualizarApoliceUseCase atualizarApoliceUseCase,
                             ExcluirApoliceUseCase excluirApoliceUseCase) {
        this.salvarApoliceUseCase = salvarApoliceUseCase;
        this.buscarApolicePorIdUseCase = buscarApolicePorIdUseCase;
        this.buscarApolicesUseCase = buscarApolicesUseCase;
        this.atualizarApoliceUseCase = atualizarApoliceUseCase;
        this.excluirApoliceUseCase = excluirApoliceUseCase;
    }

    @ApiOperation(value = "API responsavel por cadastrar uma Apolice", response = ApoliceHttpModel.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created")})
    @PostMapping(path = "/apolices")
    public ResponseEntity<ApoliceHttpModel> salvarApolice(@RequestBody @Valid ApoliceHttpModel apolice) {
        log.info(SALVAR_APOLICE_INICIO);

        ApoliceEntity result = this.salvarApoliceUseCase.salvar(ApoliceHttpModelMapper.INSTANCE.toEntity(apolice));

        return ResponseEntity.created(URI.create("apolices")).body(ApoliceHttpModelMapper.INSTANCE.toHttpModel(result));
    }

    @ApiOperation(value = "API responsavel por buscar uma Apolice por Id", response = ApoliceHttpModel.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 204, message = "No Content")})
    @GetMapping(path = "/apolices/{idApolice}")
    public ResponseEntity<ApoliceHttpModel> buscarApolicePorId(@PathVariable @NotNull String idApolice) {
        log.info(BUSCAR_APOLICE_ID_INICIO);

        ApoliceEntity result = this.buscarApolicePorIdUseCase.buscarPorId(idApolice);

        if (result == null)
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(ApoliceHttpModelMapper.INSTANCE.toHttpModel(result));
    }

    @ApiOperation(value = "API responsavel por buscar todos as apolices", response = ApoliceResponse.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 204, message = "No Content")})
    @GetMapping(path = "/apolices")
    public ResponseEntity<ApoliceResponse> buscarApolices(@RequestParam(defaultValue = "0") Integer offset,
                                                          @RequestParam(defaultValue = "100") Integer limit) {
        log.info(BUSCAR_APOLICES_INICIO);

        ApolicePageResult result = this.buscarApolicesUseCase.buscarApolices(offset, limit);

        if (result == null || result.getApolices() == null || result.getApolices().isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(ApoliceResponseMapper.INSTANCE.toResponse(result));
    }

    @ApiOperation(value = "API responsavel por atualizar uma Apolice")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Sucesso")})
    @PatchMapping(path = "/apolices/{idApolice}")
    public ResponseEntity<Void> atualizarApolice(@RequestBody @NotNull ApoliceHttpModel apolice,
                                                 @PathVariable @NotNull String idApolice) {
        log.info(ATUALIZAR_APOLICE_INICIO);

        this.atualizarApoliceUseCase.atualizarApolice(ApoliceHttpModelMapper.INSTANCE.toEntity(apolice), idApolice);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "API responsavel por excluir uma Apolice", response = ApoliceHttpModel.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Sucesso")})
    @DeleteMapping(path = "/apolices/{idApolice}")
    public ResponseEntity<Void> excluirApolice(@PathVariable String idApolice) {
        log.info(EXCLUIR_APOLICE_INICIO);

        this.excluirApoliceUseCase.excluirApolice(idApolice);

        return ResponseEntity.ok().build();
    }
}
