package com.gibson.pricewatch.outbound;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import com.gibson.pricewatch.POJO.cards.BytownSWDCard;
import com.gibson.pricewatch.POJO.cards.KWSWDCard;
import com.gibson.pricewatch.POJO.cards.MMSWDCard;
import com.gibson.pricewatch.POJO.cards.SWDestinyDBCard;
import com.gibson.pricewatch.POJO.cards.SpartaSWDCard;
import com.gibson.pricewatch.POJO.cards.TDTCGSWDCard;
import com.gibson.pricewatch.POJO.exchange.ExchangeRate;
import com.gibson.pricewatch.POJO.responses.BytownResponse;
import com.gibson.pricewatch.POJO.responses.KingWoodResponse;
import com.gibson.pricewatch.POJO.responses.MiniMarketResponse;
import com.gibson.pricewatch.POJO.responses.SpartaResponse;
import com.gibson.pricewatch.POJO.set.SWDestinyDBSet;
import com.gibson.pricewatch.deserializers.BytownDeserializer;
import com.gibson.pricewatch.deserializers.ExchangeRateDeserializer;
import com.gibson.pricewatch.deserializers.KingWoodDeserializer;
import com.gibson.pricewatch.deserializers.MiniMarketDeserializer;
import com.gibson.pricewatch.deserializers.SWDestinyCardDeserializer;
import com.gibson.pricewatch.deserializers.SpartaDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JSONService {

    public Map<String, SWDestinyDBCard> populateCardData() {
        URI uri = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        StringBuffer result = new StringBuffer();
        String line = "";
        BufferedReader rd = null;
        try {
            uri = new URIBuilder().setScheme("https").setHost("swdestinydb.com/api/public/cards/").build();
            HttpGet httpget = new HttpGet(uri);
            httpget.addHeader("User-Agent", "Mozilla/4.0");
            response = httpclient.execute(httpget);
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            response.close();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Type swdSetType = new TypeToken<HashSet<SWDestinyDBCard>>() {}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(SWDestinyDBCard.class, new SWDestinyCardDeserializer());
        Gson gson = gsonBuilder.create();

        HashSet<SWDestinyDBCard> cardSet = (HashSet<SWDestinyDBCard>) gson.fromJson(result.toString(), swdSetType);
        Map<String, SWDestinyDBCard> returnMap = new HashMap<String, SWDestinyDBCard>();
        for (SWDestinyDBCard card : cardSet) {
            String key = card.getSetName().toUpperCase() + card.getDisplayName().toUpperCase();
            returnMap.put(key, card);
        }

        return returnMap;
    }
    
    public Map<Integer, SWDestinyDBSet> populateSetData() {
        URI uri = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        StringBuffer result = new StringBuffer();
        String line = "";
        BufferedReader rd = null;
        try {
            uri = new URIBuilder().setScheme("https").setHost("swdestinydb.com/api/public/sets/").build();
            HttpGet httpget = new HttpGet(uri);
            httpget.addHeader("User-Agent", "Mozilla/4.0");
            response = httpclient.execute(httpget);
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            response.close();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Type swdSetType = new TypeToken<HashSet<SWDestinyDBSet>>() {}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        HashSet<SWDestinyDBSet> cardSet = (HashSet<SWDestinyDBSet>) gson.fromJson(result.toString(), swdSetType);
        Map<Integer, SWDestinyDBSet> returnMap = new HashMap<Integer, SWDestinyDBSet>();
        for (SWDestinyDBSet set : cardSet) {
            returnMap.put(set.getPosition(), set);
        }

        return returnMap;
    }

    public List<ExchangeRate> getExchangeRates() {
        URI uri = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        StringBuffer result = new StringBuffer();
        String line = "";
        BufferedReader rd = null;
        try {
            uri = new URIBuilder().setScheme("https").setHost("query.yahooapis.com/v1/public/yql")
                            .setParameter("q", "select * from yahoo.finance.xchange where pair in (\"CADUSD\")")
                            .setParameter("format", "json")
                            .setParameter("env", "store://datatables.org/alltableswithkeys").build();
            HttpGet httpget = new HttpGet(uri);
            httpget.addHeader("User-Agent", "Mozilla/4.0");
            response = httpclient.execute(httpget);
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            response.close();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type exchangeType = new TypeToken<List<ExchangeRate>>() {}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(exchangeType, new ExchangeRateDeserializer());
        Gson gson = gsonBuilder.create();

        List<ExchangeRate> returnList = (List<ExchangeRate>) gson.fromJson(result.toString(), exchangeType);

        return returnList;
    }

    public List<MMSWDCard> getMMResponses() {
        List<MMSWDCard> responseList = new ArrayList<>();
        URI uri = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        BufferedReader rd = null;
        StringBuffer result = new StringBuffer();
        String line = "";

        int nextPage = 1;
        while (nextPage > 0) {
            result = new StringBuffer();
            try {
                uri = new URIBuilder().setScheme("http").setHost("api.searchspring.net/api/search/search.json")
                        .setParameter("websiteKey", "6f9c319d45519a85863e68be9c3f5d81")
                        .setParameter("resultsFormat", "native")
                        .setParameter("bgfilter.category_hierarchy", "Board Games/Star Wars Destiny/Singles")
                        .setParameter("page", String.valueOf(nextPage)).setParameter("sort.sku_lowercase", "asc")
                        .build();
                HttpGet httpget = new HttpGet(uri);
                httpget.addHeader("User-Agent", "Mozilla/4.0");

                response = httpclient.execute(httpget);
                rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                response.close();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(MiniMarketResponse.class, new MiniMarketDeserializer());
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            MiniMarketResponse mmCardObject = (MiniMarketResponse) gson.fromJson(result.toString(),
                    MiniMarketResponse.class);
            responseList.addAll(mmCardObject.getCardList());
            nextPage = mmCardObject.getNextPage();
        }

        return responseList;
    }

    public List<KWSWDCard> getKWResponses() {
        List<KWSWDCard> returnList = new ArrayList<>();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        BufferedReader rd = null;
        StringBuffer result = null;
        String line = "";

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        for (int setId = 18; setId >= 17; setId--) {
            result = new StringBuffer();
            try {
                HttpPost request = new HttpPost("http://www.kingwoodhobbies.com/ajax/api/JsonRPC/Commerce/");

                StringEntity params = new StringEntity(
                        "{\"jsonrpc\":\"2.0\",\"method\":\"Category::generateProductList\",\"params\":[\"" + setId
                                + "\",\"0\",\"200\"],\"id\":0} ");
                request.addHeader("content-type", "application/json");
                request.addHeader("charset", "UTF-8");
                request.setEntity(params);

                CloseableHttpResponse response = httpClient.execute(request);

                response = httpclient.execute(request);
                rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                response.close();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(KingWoodResponse.class, new KingWoodDeserializer());
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            KingWoodResponse kwCardObject = (KingWoodResponse) gson.fromJson(result.toString(), KingWoodResponse.class);
            for (KWSWDCard card : kwCardObject.getCardList()) {
                if (setId == 18) {
                    card.setSetName("Awakenings");
                } else if (setId == 17) {
                    card.setSetName("Spirit of Rebellion");
                }
            }
            returnList.addAll(kwCardObject.getCardList());
        }
        return returnList;
    }

    public List<SpartaSWDCard> getSPResponses() {
        List<SpartaSWDCard> resultList = new ArrayList<>();
        URI uri = null;
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        CookieStore cookieStore = new BasicCookieStore();
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).setDefaultCookieStore(cookieStore).build();
        CloseableHttpResponse response = null;
        BufferedReader rd = null;
        StringBuffer result = new StringBuffer();
        String line = "";
        for (int pageNo = 1; pageNo < 5; pageNo++) {
            result = new StringBuffer();
            try {
                uri = new URIBuilder().setScheme("https")
                        .setHost("sparta-games-2.myshopify.com/collections/star-wars-destiny/products.json")
                        .setParameter("limit", "100").setParameter("q", "Destiny+Single")
                        .setParameter("page", String.valueOf(pageNo)).build();
                HttpGet httpget = new HttpGet(uri);
                httpget.addHeader("User-Agent", "Mozilla/4.0");

                response = httpClient.execute(httpget);
                rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                response.close();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(SpartaResponse.class, new SpartaDeserializer());
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            SpartaResponse spCardObject = (SpartaResponse) gson.fromJson(result.toString(), SpartaResponse.class);

            resultList.addAll(spCardObject.getCardList());
        }
        return resultList;
    }

    public List<BytownSWDCard> getBytownResponses() {
        List<BytownSWDCard> resultList = new ArrayList<>();
        URI uri = null;
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        CookieStore cookieStore = new BasicCookieStore();
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).setDefaultCookieStore(cookieStore).build();
        CloseableHttpResponse response = null;
        BufferedReader rd = null;
        StringBuffer result = new StringBuffer();
        String line = "";
        for (int pageNo = 1; pageNo < 5; pageNo++) {
            result = new StringBuffer();
            try {
                uri = new URIBuilder().setScheme("https")
                        .setHost("bytown-traders.myshopify.com/collections/swd-singles/products.json")
                        .setParameter("limit", "100").setParameter("page", String.valueOf(pageNo)).build();
                HttpGet httpget = new HttpGet(uri);
                httpget.addHeader("User-Agent", "Mozilla/4.0");

                response = httpClient.execute(httpget);
                rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                response.close();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(BytownResponse.class, new BytownDeserializer());
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            BytownResponse byCardObject = (BytownResponse) gson.fromJson(result.toString(), BytownResponse.class);

            resultList.addAll(byCardObject.getCardList());
        }
        return resultList;
    }
    
    public List<TDTCGSWDCard> getTopDeckResponses() {
        List<TDTCGSWDCard> returnList = new ArrayList<>();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        BufferedReader rd = null;
        StringBuffer result = null;
        String line = "";

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        result = new StringBuffer();
        try {
            HttpPost request = new HttpPost("https://ecom.wix.com/catalog/view");

            StringEntity params = new StringEntity(
                    "{\"viewName\":\"StoreFrontFiltersView\",\"params\":{\"enabledFilters\":[\"CATEGORY\",\"PRICE\",\"OPTIONS\"],\"appliedFilters\":[{\"field\":\"categoryId\",\"op\":\"EQUALS\",\"values\":[\"00000000-000000-000000-000000000001\"]}],\"categories\":{\"mainCategory\":\"00000000-000000-000000-000000000001\"}}}");
            request.addHeader("content-type", "application/json");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);

            CloseableHttpResponse response = httpClient.execute(request);

            response = httpclient.execute(request);
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            response.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnList;
    }
}
