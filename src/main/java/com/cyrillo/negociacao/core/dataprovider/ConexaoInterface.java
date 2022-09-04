package com.cyrillo.negociacao.core.dataprovider;
import com.cyrillo.negociacao.core.dataprovider.excecao.FalhaObterConexaoRepoDataProvExcecao;

import java.sql.Connection;
// Interface para apoiar o data provider
public interface ConexaoInterface {
    public Connection getConnection() throws FalhaObterConexaoRepoDataProvExcecao;
    public void setConnectionAtiva(boolean conexaoAtiva);
}
