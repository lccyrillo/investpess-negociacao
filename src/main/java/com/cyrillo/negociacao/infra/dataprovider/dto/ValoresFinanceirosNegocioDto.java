package com.cyrillo.negociacao.infra.dataprovider.dto;

import com.cyrillo.negociacao.core.dataprovider.tipo.DataProviderInterface;
import com.cyrillo.negociacao.core.tipobasico.UtilitarioInterface;
import com.cyrillo.negociacao.core.dataprovider.tipo.ValoresFinanceirosNegocioDtoInterface;
import com.google.gson.annotations.Expose;

public class ValoresFinanceirosNegocioDto implements ValoresFinanceirosNegocioDtoInterface {
    @Expose(serialize = true, deserialize = true)
    private Double valorVendasAVista;
    @Expose(serialize = true, deserialize = true)
    private Double valorComprasAVista;
    @Expose(serialize = true, deserialize = true)
    private Double valorTaxaLiquidacao;
    @Expose(serialize = true, deserialize = true)
    private Double valorEmolumentos;
    @Expose(serialize = true, deserialize = true)
    private Double valorCorretagem;
    @Expose(serialize = true, deserialize = true)
    private Double valorIss;
    @Expose(serialize = true, deserialize = true)
    private Double valorIrrf;
    @Expose(serialize = true, deserialize = true)
    private Double valorLiquidoConta;
    @Expose(serialize = false, deserialize = false)
    private UtilitarioInterface meuUtilitario;

    public ValoresFinanceirosNegocioDto(DataProviderInterface data, Double valorVendasAVista, Double valorComprasAVista, Double valorTaxaLiquidacao, Double valorEmolumentos
            , Double valorCorretagem, Double valorIss, Double valorIrrf, Double valorLiquidoConta) {
        meuUtilitario = data.getUtilitario();
        this.valorVendasAVista = valorVendasAVista;
        this.valorComprasAVista = valorComprasAVista;
        this.valorTaxaLiquidacao = valorTaxaLiquidacao;
        this.valorEmolumentos = valorEmolumentos;
        this.valorCorretagem = valorCorretagem;
        this.valorIss = valorIss;
        this.valorIrrf = valorIrrf;
        this.valorLiquidoConta = valorLiquidoConta;
    }

    @Override
    public Double getValorVendasAVista() {
        return valorVendasAVista;
    }

    @Override
    public Double getValorComprasAVista() {
        return valorComprasAVista;
    }

    @Override
    public Double getValorTotalComprasEVendasAVista() {
        return valorComprasAVista +valorVendasAVista ;
    }

    @Override
    public Double getCustoTotalNota() {
        return valorTaxaLiquidacao +valorEmolumentos+valorCorretagem+valorIss ;
    }


    @Override
    public Double getValorTaxaLiquidacao() {
        return valorTaxaLiquidacao;
    }

    @Override
    public Double getValorEmolumentos() {
        return valorEmolumentos;
    }

    @Override
    public Double getValorCorretagem() {
        return valorCorretagem;
    }

    @Override
    public Double getValorIss() {
        return valorIss;
    }

    @Override
    public Double getValorIrrf() {
        return valorIrrf;
    }

    @Override
    public Double getValorLiquidoConta() {
        return valorLiquidoConta;
    }

    @Override
    public String toString() {
        return meuUtilitario.converterObjetoParaJson(this);
    }
}
