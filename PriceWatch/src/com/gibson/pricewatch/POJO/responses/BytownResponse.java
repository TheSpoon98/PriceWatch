package com.gibson.pricewatch.POJO.responses;

import java.util.List;

import com.gibson.pricewatch.POJO.cards.BytownSWDCard;

public class BytownResponse {
    private List<BytownSWDCard> cardList;

    /**
     * @return the cardList
     */
    public List<BytownSWDCard> getCardList() {
        return cardList;
    }

    /**
     * @param cardList
     *            the cardList to set
     */
    public void setCardList(List<BytownSWDCard> cardList) {
        this.cardList = cardList;
    }

}
