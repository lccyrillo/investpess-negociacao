package com.cyrillo.negociacao.core.dataprovider.tipo;

import java.time.LocalDateTime;

public interface IdentificacaoNegocioDtoInterface {
    public String getIdentificadorNegocio();
    public String getCorretora();
    public String getIdentificacaoClienteNegocio();
    public LocalDateTime getDataNegocio();
    public LocalDateTime getDataLiquidacao();
    public String toString();
}
