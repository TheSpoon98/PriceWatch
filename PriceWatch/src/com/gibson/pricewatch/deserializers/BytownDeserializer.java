package com.gibson.pricewatch.deserializers;

import java.lang.reflect.Type;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.gibson.pricewatch.POJO.cards.BytownSWDCard;
import com.gibson.pricewatch.POJO.responses.BytownResponse;
import com.gibson.pricewatch.utilities.Utilities;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;

public class BytownDeserializer implements JsonDeserializer<BytownResponse> {

    @Override
    public BytownResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        List<BytownSWDCard> cardList = new ArrayList<>();
        BytownSWDCard card;

        BytownResponse byProducts = new Gson().fromJson(json.getAsJsonObject(), BytownResponse.class);
        for (JsonElement jPro : json.getAsJsonObject().getAsJsonArray("products")) {
            card = new BytownSWDCard();

            // Get Name/DisplayName/Subtitle from Json Object
            String cardName = jPro.getAsJsonObject().get("title").getAsString().trim();
            cardName = Normalizer.normalize(cardName, Normalizer.Form.NFKD);
            if (StringUtils.contains(cardName, " - ")) {
                card.setName(StringUtils.substringBefore(cardName, " - "));
                card.setDisplayName(Utilities.parseDisplayName(StringUtils.substringBefore(cardName, " - ")));
                card.setSubtitle(StringUtils.substringAfter(cardName, " - "));
            } else {
                card.setName(cardName);
                card.setDisplayName(Utilities.parseDisplayName(cardName));
            }

            // Get SetName from Json Object
            for (JsonElement jTags : jPro.getAsJsonObject().get("tags").getAsJsonArray()) {
                String tagValue = jTags.getAsString();
                if (tagValue.contains("Set_")) {
                    card.setSetName(StringUtils.substringAfter(tagValue, "Set_"));
                    break;
                }
            }

            // Get Price/Availibilty/Sku from Json Object
            for (JsonElement jVar : jPro.getAsJsonObject().getAsJsonArray("variants")) {
                card.setAvail(jVar.getAsJsonObject().get("available").getAsString());
                card.setPrice(jVar.getAsJsonObject().get("price").getAsString());
                if (jVar.getAsJsonObject().get("sku") != JsonNull.INSTANCE) {
                    card.setSku(jVar.getAsJsonObject().get("sku").getAsString());
                }
            }

            if (StringUtils.isNotEmpty(card.getSetName()) && Boolean.valueOf(card.getAvail())) {
                cardList.add(card);
            }
        }
        byProducts.setCardList(cardList);
        return byProducts;
    }

}
