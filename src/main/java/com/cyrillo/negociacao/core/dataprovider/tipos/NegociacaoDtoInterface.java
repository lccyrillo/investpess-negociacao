package com.cyrillo.negociacao.core.dataprovider.tipos;

import java.util.List;

public interface NegociacaoDtoInterface {
    public IdentificacaoNegocioDtoInterface getIdentificacaoNegocioDtoInterface();
    public List<AtivoNegociadoDtoInterface> listarTodosAtivos();
    public String toString();
}