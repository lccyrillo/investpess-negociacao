package com.cyrillo.negociacao.infra.dataprovider.dto;

import com.cyrillo.negociacao.core.dataprovider.tipos.*;
import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NegociacaoDto implements NegociacaoDtoInterface {
    @Expose(serialize = true, deserialize = true)
    private IdentificacaoNegocioDto identificacaoNegocioDto;
    @Expose(serialize = true, deserialize = true)
    private List<AtivoNegociadoDto> listaAtivoNegociado = new ArrayList<>();
    @Expose(serialize = false, deserialize = false)
    UtilitarioInterface meuUtilitario;

    public NegociacaoDto(DataProviderInterface data,String identificadorNegocio, String corretora, String identificacaoClienteNegocio,  long segundosDataNegociacao, int nanosDataNegociacao, long segundosDataLiquidacao, int nanosDataLiquidacao){
        meuUtilitario = data.getUtilitario();
        LocalDateTime dataNegocio = meuUtilitario.converterGoogleProtobufTimeStampParaLocalDateTime(segundosDataNegociacao,nanosDataNegociacao);
        LocalDateTime dataLiquidacao = meuUtilitario.converterGoogleProtobufTimeStampParaLocalDateTime(segundosDataLiquidacao, nanosDataLiquidacao);
        identificacaoNegocioDto = new IdentificacaoNegocioDto(identificadorNegocio,corretora,identificacaoClienteNegocio,dataNegocio, dataLiquidacao);
        //data.getLoggingInterface().logInfo("1","2",data.converterObjetoParaJson(this));
    }

    @Override
    public IdentificacaoNegocioDtoInterface getIdentificacaoNegocioDtoInterface() {
        return this.identificacaoNegocioDto;
    }

    @Override
    public List<AtivoNegociadoDtoInterface> listarTodosAtivos() {
        return null;
    }

    @Override
    public String toString(){
        //return null;
        return meuUtilitario.converterObjetoParaJson(this);
    }
}
