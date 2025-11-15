package com.ic.data.provider;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Alpha Vantage data provider implementation
 * Free tier: 25 requests per day
 * Requires API key
 */
public class AlphaVantageProvider implements DataSourceProvider {
    private static final Logger log = Logger.getLogger(AlphaVantageProvider.class.getName());
    private final String apiKey;
    private static final String BASE_URL = "https://www.alphavantage.co/query";

    public AlphaVantageProvider(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public JSONObject fetchDailyData(String symbol, String interval) throws IOException {
        String url = String.format(
            "%s?function=TIME_SERIES_DAILY&symbol=%s&apikey=%s&outputsize=compact",
            BASE_URL, symbol, apiKey
        );

        log.info("Fetching data from Alpha Vantage for symbol: " + symbol);
        return fetchJson(url);
    }

    @Override
    public String getProviderName() {
        return "Alpha Vantage";
    }

    @Override
    public boolean requiresApiKey() {
        return true;
    }

    private JSONObject fetchJson(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("HTTP error code: " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        return new JSONObject(response.toString());
    }
}

