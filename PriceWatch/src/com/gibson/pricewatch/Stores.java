package com.gibson.pricewatch;

public enum Stores {
    MINIMART("Miniature Market", null),
    CSI("Cool Stuff Inc", new String[] { "http://www.coolstuffinc.com/page/2801?sh=1",
            "http://www.coolstuffinc.com/page/3544?sh=1" }),
    TEAMCOV("Team Covenant", new String[] {"https://teamcovenant.com/product-category/star-wars-destiny-card-game-ffg/buy-destiny-singles" }),
    KINGWOOD("KingWood Hobbies", null),
    TROLLANDTOAD("Troll and Toad", new String[] {"http://www.trollandtoad.com/Force-of-Will-and-Other-CCGs/10349.html?pageLimiter=500" }),
    SPARTA("Sparta Games", null),
    BYTOWN("Bytown Traders", null),
    TOPDECKTCG("Top Deck TCG", null);

    private final String storeName;
    private final String[] urlArray;

    private Stores(String storeName, String[] urlArray) {
        this.storeName = storeName;
        this.urlArray = urlArray;
    }

    public String getStoreName() {
        return this.storeName;
    }
    public String[] getUrlArray() {
        return this.urlArray;
    }
}
