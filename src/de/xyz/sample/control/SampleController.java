package de.xyz.sample.control;

import de.xyz.sample.model.WeatherData;
import de.xyz.web.com.weather.List;
import de.xyz.web.com.weather.Rain;
import de.xyz.web.com.weather.Weather;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class SampleController implements Initializable {

    @FXML
    private Label aktuellLbl;
    @FXML
    private Label temperaturLbl;
    @FXML
    private Label wolkenLbl;
    @FXML
    private Label niederschlageLbl;
    @FXML
    private Label windLbl;
    @FXML
    private Label luftfeuchteLbl;
    @FXML
    private Label luftdruckLbl;

    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private CategoryAxis xAxis2;
    @FXML
    private NumberAxis yAxis2;
    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxisLuftdruck;
    @FXML
    private NumberAxis yAxisLuftdruck;
    @FXML
    private LineChart<String, Number> luftdruckLineChart;

    @FXML
    private CategoryAxis xAxisLuftfeuchtigkeit;
    @FXML
    private NumberAxis yAxisLuftfeuchtigkeit;
    @FXML
    private LineChart<String, Number> luftfeuchtigkeitLineChart;

    @FXML
    private CategoryAxis xAxisWind;
    @FXML
    private NumberAxis yAxisWind;
    @FXML
    private BarChart<String, Number> windBarChart;

    @FXML
    private TableView table;
    @FXML
    private TableColumn<WeatherData, String> dateCol;
    @FXML
    private TableColumn<WeatherData, String> timeCol;
    @FXML
    private TableColumn<WeatherData, Double> tempCol;
    @FXML
    private TableColumn<WeatherData, String> wolkenCol;
    @FXML
    private TableColumn<WeatherData, Double> niederschlagCol;
    @FXML
    private TableColumn<WeatherData, Double> windCol;
    @FXML
    private TableColumn<WeatherData, Integer> luftfeuchteCol;
    @FXML
    private TableColumn<WeatherData, Double> luftdruckCol;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        timeCol.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        tempCol.setCellValueFactory(cellData -> cellData.getValue().tempProperty().asObject());
        wolkenCol.setCellValueFactory(cellData -> cellData.getValue().wolkenProperty());
        niederschlagCol.setCellValueFactory(cellData -> cellData.getValue().niederschlagProperty().asObject());
        windCol.setCellValueFactory(cellData -> cellData.getValue().windProperty().asObject());
        luftfeuchteCol.setCellValueFactory(cellData -> cellData.getValue().luftfeuchteProperty().asObject());
        luftdruckCol.setCellValueFactory(cellData -> cellData.getValue().luftdruckProperty().asObject());

        xAxis = new CategoryAxis();
        xAxis.setLabel("Zeit");
        yAxis = new NumberAxis();
        yAxis.setLabel("Temperatur (°C)");

        lineChart.setTitle("Temperatur");

        xAxis2 = new CategoryAxis();
        xAxis2.setLabel("Zeit");
        yAxis2 = new NumberAxis();
        yAxis2.setLabel("Niederschlag (mm)");

        barChart.setTitle("Niederschlag");

        xAxisWind = new CategoryAxis();
        xAxisWind.setLabel("Zeit");
        yAxisWind = new NumberAxis();
        yAxisWind.setLabel("Wind (km/h)");

        windBarChart.setTitle("Wind");

        xAxisLuftdruck = new CategoryAxis();
        xAxisLuftdruck.setLabel("Zeit");
        yAxisLuftdruck = new NumberAxis();
        yAxisLuftdruck.setLabel("Luftdruck (hPa)");

        luftdruckLineChart.setTitle("Luftdruck (hPa)");
        xAxisLuftfeuchtigkeit = new CategoryAxis();
        xAxisLuftfeuchtigkeit.setLabel("Zeit");
        yAxisLuftfeuchtigkeit = new NumberAxis();
        yAxisLuftfeuchtigkeit.setLabel("Luftfeuchtigkeit (%)");

        luftfeuchtigkeitLineChart.setTitle("Luftfeuchtigkeit (%)");

    }

    public void setData(Weather weather) {
        ObservableList<WeatherData> data = generateData(weather);
        table.setItems(data);

        aktuellLbl.setText(weather.getList().get(0).getWeather().get(0).getIcon());
        temperaturLbl.setText(String.valueOf(weather.getList().get(0).getMain().getTemp() + " °C"));
        wolkenLbl.setText(String.valueOf(weather.getList().get(0).getWeather().get(0).getDescription()));
        Rain rain = weather.getList().get(0).getRain();
        if (rain == null) {
            niederschlageLbl.setText("0,0 mm");
        } else {
            niederschlageLbl.setText(String.valueOf(rain.get3h() + " mm"));
        }
        windLbl.setText(String.valueOf(Math.round(weather.getList().get(0).getWind().getSpeed() * 3.6d) + " km/h"));
        luftfeuchteLbl.setText(String.valueOf(weather.getList().get(0).getMain().getHumidity() + " %"));
        luftdruckLbl.setText(String.valueOf(weather.getList().get(0).getMain().getPressure() + " hPa"));

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Temperatur");
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("TemperaturMin");
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("TemperaturMax");

        XYChart.Series series4 = new XYChart.Series();
        series4.setName("Niederschlag");

        XYChart.Series series5 = new XYChart.Series();
        series4.setName("Wind");

        XYChart.Series series6 = new XYChart.Series();
        series6.setName("Luftdruck");

        XYChart.Series series7 = new XYChart.Series();
        series7.setName("Luftfeuchtigkeit");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (List list : weather.getList()) {
            LocalDateTime localDateTime = LocalDateTime.parse(list.getDtTxt(), formatter);
            String dateTime = localDateTime.toLocalDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.GERMAN) + ", " + localDateTime.toLocalTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
            series1.getData().add(new XYChart.Data(dateTime, list.getMain().getTemp()));
            series2.getData().add(new XYChart.Data(dateTime, list.getMain().getTempMin()));
            series3.getData().add(new XYChart.Data(dateTime, list.getMain().getTempMax()));
            Double rain2 = 0.0d;
            if (list.getRain() != null && list.getRain().get3h() != null) {
                rain2 = list.getRain().get3h();
            }
            series4.getData().add(new XYChart.Data(dateTime, rain2));

            series5.getData().add(new XYChart.Data(dateTime,Math.round(list.getWind().getSpeed() * 3.6d)));

            series6.getData().add(new XYChart.Data(dateTime, list.getMain().getPressure()));

            series7.getData().add(new XYChart.Data(dateTime, list.getMain().getHumidity()));
        }

        lineChart.setTitle("Vorhersage für " + weather.getCity().getName() + ", " + weather.getCity().getCountry());
        lineChart.getData().addAll(series1, series2, series3);

        barChart.getData().addAll(series4);

        windBarChart.getData().addAll(series5);

        luftdruckLineChart.getData().addAll(series6);

        luftfeuchtigkeitLineChart.getData().addAll(series7);
    }

    private ObservableList<WeatherData> generateData(Weather weather) {
        ObservableList<WeatherData> data = FXCollections.observableArrayList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (List list : weather.getList()) {
            LocalDateTime localDateTime = LocalDateTime.parse(list.getDtTxt(), formatter);

            Rain rain = weather.getList().get(0).getRain();
            Double niederschlag;
            if (rain == null) {
                niederschlag = 0.0d;
            } else {
                niederschlag = rain.get3h();
            }

            data.add(new WeatherData(localDateTime.toLocalDate(), localDateTime.toLocalTime(), list.getMain().getTemp(),
                    list.getWeather().get(0).getDescription(), niederschlag, (double) Math.round(list.getWind().getSpeed() * 3.6),
                    list.getMain().getHumidity(), list.getMain().getPressure()));
        }

        return data;
    }
}
