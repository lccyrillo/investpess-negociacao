package com.cyrillo.negociacao.core.dataprovider.tipo;

import com.cyrillo.negociacao.core.tipobasico.UtilitarioInterface;

// Acerto para apontar para conexao interface
public interface DataProviderInterface {
    public LogInterface getLoggingInterface();
    public boolean healthCheckOk(DataProviderInterface data);
    public DataProviderInterface geraSessao();
    public AtivoRepositorioInterface getAtivoRepositorio();
    public String getSessionId();
    public void setFlowId(String flowId);
    public String getFlowId();
    public UtilitarioInterface getUtilitario();
    public NotaNegociacaoRepositorioInterface getNotaNegocicacaoRepositorio();
    public EventoRepositorioInterface getEventoRepositorioInterface();
}
