package com.cyrillo.negociacao.core.dataprovider;

import java.util.UUID;

// Acerto para apontar para conexao interface
public interface DataProviderInterface {
    public LogInterface getLoggingInterface();
    public UUID getUniqueKey();
    public boolean healthCheckOk(DataProviderInterface data);
    public DataProviderInterface geraSessao();
}
