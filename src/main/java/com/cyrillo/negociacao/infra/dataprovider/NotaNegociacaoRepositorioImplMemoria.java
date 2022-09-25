package com.cyrillo.negociacao.infra.dataprovider;

import com.cyrillo.negociacao.core.dataprovider.excecao.ComunicacaoRepoDataProvExcecao;
import com.cyrillo.negociacao.core.dataprovider.tipo.*;
import com.cyrillo.negociacao.infra.dataprovider.dto.AtivoNegociadoDto;
import com.cyrillo.negociacao.infra.dataprovider.dto.IdentificacaoNegocioDto;
import com.cyrillo.negociacao.infra.dataprovider.dto.NegociacaoDto;
import com.cyrillo.negociacao.infra.dataprovider.dto.ValoresFinanceirosNegocioDto;
import com.cyrillo.negociacao.infra.entrypoint.servicogrpc.negociacaoproto.AtivoNegociado;
import com.cyrillo.negociacao.infra.entrypoint.servicogrpc.negociacaoproto.IdentificacaoNegocio;
import com.cyrillo.negociacao.infra.entrypoint.servicogrpc.negociacaoproto.RegistraNegociacaoRequest;
import com.cyrillo.negociacao.infra.entrypoint.servicogrpc.negociacaoproto.ValoresFinanceirosNegocio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class NotaNegociacaoRepositorioImplMemoria implements NotaNegociacaoRepositorioInterface {

    private List<NegociacaoDtoInterface> listaNegociacao = new ArrayList<>();

    @Override
    public boolean consultarNotaNegociacao(DataProviderInterface data, String identificadorNegocio, String corretoraNegocio, String identificacaoClienteNegocio, LocalDate dataNegocio) throws ComunicacaoRepoDataProvExcecao {
        String sessionId = String.valueOf(data.getSessionId());
        String flowId = data.getFlowId();
        LogInterface log = data.getLoggingInterface();
        log.logInfo(flowId, sessionId,corretoraNegocio);

        if (this.listaNegociacao.stream()
            .filter( ((Predicate<NegociacaoDtoInterface>) a -> a.getIdentificacaoNegocioDtoInterface().getIdentificadorNegocio().equals(identificadorNegocio))
                    .and(a -> a.getIdentificacaoNegocioDtoInterface().getCorretora().equals(corretoraNegocio))
                    .and(a -> a.getIdentificacaoNegocioDtoInterface().getIdentificacaoClienteNegocio().equals(identificacaoClienteNegocio)) )
                    .findFirst().isPresent()) {
            return true;
        }
        else {
            return false;
        }
    }



    @Override
    public int armazenarNotaNegociacao(DataProviderInterface data, NotaNegociacaoInterface notaNegociacaoInterface) throws ComunicacaoRepoDataProvExcecao {
        NegociacaoDto negociacaoDto = geraObjetoDtoComObjetoNegociacaoInterface(data,notaNegociacaoInterface);
        this.listaNegociacao.add(negociacaoDto);
        return listaNegociacao.indexOf(negociacaoDto)+100;

    }

    private NegociacaoDto geraObjetoDtoComObjetoNegociacaoInterface(DataProviderInterface data, NotaNegociacaoInterface notaNegociacaoInterface){
        String identificadorNegocio = notaNegociacaoInterface.getIdentificadorNegocio();
        String corretora = notaNegociacaoInterface.getCorretora();
        String identificacaoClienteNegocio = notaNegociacaoInterface.getIdentificacaoClienteNegocio();

        LocalDate dataNegocio = notaNegociacaoInterface.getDataNegocio();
        LocalDate dataLiquidacao = notaNegociacaoInterface.getDataLiquidacao();
        IdentificacaoNegocioDto identificacaoNegocioDto = new IdentificacaoNegocioDto(data,identificadorNegocio, corretora,identificacaoClienteNegocio, dataNegocio,dataLiquidacao);

        ValoresFinanceirosNegocioDto valoresFinanceirosNegocioDto = new ValoresFinanceirosNegocioDto(data,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0);
        NegociacaoDto negociacaoDto = new NegociacaoDto(data,identificacaoNegocioDto, valoresFinanceirosNegocioDto);
        return negociacaoDto;
    }
}
