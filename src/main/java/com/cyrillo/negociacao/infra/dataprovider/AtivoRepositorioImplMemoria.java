package com.cyrillo.negociacao.infra.dataprovider;


import com.cyrillo.negociacao.core.dataprovider.tipos.AtivoDtoInterface;
import com.cyrillo.negociacao.core.dataprovider.tipos.AtivoRepositorioInterface;
import com.cyrillo.negociacao.core.dataprovider.tipos.DataProviderInterface;
import com.cyrillo.negociacao.core.dataprovider.excecao.ComunicacaoRepoDataProvExcecao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AtivoRepositorioImplMemoria implements AtivoRepositorioInterface {
    private List<AtivoDtoInterface> listaAtivoObjeto = new ArrayList<>();

    public AtivoRepositorioImplMemoria(){

    }

    @Override
    public void incluir(DataProviderInterface data, AtivoDtoInterface ativoObjeto) throws ComunicacaoRepoDataProvExcecao {
        this.listaAtivoObjeto.add(ativoObjeto);
    }

    @Override
    public boolean consultarPorSigla(DataProviderInterface data, String siglaAtivo) throws ComunicacaoRepoDataProvExcecao {
        if (this.listaAtivoObjeto.stream()
            .filter(a -> a.getSigla().equals(siglaAtivo))
            .findFirst().isPresent()) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public List<AtivoDtoInterface> listarTodosAtivos(DataProviderInterface data) {
        return this.listaAtivoObjeto;

    }

    @Override
    public List<AtivoDtoInterface> listarAtivosPorTipo(DataProviderInterface data, int tipoAtivo) {

        return this.listaAtivoObjeto.stream()
                .filter(a -> a.getTipoAtivoInt() == tipoAtivo)
                .collect(Collectors.toList());
    }

    @Override
    public void healthCheck(DataProviderInterface data) throws ComunicacaoRepoDataProvExcecao {

    }
}
