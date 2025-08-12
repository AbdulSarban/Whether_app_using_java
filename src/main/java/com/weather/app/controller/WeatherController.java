package com.weather.app.controller;

import com.weather.app.model.Weather;
import com.weather.app.service.WeatherService;
import com.weather.app.view.WeatherView;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;

public class WeatherController {

    private final WeatherView view;
    private final WeatherService weatherService;

    public WeatherController(WeatherView view, String apiKey) {
        this.view = view;
        this.weatherService = new WeatherService(apiKey);
        initializeEventHandlers();
    }

    private void initializeEventHandlers() {
        view.getGetWeatherButton().setOnAction(e -> handleGetWeather());
        view.getCityInput().setOnAction(e -> handleGetWeather());

        view.getCityInput().textProperty().addListener((obs, oldText, newText) -> {
            view.getGetWeatherButton().setDisable(newText.trim().isEmpty());
        });

        view.getGetWeatherButton().setDisable(true);
    }

    private void handleGetWeather() {
        String cityName = view.getCityInput().getText().trim();

        if (cityName.isEmpty()) {
            showError("Please enter a city name");
            return;
        }

        setLoadingState(true);
        view.hideWeatherInfo();
        view.getStatusLabel().setText("Fetching weather data...");

        Task<Weather> weatherTask = new Task<Weather>() {
            @Override
            protected Weather call() throws Exception {
                return weatherService.getWeather(cityName);
            }

            @Override
            protected void succeeded() {
                Platform.runLater(() -> {
                    Weather weather = getValue();
                    displayWeather(weather);
                    setLoadingState(false);
                });
            }

            @Override
            protected void failed() {
                Platform.runLater(() -> {
                    Throwable exception = getException();
                    handleWeatherError(exception);
                    setLoadingState(false);
                });
            }
        };

        Thread weatherThread = new Thread(weatherTask);
        weatherThread.setDaemon(true);
        weatherThread.start();
    }

    private void displayWeather(Weather weather) {
        view.getCityLabel().setText(weather.getCityName());
        view.getTemperatureLabel().setText(String.format("%.1f°C", weather.getTemperature()));
        view.getHumidityLabel().setText(String.format("%d%%", weather.getHumidity()));
        view.getWindSpeedLabel().setText(String.format("%.1f m/s", weather.getWindSpeed()));
        view.getConditionLabel().setText(capitalizeFirst(weather.getDescription()));

        String iconUrl = weatherService.getIconUrl(weather.getIconCode());
        view.loadWeatherIcon(iconUrl);

        view.showWeatherInfo();
        view.getStatusLabel().setText("Weather data updated successfully");
        view.getCityInput().clear();
    }

    private void handleWeatherError(Throwable exception) {
        String errorMessage;

        if (exception.getMessage().contains("City not found")) {
            errorMessage = "City not found. Please check the spelling and try again.";
        } else if (exception.getMessage().contains("timeout")) {
            errorMessage = "Request timed out. Please check your internet connection.";
        } else if (exception.getMessage().contains("HTTP Error")) {
            errorMessage = "Service temporarily unavailable. Please try again later.";
        } else {
            errorMessage = "Failed to fetch weather data: " + exception.getMessage();
        }

        view.getStatusLabel().setText(errorMessage);
        view.hideWeatherInfo();

        if (!exception.getMessage().contains("City not found")) {
            showError(errorMessage);
        }
    }

    private void setLoadingState(boolean loading) {
        view.getGetWeatherButton().setDisable(loading);
        view.getCityInput().setDisable(loading);

        if (loading) {
            view.getGetWeatherButton().setText("Loading...");
        } else {
            view.getGetWeatherButton().setText("Get Weather");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Weather App Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String capitalizeFirst(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
