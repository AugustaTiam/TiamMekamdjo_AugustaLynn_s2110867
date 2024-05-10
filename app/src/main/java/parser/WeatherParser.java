package parser;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import model.Weather;

public class WeatherParser {

    public static List<Weather> parse(InputStream inputStream) {
        List<Weather> weatherList = new ArrayList<>();
        try {
            // Initialize XML parser
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
            XmlPullParser parser = xmlPullParserFactory.newPullParser();

            // Set input source
            parser.setInput(inputStream, null);

            // Parse XML data
            int eventType = parser.getEventType();
            Weather currentWeather = null;

            while (eventType!= XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("item".equalsIgnoreCase(tagName)) {
                            currentWeather = new Weather();
                        } else if (currentWeather!= null && "description".equalsIgnoreCase(tagName)) {
                            String description = parser.nextText();
                            parseDescription(description, currentWeather);
                        } else if (currentWeather!= null && "pubDate".equalsIgnoreCase(tagName)) {
                            currentWeather.setTime(parser.nextText());
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if ("item".equalsIgnoreCase(tagName) && currentWeather!= null) {
                            weatherList.add(currentWeather); // Add the currentWeather object to the list
                            currentWeather = null; // Reset currentWeather for the next item
                        }
                        break;
                }
                eventType = parser.next(); // Move to the next XML event
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        Log.d("WEATHERDATA", "===========");
        for (Weather weather : weatherList) {
            Log.d("WEATHERDATA", weather.toString());

        }
        return weatherList;
    }

    private static String parseLocationFromTitle(String title) {
        // Extract location from the title tag
        String[] parts = title.split(" - Forecast for ");
        if (parts.length > 1) {
            String location = parts[1].trim();
            Log.d("WeatherParser", "Parsed location from title: " + location);
            return location;
        } else {
            Log.d("WeatherParser", "Unable to parse location from title: " + title);
            return "";
        }
    }

    private static void parseDescription(String description, Weather weather) {
        // Split the description and set attributes accordingly
        String[] parts = description.split(",");
        if (parts.length >= 2) {
            weather.setTemperature(parts[0].trim());
            weather.setCondition(parts[1].trim());
        }
    }
}

