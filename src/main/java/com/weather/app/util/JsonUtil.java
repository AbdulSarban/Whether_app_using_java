package com.weather.app.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class JsonUtil {

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static <T> T fromJson(String json, Class<T> clazz) throws JsonSyntaxException {
        if (json == null || json.trim().isEmpty()) {
            throw new JsonSyntaxException("JSON string is null or empty");
        }
        return gson.fromJson(json, clazz);
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static boolean isValidJson(String json) {
        try {
            gson.fromJson(json, Object.class);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }
}
