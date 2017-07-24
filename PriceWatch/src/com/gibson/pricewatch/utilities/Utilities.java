package com.gibson.pricewatch.utilities;

import org.apache.commons.lang3.StringUtils;

public class Utilities {

    public static String parseDisplayName(String pName) {
        String displayName = "";
        if (pName != null) {
            displayName = pName;
            displayName = StringUtils.stripAccents(displayName);
            displayName = displayName.replaceAll("[^A-Za-z0-9]", "");
//            displayName = StringUtils.replaceAll(displayName, "’", "'");
//            displayName = StringUtils.replaceAll(displayName, "\"", "");
            
        }
        return displayName;
    }
}
