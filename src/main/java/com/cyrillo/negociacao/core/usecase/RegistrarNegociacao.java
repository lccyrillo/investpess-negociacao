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
        AtivoRepositorioInterface repoAtivo = data.getAtivoRepositorio();


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
