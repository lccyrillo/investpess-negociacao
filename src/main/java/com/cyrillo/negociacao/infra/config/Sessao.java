package com.cyrillo.negociacao.infra.config;

import com.cyrillo.negociacao.core.dataprovider.tipos.AtivoRepositorioInterface;
import com.cyrillo.negociacao.core.dataprovider.tipos.DataProviderInterface;
import com.cyrillo.negociacao.core.dataprovider.tipos.LogInterface;

import java.time.LocalDateTime;
import java.util.UUID;

public class Sessao implements DataProviderInterface {

    private UUID sessionId;
    private LogInterface log;
    private String flowId;

    public Sessao(){
        this.sessionId = UUID.randomUUID();
        this.log = Aplicacao.getInstance().gerarNovoObjetoLog();
    }


    public String getSessionId() {
        return String.valueOf(sessionId);
    }
    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowId() {
        return flowId;
    }

    @Override
    public boolean healthCheckOk(DataProviderInterface data) {
        return Aplicacao.getInstance().healthCheckOk(data);
    }

    @Override
    public DataProviderInterface geraSessao() {
        return this;
    }


    public LogInterface getLoggingInterface() {
        return this.log;
    }


    public AtivoRepositorioInterface getAtivoRepositorio() {
        return Aplicacao.getInstance().getAtivoRepositorio();
    }

    public void setAtivoRepositorio(AtivoRepositorioInterface ativoRepositorio) {
        Aplicacao.getInstance().setAtivoRepositorio(ativoRepositorio);
    }

    public String converterGoogleProtobufTimeStampParaStringData(long seconds,int nanos){
        return Aplicacao.getInstance().converterGoogleProtobufTimeStampParaStringData(seconds, nanos);
    }

    public LocalDateTime converterGoogleProtobufTimeStampParaLocalDateTime(long seconds, int nanos){
        return Aplicacao.getInstance().converterGoogleProtobufTimeStampParaLocalDateTime(seconds, nanos);
    }

}
