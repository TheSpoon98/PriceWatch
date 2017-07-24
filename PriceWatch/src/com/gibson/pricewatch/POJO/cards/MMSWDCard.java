package com.gibson.pricewatch.POJO.cards;

import com.gibson.pricewatch.POJO.interfaces.SWDCard;
import com.google.gson.annotations.SerializedName;

public class MMSWDCard implements SWDCard {
    private String name;
    private String displayName;
    private String subtitle;
    private String setName;
    private Integer position;
    private Integer code;
    private String price;
    private String sku;
    @SerializedName("in_stock")
    private String inStock;
    @SerializedName("url_key")
    private String urlKey;
    @SerializedName("grouped_product_json")
    private String groupedProductJson;

    /*
     * (non-Javadoc)
     * 
     * @see com.gibson.pricewatch.POJO.interfaces.SWDCard#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gibson.pricewatch.POJO.interfaces.SWDCard#getSetName()
     */
    @Override
    public String getSetName() {
        return setName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gibson.pricewatch.POJO.interfaces.SWDCard#getCardNum()
     */
    @Override
    public Integer getCardNum() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gibson.pricewatch.POJO.interfaces.SWDCard#getDisplayName()
     */
    @Override
    public String getDisplayName() {
        return displayName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gibson.pricewatch.POJO.interfaces.SWDCard#getCode()
     */
    @Override
    public Integer getCode() {
        return code;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gibson.pricewatch.POJO.interfaces.SWDCard#getPrice()
     */
    @Override
    public String getPrice() {
        return price;
    }

    /**
     * 
     * @param displayName the display name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    /**
     * @return the subtitle
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * @param subtitle the subtitle to set
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    /**
     * @return the position
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * @return the sku
     */
    public String getSku() {
        return sku;
    }

    /**
     * @param sku the sku to set
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * @return the inStock
     */
    public String getInStock() {
        return inStock;
    }

    /**
     * @param inStock the inStock to set
     */
    public void setInStock(String inStock) {
        this.inStock = inStock;
    }

    /**
     * @return the urlKey
     */
    public String getUrlKey() {
        return urlKey;
    }

    /**
     * @param urlKey the urlKey to set
     */
    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    /**
     * @return the groupedProductJson
     */
    public String getGroupedProductJson() {
        return groupedProductJson;
    }

    /**
     * @param groupedProductJson the groupedProductJson to set
     */
    public void setGroupedProductJson(String groupedProductJson) {
        this.groupedProductJson = groupedProductJson;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param setName the setName to set
     */
    public void setSetName(String setName) {
        this.setName = setName;
    }

    /**
     * @param code the code to set
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    
}
