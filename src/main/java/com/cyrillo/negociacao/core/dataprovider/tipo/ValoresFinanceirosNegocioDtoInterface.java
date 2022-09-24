package com.cyrillo.negociacao.core.dataprovider.tipo;


public interface ValoresFinanceirosNegocioDtoInterface {
    public Double getValorVendasAVista();
    public Double getValorComprasAVista();
    public Double getValorTotalComprasEVendasAVista();
    public Double getCustoTotalNota();
    public Double getValorTaxaLiquidacao();
    public Double getValorEmolumentos();
    public Double getValorCorretagem();
    public Double getValorIss();
    public Double getValorIrrf();
    public Double getValorLiquidoConta();
    public String toString();

}