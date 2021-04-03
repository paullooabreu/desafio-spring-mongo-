package com.desafioviavarejo.desafiospringmongo.app.core.usecase;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApoliceEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.exception.BusinessException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ApoliceGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

import static com.desafioviavarejo.desafiospringmongo.app.core.exception.ExceptionDetails.ERRO_APOLICE_NAO_EXISTE;
import static com.desafioviavarejo.desafiospringmongo.app.core.exception.ExceptionDetails.ERRO_BUSCAR_APOLICE_NUMERO;
import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.BUSCAR_APOLICE_NUMERO_FIM;
import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.BUSCAR_APOLICE_NUMERO_INICIO;

/**
 * Responsável por monstar um relatório de uma apolice por numero
 *
 * @author Paulo Abreu
 */
@Slf4j
@Component
public class BuscarRelApolicePorNumeroUseCase {

    private final ApoliceGateway apoliceGateway;

    public BuscarRelApolicePorNumeroUseCase(ApoliceGateway apoliceGateway) {
        this.apoliceGateway = apoliceGateway;
    }

    public ApoliceEntity buscarPorNumero(String numeroApolice) {
        log.info(BUSCAR_APOLICE_NUMERO_INICIO);

        ApoliceEntity apolice = obterApolice(numeroApolice);

        validarApolice(apolice);

        return criaInformacaoApolice(apolice);
    }

    private void validarApolice(ApoliceEntity apoliceSalva) {
        if (apoliceSalva == null)
            throw new BusinessException(ERRO_APOLICE_NAO_EXISTE.getCodigo(), ERRO_APOLICE_NAO_EXISTE.getMensagem());
    }

    private ApoliceEntity obterApolice(String numeroApolice) {
        ApoliceEntity apolice;
        try {
            apolice = apoliceGateway.buscarApolicePorNumero(numeroApolice);
        } catch (ServiceUnavailableException e) {
            log.info(BUSCAR_APOLICE_NUMERO_FIM);
            throw new BusinessException(ERRO_BUSCAR_APOLICE_NUMERO.getCodigo(), ERRO_BUSCAR_APOLICE_NUMERO.getMensagem(), e);
        }
        return apolice;
    }

    private ApoliceEntity criaInformacaoApolice(ApoliceEntity apolice) {
        LocalDate dataHoje = LocalDate.now();
        LocalDate dataFinal = apolice.getFimVigencia();

        if (dataFinal.isAfter(dataHoje)) {
            apolice.setApoliceVencida(false);
            Period periodo = Period.between(dataHoje, dataFinal);
            apolice.setDescricaoApolice("Faltam " + periodo.getDays() + " para vencer a Apólice!");
        } else {
            apolice.setApoliceVencida(true);
            Period periodo = Period.between(dataFinal, dataHoje);
            if (periodo.getDays() == 1)
                apolice.setDescricaoApolice("A apólice está " + periodo.getDays() + " dia vencida!");
            else
                apolice.setDescricaoApolice("A apólice está " + periodo.getDays() + " dias vencida!");
        }
        return apolice;
    }
}
