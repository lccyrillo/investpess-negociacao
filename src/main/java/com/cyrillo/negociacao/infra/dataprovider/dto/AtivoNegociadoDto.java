package com.cyrillo.negociacao.infra.dataprovider.dto;


import com.cyrillo.negociacao.core.dataprovider.tipos.AtivoNegociadoDtoInterface;
import com.google.gson.annotations.Expose;

public class AtivoNegociadoDto implements AtivoNegociadoDtoInterface {
    @Expose(serialize = true, deserialize = true)
    private String sigla;
    @Expose(serialize = true, deserialize = true)
    private int tipoNegocio;
    @Expose(serialize = true, deserialize = true)
    private double quantidadeNegociada;
    @Expose(serialize = true, deserialize = true)
    private double precoNegociado;


    public AtivoNegociadoDto(String sigla,int tipoNegocio,double quantidadeNegociada,double precoNegociado) {
        this.sigla = sigla;
        this.tipoNegocio = tipoNegocio;
        this.quantidadeNegociada = quantidadeNegociada;
        this.precoNegociado = precoNegociado;

    }

    /*public AtivoObjeto builder() throws ParametroCNPJInvalidoEntExcecao, ParametroTipoInvalidoEntExcecao {
        return new AtivoObjeto(sigla,nomeAtivo,descricaoCNPJAtivo,new TipoAtivo(tipoAtivo));
    }
    */

    public String getSigla() {
        return sigla;
    }
    public int getTipoNegocio() {
        return tipoNegocio;
    }
    public double getQuantidadeNegociada() {
        return quantidadeNegociada;
    }
    public double getPrecoNegociado() {
        return precoNegociado;
    }
    public double getValorNegocio() {
        return quantidadeNegociada * precoNegociado;
    }

}
