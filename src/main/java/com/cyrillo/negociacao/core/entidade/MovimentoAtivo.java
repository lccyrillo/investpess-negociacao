package com.cyrillo.negociacao.core.entidade;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;

import static java.lang.Math.abs;

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


    public String getSigla() {
        return sigla;
    }

    public MovimentoAtivo(String sigla, int tipoNegocio, double quantidadeMovimento, double precoMovimentoAtivo, double custosTotaisRelativosNota, double valorTotalComprasEVendas ){
        this.sigla = sigla;
        this.tipoNegocio = tipoNegocio; // 1 compra, 2 venda
        this.quantidadeMovimento = quantidadeMovimento;
        this.precoMovimentoAtivo = precoMovimentoAtivo;
        Double valorAtivoSemCustos = quantidadeMovimento*precoMovimentoAtivo;
        Double fracaoCustoMovimentoPeloCustoTotal = valorAtivoSemCustos /  valorTotalComprasEVendas;
        this.custosRelativosMovimento = fracaoCustoMovimentoPeloCustoTotal * custosTotaisRelativosNota;
        this.precoMedioMovimentoComCustos = (valorAtivoSemCustos + custosRelativosMovimento)/quantidadeMovimento;
    }

//    public void adicionarMovimentoMesmoAtivo(MovimentoAtivo movimentoAdicionado ){
//        double quantidadeNova = this.quantidadeMovimento*this.getSinalTipoNegocio() +movimentoAdicionado.quantidadeMovimento*movimentoAdicionado.getSinalTipoNegocio();
//        int tipoNegocioNovo;
//        if (quantidadeNova > 0) {
//            tipoNegocioNovo = 2;
//        }
//        else {
//            tipoNegocioNovo = 1;
//        }
//        double valorTotalNovo = this.getValorTotalMovimento() + movimentoAdicionado.getValorTotalMovimento();
//        double valorTotalComCustosNovo = this.getValorTotalMovimentoComCustos()+movimentoAdicionado.getValorTotalMovimentoComCustos();
//
//        double epsilon = 0.0000001d;
//        double precoMovimentoAtivoNovo;
//        double precoMedioMovimentoComCustosNovo;
//        if (Math.abs(quantidadeNova) >= epsilon) {
//            precoMovimentoAtivoNovo = abs(valorTotalNovo /quantidadeNova);
//            precoMedioMovimentoComCustosNovo = abs(valorTotalComCustosNovo /quantidadeNova);
//        } else {
//            precoMovimentoAtivoNovo = 0.0;
//            precoMedioMovimentoComCustosNovo = 0.0;
//        }
//
//        double custosRelativosMovimentoNovo = this.custosRelativosMovimento + movimentoAdicionado.getCustosRelativosMovimento();
//        this.tipoNegocio = tipoNegocioNovo;
//        this.quantidadeMovimento = abs(quantidadeNova);
//        this.precoMovimentoAtivo = precoMovimentoAtivoNovo;
//        this.custosRelativosMovimento = custosRelativosMovimentoNovo;
//        this.precoMedioMovimentoComCustos = precoMedioMovimentoComCustosNovo;
//    }

    private double getValorTotalMovimento(){
        return quantidadeMovimento*precoMovimentoAtivo*getSinalTipoNegocio();
    }

    private double getValorTotalMovimentoComCustos(){
        return quantidadeMovimento*precoMedioMovimentoComCustos*getSinalTipoNegocio();
    }



    private int getSinalTipoNegocio(){
        if (tipoNegocio == 1) {
            return -1;
        }
        else {
            return 1;
        }
    }

    public double getCustosRelativosMovimento() {
        return custosRelativosMovimento;
    }
}
