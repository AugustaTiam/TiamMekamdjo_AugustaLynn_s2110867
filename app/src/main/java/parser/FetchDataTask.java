package parser;

import android.os.AsyncTask;
import android.util.Log;

import org.me.gcu.tiammekamdjo_augustalynn_s2110867.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchDataTask extends AsyncTask<String, Void, String[]> {

    private static final String TAG = "FetchDataTask";
    private Listener weatherFetchListener;

    public FetchDataTask(Listener weatherFetchListener) {
        this.weatherFetchListener = weatherFetchListener;
    }

    @Override
    protected String[] doInBackground(String... urls) {
        List<String> results = new ArrayList<>();

        for (int i = 0; i < urls.length; i++) {
            String url = urls[i];
            try {
                String data = fetchDataFromUrl(url);
                results.add(data);
            } catch (IOException e) {
                Log.e(TAG, "Error fetching data from URL: " + url, e);
            }
        }
        return results.toArray(new String[0]);
    }

    @Override
    protected void onPostExecute(String[] fetchedData) {
        Log.e(TAG, String.valueOf(fetchedData.length));
        if (weatherFetchListener != null) {
            weatherFetchListener.onDataFetched(fetchedData);
        }
    }

    private String fetchDataFromUrl(String urlString) throws IOException {
        StringBuilder result = new StringBuilder();
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return result.toString();
    }

    public interface Listener {
        void onDataFetched(String[] fetchedData);
    }
}
