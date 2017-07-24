package com.gibson.pricewatch.datafactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.gibson.pricewatch.Stores;
import com.gibson.pricewatch.POJO.cards.BytownSWDCard;
import com.gibson.pricewatch.POJO.cards.CSISWDCard;
import com.gibson.pricewatch.POJO.cards.KWSWDCard;
import com.gibson.pricewatch.POJO.cards.MMSWDCard;
import com.gibson.pricewatch.POJO.cards.SWDestinyDBCard;
import com.gibson.pricewatch.POJO.cards.SpartaSWDCard;
import com.gibson.pricewatch.POJO.cards.TCSWDCard;
import com.gibson.pricewatch.POJO.cards.TnTSWDCard;
import com.gibson.pricewatch.POJO.exchange.ExchangeRate;
import com.gibson.pricewatch.POJO.interfaces.SWDCard;
import com.gibson.pricewatch.POJO.set.SWDestinyDBSet;
import com.gibson.pricewatch.outbound.JSONService;
import com.gibson.pricewatch.outbound.JSoupService;

public class DataHandler {
    private JSONService jsonService = new JSONService();
    private JSoupService jsoupService = new JSoupService();
    public Map<String, SWDestinyDBCard> allCards = new HashMap<String, SWDestinyDBCard>();
    public Map<Integer, SWDestinyDBSet> allSets = new HashMap<Integer, SWDestinyDBSet>();
    public List<ExchangeRate> exchangeRates = new ArrayList<ExchangeRate>();

    public Integer populateSWDCardData() {
        this.allCards = jsonService.populateCardData();
        this.allSets = jsonService.populateSetData();
        this.exchangeRates = jsonService.getExchangeRates();

        return allCards.size();
    }

    @SuppressWarnings("unchecked")
    public <T> boolean populateStorePrice(Stores store) {

        try {
            switch (store) {
                case MINIMART:
                    List<MMSWDCard> mmCardMap = jsonService.getMMResponses();
                    addStorePriceToSet(Stores.MINIMART, (List<T>) mmCardMap);
                    break;
                case KINGWOOD:
                    List<KWSWDCard> kwCardList = jsonService.getKWResponses();
                    addStorePriceToSet(Stores.KINGWOOD, (List<T>) kwCardList);
                    break;
                case SPARTA:
                    List<SpartaSWDCard> spCardList = jsonService.getSPResponses();
                    addStorePriceToSet(Stores.SPARTA, (List<T>) spCardList);
                    break;
                case BYTOWN:
                    List<BytownSWDCard> byCardList = jsonService.getBytownResponses();
                    addStorePriceToSet(Stores.BYTOWN, (List<T>) byCardList);
                    break;
                case CSI:
                    List<CSISWDCard> csiCardList = jsoupService.getCSICardData();
                    addStorePriceToSet(Stores.CSI, (List<T>) csiCardList);
                    break;
                case TEAMCOV:
                    List<TCSWDCard> tcCardList = jsoupService.getTCCardData();
                    addStorePriceToSet(Stores.TEAMCOV, (List<T>) tcCardList);
                    break;
                case TROLLANDTOAD:
                    List<TnTSWDCard> tntCardList = jsoupService.getTnTCardData();
                    addStorePriceToSet(Stores.TROLLANDTOAD, (List<T>) tntCardList);
                    break;
                case TOPDECKTCG:
                default:
            }
        } catch (Exception e) {
            System.out.println("Error Populating Store Price.\n" + e.getMessage());
            return false;
        }
        return true;
    }

    private <T> void addStorePriceToSet(Stores store, List<T> cardList) {
        for (T card : cardList) {
            try {
                String key = ((SWDCard) card).getSetName().toUpperCase()
                        + ((SWDCard) card).getDisplayName().toUpperCase();
                Float rate = Float.valueOf(1.0F);
                if (store == Stores.BYTOWN) {
                    for (ExchangeRate r : this.exchangeRates) {
                        if (StringUtils.equalsAnyIgnoreCase(r.getId(), new CharSequence[] { "CADUSD" })) {
                            rate = Float.valueOf(r.getRate());
                            break;
                        }
                    }
                }
                String price = ((SWDCard) card).getPrice();
                if (StringUtils.isNotEmpty(price)) {
                    price = String.valueOf(Float.valueOf(((SWDCard) card).getPrice()).floatValue() * rate.floatValue());
                }
                allCards.get(key).addToPriceMap(store, price);
            } catch (NullPointerException e) {
                System.out.println(store.name() + " " + ((SWDCard) card).getDisplayName());
            }
        }
    }
}
