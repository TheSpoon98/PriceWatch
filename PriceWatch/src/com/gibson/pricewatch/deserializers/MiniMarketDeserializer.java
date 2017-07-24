/**
 * 
 */
package com.gibson.pricewatch.deserializers;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.gibson.pricewatch.POJO.cards.MMSWDCard;
import com.gibson.pricewatch.POJO.responses.MiniMarketResponse;
import com.gibson.pricewatch.utilities.Utilities;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * @author gibsonn2
 *
 */
public class MiniMarketDeserializer implements JsonDeserializer<MiniMarketResponse> {
    
    public MiniMarketResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        MiniMarketResponse mmResponse = new Gson().fromJson(json.getAsJsonObject(), MiniMarketResponse.class);
        MiniMarketResponse jsonPagination = new Gson().fromJson(json.getAsJsonObject().getAsJsonObject("pagination"), MiniMarketResponse.class);

        for (MMSWDCard pCard : mmResponse.getCardList()) {
            String cardName = pCard.getName().replace("''", "\"");
            if (cardName.lastIndexOf(":") > 0) {
                pCard.setName(StringUtils.substringBefore(cardName, ":").trim());
                pCard.setSubtitle(StringUtils.substringBetween(cardName, ": ", " - ").trim());
                pCard.setDisplayName(Utilities.parseDisplayName(pCard.getName()));
                
            } else {
                pCard.setName(StringUtils.substringBefore(cardName, " - ").trim());
                pCard.setDisplayName(Utilities.parseDisplayName(pCard.getName()));
            }
            if (cardName.lastIndexOf("(") > 0) {
                pCard.setSetName(StringUtils.substringBetween(cardName, " - ", "(").trim());
            } else {
                pCard.setSetName(StringUtils.substringAfter(cardName, " - ").trim());
            }
            Integer position = Integer.valueOf(Integer.parseInt(StringUtils.substringAfterLast(pCard.getUrlKey(), "-")));
            pCard.setPosition(position.intValue());

            Integer code = Integer.valueOf(Integer.parseInt(StringUtils.replaceAll(pCard.getUrlKey(), "[a-z\\-]", "")));
            pCard.setCode(code.intValue());

            Map<String, String> groupedMap = new HashMap<String, String>();

            if (Integer.valueOf(pCard.getInStock()).intValue() > 0) {
                StringBuffer sb = new StringBuffer(pCard.getGroupedProductJson().replace("&quot;", "").replace("[", "").replace("]", ""));
                String[] subArray = sb.substring(1, sb.indexOf("}")).split("\\|");
                for (int x = 0; x < subArray.length; x++) {
                    groupedMap.put(subArray[x].split(":")[0], subArray[x].split(":")[1]);
                }
                pCard.setPrice(groupedMap.get("price").trim());
            } else {
                pCard.setPrice("");
            }
        }
        jsonPagination.setCardList(mmResponse.getCardList());

        return jsonPagination;
    }
}