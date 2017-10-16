package de.xyz;


import de.xyz.sample.control.SampleController;
import de.xyz.web.WeatherClient;
import de.xyz.web.com.weather.Weather;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    private Stage stage;
    private SampleController controller;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        this.stage = primaryStage;

        Weather weather = new WeatherClient().makeRequest();
        initRootLayout();
        showWeather(weather);
    }

    private void initRootLayout() throws IOException {
        URL resource = getClass().getResource("sample/view/sample.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        controller = new SampleController();
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();

        stage.setScene(new Scene(root));
        stage.setTitle("WetterApp");
        stage.show();
    }

    private void showWeather(Weather weather) {
        controller.setData(weather);
    }
}
