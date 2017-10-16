package de.xyz.web;

import de.xyz.web.com.weather.Weather;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class WeatherClient {

    final String basePath = "http://api.openweathermap.org/";
    final String resourcePath = "data/2.5/forecast";


    public Weather makeRequest() {
        final Client client = ClientBuilder.newClient();
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", "2910831");
        params.put("units", "metric");
        params.put("lang", "de");

        try (InputStream in = Files.newInputStream(Paths.get("/Users/florian/Documents/java/key.properties"))){
            Properties properties = new Properties();
            properties.load(in);
            params.put("appid", properties.get("appid"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Weather weather = performGet(client, basePath, resourcePath, params);
        System.out.println("Response Content: " + weather);

        return weather;

    }

    private static Weather performGet(final Client client,
                                      final String basePath,
                                      final String resourcePath,
                                      final Map<String, Object> params) {
        WebTarget webTarget = client.target(basePath).path(resourcePath);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
        }
        System.out.println("\nSending ’GET’ request to URL ’" + basePath +
                resourcePath + "’");
        Weather weather = webTarget.request().accept(MediaType.APPLICATION_JSON).
                header("content-type", MediaType.APPLICATION_JSON).get(Weather.class);
        final int responseCode = Integer.parseInt(weather.getCod());
        System.out.println("Response Code: " + responseCode);

        if (responseCode != Response.Status.OK.getStatusCode()) {
            throw new RuntimeException("HTTP error code: " + responseCode);
        }

        return weather;
    }
}
