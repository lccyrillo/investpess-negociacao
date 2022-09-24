package com.cyrillo.negociacao.core.dataprovider.tipo;


import com.cyrillo.negociacao.core.dataprovider.excecao.ComunicacaoRepoDataProvExcecao;
import com.cyrillo.negociacao.core.usecase.excecao.ParametrosInvalidosUseCaseExcecao;

import java.util.List;
import java.util.Optional;

// Acerto exceção
public interface AtivoRepositorioInterface {
    void incluir(DataProviderInterface data, AtivoDtoInterface ativoObjeto) throws ComunicacaoRepoDataProvExcecao;
    Optional<AtivoDtoInterface> consultarPorSigla(DataProviderInterface data, String siglaAtivo) throws ComunicacaoRepoDataProvExcecao;
    List<AtivoDtoInterface> listarTodosAtivos(DataProviderInterface data);
    List<AtivoDtoInterface> listarAtivosPorTipo(DataProviderInterface data, int tipoAtivo) throws ComunicacaoRepoDataProvExcecao, ParametrosInvalidosUseCaseExcecao;
    void healthCheck(DataProviderInterface data) throws ComunicacaoRepoDataProvExcecao;
}
