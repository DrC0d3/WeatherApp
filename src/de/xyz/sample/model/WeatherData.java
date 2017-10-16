package de.xyz.sample.model;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class WeatherData {

    private StringProperty date;
    private StringProperty time;
    private DoubleProperty temp;
    private StringProperty wolken;
    private DoubleProperty niederschlag;
    private DoubleProperty wind;
    private IntegerProperty luftfeuchte;
    private DoubleProperty luftdruck;

    public WeatherData() {
        this(null, null, null, null, null, null, null, null);
    }

    public WeatherData(LocalDate date, LocalTime time, Double temp, String wolken, Double niederschlag, Double wind, Integer luftfeuchte, Double luftdruck) {
        this.date = new SimpleStringProperty(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
        this.time = new SimpleStringProperty(time.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)));
        this.temp = new SimpleDoubleProperty(temp);
        this.wolken = new SimpleStringProperty(wolken);
        this.niederschlag = new SimpleDoubleProperty(niederschlag);
        this.wind = new SimpleDoubleProperty(wind);
        this.luftfeuchte = new SimpleIntegerProperty(luftfeuchte);
        this.luftdruck = new SimpleDoubleProperty(luftdruck);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public double getTemp() {
        return temp.get();
    }

    public DoubleProperty tempProperty() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp.set(temp);
    }

    public String getWolken() {
        return wolken.get();
    }

    public StringProperty wolkenProperty() {
        return wolken;
    }

    public void setWolken(String wolken) {
        this.wolken.set(wolken);
    }

    public double getNiederschlag() {
        return niederschlag.get();
    }

    public DoubleProperty niederschlagProperty() {
        return niederschlag;
    }

    public void setNiederschlag(double niederschlag) {
        this.niederschlag.set(niederschlag);
    }

    public double getWind() {
        return wind.get();
    }

    public DoubleProperty windProperty() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind.set(wind);
    }

    public double getLuftfeuchte() {
        return luftfeuchte.get();
    }

    public IntegerProperty luftfeuchteProperty() {
        return luftfeuchte;
    }

    public void setLuftfeuchte(int luftfeuchte) {
        this.luftfeuchte.set(luftfeuchte);
    }

    public double getLuftdruck() {
        return luftdruck.get();
    }

    public DoubleProperty luftdruckProperty() {
        return luftdruck;
    }

    public void setLuftdruck(double luftdruck) {
        this.luftdruck.set(luftdruck);
    }
}
