package com.gibson.pricewatch.POJO.responses;

import java.util.List;

import com.gibson.pricewatch.POJO.cards.SpartaSWDCard;

public class SpartaResponse {
    private List<SpartaSWDCard> cardList;

    /**
     * @return the cardList
     */
    public List<SpartaSWDCard> getCardList() {
        return cardList;
    }

    /**
     * @param cardList
     *            the cardList to set
     */
    public void setCardList(List<SpartaSWDCard> cardList) {
        this.cardList = cardList;
    }

}
