package com.cyrillo.negociacao.core.dataprovider.dto;

import com.cyrillo.negociacao.core.dataprovider.tipos.IdentificacaoNegocioDtoInterface;
import com.google.protobuf.Timestamp;

import java.time.LocalDateTime;

public class IdentificacaoNegocioDto implements IdentificacaoNegocioDtoInterface {
    private String identificadorNegocio;
    private String corretora;
    private String identificacaoClienteNegocio;
    private LocalDateTime dataNegocio;
    private LocalDateTime dataLiquidacao;

    public IdentificacaoNegocioDto(String identificadorNegocio, String corretora,String identificacaoClienteNegocio,LocalDateTime dataNegocio, LocalDateTime dataLiquidacao) {
        this.identificadorNegocio = identificadorNegocio;
        this.corretora = corretora;
        this.identificacaoClienteNegocio = identificacaoClienteNegocio;
        this.dataNegocio = dataNegocio;
        this.dataLiquidacao = dataLiquidacao;
    }

    public String getIdentificadorNegocio() {
        return identificadorNegocio;
    }

    public String getCorretora() {
        return corretora;
    }

    public String getIdentificacaoClienteNegocio() {
        return identificacaoClienteNegocio;
    }

    public LocalDateTime getDataNegocio() {
        return dataNegocio;
    }

    public LocalDateTime getDataLiquidacao() {
        return dataLiquidacao;
    }

    public String toString() {
      return "Identificador Negócio: " + identificadorNegocio + " | " + "Cliente: " + identificacaoClienteNegocio + " | " + "Corretora: " + corretora + " | " + "Data Negócio: " + dataNegocio.toString();
    }
}
