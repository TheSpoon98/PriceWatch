package com.gibson.pricewatch.POJO.cards;

import com.gibson.pricewatch.POJO.interfaces.SWDCard;

public class TnTSWDCard implements SWDCard {

    private String name;
    private String displayName;
    private String subtitle;
    private String price;
    private Integer code;
    private String setName;
    private Integer position;

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
        return code;
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
     * @param code
     *            the code to set
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @param setName
     *            the setName to set
     */
    public void setSetName(String setName) {
        this.setName = setName;
    }

    /**
     * @return the position
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * @param position
     *            the position to set
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

}
