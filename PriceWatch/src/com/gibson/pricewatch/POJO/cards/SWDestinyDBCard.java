package com.gibson.pricewatch.POJO.cards;

import java.util.HashMap;
import java.util.Map;

import com.gibson.pricewatch.Stores;
import com.google.gson.annotations.SerializedName;

public class SWDestinyDBCard implements Comparable<SWDestinyDBCard> {
    // **Defined in SWDestinyDB API**//
    private String name;
    private String subtitle;
    @SerializedName("set_name")
    private String setName;
    @SerializedName("set_code")
    private String setCode;
    private Integer code;
    @SerializedName("position")
    private int cardNum;
    @SerializedName("type_code")
    private String typeCode;
    @SerializedName("type_name")
    private String typeName;
    @SerializedName("faction_code")
    private String factionCode;
    @SerializedName("faction_name")
    private String factionName;
    @SerializedName("affiliation_code")
    private String affiliationCode;
    @SerializedName("affiliation_name")
    private String affiliationName;
    private String cost;
    private int health;
    private String points;
    private String text;
    @SerializedName("deck_limit")
    private int deckLimit;
    private String flavor;
    private String illustrator;
    @SerializedName("is_unique")
    private Boolean isUnique;
    @SerializedName("has_die")
    private Boolean hasDie;
    @SerializedName("has_errata")
    private Boolean hasErrata;
    private String imagesrc;
    private String url;

    // **Populated by SWDestinyCardDeserializer**/
    private String displayName; // Name, stripped of accents and quotes
    private int setOrder; // Incremental order of set release
    private Map<Stores, String> priceMap = new HashMap<Stores, String>();

    /*
     * (non-Javadoc)
     * 
     * @see com.gibson.pricewatch.POJO.cards.SWDCard#toString()
     */
    @Override
    public String toString() {
        return "SWDestinyCard [name=" + this.name + ", subtitle=" + this.subtitle + ", code=" + this.code
                + ", set_name=" + this.setName + ", setOrder=" + this.setOrder + ", cardNum=" + this.cardNum
                + ", imagesrc=" + this.imagesrc + ", url=" + this.url + ", priceMap=" + this.priceMap + "]";
    }

    @Override
    public int compareTo(SWDestinyDBCard compareCard) {
        int compareCardNum = compareCard.getCode();

        return this.code - compareCardNum;
    }

    public void addToPriceMap(Stores store, String price) {
        this.priceMap.put(store, price);
    }

    public String getPriceFromMap(Stores store) {
        return this.priceMap.get(store) == null ? "" : (String) this.priceMap.get(store);
    }

    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
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

    public String getSetName() {
        return setName;
    }

    /**
     * @param setName
     *            the setName to set
     */
    public void setSetName(String setName) {
        this.setName = setName;
    }

    /**
     * @return the setCode
     */
    public String getSetCode() {
        return setCode;
    }

    /**
     * @param setCode
     *            the setCode to set
     */
    public void setSetCode(String setCode) {
        this.setCode = setCode;
    }

    /**
     * @return the code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    public Integer getCardNum() {
        return cardNum;
    }

    /**
     * @param cardNum
     *            the cardNum to set
     */
    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }

    /**
     * @return the typeCode
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * @param typeCode
     *            the typeCode to set
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * @return the typeName
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * @param typeName
     *            the typeName to set
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * @return the factionCode
     */
    public String getFactionCode() {
        return factionCode;
    }

    /**
     * @param factionCode
     *            the factionCode to set
     */
    public void setFactionCode(String factionCode) {
        this.factionCode = factionCode;
    }

    /**
     * @return the factionName
     */
    public String getFactionName() {
        return factionName;
    }

    /**
     * @param factionName
     *            the factionName to set
     */
    public void setFactionName(String factionName) {
        this.factionName = factionName;
    }

    /**
     * @return the affiliationCode
     */
    public String getAffiliationCode() {
        return affiliationCode;
    }

    /**
     * @param affiliationCode
     *            the affiliationCode to set
     */
    public void setAffiliationCode(String affiliationCode) {
        this.affiliationCode = affiliationCode;
    }

    /**
     * @return the affiliationName
     */
    public String getAffiliationName() {
        return affiliationName;
    }

    /**
     * @param affiliationName
     *            the affiliationName to set
     */
    public void setAffiliationName(String affiliationName) {
        this.affiliationName = affiliationName;
    }

    /**
     * @return the cost
     */
    public String getCost() {
        return cost;
    }

    /**
     * @param cost
     *            the cost to set
     */
    public void setCost(String cost) {
        this.cost = cost;
    }

    /**
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * @param health
     *            the health to set
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * @return the points
     */
    public String getPoints() {
        return points;
    }

    /**
     * @param points
     *            the points to set
     */
    public void setPoints(String points) {
        this.points = points;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     *            the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the deckLimit
     */
    public int getDeckLimit() {
        return deckLimit;
    }

    /**
     * @param deckLimit
     *            the deckLimit to set
     */
    public void setDeckLimit(int deckLimit) {
        this.deckLimit = deckLimit;
    }

    /**
     * @return the flavor
     */
    public String getFlavor() {
        return flavor;
    }

    /**
     * @param flavor
     *            the flavor to set
     */
    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    /**
     * @return the illustrator
     */
    public String getIllustrator() {
        return illustrator;
    }

    /**
     * @param illustrator
     *            the illustrator to set
     */
    public void setIllustrator(String illustrator) {
        this.illustrator = illustrator;
    }

    /**
     * @return the isUnique
     */
    public Boolean getIsUnique() {
        return isUnique;
    }

    /**
     * @param isUnique
     *            the isUnique to set
     */
    public void setIsUnique(Boolean isUnique) {
        this.isUnique = isUnique;
    }

    /**
     * @return the hasDie
     */
    public Boolean getHasDie() {
        return hasDie;
    }

    /**
     * @param hasDie
     *            the hasDie to set
     */
    public void setHasDie(Boolean hasDie) {
        this.hasDie = hasDie;
    }

    /**
     * @return the hasErrata
     */
    public Boolean getHasErrata() {
        return hasErrata;
    }

    /**
     * @param hasErrata
     *            the hasErrata to set
     */
    public void setHasErrata(Boolean hasErrata) {
        this.hasErrata = hasErrata;
    }

    /**
     * @return the imagesrc
     */
    public String getImagesrc() {
        return imagesrc;
    }

    /**
     * @param imagesrc
     *            the imagesrc to set
     */
    public void setImagesrc(String imagesrc) {
        this.imagesrc = imagesrc;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName
     *            the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the setOrder
     */
    public int getSetOrder() {
        return setOrder;
    }

    /**
     * @param setOrder
     *            the setOrder to set
     */
    public void setSetOrder(int setOrder) {
        this.setOrder = setOrder;
    }

    /**
     * @return the priceMap
     */
    public Map<Stores, String> getPriceMap() {
        return priceMap;
    }

    /**
     * @param priceMap
     *            the priceMap to set
     */
    public void setPriceMap(Map<Stores, String> priceMap) {
        this.priceMap = priceMap;
    }
}
