package com.weather.app.model;

public class Weather {

    private String cityName;
    private double temperature;
    private int humidity;
    private double windSpeed;
    private String condition;
    private String description;
    private String iconCode;

    public Weather() {
    }

    public Weather(String cityName, double temperature, int humidity,
            double windSpeed, String condition, String description, String iconCode) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.condition = condition;
        this.description = description;
        this.iconCode = iconCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

   public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconCode() {
        return iconCode;
    }

    public void setIconCode(String iconCode) {
        this.iconCode = iconCode;
    }

    @Override
    public String toString() {
        return String.format("Weather{city='%s', temp=%.1f°C, humidity=%d%%, wind=%.1f m/s, condition='%s'}",
                cityName, temperature, humidity, windSpeed, condition);
    }
}
