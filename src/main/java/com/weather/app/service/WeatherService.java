package com.weather.app.service;

import com.weather.app.model.Weather;
import com.weather.app.model.WeatherData;
import com.weather.app.util.HttpUtil;
import com.weather.app.util.JsonUtil;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class WeatherService {

    private static final String API_BASE_URL = "https://api.openweathermap.org/data/2.5/weather";
    private final String apiKey;

    public WeatherService(String apiKey) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("API key cannot be null or empty");
        }
        this.apiKey = apiKey.trim();
    }

    public Weather getWeather(String cityName) throws IOException {
        validateCityName(cityName);

        String url = buildApiUrl(cityName);
        String jsonResponse = HttpUtil.get(url);

        try {
            WeatherData weatherData = JsonUtil.fromJson(jsonResponse, WeatherData.class);
            return weatherData.toWeather();
        } catch (Exception e) {
            throw new IOException("Failed to parse weather data: " + e.getMessage(), e);
        }
    }

    public CompletableFuture<Weather> getWeatherAsync(String cityName) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getWeather(cityName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private String buildApiUrl(String cityName) {
        return String.format("%s?q=%s&appid=%s&units=metric",
                API_BASE_URL,
                HttpUtil.urlEncode(cityName),
                apiKey);
    }

    private void validateCityName(String cityName) {
        if (cityName == null || cityName.trim().isEmpty()) {
            throw new IllegalArgumentException("City name cannot be null or empty");
        }

        if (cityName.trim().length() < 2) {
            throw new IllegalArgumentException("City name must be at least 2 characters long");
        }
    }

    public String getIconUrl(String iconCode) {
        if (iconCode == null || iconCode.trim().isEmpty()) {
            return null;
        }
        return String.format("https://openweathermap.org/img/wn/%s@2x.png", iconCode);
    }
}
