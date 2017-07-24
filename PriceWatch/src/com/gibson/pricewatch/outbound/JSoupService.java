package com.gibson.pricewatch.outbound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gibson.pricewatch.Stores;
import com.gibson.pricewatch.POJO.cards.CSISWDCard;
import com.gibson.pricewatch.POJO.cards.TCSWDCard;
import com.gibson.pricewatch.POJO.cards.TnTSWDCard;
import com.gibson.pricewatch.utilities.Utilities;

public class JSoupService {

    public List<CSISWDCard> getCSICardData() {
        List<CSISWDCard> cardList = new ArrayList<CSISWDCard>();
        Document doc = null;
        try {
            for (String storeURL : Stores.CSI.getUrlArray()) {
                String nextLink = storeURL;
                while (!nextLink.isEmpty()) {
                    doc = Jsoup.connect(nextLink).get();
                    Elements divs = doc.getElementsByAttributeValueMatching("itemtype", "http://schema.org/Product");
                    for (Element src : divs) {
                        Element eleName = src.getElementsByAttributeValue("itemprop", "name").first();
                        Element elePrice = src.getElementsByAttributeValue("itemprop", "price").first();
                        Element eleSet = src.getElementsByClass("sSec").first();

                        CSISWDCard csiCard = new CSISWDCard();
                        String price = elePrice.html().replaceAll("(<b>)*\\$", "").replaceAll("</b>", "");
                        if (elePrice.hasAttr("class")) {
                            if (StringUtils.equalsAnyIgnoreCase(elePrice.attr("class"),
                                    new CharSequence[] { "strike" })) {
                                price = "";
                                break;
                            }
                        }
                        csiCard.setPrice(price);

                        String cardName = eleName.html();
                        if (eleName.html().indexOf(" - ") > 0) {
                            csiCard.setName(StringUtils.substringBefore(cardName, " - "));
                            csiCard.setDisplayName(
                                    Utilities.parseDisplayName(StringUtils.substringBefore(cardName, " - ")));
                            csiCard.setSubtitle(StringUtils.substringAfter(cardName, " - "));
                        } else {
                            csiCard.setName(cardName);
                            csiCard.setDisplayName(Utilities.parseDisplayName(cardName));
                        }

                        csiCard.setSetName(StringUtils.substringBetween(eleSet.childNode(0).toString(), "y » ", "<"));
                        cardList.add(csiCard);
                    }
                    if (doc.getElementById("nextLink") != null) {
                        nextLink = doc.getElementById("nextLink").select("a").first().attr("abs:href");
                    } else {
                        nextLink = "";
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cardList;
    }

    public List<TCSWDCard> getTCCardData() {
        List<TCSWDCard> cardList = new ArrayList<TCSWDCard>();
        Document doc = null;
        try {
            for (String storeURL : Stores.TEAMCOV.getUrlArray()) {
                String nextLink = storeURL;
                while (!nextLink.isEmpty()) {
                    doc = Jsoup.connect(nextLink).get();
                    Elements divs = doc.getElementsByClass("instock");
                    for (Element src : divs) {
                        Element child = src.getElementsByClass("gtm4wp_productdata").first();
                        String eleName = child.attr("data-gtm4wp_product_name");
                        String price = child.attr("data-gtm4wp_product_price");
                        String code = child.attr("data-gtm4wp_product_id");
                        TCSWDCard teamCovCard = new TCSWDCard();

                        String cardName = eleName;
                        if (cardName.indexOf(" - ") > 0) {
                            teamCovCard.setName(StringUtils.substringBefore(cardName, " - "));
                            teamCovCard.setSubtitle(StringUtils.substringAfter(cardName, " - "));
                            cardName = StringUtils.substringBefore(cardName, " - ");
                        } else {
                            teamCovCard.setName(cardName);
                        }
                        teamCovCard.setDisplayName(Utilities.parseDisplayName(cardName));

                        teamCovCard.setPrice(price.trim());
                        teamCovCard.setCode(Integer.parseInt(StringUtils.replaceAll(code.trim(), "[A-Z\\-]+", "")));
                        String setName = "";
                        switch (StringUtils.left(StringUtils.replaceAll(code.trim(), "[A-Z\\-]+", ""), 2)) {
                            case "01":
                                setName = "Awakenings";
                                break;
                            case "02":
                                setName = "Spirit of Rebellion";
                                break;
                            default:
                        }
                        teamCovCard.setSetName(setName);
                        cardList.add(teamCovCard);
                    }
                    if (doc.getElementsByClass("next").size() > 0) {
                        nextLink = doc.getElementsByClass("next").select("a").first().attr("abs:href");
                    } else {
                        nextLink = "";
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cardList;
    }

    public List<TnTSWDCard> getTnTCardData() {
        List<TnTSWDCard> cardList = new ArrayList<TnTSWDCard>();
        Document doc = null;
        try {
            for (String storeURL : Stores.TROLLANDTOAD.getUrlArray()) {
                String nextLink = storeURL;
                while (!nextLink.isEmpty()) {
                    doc = Jsoup.connect(nextLink).get();
                    Elements divs = doc.getElementsByClass("cat_result_wrapper");
                    for (Element src : divs) {
                        Element eleChild = src.getElementsByClass("cat_result_text").first();
                        Element eleSetName = src.getElementsByClass("cat_result_category_link").first();
                        String name = eleChild.select("a[href]").first().html();
                        String qty = eleChild.getElementsByClass("quantity_text").first().html();
                        String setName = eleSetName.html();
                        String price = src.getElementsByClass("price_text").first().html();

                        if (Integer.parseInt(qty.replaceAll("[^\\d.]", "")) > 0) {
                            TnTSWDCard tntCard = new TnTSWDCard();

                            String[] info = StringUtils.splitByWholeSeparator(name, " - ");
                            switch (StringUtils.countMatches(name, " - ")) {
                                case 2:
                                    tntCard.setName(info[0]);
                                    tntCard.setDisplayName(Utilities.parseDisplayName(info[0]));
                                    tntCard.setPosition(Integer.valueOf(info[1].trim()).intValue());
                                    break;
                                case 3:
                                    tntCard.setName(info[0]);
                                    tntCard.setDisplayName(Utilities.parseDisplayName(info[0]));
                                    tntCard.setSubtitle(info[1].trim());
                                    tntCard.setPosition(Integer.valueOf(info[2].trim()).intValue());
                                    break;
                                default:
                                    continue;
                            }
                            tntCard.setSetName(StringUtils.substringBetween(setName, ": ", " Singles"));
                            tntCard.setPrice(StringUtils.substringBefore(price, " -").replaceAll("\\$", ""));

                            cardList.add(tntCard);
                        }
                    }
                    if (doc.getElementsByClass("next").size() > 0) {
                        nextLink = doc.getElementsByClass("next").select("a").first().attr("abs:href");
                    } else {
                        nextLink = "";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardList;
    }
}
