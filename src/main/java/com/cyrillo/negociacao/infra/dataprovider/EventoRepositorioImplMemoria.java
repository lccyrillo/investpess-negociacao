package com.cyrillo.negociacao.infra.dataprovider;

import com.cyrillo.negociacao.core.dataprovider.tipo.*;

public class EventoRepositorioImplMemoria implements EventoRepositorioInterface {
    @Override
    public void notificarEvento(DataProviderInterface data, EventoInterface eventoInterface, NotaNegociacaoInterface notaNegociacaoInterface) {
        String sessionId = String.valueOf(data.getSessionId());
        String flowId = data.getFlowId();
        LogInterface log = data.getLoggingInterface();
        log.logInfo(flowId, sessionId,eventoInterface.getMensagemEvento());
    }

}
