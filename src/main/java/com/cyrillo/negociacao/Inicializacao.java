package com.cyrillo.negociacao;

import com.cyrillo.negociacao.infra.config.Aplicacao;

public class Inicializacao {

    public static void main(String[] args)  {
        Aplicacao.getInstance().inicializaAplicacao();
    }
}
