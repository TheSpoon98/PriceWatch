/**
 * 
 */
package com.gibson.pricewatch.POJO.cards;

import com.gibson.pricewatch.POJO.interfaces.SWDCard;

/**
 * @author gibsonn2
 *
 */
public class CSISWDCard implements SWDCard {

    private String name;
    private String displayName;
    private String subtitle;
    private String price;
    private String setName;

    /**
     * @param displayName
     *            the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

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
        // TODO Auto-generated method stub
        return null;
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
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
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
