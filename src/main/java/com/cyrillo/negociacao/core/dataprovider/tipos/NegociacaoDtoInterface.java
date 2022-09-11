package com.cyrillo.negociacao.core.dataprovider.tipos;

import com.cyrillo.negociacao.infra.dataprovider.dto.ValoresFinanceirosNegocioDto;

import java.util.List;

public interface NegociacaoDtoInterface {
    public IdentificacaoNegocioDtoInterface getIdentificacaoNegocioDtoInterface();
    public List<AtivoNegociadoDtoInterface> listarTodosAtivos();
    public ValoresFinanceirosNegocioDtoInterface getValoresFinanceirosNegocioDtoInterface();
    public String toString();
}