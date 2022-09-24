package com.cyrillo.negociacao.infra.dataprovider;

import com.cyrillo.negociacao.core.dataprovider.excecao.ComunicacaoRepoDataProvExcecao;
import com.cyrillo.negociacao.core.dataprovider.tipo.DataProviderInterface;
import com.cyrillo.negociacao.core.dataprovider.tipo.LogInterface;
import com.cyrillo.negociacao.core.dataprovider.tipo.NotaNegociacaoRepositorioInterface;

public class NotaNegociacaoRepositorioImplMemoria implements NotaNegociacaoRepositorioInterface {
    @Override
    public boolean consultarNotaNegociacao(DataProviderInterface data, String identificadorNegocio, String corretoraNegocio, String identificacaoClienteNegocio) throws ComunicacaoRepoDataProvExcecao {
        String sessionId = String.valueOf(data.getSessionId());
        String flowId = data.getFlowId();
        LogInterface log = data.getLoggingInterface();
        log.logInfo(flowId, sessionId,corretoraNegocio);

        if (corretoraNegocio.equals("itaucorretora")) {
            return true;
        }
        else {
            return false;
        }
    }
}
