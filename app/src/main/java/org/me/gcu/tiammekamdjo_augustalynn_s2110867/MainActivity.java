package org.me.gcu.tiammekamdjo_augustalynn_s2110867;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import model.Weather;
import parser.FetchDataTask;
import parser.WeatherParser;

public class MainActivity extends AppCompatActivity implements FetchDataTask.Listener {

    private ListView weatherListView;
    private WeatherAdapter adapter;
    private List<Weather> allWeatherData;
    private EditText searchEditText;

    String[] fetchedDataArray;

    String[] locUrls = {
            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743",
            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579",
            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581",
            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/287286",
            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154",
            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/1185241"
    };

    String[] locations = {
            "London",
            "Glasgow",
            "New York",
            "Oman",
            "Mauritius",
            "Bangladesh"
    };

    private WeatherUpdateScheduler weatherUpdateScheduler; // Declare WeatherUpdateScheduler

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ListView
        weatherListView = findViewById(R.id.weatherListView);

        // Initialize EditText for search
        searchEditText = findViewById(R.id.searchEditText);

        // Create a custom adapter
        adapter = new WeatherAdapter(this, new ArrayList<>());
        allWeatherData = new ArrayList<>();

        // Set the adapter to the ListView
        weatherListView.setAdapter(adapter);

        // Fetch weather data synchronously for each URL
        fetchWeatherData();

        // Add TextWatcher to EditText for search functionality
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("SEARCHTEXT", s.toString());
                adapter.filter(s.toString());
            }
        });

        // Initialize and start the WeatherUpdateScheduler
        weatherUpdateScheduler = new WeatherUpdateScheduler(this);
//        weatherUpdateScheduler.start();
    }

    private void fetchWeatherData() {
        // Create a list to hold all weather data
        List<Weather> weatherList = new ArrayList<>();

        Log.d("BEFOREX", "YES");
        FetchDataTask fetchDataTask = new FetchDataTask(this);
        fetchDataTask.execute(locUrls);

        Log.d("BEFOREX", "NO");

    }

    @Override
    public void onDataFetched(String[] fetchedData) {
        fetchedDataArray = fetchedData;
        List<Weather> parsedWeatherList = new ArrayList<>();

        for (int i = 0; i < fetchedData.length; i++) {
            String data = fetchedData[i];
            Log.d("FetchedDataX", data);

            try {
                // Convert the XML data string to an InputStream
                InputStream inputStream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));

                List<Weather> list = WeatherParser.parse(inputStream);
                Weather datum = list.get(0);
                datum.setLocation(locations[i]);
                datum.setForecast1(list.get(1).getTemperature());
                datum.setForecast2(list.get(2).getTemperature());
                // Call your XML pull parser method to parse the data

                parsedWeatherList.add(datum);
                Log.d("DATUM", datum.toString());

                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Add all fetched data to the adapter's list
        allWeatherData.addAll(parsedWeatherList);

        // Update the adapter's data
        adapter.addAll(parsedWeatherList);

        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged();
    }

    private void filter(String searchText) {
        List<Weather> filteredWeatherList = new ArrayList<>();
        for (Weather weather : allWeatherData) {
            // Filter by location
            if (weather.getLocation().toLowerCase().contains(searchText.toLowerCase())) {
                filteredWeatherList.add(weather);
            }
        }

        // Clear the adapter's existing data
        adapter.clear();

        // Add filtered data to the adapter
        adapter.addAll(filteredWeatherList);

        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop the WeatherUpdateScheduler when the activity is destroyed
        if (weatherUpdateScheduler != null) {
            weatherUpdateScheduler.stop();
        }
    }

//    @Override
//    public void onWeatherFetched(String[] fetchedData) {
//
//    }
//
//    public interface FetchWeatherListener {
//        void onWeatherFetched(Weather weather);
//    }
}
