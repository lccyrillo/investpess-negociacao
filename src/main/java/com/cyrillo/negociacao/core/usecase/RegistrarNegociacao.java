package com.cyrillo.negociacao.core.usecase;


import com.cyrillo.negociacao.core.dataprovider.tipos.*;
import com.cyrillo.negociacao.core.usecase.excecao.ComunicacaoRepoUseCaseExcecao;
import com.cyrillo.negociacao.core.usecase.excecao.ValoresFinanceirosNaoConferemUseCaseExcecao;
import com.cyrillo.negociacao.infra.dataprovider.dto.AtivoNegociadoDto;
import com.cyrillo.negociacao.infra.entrypoint.servicogrpc.negociacaoproto.AtivoNegociado;

import java.util.List;

public class RegistrarNegociacao {

    public RegistrarNegociacao() {
    }

    public void executar(DataProviderInterface data, String flowId, NegociacaoDtoInterface negociacaoDtoInterface) throws ComunicacaoRepoUseCaseExcecao, ValoresFinanceirosNaoConferemUseCaseExcecao {
        // Mapa de resultados do use case
        LogInterface log = data.getLoggingInterface();
        data.setFlowId(flowId);
        String sessionId = String.valueOf(data.getSessionId());

        log.logInfo(flowId, sessionId, "Iniciando Use Case RegistrarNegociacao()");
        log.logInfo(flowId, sessionId, "Dados requisição: " + negociacaoDtoInterface.toString());
        AtivoRepositorioInterface repoAtivo = data.getAtivoRepositorio();

        // 1. Validar dados da negociação
        // 1.1 - Verifica se o valor líquido em conta bate com outros valores informados
        validarValoresFinanceirosComparadosComValorLiquidoConta(data, flowId, negociacaoDtoInterface);
        // 1.2 - Verificar se o valor somado dos ativos informados corresponde com o valor informado da nota de negociação
        validarValoresFinanceirosAtivosComparadosComValorVendasECompras(data, flowId, negociacaoDtoInterface);

        // 1.3 nota de negociação ainda não existe na base (chave: corretora, numero_nota e usuario)
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

    private void validarValoresFinanceirosComparadosComValorLiquidoConta(DataProviderInterface data, String flowId, NegociacaoDtoInterface negociacaoDtoInterface) throws ValoresFinanceirosNaoConferemUseCaseExcecao {
        // Mapa de resultados do use case
        LogInterface log = data.getLoggingInterface();
        String sessionId = String.valueOf(data.getSessionId());

        Double valorComprasAVista = negociacaoDtoInterface.getValoresFinanceirosNegocioDtoInterface().getValorComprasAVista();
        Double valorVendasAVista = negociacaoDtoInterface.getValoresFinanceirosNegocioDtoInterface().getValorVendasAVista();
        Double valorTaxaLiquidacao = negociacaoDtoInterface.getValoresFinanceirosNegocioDtoInterface().getValorTaxaLiquidacao();
        Double valorEmolumentos = negociacaoDtoInterface.getValoresFinanceirosNegocioDtoInterface().getValorEmolumentos();
        Double valorCorretagem = negociacaoDtoInterface.getValoresFinanceirosNegocioDtoInterface().getValorCorretagem();
        Double valorIss = negociacaoDtoInterface.getValoresFinanceirosNegocioDtoInterface().getValorIss();
        Double valorLiquidoConta = negociacaoDtoInterface.getValoresFinanceirosNegocioDtoInterface().getValorLiquidoConta();

        Double valorConferenciaLiquidoConta = (valorComprasAVista + valorTaxaLiquidacao + valorEmolumentos + valorCorretagem
                + valorIss - valorVendasAVista) * -1;

        // log.logInfo(flowId,sessionId,"valorConferenciaLiquidoConta: " + valorConferenciaLiquidoConta.toString());
        // log.logInfo(flowId,sessionId,"valorLiquidoConta: " + valorLiquidoConta.toString());


        // Comparação de valores double em java.
        double epsilon = 0.0000001d;
        if (Math.abs(valorConferenciaLiquidoConta - valorLiquidoConta) >= epsilon) {
            throw new ValoresFinanceirosNaoConferemUseCaseExcecao("Valor de conferência de líquido para conta não confere com valor informado!");
        }
    }

    private void validarValoresFinanceirosAtivosComparadosComValorVendasECompras(DataProviderInterface data, String flowId, NegociacaoDtoInterface negociacaoDtoInterface) throws ValoresFinanceirosNaoConferemUseCaseExcecao {
        // Mapa de resultados do use case
        LogInterface log = data.getLoggingInterface();
        String sessionId = String.valueOf(data.getSessionId());

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
