package com.cyrillo.negociacao.core.dataprovider.tipos;


import com.cyrillo.negociacao.core.dataprovider.excecao.ComunicacaoRepoDataProvExcecao;
import com.cyrillo.negociacao.core.usecase.excecao.ParametrosInvalidosUseCaseExcecao;

import java.util.List;

// Acerto exceção
public interface AtivoRepositorioInterface {
    void incluir(DataProviderInterface data, AtivoDtoInterface ativoObjeto) throws ComunicacaoRepoDataProvExcecao;
    boolean consultarPorSigla(DataProviderInterface data, String siglaAtivo) throws ComunicacaoRepoDataProvExcecao;
    List<AtivoDtoInterface> listarTodosAtivos(DataProviderInterface data);
    List<AtivoDtoInterface> listarAtivosPorTipo(DataProviderInterface data, int tipoAtivo) throws ComunicacaoRepoDataProvExcecao, ParametrosInvalidosUseCaseExcecao;
    void healthCheck(DataProviderInterface data) throws ComunicacaoRepoDataProvExcecao;
}
