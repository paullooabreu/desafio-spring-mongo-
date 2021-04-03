package com.desafioviavarejo.desafiospringmongo.app.core.usecase;

import com.desafioviavarejo.desafiospringmongo.app.core.entity.ApoliceEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.entity.ClienteEntity;
import com.desafioviavarejo.desafiospringmongo.app.core.exception.BusinessException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.exception.ServiceUnavailableException;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ApoliceGateway;
import com.desafioviavarejo.desafiospringmongo.app.dataprovider.gateway.ClienteGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;

import static com.desafioviavarejo.desafiospringmongo.app.core.exception.ExceptionDetails.*;
import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.SALVAR_CLIENTE_FIM;
import static com.desafioviavarejo.desafiospringmongo.app.util.MensagensLog.SALVAR_CLIENTE_INICIO;
/**
 * Responsável por salvar uma apolice
 *
 * @author Paulo Abreu
 */
@Slf4j
@Component
public class SalvarApoliceUseCase {

    private final ApoliceGateway apoliceGateway;

    private final ClienteGateway clienteGateway;

    @Autowired
    public SalvarApoliceUseCase(ApoliceGateway apoliceGateway, ClienteGateway clienteGateway) {
        this.apoliceGateway = apoliceGateway;
        this.clienteGateway = clienteGateway;
    }

    public ApoliceEntity salvar(ApoliceEntity apolice) {
        log.info(SALVAR_CLIENTE_INICIO);

        validarDatas(apolice);

        validaCliente(apolice);

        apolice.setNumeroApolice(gerarNumeroApolice());

        return salvarApolice(apolice);
    }

    private ApoliceEntity salvarApolice(ApoliceEntity apolice) {
        try {
            return apoliceGateway.salvarApolice(apolice);
        } catch (ServiceUnavailableException e) {
            log.info(SALVAR_CLIENTE_FIM);
            throw new BusinessException(ERRO_SALVAR_APOLICE.getCodigo(), ERRO_SALVAR_APOLICE.getMensagem(), e);
        }
    }

    private String gerarNumeroApolice() {

        String numeroApolice = gerarNumeroAleatorio();

        ApoliceEntity apolice;

        boolean validaApolice = true;

        while (validaApolice) {
            try {
                apolice = apoliceGateway.buscarApolicePorNumero(numeroApolice);
            } catch (ServiceUnavailableException e) {
                throw new BusinessException(ERRO_SALVAR_APOLICE.getCodigo(), ERRO_SALVAR_APOLICE.getMensagem(), e);
            }
            if (apolice == null)
                validaApolice = false;
            else
                numeroApolice = gerarNumeroAleatorio();
        }
        return numeroApolice;
    }

    private String gerarNumeroAleatorio() {
        Random random = new Random();
        return String.format("%07d", random.nextInt(9999999));
    }

    private void validaCliente(ApoliceEntity apolice) {
        if (apolice.getIdCliente() == null)
            throw new BusinessException(ERRO_ID_CLIENTE.getCodigo(), ERRO_ID_CLIENTE.getMensagem());

        ClienteEntity cliente;
        try {
            cliente = clienteGateway.buscarClientePorId(apolice.getIdCliente());
        } catch (ServiceUnavailableException e) {
            throw new BusinessException(ERRO_OBTER_CLIENTE_ID.getCodigo(), ERRO_OBTER_CLIENTE_ID.getMensagem(), e);
        }

        if (cliente == null)
            throw new BusinessException(ERRO_CLIENTE_NULL.getCodigo(), ERRO_CLIENTE_NULL.getMensagem());
    }

    private void validarDatas(ApoliceEntity apolice) {
        LocalDate dataInicial = apolice.getInicioVigencia();
        LocalDate dataFinal = apolice.getFimVigencia();

        if (dataInicial.isAfter(dataFinal))
            throw new BusinessException(ERRO_DATA_APOLICE.getCodigo(), ERRO_DATA_APOLICE.getMensagem());
    }
}
