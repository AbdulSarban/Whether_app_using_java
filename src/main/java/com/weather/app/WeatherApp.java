package com.weather.app;

import com.weather.app.controller.WeatherController;
import com.weather.app.view.WeatherView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class WeatherApp extends Application {

    // Replace with your actual API key
    private static final String API_KEY = "dced2373c5f4ce53e966660cc5d2bff0";

    @Override
    public void start(Stage primaryStage) {
        try {
            WeatherView view = new WeatherView();
            WeatherController controller = new WeatherController(view, API_KEY);

            Scene scene = view.createScene();
            primaryStage.setTitle("Weather Forecast App");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.show();

        } catch (Exception e) {
            showError("Failed to start application", e.getMessage());
            e.printStackTrace();
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
