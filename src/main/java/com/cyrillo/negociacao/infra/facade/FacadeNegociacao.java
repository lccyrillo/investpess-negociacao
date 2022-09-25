package com.cyrillo.negociacao.infra.facade;

import com.cyrillo.negociacao.core.dataprovider.tipo.DataProviderInterface;
import com.cyrillo.negociacao.core.dataprovider.tipo.LogInterface;
import com.cyrillo.negociacao.core.usecase.RegistrarNegociacao;
import com.cyrillo.negociacao.core.usecase.excecao.*;
import com.cyrillo.negociacao.infra.dataprovider.dto.NegociacaoDto;

public class FacadeNegociacao {
    public FacadeNegociacao(){
    }
    public void executarRegistrarNegocicacao(DataProviderInterface data, NegociacaoDto negociacaoDto) throws ComunicacaoRepoUseCaseExcecao, ParametrosInvalidosUseCaseExcecao, ValoresFinanceirosNaoConferemUseCaseExcecao, NotaNegociacaoExistenteUseCaseExcecao, AtivoNaoIdentificadoUseCaseExcecao, AtivoNaoEAcaoUseCaseExcecao {

        LogInterface log = data.getLoggingInterface();
        String flowId = data.getFlowId();
        String sessionId = data.getSessionId();
        log.logInfo(flowId,sessionId,"Iniciando Facade Registrar Negociação");
        //log.logInfo(flowId,sessionId,negociacaoDto.toString());
        new RegistrarNegociacao().executar(data,flowId,negociacaoDto);
    }
}
