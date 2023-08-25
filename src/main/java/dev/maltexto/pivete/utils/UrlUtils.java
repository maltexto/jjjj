package dev.maltexto.pivete.utils;

import java.net.URI;

public class UrlUtils {
    public static boolean isSameDomain(String domain, URI uri) {
        return domain.equalsIgnoreCase(uri.getHost());
    }

    public static boolean isSameDomain(URI uri, URI otherURI) {
        return uri.getHost().equalsIgnoreCase(otherURI.getHost());
    }
}
