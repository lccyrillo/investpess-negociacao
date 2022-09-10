package com.cyrillo.negociacao.infra.entrypoint.servicogrpc;

import com.cyrillo.negociacao.infra.dataprovider.dto.IdentificacaoNegocioDto;
import com.cyrillo.negociacao.core.dataprovider.tipos.DataProviderInterface;
import com.cyrillo.negociacao.core.dataprovider.tipos.IdentificacaoNegocioDtoInterface;
import com.cyrillo.negociacao.core.dataprovider.tipos.LogInterface;
import com.cyrillo.negociacao.core.usecase.excecao.ComunicacaoRepoUseCaseExcecao;
import com.cyrillo.negociacao.core.usecase.excecao.ParametrosInvalidosUseCaseExcecao;
import com.cyrillo.negociacao.infra.dataprovider.dto.NegociacaoDto;
import com.cyrillo.negociacao.infra.entrypoint.servicogrpc.negociacaoproto.IdentificacaoNegocio;
import com.cyrillo.negociacao.infra.entrypoint.servicogrpc.negociacaoproto.NegociacaoServiceGrpc;
import com.cyrillo.negociacao.infra.entrypoint.servicogrpc.negociacaoproto.RegistraNegociacaoRequest;
import com.cyrillo.negociacao.infra.entrypoint.servicogrpc.negociacaoproto.RegistraNegociacaoResponse;
import com.cyrillo.negociacao.infra.facade.FacadeNegociacao;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;

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

            IdentificacaoNegocio identificacaoNegocio = request.getIdentificacaoNegocio();
            String identificadorNegocio = identificacaoNegocio.getIdentificadorNegocio();
            String corretora = identificacaoNegocio.getCorretoraNegocio();
            String identificacaoClienteNegocio = identificacaoNegocio.getIdentificacaoClienteNegocio();
            long segundosDataNegociacao =identificacaoNegocio.getDataNegocio().getSeconds();
            int nanosDataNegociacao = identificacaoNegocio.getDataNegocio().getNanos();
            long segundosDataLiquidacao =identificacaoNegocio.getDataLiquidacao().getSeconds();
            int nanosDataLiquidacao = identificacaoNegocio.getDataLiquidacao().getNanos();


            NegociacaoDto negociacaoDto = new NegociacaoDto(data,identificadorNegocio, corretora, identificacaoClienteNegocio, segundosDataNegociacao, nanosDataNegociacao, segundosDataLiquidacao, nanosDataLiquidacao);

            dataProvider.setFlowId(flowId);
            log.logInfo(flowId, sessionId,"Iniciando método GRPC para registro de negociação.");

            // executa o caso de uso
            new FacadeNegociacao().executarRegistrarNegocicacao(dataProvider,negociacaoDto);
            codResultado = 200;
            msgResultado = "Negociação registrada!";
        }
        catch (ComunicacaoRepoUseCaseExcecao e) {
            codResultado = 401;
            msgResultado = "Erro na persistência do Ativo no banco de dados!";
        }
        catch (ParametrosInvalidosUseCaseExcecao e) {
            codResultado = 401;
            msgResultado = "Erro na persistência do Ativo no banco de dados!";
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

}