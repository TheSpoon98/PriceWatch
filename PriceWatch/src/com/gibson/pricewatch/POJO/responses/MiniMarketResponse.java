/**
 * 
 */
package com.gibson.pricewatch.POJO.responses;

import java.util.List;

import com.gibson.pricewatch.POJO.cards.MMSWDCard;
import com.google.gson.annotations.SerializedName;

/**
 * @author gibsonn2
 *
 */
public class MiniMarketResponse {
    private Integer totalResults;
    private Integer begin;
    private Integer end;
    private Integer currentPage;
    private Integer totalPages;
    private Integer previousPage;
    private Integer nextPage;
    private Integer perPage;
    private Integer defaultPerPage;
    @SerializedName("results")
    private List<MMSWDCard> cardList;

    /**
     * @return the totalResults
     */
    public Integer getTotalResults() {
        return totalResults;
    }

    /**
     * @param totalResults
     *            the totalResults to set
     */
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    /**
     * @return the begin
     */
    public Integer getBegin() {
        return begin;
    }

    /**
     * @param begin
     *            the begin to set
     */
    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    /**
     * @return the end
     */
    public Integer getEnd() {
        return end;
    }

    /**
     * @param end
     *            the end to set
     */
    public void setEnd(Integer end) {
        this.end = end;
    }

    /**
     * @return the currentPage
     */
    public Integer getCurrentPage() {
        return currentPage;
    }

    /**
     * @param currentPage
     *            the currentPage to set
     */
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * @return the totalPages
     */
    public Integer getTotalPages() {
        return totalPages;
    }

    /**
     * @param totalPages
     *            the totalPages to set
     */
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * @return the previousPage
     */
    public Integer getPreviousPage() {
        return previousPage;
    }

    /**
     * @param previousPage
     *            the previousPage to set
     */
    public void setPreviousPage(Integer previousPage) {
        this.previousPage = previousPage;
    }

    /**
     * @return the nextPage
     */
    public Integer getNextPage() {
        return nextPage;
    }

    /**
     * @param nextPage
     *            the nextPage to set
     */
    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    /**
     * @return the perPage
     */
    public Integer getPerPage() {
        return perPage;
    }

    /**
     * @param perPage
     *            the perPage to set
     */
    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    /**
     * @return the defaultPerPage
     */
    public Integer getDefaultPerPage() {
        return defaultPerPage;
    }

    /**
     * @param defaultPerPage
     *            the defaultPerPage to set
     */
    public void setDefaultPerPage(Integer defaultPerPage) {
        this.defaultPerPage = defaultPerPage;
    }

    /**
     * @return the cardList
     */
    public List<MMSWDCard> getCardList() {
        return cardList;
    }

    /**
     * @param cardList
     *            the cardList to set
     */
    public void setCardList(List<MMSWDCard> cardList) {
        this.cardList = cardList;
    }
}
