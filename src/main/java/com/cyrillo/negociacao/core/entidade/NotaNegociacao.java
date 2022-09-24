package com.cyrillo.negociacao.core.entidade;

import com.cyrillo.negociacao.core.tipobasico.UtilitarioInterface;
import com.google.gson.annotations.Expose;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NotaNegociacao {
    @Expose(serialize = true, deserialize = true)
    private String identificadorNegocio;
    @Expose(serialize = true, deserialize = true)
    private String corretora;
    @Expose(serialize = true, deserialize = true)
    private String identificacaoClienteNegocio;
    @Expose(serialize = true, deserialize = true)
    private LocalDate dataNegocio;
    @Expose(serialize = true, deserialize = true)
    private LocalDate dataLiquidacao;
    @Expose(serialize = false, deserialize = false)
    private UtilitarioInterface utilitario;

    public NotaNegociacao(UtilitarioInterface utilitario, String identificadorNegocio, String corretora, String identificacaoClienteNegocio, LocalDateTime dataNegocio, LocalDateTime dataLiquidacao) {
        this.utilitario = utilitario;
        this.identificadorNegocio = identificadorNegocio;
        this.corretora = corretora;
        this.identificacaoClienteNegocio = identificacaoClienteNegocio;
        this.dataNegocio = dataNegocio.toLocalDate();
        this.dataLiquidacao = dataLiquidacao.toLocalDate();
    }

    public String toString() {
        String dataNegocioString = utilitario.converterLocalDateparaStringData(this.dataNegocio);
        String dataLiquidacaoString = utilitario.converterLocalDateparaStringData(this.dataLiquidacao);
        return "Identificador Negócio: " + this.identificadorNegocio + " | Corretora: " + this.corretora + " | Cliente: " + this.identificacaoClienteNegocio
                + " | Data Negócio: " + dataNegocioString + " | Data Liquidação: " + dataLiquidacaoString;
    }

    public String toJson() {
        return utilitario.converterObjetoParaJson(this);
    }


}
