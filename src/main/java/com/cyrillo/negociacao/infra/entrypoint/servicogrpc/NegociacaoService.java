package com.cyrillo.negociacao.infra.entrypoint.servicogrpc;

import com.cyrillo.negociacao.core.usecase.excecao.*;
import com.cyrillo.negociacao.infra.dataprovider.dto.AtivoNegociadoDto;
import com.cyrillo.negociacao.infra.dataprovider.dto.IdentificacaoNegocioDto;
import com.cyrillo.negociacao.core.dataprovider.tipo.DataProviderInterface;
import com.cyrillo.negociacao.core.dataprovider.tipo.LogInterface;
import com.cyrillo.negociacao.infra.dataprovider.dto.NegociacaoDto;
import com.cyrillo.negociacao.infra.dataprovider.dto.ValoresFinanceirosNegocioDto;
import com.cyrillo.negociacao.infra.entrypoint.servicogrpc.negociacaoproto.*;
import com.cyrillo.negociacao.infra.facade.FacadeNegociacao;
import io.grpc.stub.StreamObserver;

import java.util.List;

public class NegociacaoService extends NegociacaoServiceGrpc.NegociacaoServiceImplBase {
    private DataProviderInterface data;

    public NegociacaoService(DataProviderInterface dataProviderInterface){
        this.data = dataProviderInterface;
    }

    @Override
    public void registraNegociacao(RegistraNegociacaoRequest request, StreamObserver<RegistraNegociacaoResponse> responseObserver) {
        String msgResultado;
        int codResultado;

        try {

            DataProviderInterface dataProvider = data.geraSessao();
            String sessionId = String.valueOf(dataProvider.getSessionId());
            LogInterface log = dataProvider.getLoggingInterface();

            // Captura dados da requisição
            String flowId = request.getFlowId();
            dataProvider.setFlowId(flowId);
            log.logInfo(flowId, sessionId,"Iniciando método GRPC para registro de negociação.");

            // Montagem do Objeto DTO para iniciar o caso de uso
            NegociacaoDto negociacaoDto = geraObjetoDtoComObjetoRequisicao(request);

            // executa o caso de uso
            new FacadeNegociacao().executarRegistrarNegocicacao(dataProvider,negociacaoDto);
            codResultado = 200;
            msgResultado = "Negociação registrada!";
        }
        catch (ValoresFinanceirosNaoConferemUseCaseExcecao e) {
            codResultado = 102;
            msgResultado = e.getMessage();
        }
        catch (AtivoNaoIdentificadoUseCaseExcecao e) {
            codResultado = 102;
            msgResultado = e.getMessage();
        }
        catch (AtivoNaoEAcaoUseCaseExcecao e) {
            codResultado = 102;
            msgResultado = e.getMessage();
        }
        catch (ComunicacaoRepoUseCaseExcecao e) {
            codResultado = 401;
            msgResultado = "Erro na comunicação com repositório de dados!";
        }
        catch (ParametrosInvalidosUseCaseExcecao e) {
            codResultado = 401;
            msgResultado = "Erro na persistência do Ativo no banco de dados!";
        }
        catch (NotaNegociacaoExistenteUseCaseExcecao e) {
            codResultado = 101;
            msgResultado = e.getMessage();
        }

        //Formata objeto de saída
        RegistraNegociacaoResponse response = RegistraNegociacaoResponse.newBuilder()
                .setResponseCode(codResultado)
                .setResponseMessage(msgResultado)
                .setChaveIdentificadoraNegociacao(1)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private NegociacaoDto geraObjetoDtoComObjetoRequisicao(RegistraNegociacaoRequest requisicao){
        IdentificacaoNegocio identificacaoNegocio = requisicao.getIdentificacaoNegocio();
        String identificadorNegocio = identificacaoNegocio.getIdentificadorNegocio();
        String corretora = identificacaoNegocio.getCorretoraNegocio();
        String identificacaoClienteNegocio = identificacaoNegocio.getIdentificacaoClienteNegocio();
        long segundosDataNegociacao =identificacaoNegocio.getDataNegocio().getSeconds();
        int nanosDataNegociacao = identificacaoNegocio.getDataNegocio().getNanos();
        long segundosDataLiquidacao =identificacaoNegocio.getDataLiquidacao().getSeconds();
        int nanosDataLiquidacao = identificacaoNegocio.getDataLiquidacao().getNanos();
        IdentificacaoNegocioDto identificacaoNegocioDto = new IdentificacaoNegocioDto(this.data,identificadorNegocio, corretora,identificacaoClienteNegocio, segundosDataNegociacao, nanosDataNegociacao, segundosDataLiquidacao, nanosDataLiquidacao);


        ValoresFinanceirosNegocio valoresFinanceirosNegocio = requisicao.getValoresFinanceiros();
        Double valorVendasAVista = valoresFinanceirosNegocio.getValorVendasAVista();
        Double valorComprasAVista = valoresFinanceirosNegocio.getValorComprasAVista();
        Double valorTaxaLiquidacao = valoresFinanceirosNegocio.getValorTaxaLiquidacao();
        Double valorEmolumentos = valoresFinanceirosNegocio.getValorEmolumentos();
        Double valorCorretagem = valoresFinanceirosNegocio.getValorCorretagem();
        Double valorIss = valoresFinanceirosNegocio.getValorIss();
        Double valorIrrf = valoresFinanceirosNegocio.getValorIrrf();
        Double valorLiquidoConta = valoresFinanceirosNegocio.getValorLiquidoConta();

        ValoresFinanceirosNegocioDto valoresFinanceirosNegocioDto = new ValoresFinanceirosNegocioDto(this.data,valorVendasAVista,valorComprasAVista,valorTaxaLiquidacao,valorEmolumentos,valorCorretagem,valorIss,valorIrrf,valorLiquidoConta);
        NegociacaoDto negociacaoDto = new NegociacaoDto(this.data,identificacaoNegocioDto, valoresFinanceirosNegocioDto);

        // Para cada Ativo Negociado existente, inclui no objeto de Negociacao
        List<AtivoNegociado> listaAtivoNegociado = requisicao.getAtivosNegociadosList();
        AtivoNegociado ativoNegociado;
        for (int i = 0; i < listaAtivoNegociado.size(); i++) {
            ativoNegociado = listaAtivoNegociado.get(i);
            negociacaoDto.adicionarAtivoNegociado(new AtivoNegociadoDto(ativoNegociado.getSiglaAtivo(),ativoNegociado.getTipoNegocio(),ativoNegociado.getQuantidadeNegociada(),ativoNegociado.getPrecoNegociado()));
        }
        return negociacaoDto;
    }

}