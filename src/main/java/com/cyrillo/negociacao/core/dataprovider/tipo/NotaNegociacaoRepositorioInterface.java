package com.cyrillo.negociacao.core.dataprovider.tipo;

import com.cyrillo.negociacao.core.dataprovider.excecao.ComunicacaoRepoDataProvExcecao;
import com.cyrillo.negociacao.core.entidade.NotaNegociacao;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface NotaNegociacaoRepositorioInterface {
    boolean consultarNotaNegociacao(DataProviderInterface data, String identificadorNegocio, String corretoraNegocio, String identificacaoClienteNegocio, LocalDate dataNegocio) throws ComunicacaoRepoDataProvExcecao;
    int armazenarNotaNegociacao(DataProviderInterface data, NotaNegociacaoInterface notaNegociacaoInterface) throws ComunicacaoRepoDataProvExcecao;
}