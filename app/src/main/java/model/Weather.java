package model;

public class Weather {
    private String location;
    private String temperature;
    private String condition;
    private String time;

    private String forecast1;
    private String forecast2;


    public String getForecast1() {
        return forecast1;
    }

    public void setForecast1(String forecast1) {
        this.forecast1 = forecast1;
    }

    public String getForecast2() {
        return forecast2;
    }

    public void setForecast2(String forecast2) {
        this.forecast2 = forecast2;
    }

    public Weather(String location, String temperature, String condition, String time, String forecast1, String forecast2) {
        this.location = location;
        this.temperature = temperature;
        this.condition = condition;
        this.time = time;
        this.forecast1 = forecast1;
        this.forecast2 = forecast2;
    }

    public Weather() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "location='" + location + '\'' +
                ", temperature='" + temperature + '\'' +
                ", condition='" + condition + '\'' +
                ", time='" + time + '\'' +
                ", forecast1='" + forecast1 + '\'' +
                ", forecast2='" + forecast2 + '\'' +
                '}';
    }

}
