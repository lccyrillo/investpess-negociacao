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
    private double precoMedioMovimentoComCustos;

    @Expose(serialize = true, deserialize = true)
    private double precoMedioMovimentoSemCustos;

    @Expose(serialize = true, deserialize = true)
    private double custosRelativosMovimento;

    @Expose(serialize = true, deserialize = true)
    private LocalDateTime dataNegocio;



}
