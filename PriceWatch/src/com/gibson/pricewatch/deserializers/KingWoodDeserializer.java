package com.gibson.pricewatch.deserializers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gibson.pricewatch.POJO.cards.KWSWDCard;
import com.gibson.pricewatch.POJO.responses.KingWoodResponse;
import com.gibson.pricewatch.utilities.Utilities;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class KingWoodDeserializer implements JsonDeserializer<KingWoodResponse> {

    @Override
    public KingWoodResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        KingWoodResponse kwResponse = new Gson().fromJson(json.getAsJsonObject(), KingWoodResponse.class);
        KingWoodResponse kwResult = new Gson().fromJson(json.getAsJsonObject().getAsJsonObject("result"), KingWoodResponse.class);

        List<KWSWDCard> cardList = new ArrayList<>();
        Document doc = null;
        try {
            doc = Jsoup.parse(kwResult.getContent());
            Elements divs = doc.getElementsByClass("product-excerpt-link");
            for (Element src : divs) {
                Element name = src.getElementsByClass("product-excerpt-title").first();
                Element price = src.getElementsByClass("product-excerpt-price-regular").first();
                KWSWDCard kwCard = new KWSWDCard();
                String cardName = name.html().replaceAll("&nbsp;", " ");
                String cardPrice = price.html();
                
                if (cardName.indexOf("(") > 0) {
                    continue;
                }

                if (cardName.indexOf(" - ") > 0) {
                    kwCard.setName(StringUtils.substringBefore(cardName, " - ").trim());
                    kwCard.setSubtitle(StringUtils.substringBetween(cardName, " - ", " + "));
                } else {
                    kwCard.setName(StringUtils.substringBefore(cardName, " + "));
                }
                kwCard.setDisplayName(Utilities.parseDisplayName(kwCard.getName()));
                kwCard.setPrice(StringUtils.substringBefore(cardPrice, " -").replaceAll("\\$", ""));
                cardList.add(kwCard);
            }
        } catch (Exception localException) {
            System.out.println("Bummer dude");
        }
        kwResponse.setCardList(cardList);
        return kwResponse;
    }

}
