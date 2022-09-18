package com.cyrillo.negociacao.core.usecase.excecao;

public class NotaNegociacaoExistenteUseCaseExcecao extends Exception{
    public NotaNegociacaoExistenteUseCaseExcecao(String msg){
        super(msg);
    }
}
