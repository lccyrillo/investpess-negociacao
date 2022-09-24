package com.cyrillo.negociacao.infra.util;

import com.cyrillo.negociacao.core.tipobasico.UtilitarioInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Utilitario implements UtilitarioInterface {
    private Gson meuConversorJson;

    public Utilitario() {
        this.meuConversorJson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter())
                .registerTypeAdapter(LocalDate.class, new LocalDateConverter())
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }


    public String converterGoogleProtobufTimeStampParaStringData(long seconds, int nanos) {
        Instant instant = Instant.ofEpochSecond(seconds, nanos);
        String PATTERN_FORMAT = "dd/MM/yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT).withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }

    public LocalDateTime converterGoogleProtobufTimeStampParaLocalDateTime(long seconds, int nanos) {
        Instant instant = Instant.ofEpochSecond(seconds, nanos);
        //LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("America/Sao_Paulo"));
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime;
    }

    public String converterObjetoParaJson(Object objeto) {
        return meuConversorJson.toJson(objeto);
    }


    public String converterLocalDateTimeparaStringData(LocalDateTime dataHora) {
        String PATTERN_FORMAT = "dd/MM/yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT).withZone(ZoneId.systemDefault());
        return formatter.format(dataHora);
    }

    public String converterLocalDateTimeparaStringDataHora(LocalDateTime dataHora) {
        String PATTERN_FORMAT = "dd/MM/yyyy HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT).withZone(ZoneId.systemDefault());
        return formatter.format(dataHora);
    }

    public String converterLocalDateparaStringData(LocalDate data){
        String PATTERN_FORMAT = "dd/MM/yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT).withZone(ZoneId.systemDefault());
        return formatter.format(data);
    }

    public String converterLocalDateparaStringDataHora(LocalDate data){
        String PATTERN_FORMAT = "dd/MM/yyyy HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT).withZone(ZoneId.systemDefault());
        return formatter.format(data);
    }
}
