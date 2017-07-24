package com.gibson.pricewatch.deserializers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.gibson.pricewatch.POJO.exchange.ExchangeRate;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

public class ExchangeRateDeserializer implements JsonDeserializer<List<ExchangeRate>> {
    public List<ExchangeRate> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx) {
        JsonElement tmp = json.getAsJsonObject().get("query").getAsJsonObject().get("results").getAsJsonObject().get("rate");

        List<ExchangeRate> vals = new ArrayList<>();
        if (tmp.isJsonArray()) {
            for (JsonElement e : tmp.getAsJsonArray()) {
                vals.add((ExchangeRate) ctx.deserialize(e, ExchangeRate.class));
            }
        } else if (tmp.isJsonObject()) {
            vals.add((ExchangeRate) ctx.deserialize(tmp, ExchangeRate.class));
        } else {
            throw new RuntimeException("Unexpected JSON type: " + tmp.getClass());
        }
        return vals;
    }
}