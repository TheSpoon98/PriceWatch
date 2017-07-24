package com.gibson.pricewatch.POJO.cards;

import com.gibson.pricewatch.POJO.interfaces.SWDCard;
import com.google.gson.annotations.SerializedName;

public class SpartaSWDCard implements SWDCard {

    @SerializedName("title")
    private String name;
    private String displayName;
    private String subtitle;
    private String price;
    @SerializedName("set_Name")
    private String setName;
    private String avail;
    private String sku;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSetName() {
        return setName;
    }

    @Override
    public Integer getCardNum() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public Integer getCode() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPrice() {
        return price;
    }

    /**
     * @return the subtitle
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * @param subtitle
     *            the subtitle to set
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    /**
     * @return the avail
     */
    public String getAvail() {
        return avail;
    }

    /**
     * @param avail
     *            the avail to set
     */
    public void setAvail(String avail) {
        this.avail = avail;
    }

    /**
     * @return the sku
     */
    public String getSku() {
        return sku;
    }

    /**
     * @param sku
     *            the sku to set
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param displayName
     *            the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @param price
     *            the price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * @param setName
     *            the setName to set
     */
    public void setSetName(String setName) {
        this.setName = setName;
    }

}
