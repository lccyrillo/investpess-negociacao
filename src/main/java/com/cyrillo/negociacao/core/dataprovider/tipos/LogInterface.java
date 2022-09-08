package com.cyrillo.negociacao.core.dataprovider.tipos;


import com.cyrillo.negociacao.core.dataprovider.excecao.ComunicacaoLogDataProvExcecao;
import com.cyrillo.negociacao.core.dataprovider.tipos.DataProviderInterface;

public interface LogInterface {
    public void logError(String flowid, String sessionId, String mensagem);
    public void logInfo(String flowid, String sessionId, String mensagem);
    void healthCheck(DataProviderInterface data) throws ComunicacaoLogDataProvExcecao;

}
