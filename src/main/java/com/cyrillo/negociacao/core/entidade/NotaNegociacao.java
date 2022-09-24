package com.cyrillo.negociacao.core.entidade;

import com.cyrillo.negociacao.core.dataprovider.excecao.ComunicacaoRepoDataProvExcecao;
import com.cyrillo.negociacao.core.dataprovider.tipo.AtivoDtoInterface;
import com.cyrillo.negociacao.core.dataprovider.tipo.DataProviderInterface;
import com.cyrillo.negociacao.core.entidade.excecao.ValoresFinanceirosNaoConferemEntidadeExcecao;
import com.cyrillo.negociacao.core.tipobasico.UtilitarioInterface;
import com.cyrillo.negociacao.core.usecase.excecao.ValoresFinanceirosNaoConferemUseCaseExcecao;
import com.google.gson.annotations.Expose;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NotaNegociacao {
    @Expose(serialize = true, deserialize = true)
    private String identificadorNegocio;
    @Expose(serialize = true, deserialize = true)
    private String corretora;
    @Expose(serialize = true, deserialize = true)
    private String identificacaoClienteNegocio;
    @Expose(serialize = true, deserialize = true)
    private LocalDate dataNegocio;
    @Expose(serialize = true, deserialize = true)
    private LocalDate dataLiquidacao;
    @Expose(serialize = false, deserialize = false)
    private UtilitarioInterface utilitario;

    @Expose(serialize = true, deserialize = true)
    private Double valorComprasAVista;
    @Expose(serialize = true, deserialize = true)
    private Double valorVendasAVista;
    @Expose(serialize = true, deserialize = true)
    private Double valorTaxaLiquidacao;
    @Expose(serialize = true, deserialize = true)
    private Double valorEmolumentos;
    @Expose(serialize = true, deserialize = true)
    private Double valorCorretagem;
    @Expose(serialize = true, deserialize = true)
    private Double valorIss;
    @Expose(serialize = true, deserialize = true)
    private Double valorLiquidoConta;
    @Expose(serialize = true, deserialize = true)
    private List<MovimentoAtivo> listaMovimentoAtivo = new ArrayList<>();


    public NotaNegociacao(UtilitarioInterface utilitario, String identificadorNegocio, String corretora, String identificacaoClienteNegocio, LocalDateTime dataNegocio, LocalDateTime dataLiquidacao) {
        this.utilitario = utilitario;
        this.identificadorNegocio = identificadorNegocio;
        this.corretora = corretora;
        this.identificacaoClienteNegocio = identificacaoClienteNegocio;
        this.dataNegocio = dataNegocio.toLocalDate();
        this.dataLiquidacao = dataLiquidacao.toLocalDate();
    }

    public String toString() {
        String dataNegocioString = utilitario.converterLocalDateparaStringData(this.dataNegocio);
        String dataLiquidacaoString = utilitario.converterLocalDateparaStringData(this.dataLiquidacao);
        return "Identificador Negócio: " + this.identificadorNegocio + " | Corretora: " + this.corretora + " | Cliente: " + this.identificacaoClienteNegocio
                + " | Data Negócio: " + dataNegocioString + " | Data Liquidação: " + dataLiquidacaoString;
    }

    public String toJson() {
        return utilitario.converterObjetoParaJson(this);
    }

    public String getIdentificadorNegocio() {
        return identificadorNegocio;
    }

    public String getCorretora() {
        return corretora;
    }

    public String getIdentificacaoClienteNegocio() {
        return identificacaoClienteNegocio;
    }

    public LocalDate getDataNegocio() {
        return dataNegocio;
    }

    public LocalDate getDataLiquidacao() {
        return dataLiquidacao;
    }

    public void atualizaValoresFinanceiros(Double valorComprasAVista, Double valorVendasAVista, Double valorTaxaLiquidacao, Double valorEmolumentos, Double valorCorretagem, Double valorIss, Double valorLiquidoConta) {
        this.valorComprasAVista=valorComprasAVista;
        this.valorVendasAVista=valorVendasAVista;
        this.valorTaxaLiquidacao=valorTaxaLiquidacao;
        this.valorEmolumentos=valorEmolumentos;
        this.valorCorretagem=valorCorretagem;
        this.valorIss=valorIss;
        this.valorLiquidoConta=valorLiquidoConta;

    }

    public void validarValoresFinanceirosComparadosComValorLiquidoConta() throws ValoresFinanceirosNaoConferemEntidadeExcecao {
        Double valorConferenciaLiquidoConta = (valorComprasAVista + valorTaxaLiquidacao + valorEmolumentos + valorCorretagem
                + valorIss - valorVendasAVista) * -1;

        // log.logInfo(flowId,sessionId,"valorConferenciaLiquidoConta: " + valorConferenciaLiquidoConta.toString());
        // log.logInfo(flowId,sessionId,"valorLiquidoConta: " + valorLiquidoConta.toString());


        // Comparação de valores double em java.
        double epsilon = 0.0000001d;
        if (Math.abs(valorConferenciaLiquidoConta - valorLiquidoConta) >= epsilon) {
            throw new ValoresFinanceirosNaoConferemEntidadeExcecao("Valor de conferência de líquido para conta não confere com valor informado!");
        }
    }


    public void adicionaMovimentoAtivo(String sigla,int tipoNegocio,double quantidadeMovimento,double precoMovimentoAtivo, double custosTotaisRelativosNota, double valorTotalComprasEVendas ){
        MovimentoAtivo movimentoAtivo = new MovimentoAtivo(sigla,tipoNegocio,quantidadeMovimento,precoMovimentoAtivo, custosTotaisRelativosNota, valorTotalComprasEVendas);
        this.listaMovimentoAtivo.add(movimentoAtivo);
    }

//    private void adicionaMovimentoNaListaParaGarantirUnicidadeAtivo(MovimentoAtivo movimentoAtivo){
//        // Preciso ver se o movimento novo já existe na lista atual de ativos
//        String siglaAtivoMovimento = movimentoAtivo.getSigla();
//        Optional<MovimentoAtivo> movimentoConsolidadoAtivo = this.consultarPorSigla(siglaAtivoMovimento);
//        if (movimentoConsolidadoAtivo.isPresent()) {
//            // já existe um movimento ativo e é necessário consolidar
//            movimentoConsolidadoAtivo.get().adicionarMovimentoMesmoAtivo(movimentoAtivo);
//        }
//        else {
//            // posso adicionar na lista diretamente
//            this.listaMovimentoAtivo.add(movimentoAtivo);
//        }
//    }

    public Optional<MovimentoAtivo> consultarPorSigla(String siglaAtivo) {
        return this.listaMovimentoAtivo.stream()
                .filter(a -> a.getSigla().equals(siglaAtivo))
                .findFirst();
    }




}
