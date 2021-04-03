package com.desafioviavarejo.desafiospringmongo.app.entrypoint.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApoliceHttpModel {

    private String idApolice;

    @NotBlank(message = "O campo Cliente é obrigatório")
    private String idCliente;

    private String numeroApolice;

    @NotNull(message = "O campo inicio da vigencia é obrigatório")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate inicioVigencia;

    @NotNull(message = "O campo fim da vigencia é obrigatório")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate fimVigencia;

    @NotBlank(message = "O campo Placa do Veiculo é obrigatório")
    private String placaVeiculo;

    @NotNull(message = "O campo Valor é obrigatório")
    private BigDecimal valorApolice;
}
