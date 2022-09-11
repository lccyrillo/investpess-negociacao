package com.cyrillo.negociacao.core.dataprovider.tipos;


public interface ValoresFinanceirosNegocioDtoInterface {
    public Double getValorVendasAVista();
    public Double getValorComprasAVista();
    public Double getValorTaxaLiquidacao();
    public Double getValorEmolumentos();
    public Double getValorCorretagem();
    public Double getValorIss();
    public Double getValorIrrf();
    public Double getValorLiquidoConta();
    public String toString();
}