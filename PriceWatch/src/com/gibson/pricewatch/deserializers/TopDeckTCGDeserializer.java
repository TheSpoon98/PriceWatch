package com.gibson.pricewatch.deserializers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gibson.pricewatch.POJO.cards.TDTCGSWDCard;
import com.gibson.pricewatch.POJO.responses.TopDeckTCGResponse;
import com.gibson.pricewatch.utilities.Utilities;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class TopDeckTCGDeserializer implements JsonDeserializer<TopDeckTCGResponse> {
    
    @Override
    public TopDeckTCGResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        TopDeckTCGResponse tdResponse = new Gson().fromJson(json.getAsJsonObject(), TopDeckTCGResponse.class);
        TopDeckTCGResponse tdResult = new Gson().fromJson(json.getAsJsonObject().getAsJsonObject("result"), TopDeckTCGResponse.class);

        List<TDTCGSWDCard> cardList = new ArrayList<>();
        Document doc = null;
        try {
            doc = Jsoup.parse(tdResult.getContent());
            Elements divs = doc.getElementsByClass("product-excerpt-link");
            for (Element src : divs) {
                Element name = src.getElementsByClass("product-excerpt-title").first();
                Element price = src.getElementsByClass("product-excerpt-price-regular").first();
                TDTCGSWDCard tdCard = new TDTCGSWDCard();
                String cardName = name.html().replaceAll("&nbsp;", " ");
                String cardPrice = price.html();
                
                if (cardName.indexOf("(") > 0) {
                    continue;
                }

                if (cardName.indexOf(" - ") > 0) {
                    tdCard.setName(StringUtils.substringBefore(cardName, " - ").trim());
                    tdCard.setSubtitle(StringUtils.substringBetween(cardName, " - ", " + "));
                } else {
                    tdCard.setName(StringUtils.substringBefore(cardName, " + "));
                }
                tdCard.setDisplayName(Utilities.parseDisplayName(tdCard.getName()));
                tdCard.setPrice(StringUtils.substringBefore(cardPrice, " -").replaceAll("\\$", ""));
                cardList.add(tdCard);
            }
        } catch (Exception localException) {
            System.out.println("Bummer dude");
        }
        tdResponse.setCardList(cardList);
        return tdResponse;
    }
}
