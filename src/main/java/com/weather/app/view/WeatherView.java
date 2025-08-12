package com.weather.app.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class WeatherView {

    private VBox root;
    private TextField cityInput;
    private Button getWeatherButton;
    private Label statusLabel;

    private Label cityLabel;
    private Label temperatureLabel;
    private Label humidityLabel;
    private Label windSpeedLabel;
    private Label conditionLabel;
    private ImageView weatherIcon;
    private VBox weatherDisplay;

    public WeatherView() {
        initializeComponents();
        layoutComponents();
        styleComponents();
    }

    private void initializeComponents() {
        root = new VBox(15);

        cityInput = new TextField();
        getWeatherButton = new Button("Get Weather");
        statusLabel = new Label("Enter a city name to get weather information");

        cityLabel = new Label();
        temperatureLabel = new Label();
        humidityLabel = new Label();
        windSpeedLabel = new Label();
        conditionLabel = new Label();
        weatherIcon = new ImageView();
        weatherDisplay = new VBox(10);

        cityInput.setPromptText("Enter city name (e.g., London, New York)");
        cityInput.setPrefWidth(300);
        getWeatherButton.setPrefWidth(150);
        weatherIcon.setFitWidth(64);
        weatherIcon.setFitHeight(64);
        weatherDisplay.setVisible(false);
    }

    private void layoutComponents() {
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.getChildren().addAll(cityInput, getWeatherButton);

        weatherDisplay.setAlignment(Pos.CENTER);
        weatherDisplay.getChildren().addAll(
                cityLabel,
                weatherIcon,
                temperatureLabel,
                conditionLabel,
                createInfoRow("Humidity:", humidityLabel),
                createInfoRow("Wind Speed:", windSpeedLabel)
        );

        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(
                createTitle(),
                inputBox,
                statusLabel,
                weatherDisplay
        );
    }

    private void styleComponents() {
        root.getStyleClass().add("root");

        cityLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        temperatureLabel.setFont(Font.font("System", FontWeight.BOLD, 36));
        conditionLabel.setFont(Font.font("System", FontWeight.NORMAL, 18));

        statusLabel.getStyleClass().add("status-label");
        getWeatherButton.getStyleClass().add("weather-button");
        weatherDisplay.getStyleClass().add("weather-display");
    }

    private Label createTitle() {
        Label title = new Label("Weather Forecast App");
        title.setFont(Font.font("System", FontWeight.BOLD, 28));
        title.getStyleClass().add("title");
        return title;
    }

    private HBox createInfoRow(String label, Label valueLabel) {
        HBox row = new HBox(5);
        row.setAlignment(Pos.CENTER);

        Label keyLabel = new Label(label);
        keyLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        row.getChildren().addAll(keyLabel, valueLabel);
        return row;
    }

    public Scene createScene() {
        Scene scene = new Scene(root, 500, 600);

        try {
            String css = getClass().getResource("/styles/weather-app.css").toExternalForm();
            scene.getStylesheets().add(css);
        } catch (Exception e) {
            System.out.println("Warning: Could not load CSS stylesheet");
        }

        return scene;
    }

    // Getters for controller access
    public TextField getCityInput() {
        return cityInput;
    }

    public Button getGetWeatherButton() {
        return getWeatherButton;
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    public Label getCityLabel() {
        return cityLabel;
    }

    public Label getTemperatureLabel() {
        return temperatureLabel;
    }

    public Label getHumidityLabel() {
        return humidityLabel;
    }

    public Label getWindSpeedLabel() {
        return windSpeedLabel;
    }

    public Label getConditionLabel() {
        return conditionLabel;
    }

    public ImageView getWeatherIcon() {
        return weatherIcon;
    }

    public VBox getWeatherDisplay() {
        return weatherDisplay;
    }

    public void showWeatherInfo() {
        weatherDisplay.setVisible(true);
    }

    public void hideWeatherInfo() {
        weatherDisplay.setVisible(false);
    }

    public void loadWeatherIcon(String iconUrl) {
        if (iconUrl != null && !iconUrl.isEmpty()) {
            try {
                Image image = new Image(iconUrl, true);
                weatherIcon.setImage(image);
            } catch (Exception e) {
                System.out.println("Warning: Could not load weather icon: " + e.getMessage());
                weatherIcon.setImage(null);
            }
        } else {
            weatherIcon.setImage(null);
        }
    }
}
