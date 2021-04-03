package com.desafioviavarejo.desafiospringmongo.app.entrypoint;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApoliceEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.usecase.BuscarRelApolicePorNumeroUseCase;
import com.desafioviavarejo.desafiospringmongo.app.entrypoint.entity.RelatorioApoliceHttpModel;
import com.desafioviavarejo.desafiospringmongo.app.entrypoint.mappers.RelatorioApoliceHttpModelMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.BUSCAR_APOLICE_NUMERO_INICIO;

/**
 * Api Relatório de Apolices
 *
 * @author Paulo Abreu
 */
@Slf4j
@RestController
@RequestMapping("/desafio")
public class RelatorioApoliceEntryPoint {

    private final BuscarRelApolicePorNumeroUseCase buscarRelApolicePorNumeroUseCase;

    @Autowired
    public RelatorioApoliceEntryPoint(BuscarRelApolicePorNumeroUseCase buscarRelApolicePorNumeroUseCase) {
        this.buscarRelApolicePorNumeroUseCase = buscarRelApolicePorNumeroUseCase;
    }

    @ApiOperation(value = "API responsavel por buscar um relatorio de Apolice", response = RelatorioApoliceHttpModel.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 204, message = "No Content")})
    @GetMapping(path = "/relatorio-apolices/{numeroApolice}")
    public ResponseEntity<RelatorioApoliceHttpModel> buscarApolicePorNumero(@PathVariable @NotNull String numeroApolice) {
        log.info(BUSCAR_APOLICE_NUMERO_INICIO);
        ApoliceEntity result = this.buscarRelApolicePorNumeroUseCase.buscarPorNumero(numeroApolice);

        if (result == null)
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(RelatorioApoliceHttpModelMapper.INSTANCE.toRelatorio(result));
    }
}
