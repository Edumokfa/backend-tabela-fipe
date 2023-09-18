package br.com.integracaoFipe.integracaoFipe.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

public class MoneyDeserializer extends JsonDeserializer<BigDecimal> {

    @Override
    public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String valorMonetario = p.getValueAsString();
        try {
            DecimalFormat df = new DecimalFormat("#,##0.00");
            return new BigDecimal(df.parse(valorMonetario.replaceAll("[R$ ]", "")).toString());
        } catch (ParseException e) {
            throw new IOException("Erro ao desserializar valor monetário.", e);
        }
    }
}
