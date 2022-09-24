package com.cyrillo.negociacao.core.entidade;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;

public class MovimentoAtivo {

    @Expose(serialize = true, deserialize = true)
    private String sigla;

    @Expose(serialize = true, deserialize = true)
    private int tipoNegocio; // 1 - comprado | 2 - vendido

    @Expose(serialize = true, deserialize = true)
    private double quantidadeMovimento;

    @Expose(serialize = true, deserialize = true)
    private double precoMovimentoAtivo;

    @Expose(serialize = true, deserialize = true)
    private double precoMedioMovimentoComCustos;

    @Expose(serialize = true, deserialize = true)
    private double custosRelativosMovimento;


    public MovimentoAtivo(String sigla,int tipoNegocio,double quantidadeMovimento,double precoMovimentoAtivo, double custosTotaisRelativosNota, double valorTotalComprasEVendas ){
        this.sigla = sigla;
        this.tipoNegocio = tipoNegocio; // 1 compra, 2 venda
        this.quantidadeMovimento = quantidadeMovimento;
        this.precoMovimentoAtivo = precoMovimentoAtivo;
        Double valorAtivoSemCustos = quantidadeMovimento*precoMovimentoAtivo;
        Double fracaoCustoMovimentoPeloCustoTotal = valorAtivoSemCustos /  valorTotalComprasEVendas;
        this.custosRelativosMovimento = fracaoCustoMovimentoPeloCustoTotal * custosTotaisRelativosNota;
        this.precoMedioMovimentoComCustos = (valorAtivoSemCustos + custosRelativosMovimento)/quantidadeMovimento;
    }
}
