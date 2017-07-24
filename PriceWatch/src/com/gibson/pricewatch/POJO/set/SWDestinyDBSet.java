package com.gibson.pricewatch.POJO.set;

public class SWDestinyDBSet implements Comparable<SWDestinyDBSet> {

    // **Defined in SWDestinyDB API**//
    private String name;
    private String code;
    private Integer position;
    private String available;
    private Integer known;
    private Integer total;
    private String url;

    @Override
    public String toString() {
        return this.position + " - " + this.name;        
    }
    
    @Override
    public int compareTo(SWDestinyDBSet position) {
        return Integer.compare(this.position, position.getPosition());
    }

    /**
     * @return the name
     */
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
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
        this.code = code;
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

    /**
     * @return the available
     */
    public String getAvailable() {
        return available;
    }

    /**
     * @param available
     *            the available to set
     */
    public void setAvailable(String available) {
        this.available = available;
    }

    /**
     * @return the known
     */
    public Integer getKnown() {
        return known;
    }

    /**
     * @param known
     *            the known to set
     */
    public void setKnown(Integer known) {
        this.known = known;
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

}
