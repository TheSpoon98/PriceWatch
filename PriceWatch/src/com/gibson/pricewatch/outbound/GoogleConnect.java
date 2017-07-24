package com.gibson.pricewatch.outbound;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.gibson.pricewatch.Stores;
import com.gibson.pricewatch.POJO.cards.SWDestinyDBCard;
import com.gibson.pricewatch.POJO.set.SWDestinyDBSet;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

public class GoogleConnect {
    // private static final String APPLICATION_NAME = "Google Sheets API";
    private static final File DATA_STORE_DIR = new File(System.getProperty("user.home"),
            ".credentials/sheets.googleapis.com-java-quickstart");
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport HTTP_TRANSPORT;
    private static final List<String> SCOPES = Arrays
            .asList(new String[] { "https://www.googleapis.com/auth/spreadsheets" });

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public Credential authorize() throws IOException {
        InputStream in = GoogleConnect.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    public void createConnection(Map<String, SWDestinyDBCard> pCardData, Map<Integer, SWDestinyDBSet> pSetData,
            String pSheetID, String pRange) throws IOException, GeneralSecurityException {
        String valueInputOption = "USER_ENTERED";

        List<List<Object>> writeData = new ArrayList<>();
        List<SWDestinyDBCard> cardList = new ArrayList<>(pCardData.values());
        List<SWDestinyDBSet> setList = new ArrayList<>(pSetData.values());
        Collections.sort(cardList);
        Collections.sort(setList);
        List<Object> dataRow = new ArrayList<>();
        dataRow.add("Card");
        dataRow.add("Miniature Market");
        dataRow.add("Cool Stuff Inc");
        dataRow.add("Team Covenant");
        dataRow.add("KingWood Hobbies");
        dataRow.add("Troll And Toad");
        dataRow.add("Sparta Games");
        dataRow.add("Bytown Traders");
        writeData.add(dataRow);
        
        List<SWDestinyDBCard> removeList = new ArrayList<>();
        for (SWDestinyDBSet set : setList) {
            int cardNum = 1;
            cardList.removeAll(removeList);
            for (SWDestinyDBCard card : cardList) {
                if (!StringUtils.equals(set.getName(), card.getSetName())) {
                    continue;
                }
                dataRow = new ArrayList<>();
                if (cardNum < card.getCardNum()) {
                    for (int x = cardNum; x < card.getCardNum(); x++) {
                        dataRow.add("");
                        writeData.add(dataRow);
                        dataRow = new ArrayList<>();
                    }
                }
                dataRow.add(card.getName());
                
                dataRow.add(card.getPriceFromMap(Stores.MINIMART));
                dataRow.add(card.getPriceFromMap(Stores.CSI));
                dataRow.add(card.getPriceFromMap(Stores.TEAMCOV));
                dataRow.add(card.getPriceFromMap(Stores.KINGWOOD));
                dataRow.add(card.getPriceFromMap(Stores.TROLLANDTOAD));
                dataRow.add(card.getPriceFromMap(Stores.SPARTA));
                dataRow.add(card.getPriceFromMap(Stores.BYTOWN));
                removeList.add(card);
                cardNum = card.getCardNum()+1;
                writeData.add(dataRow);
                if (cardNum > set.getTotal()) {
                    break;
                }
            }
        }
        ValueRange requestBody = new ValueRange().setValues(writeData).setMajorDimension("ROWS");

        Sheets sheetsService = createSheetsService();
        Sheets.Spreadsheets.Values.Update request = sheetsService.spreadsheets().values().update(pSheetID, pRange,
                requestBody);
        request.setValueInputOption(valueInputOption);

        UpdateValuesResponse response = (UpdateValuesResponse) request.execute();

        System.out.println(response);
    }

    public Sheets createSheetsService() throws IOException, GeneralSecurityException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        Credential credential = authorize();

        return new Sheets.Builder(httpTransport, jsonFactory, credential).setApplicationName("Google Sheets API")
                .build();
    }
}
