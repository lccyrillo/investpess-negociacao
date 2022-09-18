package com.cyrillo.negociacao.core.dataprovider.tipos;

import com.cyrillo.negociacao.core.dataprovider.excecao.ComunicacaoRepoDataProvExcecao;

public interface NotaNegociacaoRepositorioInterface {
    boolean consultarNotaNegociacao(DataProviderInterface data, String identificadorNegocio, String corretoraNegocio, String identificacaoClienteNegocio) throws ComunicacaoRepoDataProvExcecao;
}