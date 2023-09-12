package com.tati.backend.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class WeatherAPI {

    private String apiKey = "YOUR_API_KEY";

    public WeatherAPI() {

    }

    public WeatherAPI(String apiKey) {
        this.apiKey = apiKey;
    }

    private static String getValueIfExists(Document parentElement, String childTagName, String attributeName) {
        NodeList childList = parentElement.getElementsByTagName(childTagName);
        if (childList.getLength() > 0) {
            Element childElement = (Element) childList.item(0);
            if (childElement.hasAttribute(attributeName)) {
                return childElement.getAttribute(attributeName);
            } else {
                return childElement.getTextContent();
            }
        }
        return "";
    }

    public List<String> getGeoCoding(String cityName) {

        List<String> results = new ArrayList<>();

        String apiUrl = "http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + "&appid=" + this.apiKey;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONArray jsonArray = new JSONArray(response.toString());
            // Assuming there's only one item in the array (as per the response structure)
            if (jsonArray.length() > 0) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                String lon = String.valueOf(jsonObject.getDouble("lon"));
                String lat = String.valueOf(jsonObject.getDouble("lat"));
                String name = jsonObject.getString("name");
                String country = jsonObject.getString("country");

                results.add(lon);
                results.add(lat);
                results.add(name);
                results.add(country);

                connection.disconnect();
                return results;
            } else {
                connection.disconnect();
                return results;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return results;
        }

    }

    public Map<String, String> getWeatherData(String cityName) {

        Map<String, String> data = new HashMap<>();

        List<String> results = this.getGeoCoding(cityName);

        String lon = results.get(0);
        String lat = results.get(1);
        String name = results.get(2);
        String country = results.get(3);

        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid="
                + this.apiKey + "&units=metric&mode=xml";

        try {
            // Get weather data from Weather API
            URL weatherUrl = new URL(apiUrl);
            HttpURLConnection weatherConnection = (HttpURLConnection) weatherUrl.openConnection();
            weatherConnection.setRequestMethod("GET");

            BufferedReader weatherReader = new BufferedReader(
                    new InputStreamReader(weatherConnection.getInputStream()));
            StringBuilder weatherResponse = new StringBuilder();
            String weatherLine;

            while ((weatherLine = weatherReader.readLine()) != null) {
                weatherResponse.append(weatherLine);
            }
            weatherReader.close();

            // Extract desired weather data from Weather API response
            DocumentBuilderFactory weatherFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder weatherBuilder = weatherFactory.newDocumentBuilder();
            Document weatherDocument = weatherBuilder
                    .parse(new InputSource(new StringReader(weatherResponse.toString())));

            // Check if the elements exist before extracting their values
            String sunrise = getValueIfExists(weatherDocument, "sun", "rise");
            String sunset = getValueIfExists(weatherDocument, "sun", "set");
            String temperature = getValueIfExists(weatherDocument, "temperature", "value");
            String feelsLike = getValueIfExists(weatherDocument, "feels_like", "value");
            String humidity = getValueIfExists(weatherDocument, "humidity", "value");
            String pressure = getValueIfExists(weatherDocument, "pressure", "value");
            String speed = getValueIfExists(weatherDocument, "speed", "value");
            String directionName = getValueIfExists(weatherDocument, "direction", "name");
            String speedName = getValueIfExists(weatherDocument, "speed", "name");
            String weatherIcon = getValueIfExists(weatherDocument, "weather", "icon");
            String weatherValue = getValueIfExists(weatherDocument, "weather", "value");
            String lastUpdate = getValueIfExists(weatherDocument, "lastupdate", "value");

            data.put("name", name);
            data.put("country", country);
            data.put("sunrise", sunrise);
            data.put("sunset", sunset);
            data.put("temperature", temperature);
            data.put("feelsLike", feelsLike);
            data.put("humidity", humidity);
            data.put("pressure", pressure);
            data.put("speed", speed);
            data.put("directionName", directionName);
            data.put("speedName", speedName);
            data.put("weatherIcon", weatherIcon);
            data.put("lastUpdate", lastUpdate);
            data.put("weatherValue", weatherValue);

            weatherConnection.disconnect();

            System.out.println(data.toString());

            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
