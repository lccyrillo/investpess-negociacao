package com.cyrillo.negociacao.core.usecase.excecao;

public class ComunicacaoRepoUseCaseExcecao  extends Exception{
    public ComunicacaoRepoUseCaseExcecao(String msg) {
        super(msg);
    }
}