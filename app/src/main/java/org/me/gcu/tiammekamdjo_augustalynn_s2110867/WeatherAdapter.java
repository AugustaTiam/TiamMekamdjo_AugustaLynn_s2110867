package org.me.gcu.tiammekamdjo_augustalynn_s2110867;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import model.Weather;

public class WeatherAdapter extends ArrayAdapter<Weather> {

    private List<Weather> originalData;
    private List<Weather> filteredData;


    public WeatherAdapter(Context context, List<Weather> weatherList) {
        super(context, 0, weatherList);
        this.originalData = new ArrayList<>(weatherList);
        this.filteredData = new ArrayList<>(weatherList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Weather weather = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.location_item, parent, false);
        }

        // Lookup view for data population
        TextView locationNameTextView = convertView.findViewById(R.id.locationNameTextView);
        TextView temperatureTextView = convertView.findViewById(R.id.temperatureTextView);
        TextView weatherTextView = convertView.findViewById(R.id.weatherTextView);
        TextView timeTextView = convertView.findViewById(R.id.timeTextView);

        TextView forecast1 = convertView.findViewById(R.id.forecast1);
        TextView forecast2 = convertView.findViewById(R.id.forecast2);

        // Populate the data into the template view using the data object
        locationNameTextView.setText(weather.getLocation());
        temperatureTextView.setText(weather.getTemperature());
        weatherTextView.setText(weather.getCondition());
        timeTextView.setText(weather.getTime());
        forecast1.setText(weather.getForecast1());
        forecast2.setText(weather.getForecast2());

        // Return the completed view to render on screen
        return convertView;
    }

    public void filter(String searchText) {
        filteredData.clear();
        if (searchText.isEmpty()) {
            filteredData.addAll(originalData);
        } else {
            Log.d("TRIALX", String.valueOf(originalData.size()));
            for (Weather weather : originalData) {
                Log.d("TRIALX", weather.getLocation().toLowerCase());
                if (weather.getLocation().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredData.add(weather);
                    Log.d("FilteredItem", weather.toString());
                }
            }
        }
        notifyDataSetChanged();
    }

}

