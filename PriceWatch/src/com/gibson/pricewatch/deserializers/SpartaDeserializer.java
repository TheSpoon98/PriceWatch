package com.gibson.pricewatch.deserializers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.gibson.pricewatch.POJO.cards.SpartaSWDCard;
import com.gibson.pricewatch.POJO.responses.SpartaResponse;
import com.gibson.pricewatch.utilities.Utilities;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;

public class SpartaDeserializer implements JsonDeserializer<SpartaResponse> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement,
     * java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
     */
    @Override
    public SpartaResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        List<SpartaSWDCard> cardList = new ArrayList<>();
        SpartaSWDCard card;

        SpartaResponse spProducts = new Gson().fromJson(json.getAsJsonObject(), SpartaResponse.class);
        for (JsonElement jPro : json.getAsJsonObject().getAsJsonArray("products")) {
            card = new SpartaSWDCard();

            // Get Name/DisplayName/Subtitle from Json Object
            String cardName = StringUtils.substringAfterLast(jPro.getAsJsonObject().get("title").getAsString(), ": ").trim();
            if (cardName.indexOf(" - ") > 0) {
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
                if (tagValue.contains("Group")) {
                    card.setSetName(StringUtils.substringAfter(tagValue, " - "));
                    break;
                }
            }

            // Get Price/Availibilty/Sku from Json Object
            for (JsonElement jVar : jPro.getAsJsonObject().getAsJsonArray("variants")) {
                card.setPrice(jVar.getAsJsonObject().get("price").getAsString());
                card.setAvail(jVar.getAsJsonObject().get("available").getAsString());
                if (jVar.getAsJsonObject().get("sku") != JsonNull.INSTANCE) {
                    card.setSku(jVar.getAsJsonObject().get("sku").getAsString());
                }
            }

            if (StringUtils.isNotEmpty(card.getSetName()) && Boolean.valueOf(card.getAvail())) {
                cardList.add(card);
            }
        }
        spProducts.setCardList(cardList);
        return spProducts;
    }

}
