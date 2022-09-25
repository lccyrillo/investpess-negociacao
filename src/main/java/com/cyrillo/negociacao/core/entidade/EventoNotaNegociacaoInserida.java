package com.cyrillo.negociacao.core.entidade;

import com.cyrillo.negociacao.core.dataprovider.tipo.EventoInterface;

public class EventoNotaNegociacaoInserida implements EventoInterface {
    private int codigoEvento;
    private int codigoEntidadeEvento;
    private String nomeEvento;
    private String jsonEvento;


    public EventoNotaNegociacaoInserida(int codigoEvento, int codigoEntidadeEvento, String nomeEvento, String jsonEvento){
        this.codigoEvento = codigoEvento;
        this.codigoEntidadeEvento = codigoEntidadeEvento;
        this.nomeEvento = nomeEvento;
        this.jsonEvento = jsonEvento;
    }

    @Override
    public int getCodigoEvento() {
        return codigoEvento;
    }

    @Override
    public int getCodigoEntidadeEvento() {
        return codigoEntidadeEvento;
    }


    @Override
    public String getNomeEvento() {
        return nomeEvento;
    }

    @Override
    public String getJsonEvento() {
        return jsonEvento;
    }

    @Override
    public String getMensagemEvento() {
        return "MENSAGEM ||| Evento: " + getCodigoEvento() + " | Nome Evento: " + getNomeEvento() + "| Código Entidade: " + getCodigoEntidadeEvento() + "| Json: " +getJsonEvento();
    }
}
