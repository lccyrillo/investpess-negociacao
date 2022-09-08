package com.cyrillo.negociacao.core.dataprovider.dto;


import com.cyrillo.negociacao.core.dataprovider.tipos.AtivoNegociadoDtoInterface;

public class AtivoNegociadoDto implements AtivoNegociadoDtoInterface {
    private String sigla;
    private int tipoNegocio;
    private double quantidadeNegociada;
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
