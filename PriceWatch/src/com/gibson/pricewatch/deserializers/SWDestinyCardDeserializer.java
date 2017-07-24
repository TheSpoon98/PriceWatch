package com.gibson.pricewatch.deserializers;

import java.lang.reflect.Type;
import java.text.Normalizer;

import com.gibson.pricewatch.POJO.cards.SWDestinyDBCard;
import com.gibson.pricewatch.utilities.Utilities;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class SWDestinyCardDeserializer implements JsonDeserializer<SWDestinyDBCard> {

    public SWDestinyDBCard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        SWDestinyDBCard swdCard = (SWDestinyDBCard) new Gson().fromJson(json.getAsJsonObject(), SWDestinyDBCard.class);

        try {
            String displayName = "";
            if (json.getAsJsonObject().get("name") != null) {
                displayName = json.getAsJsonObject().get("name").getAsString();
                Normalizer.normalize(displayName, Normalizer.Form.NFKD);
                swdCard.setDisplayName(Utilities.parseDisplayName(displayName));
            }

            if (json.getAsJsonObject().get("set_code") != null) {
                switch (json.getAsJsonObject().get("set_code").getAsString()) {
                    case "AW":
                        swdCard.setSetOrder(1);
                        break;
                    case "SoR":
                        swdCard.setSetOrder(2);
                        break;
                    case "EaW":
                        swdCard.setSetOrder(3);
                        break;
                    default:
                        swdCard.setSetOrder(0);
                }
            }
        } catch (IllegalArgumentException ie) {
            System.out.println(ie.getMessage());
        }
        return swdCard;
    }
}
