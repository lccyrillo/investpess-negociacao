package com.cyrillo.negociacao.core.tipobasico;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public interface UtilitarioInterface {
    public String converterGoogleProtobufTimeStampParaStringData(long seconds,int nanos);
    public LocalDateTime converterGoogleProtobufTimeStampParaLocalDateTime(long seconds, int nanos);
    public String converterObjetoParaJson(Object objeto);
    public String converterLocalDateTimeparaStringData(LocalDateTime dataHora);
    public String converterLocalDateTimeparaStringDataHora(LocalDateTime dataHora);
    public String converterLocalDateparaStringData(LocalDate dataHora);
    public String converterLocalDateparaStringDataHora(LocalDate dataHora);
}
