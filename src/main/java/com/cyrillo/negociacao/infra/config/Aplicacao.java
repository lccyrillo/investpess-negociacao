package com.cyrillo.negociacao.infra.config;

public class Aplicacao {
    private static Aplicacao instance;
    private Aplicacao(){
    }

    public static Aplicacao getInstance(){
        if(instance == null){
            synchronized (Aplicacao.class) {
                if(instance == null){
                    instance = new Aplicacao();
                }
            }
        }
        return instance;
    }

    public void inicializaAplicacao(){
        try {
            System.out.println("Aplicação inicializada com sucesso.");
        }
        catch (Exception e){
            System.out.println("Não foi possível inicializar a aplicação.");
            e.printStackTrace();
        }
    }
}
