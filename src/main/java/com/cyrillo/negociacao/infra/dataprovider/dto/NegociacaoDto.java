package com.cyrillo.negociacao.infra.dataprovider.dto;

import com.cyrillo.negociacao.core.dataprovider.tipos.*;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class NegociacaoDto implements NegociacaoDtoInterface {


    @Expose(serialize = true, deserialize = true)
    private IdentificacaoNegocioDto identificacaoNegocioDto;
    @Expose(serialize = true, deserialize = true)
    private ValoresFinanceirosNegocioDto valoresFinanceirosNegocioDto;
    @Expose(serialize = true, deserialize = true)
    private List<AtivoNegociadoDtoInterface> listaAtivoNegociado = new ArrayList<>();


    @Expose(serialize = false, deserialize = false)
    private UtilitarioInterface meuUtilitario;


    public NegociacaoDto(DataProviderInterface data,IdentificacaoNegocioDto identificacaoNegocioDto, ValoresFinanceirosNegocioDto valoresFinanceirosNegocioDto){
        meuUtilitario = data.getUtilitario();
        this.identificacaoNegocioDto = identificacaoNegocioDto;
        this.valoresFinanceirosNegocioDto = valoresFinanceirosNegocioDto;
    }

    @Override
    public IdentificacaoNegocioDtoInterface getIdentificacaoNegocioDtoInterface() {
        return this.identificacaoNegocioDto;
    }

    @Override
    public ValoresFinanceirosNegocioDto getValoresFinanceirosNegocioDto() {
        return valoresFinanceirosNegocioDto;
    }

    @Override
    public String toString(){
        return meuUtilitario.converterObjetoParaJson(this);
    }

    @Override
    public List<AtivoNegociadoDtoInterface> listarTodosAtivos() {
        return this.listaAtivoNegociado;
    }

    public void adicionarAtivoNegociado(AtivoNegociadoDto ativoNegociadoDto) {
        this.listaAtivoNegociado.add(ativoNegociadoDto);
    }
}
