package com.weather.app.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class WeatherData {

    @SerializedName("name")
    private String cityName;

    @SerializedName("main")
    private Main main;

    @SerializedName("weather")
    private List<WeatherInfo> weather;

    @SerializedName("wind")
    private Wind wind;

    public static class Main {

        @SerializedName("temp")
        private double temperature;

        @SerializedName("humidity")
        private int humidity;

        public double getTemperature() {
            return temperature;
        }

        public int getHumidity() {
            return humidity;
        }
    }

    public static class WeatherInfo {

        @SerializedName("main")
        private String main;

        @SerializedName("description")
        private String description;

        @SerializedName("icon")
        private String icon;

        public String getMain() {
            return main;
        }

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }
    }

    public static class Wind {

        @SerializedName("speed")
        private double speed;

        public double getSpeed() {
            return speed;
        }
    }

    public String getCityName() {
        return cityName;
    }

    public Main getMain() {
        return main;
    }

    public List<WeatherInfo> getWeather() {
        return weather;
    }

    public Wind getWind() {
        return wind;
    }

    public Weather toWeather() {
        Weather weather = new Weather();
        weather.setCityName(this.cityName);
        weather.setTemperature(this.main.getTemperature());
        weather.setHumidity(this.main.getHumidity());
        weather.setWindSpeed(this.wind.getSpeed());

        if (!this.weather.isEmpty()) {
            WeatherInfo info = this.weather.get(0);
            weather.setCondition(info.getMain());
            weather.setDescription(info.getDescription());
            weather.setIconCode(info.getIcon());
        }

        return weather;
    }
}
