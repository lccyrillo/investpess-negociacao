package com.cyrillo.negociacao.core.usecase.excecao;

public class AtivoNaoIdentificadoUseCaseExcecao  extends Exception{
    public AtivoNaoIdentificadoUseCaseExcecao(String msg) {
        super(msg);
    }
}
