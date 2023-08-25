package dev.maltexto.pivete.utils;

public class StringUtils {
    public static Boolean isAlphanumeric(String str) {
        String regex = "[a-zA-Z0-9]+";
        return str.matches(regex);
    }
}
