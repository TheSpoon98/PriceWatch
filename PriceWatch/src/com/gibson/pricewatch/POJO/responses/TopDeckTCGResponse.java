package com.gibson.pricewatch.POJO.responses;

import java.util.List;

import com.gibson.pricewatch.POJO.cards.TDTCGSWDCard;

public class TopDeckTCGResponse {

    private String jsonrpc;
    private Integer id;
    private String method;
    private String content;
    private String pageList;
    private Integer total;
    private List<TDTCGSWDCard> cardList;

    /**
     * @return the jsonrpc
     */
    public String getJsonrpc() {
        return jsonrpc;
    }

    /**
     * @param jsonrpc
     *            the jsonrpc to set
     */
    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method
     *            the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the pageList
     */
    public String getPageList() {
        return pageList;
    }

    /**
     * @param pageList
     *            the pageList to set
     */
    public void setPageList(String pageList) {
        this.pageList = pageList;
    }

    /**
     * @return the total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * @param total
     *            the total to set
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * @return the cardList
     */
    public List<TDTCGSWDCard> getCardList() {
        return cardList;
    }

    /**
     * @param cardList
     *            the cardList to set
     */
    public void setCardList(List<TDTCGSWDCard> cardList) {
        this.cardList = cardList;
    }

}
