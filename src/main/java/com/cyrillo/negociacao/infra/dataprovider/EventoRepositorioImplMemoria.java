package com.cyrillo.negociacao.infra.dataprovider;

import com.cyrillo.negociacao.core.dataprovider.tipo.DataProviderInterface;
import com.cyrillo.negociacao.core.dataprovider.tipo.EventoInterface;
import com.cyrillo.negociacao.core.dataprovider.tipo.LogInterface;
import com.cyrillo.negociacao.core.dataprovider.tipo.EventoRepositorioInterface;

public class EventoRepositorioImplMemoria implements EventoRepositorioInterface {
    @Override
    public void notificarEvento(DataProviderInterface data, EventoInterface eventoInterface) {
        String sessionId = String.valueOf(data.getSessionId());
        String flowId = data.getFlowId();
        LogInterface log = data.getLoggingInterface();
        log.logInfo(flowId, sessionId,eventoInterface.getMensagemEvento());
    }
}
