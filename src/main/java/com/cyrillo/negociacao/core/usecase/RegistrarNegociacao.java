package com.cyrillo.negociacao.core.usecase;


import com.cyrillo.negociacao.core.dataprovider.excecao.ComunicacaoRepoDataProvExcecao;
import com.cyrillo.negociacao.core.dataprovider.tipo.*;
import com.cyrillo.negociacao.core.entidade.NotaNegociacao;
import com.cyrillo.negociacao.core.entidade.excecao.ValoresFinanceirosNaoConferemEntidadeExcecao;
import com.cyrillo.negociacao.core.usecase.excecao.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class RegistrarNegociacao {

    public RegistrarNegociacao() {
    }

    public void executar(DataProviderInterface data, String flowId, NegociacaoDtoInterface negociacaoDtoInterface) throws ComunicacaoRepoUseCaseExcecao, ValoresFinanceirosNaoConferemUseCaseExcecao, NotaNegociacaoExistenteUseCaseExcecao, AtivoNaoIdentificadoUseCaseExcecao, AtivoNaoEAcaoUseCaseExcecao {
        // Mapa de resultados do use case
        LogInterface log = data.getLoggingInterface();
        data.setFlowId(flowId);
        String sessionId = String.valueOf(data.getSessionId());

        log.logInfo(flowId, sessionId, "Iniciando Use Case RegistrarNegociacao()");
        log.logInfo(flowId, sessionId, "Dados requisição: " + negociacaoDtoInterface.toString());
        AtivoRepositorioInterface repoAtivo = data.getAtivoRepositorio();

        try {
            // 1.1 Cria Objeto NotaNegociacao
            NotaNegociacao notaNegociacao = criarObjetoNotaNegociacao(data, negociacaoDtoInterface);
            log.logInfo(flowId, sessionId,notaNegociacao.toJson());
            // 1.2 Verifico se a nota de Negociação já existe no repositório
            validarNotaNegociacaoJaExisteRepositorio(data, notaNegociacao);
            // 1.3 - Atualizo os Dados Financeido e Verifico se o valor líquido em conta bate com outros valores informados
            atualizar_e_ValidarValoresFinanceirosNotaNegociacao(data, notaNegociacao,negociacaoDtoInterface);
            // 1.4 - Atualizo e Incluir os movmientos financeiros na nota de negociação e valido se são do tipo ação
            atualizarMovimentacoesNotaNegociacao(data, notaNegociacao,negociacaoDtoInterface);
            log.logInfo(flowId, sessionId,notaNegociacao.toJson());
            // 1.5 - Verificar se o valor somado dos ativos informados corresponde com o valor informado da nota de negociação
            // Refatorar ---> Pensar em jogar esse cálculo para dentro da nota de negociacao objeto / não DTO
            validarValoresFinanceirosAtivosComparadosComValorVendasECompras(data, negociacaoDtoInterface);

            // Passos que faltam
            // 24/09/2022
            // 1.6 Persistir Nota de Negociação
            NotaNegociacaoRepositorioInterface notaNegociacaoRepositorio = data.getNotaNegocicacaoRepositorio();
            notaNegociacaoRepositorio.armazenarNotaNegociacao(data,notaNegociacao);
            // 1.7 Notificar serviço para calcular posiçao e resultado na venda

        } catch (ComunicacaoRepoDataProvExcecao e) {
            ComunicacaoRepoUseCaseExcecao falha = new ComunicacaoRepoUseCaseExcecao("Falha na comunicação do Use Case com Repositório: AtivoRepositorio");
            falha.addSuppressed(e);
            log.logError(flowId, sessionId,"Erro na comunicação com repositório de notas de negociação");
            e.printStackTrace();
            throw falha;
        }


        // 2. Calcular os itens de custos de cada ação da nota
        // 2.1 - Calcular posição média de cada ação que faz parte da nota
        // 2.2 - Somar todo o valor financeiro e calcular a parte de cutos de cada ação
        // 2.3 - Guardar o ponto de ação calculado (data / ação / corretora / usuario)
        // 3. Precisa ser recalculada a posição da ação ( avaliar realizar esses recálculos através de outro microsserviço notificado através de um kafka
        // 3.1 recalcular a curva de posicao da ação a partir da data do ponto de ação calculado (posição: data, ação. usuario e corretora) , considerar data como datas do ponto de ação, data de final de mes e data atual.
        // 3.2 Se for operação vendida, precisar recalcular a curva de vendas mensais e imposto de renda


  /*      sigla = sigla.toUpperCase();
        try {
            if (repo.consultarPorSigla(data, sigla) == false) {
                // --> Se a consulta falhar na comunicação com banco de dados, vai gerar uma exceção que precisará ser tratada.
                // Posso cadastrar ativo
                AtivoDto ativoDto = new AtivoDto(sigla, nomeAtivo, descricaoCNPJAtivo, tipoAtivo);
                // Faço esse passo para garantir o Dto está criando um objeto válido.
                AtivoObjeto ativoObjeto = ativoDto.builder();
                repo.incluir(data, ativoDto);
                log.logInfo(uniqueKey, "Ativo incluído com sucesso");
            }
            else {
                // Erro: Sigla já existente
                // Lançar exceção AtivoJaExistenteException
                log.logInfo(uniqueKey, "Ativo já existe no repositório");
                throw new AtivoJaExistenteUseCaseExcecao(sigla);
            }
        }
        catch (ParametroCNPJInvalidoEntExcecao e){
            log.logError(uniqueKey,"CNPJ Inválido");
            e.printStackTrace();
            throw new AtivoParametrosInvalidosUseCaseExcecao("CNPJ Inválido");
        }
        catch (ParametroTipoInvalidoEntExcecao e){
            log.logError(uniqueKey,"Tipo Inválido");
            e.printStackTrace();
            throw new AtivoParametrosInvalidosUseCaseExcecao("Tipo Inválido");
        }
        catch (ComunicacaoRepoDataProvExcecao e) {
            ComunicacaoRepoUseCaseExcecao falha = new ComunicacaoRepoUseCaseExcecao("Falha na comunicação do Use Case com Repositório: AtivoRepositorio");
            falha.addSuppressed(e);
            log.logError(uniqueKey, "Erro na comunicação com repositório.");
            e.printStackTrace();
            throw falha;
        }*/
    }

    private NotaNegociacao criarObjetoNotaNegociacao(DataProviderInterface data, NegociacaoDtoInterface negociacaoDtoInterface) throws ComunicacaoRepoDataProvExcecao,NotaNegociacaoExistenteUseCaseExcecao {
        LogInterface log = data.getLoggingInterface();
        String sessionId = String.valueOf(data.getSessionId());
        NotaNegociacaoRepositorioInterface notaNegociacaoRepositorio = data.getNotaNegocicacaoRepositorio();
        String identificador = negociacaoDtoInterface.getIdentificacaoNegocioDtoInterface().getIdentificadorNegocio();
        String cliente = negociacaoDtoInterface.getIdentificacaoNegocioDtoInterface().getIdentificacaoClienteNegocio();
        String corretora = negociacaoDtoInterface.getIdentificacaoNegocioDtoInterface().getCorretora();
        LocalDateTime dataNegocio = negociacaoDtoInterface.getIdentificacaoNegocioDtoInterface().getDataNegocio();
        LocalDateTime dataLiquidacao = negociacaoDtoInterface.getIdentificacaoNegocioDtoInterface().getDataLiquidacao();
        return new NotaNegociacao(data.getUtilitario(),identificador,corretora,cliente,dataNegocio, dataLiquidacao);
    }

    private void validarNotaNegociacaoJaExisteRepositorio(DataProviderInterface data, NotaNegociacao notaNegociacao) throws ComunicacaoRepoDataProvExcecao,NotaNegociacaoExistenteUseCaseExcecao {
        LogInterface log = data.getLoggingInterface();
        String sessionId = String.valueOf(data.getSessionId());
        String flowId = data.getFlowId();
        NotaNegociacaoRepositorioInterface notaNegociacaoRepositorio = data.getNotaNegocicacaoRepositorio();
        String identificador = notaNegociacao.getIdentificadorNegocio();
        String cliente = notaNegociacao.getIdentificacaoClienteNegocio();
        String corretora = notaNegociacao.getCorretora();
        LocalDate dataNegocio = notaNegociacao.getDataNegocio();
        if (notaNegociacaoRepositorio.consultarNotaNegociacao(data, identificador,corretora,cliente ,dataNegocio) == true) {
            log.logInfo(flowId, sessionId, "Nota negociação já existe no repositório: " + notaNegociacao.toString());
            throw new NotaNegociacaoExistenteUseCaseExcecao("Nota negociação já existe no repositório.");
        }
    }

    private void atualizar_e_ValidarValoresFinanceirosNotaNegociacao(DataProviderInterface data, NotaNegociacao notaNegociacao,NegociacaoDtoInterface negociacaoDtoInterface) throws ValoresFinanceirosNaoConferemUseCaseExcecao {
        LogInterface log = data.getLoggingInterface();
        String sessionId = String.valueOf(data.getSessionId());
        String flowId = data.getFlowId();
        try {
            Double valorComprasAVista = negociacaoDtoInterface.getValoresFinanceirosNegocioDtoInterface().getValorComprasAVista();
            Double valorVendasAVista = negociacaoDtoInterface.getValoresFinanceirosNegocioDtoInterface().getValorVendasAVista();
            Double valorTaxaLiquidacao = negociacaoDtoInterface.getValoresFinanceirosNegocioDtoInterface().getValorTaxaLiquidacao();
            Double valorEmolumentos = negociacaoDtoInterface.getValoresFinanceirosNegocioDtoInterface().getValorEmolumentos();
            Double valorCorretagem = negociacaoDtoInterface.getValoresFinanceirosNegocioDtoInterface().getValorCorretagem();
            Double valorIss = negociacaoDtoInterface.getValoresFinanceirosNegocioDtoInterface().getValorIss();
            Double valorLiquidoConta = negociacaoDtoInterface.getValoresFinanceirosNegocioDtoInterface().getValorLiquidoConta();
            notaNegociacao.atualizaValoresFinanceiros(valorComprasAVista,valorVendasAVista,valorTaxaLiquidacao,valorEmolumentos ,valorCorretagem,valorIss,valorLiquidoConta);
            notaNegociacao.validarValoresFinanceirosComparadosComValorLiquidoConta();
        } catch (ValoresFinanceirosNaoConferemEntidadeExcecao e) {
            ValoresFinanceirosNaoConferemUseCaseExcecao falha = new ValoresFinanceirosNaoConferemUseCaseExcecao("Valor de conferência de líquido para conta não confere com valor informado!.");
            falha.addSuppressed(e);
            log.logInfo(flowId, sessionId,"Valor de conferência de líquido para conta não confere com valor informado!. Nota Negociação: " + notaNegociacao.toJson());
            e.printStackTrace();
            throw falha;

        }
    }


    private void atualizarMovimentacoesNotaNegociacao(DataProviderInterface data, NotaNegociacao notaNegociacao, NegociacaoDtoInterface negociacaoDtoInterface) throws ComunicacaoRepoDataProvExcecao, AtivoNaoEAcaoUseCaseExcecao, AtivoNaoIdentificadoUseCaseExcecao {
        LogInterface log = data.getLoggingInterface();
        String sessionId = String.valueOf(data.getSessionId());
        String flowId = data.getFlowId();
        log.logInfo(flowId,sessionId,"Iniciando Atualizar Movimentacoes NotaNegociacao");
        // Passo por cada movimento financeiro recebido no request e verifico se existe no repositório e é do tipo ação, se não for, erro
        // senao, sigo em frente

        AtivoNegociadoDtoInterface ativoNegociadoDtoInterface;

        List<AtivoNegociadoDtoInterface> listaAtivoNegociado = negociacaoDtoInterface.listarTodosAtivos();
        AtivoRepositorioInterface ativoRepositorioInterface = data.getAtivoRepositorio();
        for (int i = 0; i < listaAtivoNegociado.size(); i++) {
            ativoNegociadoDtoInterface = negociacaoDtoInterface.listarTodosAtivos().get(i);
            String siglaAtivo = ativoNegociadoDtoInterface.getSigla();
            Optional<AtivoDtoInterface> ativoDtoInterface = ativoRepositorioInterface.consultarPorSigla(data, siglaAtivo);
            if (ativoDtoInterface.isPresent()) {
                // verifico se o ativo é do tipo ação (sistema só sabe processar açao
                if (ativoDtoInterface.get().getTipoAtivoInt() != 1) {
                    log.logInfo(flowId,sessionId,"Foi informado um ativo que não é do tipo Ação: "+ siglaAtivo);
                    throw new AtivoNaoEAcaoUseCaseExcecao("Foi informado um ativo que não é do tipo Ação: "+ siglaAtivo);
                } else {
                    int tipoNegocio = ativoNegociadoDtoInterface.getTipoNegocio();
                    Double quantidadeMovimento = ativoNegociadoDtoInterface.getQuantidadeNegociada();
                    Double precoMovimentoAtivo = ativoNegociadoDtoInterface.getPrecoNegociado();

                    ValoresFinanceirosNegocioDtoInterface valoresFinanceirosNegocioDto = negociacaoDtoInterface.getValoresFinanceirosNegocioDtoInterface();
                    Double valorTotalComprasEVendas = valoresFinanceirosNegocioDto.getValorTotalComprasEVendasAVista();
                    Double custosTotaisNota = valoresFinanceirosNegocioDto.getCustoTotalNota();
                    notaNegociacao.adicionaMovimentoAtivo(siglaAtivo,tipoNegocio,quantidadeMovimento,precoMovimentoAtivo,custosTotaisNota,valorTotalComprasEVendas);
                    //insiro a ação na nota de negociação
                }
            } else{
                // Erro!!
                // Não existe esse ativo objeto
                log.logInfo(flowId,sessionId,"Ativo não identificado: "+ siglaAtivo);
                throw new AtivoNaoIdentificadoUseCaseExcecao("Ativo não identificado: "+ siglaAtivo);
            }
        }
    }


    private void validarValoresFinanceirosAtivosComparadosComValorVendasECompras(DataProviderInterface data, NegociacaoDtoInterface negociacaoDtoInterface) throws ValoresFinanceirosNaoConferemUseCaseExcecao {
        // Mapa de resultados do use case
        LogInterface log = data.getLoggingInterface();
        String sessionId = String.valueOf(data.getSessionId());
        String flowId = data.getFlowId();

        Double valorComprasAVista = negociacaoDtoInterface.getValoresFinanceirosNegocioDtoInterface().getValorComprasAVista();
        Double valorVendasAVista = negociacaoDtoInterface.getValoresFinanceirosNegocioDtoInterface().getValorVendasAVista();



        // Loop por todos os ativos somando as compras e vendas
        AtivoNegociadoDtoInterface ativoNegociadoDtoInterface;
        Double valorSomadoComprasAVista = 0.0;
        Double valorSomadoVendasAVista = 0.0;
        Double valorNegocio;
        List<AtivoNegociadoDtoInterface> listaAtivoNegociado = negociacaoDtoInterface.listarTodosAtivos();
        for (int i = 0; i < listaAtivoNegociado.size(); i++) {
            ativoNegociadoDtoInterface = negociacaoDtoInterface.listarTodosAtivos().get(i);
            valorNegocio = ativoNegociadoDtoInterface.getValorNegocio();
            if (ativoNegociadoDtoInterface.getTipoNegocio() == 1) {
                valorSomadoComprasAVista = valorSomadoComprasAVista + valorNegocio;
            } else if (ativoNegociadoDtoInterface.getTipoNegocio() == 2) {
                valorSomadoVendasAVista = valorSomadoVendasAVista + valorNegocio;
            }
        }

        log.logInfo(flowId,sessionId,"valorSomadoVendasAVista: " + valorSomadoVendasAVista.toString());
        log.logInfo(flowId,sessionId,"valorComprasAVista: " + valorComprasAVista.toString());
        log.logInfo(flowId,sessionId,"valorSomadoComprasAVista: " + valorSomadoComprasAVista.toString());
        log.logInfo(flowId,sessionId,"valorVendasAVista: " + valorVendasAVista.toString());

        // Comparação de valores double em java.
        double epsilon = 0.0000001d;
        if ((Math.abs(valorSomadoVendasAVista - valorVendasAVista) >= epsilon) || (Math.abs(valorSomadoComprasAVista - valorComprasAVista) >= epsilon)) {
            throw new ValoresFinanceirosNaoConferemUseCaseExcecao("Valor de compras e vendas dos ativos não conferem com o valor de compras e vendas informados!");
        }
    }


}
