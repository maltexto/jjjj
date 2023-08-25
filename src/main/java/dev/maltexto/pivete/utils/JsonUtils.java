package dev.maltexto.pivete.utils;

import com.google.gson.*;

public class JsonUtils {

    private static final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public static String getValueFromJsonString(String key, String str) throws JsonSyntaxException {
        JsonObject json = JsonParser.parseString(str).getAsJsonObject();

        if (!json.has(key)) {
            return null;
        }
        return json.get(key).getAsString();
    }

    public static String toJson(String key, String value) {
        JsonObject reponse = new JsonObject();
        reponse.addProperty(key, value);
        return gson.toJson(reponse);
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }
}
