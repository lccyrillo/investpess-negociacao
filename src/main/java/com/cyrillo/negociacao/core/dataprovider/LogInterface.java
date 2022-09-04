package com.cyrillo.negociacao.core.dataprovider;


import com.cyrillo.negociacao.core.dataprovider.excecao.ComunicacaoLogDataProvExcecao;

public interface LogInterface {
    public void logError(String flowid, String sessionId, String mensagem);
    public void logInfo(String flowid, String sessionId, String mensagem);
    void healthCheck(DataProviderInterface data) throws ComunicacaoLogDataProvExcecao;

}
