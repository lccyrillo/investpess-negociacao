package com.cyrillo.negociacao.core.dataprovider.tipo;

import java.util.List;

public interface NegociacaoDtoInterface {
    public IdentificacaoNegocioDtoInterface getIdentificacaoNegocioDtoInterface();
    public List<AtivoNegociadoDtoInterface> listarTodosAtivos();
    public ValoresFinanceirosNegocioDtoInterface getValoresFinanceirosNegocioDtoInterface();
    public String toString();
}