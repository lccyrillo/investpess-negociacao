package com.cyrillo.negociacao.infra.facade;

import com.cyrillo.negociacao.core.dataprovider.tipos.DataProviderInterface;
import com.cyrillo.negociacao.core.dataprovider.tipos.IdentificacaoNegocioDtoInterface;
import com.cyrillo.negociacao.core.dataprovider.tipos.LogInterface;
import com.cyrillo.negociacao.core.usecase.RegistrarNegociacao;
import com.cyrillo.negociacao.core.usecase.excecao.ComunicacaoRepoUseCaseExcecao;
import com.cyrillo.negociacao.core.usecase.excecao.ParametrosInvalidosUseCaseExcecao;

public class FacadeNegociacao {
    public FacadeNegociacao(){
    }
    public void executarRegistrarNegocicacao(DataProviderInterface data, IdentificacaoNegocioDtoInterface identificacaoNegocio) throws ComunicacaoRepoUseCaseExcecao, ParametrosInvalidosUseCaseExcecao {

        LogInterface log = data.getLoggingInterface();
        String flowId = data.getFlowId();
        String sessionId = data.getSessionId();
        log.logInfo(flowId,sessionId,"Iniciando Facade Registrar Negociação");
        log.logInfo(flowId,sessionId,identificacaoNegocio.toString());
        new RegistrarNegociacao().executar(data,"flowid");
    }
}
