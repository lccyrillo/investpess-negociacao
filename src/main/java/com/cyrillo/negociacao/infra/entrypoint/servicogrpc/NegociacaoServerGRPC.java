package com.cyrillo.negociacao.infra.entrypoint.servicogrpc;

import com.cyrillo.negociacao.core.dataprovider.tipos.DataProviderInterface;
import com.cyrillo.negociacao.core.dataprovider.tipos.LogInterface;

// Avaliar melhor as importacoes. O Entry poiny não deveria referenciar frameworks externos.
import com.cyrillo.negociacao.infra.config.Aplicacao;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerMethodDefinition;
import io.grpc.ServerServiceDefinition;

import java.util.ArrayList;
import java.util.List;


public class NegociacaoServerGRPC {
    private DataProviderInterface data;

    public NegociacaoServerGRPC(DataProviderInterface dataProviderInterface) {
        this.data = dataProviderInterface;
        this.inicializaServidor();

    }

    private void inicializaServidor() {
        
        LogInterface log = Aplicacao.getInstance().getLoggingInterface();
        log.logInfo(null,null,"Inicializando Servidor GRPC.");


        try {

            List<ServerServiceDefinition> lista = new ArrayList<>();
            NegociacaoService negociacaoService = new NegociacaoService(this.data);
            HealthCheckService healthCheckService = new HealthCheckService();


            lista.add(negociacaoService.bindService());
            lista.add(healthCheckService.bindService());
            Server server = ServerBuilder.forPort(50051)
                    .addServices(lista)
                    .build()
                    .start();

            Runtime.getRuntime().addShutdownHook(new Thread( () -> {
                log.logInfo(null,null,"Recebida solicitação para encerramento do servidor.");
                server.shutdown();
                log.logInfo(null,null,"Servidor GRPC encerrado com sucesso!");

            }  ));
            log.logInfo(null,null,"Servidor GRPC inicializado com sucesso!");
            log.logInfo(null,null,listaMetodosServico(lista));
            server.awaitTermination();
        }
        catch (Exception e){
            log.logError(null,null, "Falha na inicialização do servidor GRPC.");
            log.logError(null, null,e.getMessage());
        }

    }

    private String listaMetodosServico(List<ServerServiceDefinition> lista){
        String mensagem = "";
        for (int i =0; i <lista.size(); i++) {
            mensagem = mensagem + "Serviço: ";
                    mensagem = mensagem + lista.get(i).getServiceDescriptor().getName() + " Métodos: ";
            for (ServerMethodDefinition<?, ?> methodDesc : lista.get(i).getMethods()) {
                mensagem = mensagem + methodDesc.getMethodDescriptor().getBareMethodName() + " ";
            }
        }
        return mensagem;
    }
}
