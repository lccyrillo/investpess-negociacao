package com.cyrillo.negociacao.infra.dataprovider.dto;

import com.cyrillo.negociacao.core.dataprovider.tipo.DataProviderInterface;
import com.cyrillo.negociacao.core.dataprovider.tipo.IdentificacaoNegocioDtoInterface;
import com.cyrillo.negociacao.core.tipobasico.UtilitarioInterface;
import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;

public class IdentificacaoNegocioDto implements IdentificacaoNegocioDtoInterface {
    @Expose(serialize = true, deserialize = true)
    private String identificadorNegocio;
    @Expose(serialize = true, deserialize = true)
    private String corretora;
    @Expose(serialize = true, deserialize = true)
    private String identificacaoClienteNegocio;
    @Expose(serialize = true, deserialize = true)
    private LocalDateTime dataNegocio;
    @Expose(serialize = true, deserialize = true)
    private LocalDateTime dataLiquidacao;
    @Expose(serialize = false, deserialize = false)
    UtilitarioInterface meuUtilitario;


    public IdentificacaoNegocioDto(DataProviderInterface data, String identificadorNegocio, String corretora, String identificacaoClienteNegocio, long segundosDataNegociacao, int nanosDataNegociacao, long segundosDataLiquidacao, int nanosDataLiquidacao) {
        meuUtilitario = data.getUtilitario();
        this.identificadorNegocio = identificadorNegocio;
        this.corretora = corretora;
        this.identificacaoClienteNegocio = identificacaoClienteNegocio;
        this.dataNegocio = dataNegocio;
        this.dataLiquidacao = dataLiquidacao;
        this.dataNegocio = meuUtilitario.converterGoogleProtobufTimeStampParaLocalDateTime(segundosDataNegociacao,nanosDataNegociacao);
        this.dataLiquidacao = meuUtilitario.converterGoogleProtobufTimeStampParaLocalDateTime(segundosDataLiquidacao, nanosDataLiquidacao);
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
        return meuUtilitario.converterObjetoParaJson(this);
    }
}
