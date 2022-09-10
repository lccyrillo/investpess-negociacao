package com.cyrillo.negociacao.core.dataprovider.tipos;

import java.time.LocalDateTime;

public interface UtilitarioInterface {
    public String converterGoogleProtobufTimeStampParaStringData(long seconds,int nanos);
    public LocalDateTime converterGoogleProtobufTimeStampParaLocalDateTime(long seconds, int nanos);
    public String converterObjetoParaJson(Object objeto);
}
