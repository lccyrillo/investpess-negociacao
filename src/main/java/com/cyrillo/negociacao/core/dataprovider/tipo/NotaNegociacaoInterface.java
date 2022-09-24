package com.cyrillo.negociacao.core.dataprovider.tipo;


import java.time.LocalDate;

public interface NotaNegociacaoInterface {
    String getIdentificadorNegocio();
    String getCorretora();
    String getIdentificacaoClienteNegocio();
    LocalDate getDataNegocio();
    LocalDate getDataLiquidacao();
}
