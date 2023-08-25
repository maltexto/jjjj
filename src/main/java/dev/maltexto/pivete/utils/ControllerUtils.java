package dev.maltexto.pivete.utils;

import static dev.maltexto.pivete.utils.JsonUtils.*;
import static dev.maltexto.pivete.utils.StringUtils.*;

import com.google.gson.JsonSyntaxException;

public class ControllerUtils {

    public static boolean isValidKeyword(String requestBody) {
        int minimumLength = 4;
        int maximumLength = 32;

        String keyword;

        try {
            keyword = getValueFromJsonString("keyword", requestBody);
        } catch (JsonSyntaxException exception) {
            return false;
        }

        return keyword != null && keyword.length() >= minimumLength && keyword.length() <= maximumLength;
    }

    public static boolean isAlphanumericAndEightChars(String str) {
        return isAlphanumeric(str) && str.length() <= 8;
    }
}