package com.cyrillo.negociacao.infra.entrypoint.servicogrpc;

import com.cyrillo.negociacao.core.dataprovider.DataProviderInterface;
import com.cyrillo.negociacao.core.dataprovider.LogInterface;
import com.cyrillo.negociacao.infra.entrypoint.servicogrpc.negociacaoproto.NegociacaoServiceGrpc;
import com.cyrillo.negociacao.infra.entrypoint.servicogrpc.negociacaoproto.RegistraNegociacaoRequest;
import com.cyrillo.negociacao.infra.entrypoint.servicogrpc.negociacaoproto.RegistraNegociacaoResponse;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
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

        DataProviderInterface dataProvider = data.geraSessao();
        String uniqueKey = String.valueOf(dataProvider.getUniqueKey());
        LogInterface log = dataProvider.getLoggingInterface();

        log.logInfo(uniqueKey,null,"Iniciando cadastra ativo objeto.");

        //Pega dados de entrada

        com.cyrillo.negociacao.infra.entrypoint.servicogrpc.negociacaoproto.IdentificacaoNegocio identificacaoNegocio = request.getIdentificacaoNegocio();
        String corretora = identificacaoNegocio.getCorretoraNegocio();

        // executa o caso de uso
        //try {
         //   new FacadeAtivo().executarIncluirNovoAtivo(dataProvider,sigla_ativo,nome_ativo,descricao_cnpj_ativo,tipo_ativo);
        codResultado = 200;
        msgResultado = "Negociação registrada!";
        //}
        //catch (ComunicacaoRepoUseCaseExcecao e) {
          //  codResultado = 401;
           // msgResultado = "Erro na persistência do Ativo no banco de dados!";
        //}

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