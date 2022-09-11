package com.cyrillo.negociacao.core.usecase;


import com.cyrillo.negociacao.core.dataprovider.tipos.AtivoRepositorioInterface;
import com.cyrillo.negociacao.core.dataprovider.tipos.DataProviderInterface;
import com.cyrillo.negociacao.core.dataprovider.tipos.LogInterface;
import com.cyrillo.negociacao.core.dataprovider.tipos.NegociacaoDtoInterface;
import com.cyrillo.negociacao.core.usecase.excecao.ComunicacaoRepoUseCaseExcecao;

public class RegistrarNegociacao {

    public RegistrarNegociacao(){}

    public void executar(DataProviderInterface data, String flowId, NegociacaoDtoInterface negociacaoDtoInterface) throws ComunicacaoRepoUseCaseExcecao  {
        // Mapa de resultados do use case
        LogInterface log = data.getLoggingInterface();
        data.setFlowId(flowId);
        String sessionId =String.valueOf(data.getSessionId());

        log.logInfo(flowId, sessionId,"Iniciando Use Case Incluir Novo Ativo");
        log.logInfo(flowId,sessionId,"Dados requisição: " + negociacaoDtoInterface.toString());
        AtivoRepositorioInterface repoAtivo = data.getAtivoRepositorio();

        // 1. Validar dados da negociação
        // 1.1 - Ativos existentes e são de qual tipo
        // 1.2 - Os valores financeiros da nota de negociação estão coerentes
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
}
