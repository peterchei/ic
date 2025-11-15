package com.ic.data.provider;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Yahoo Finance data provider implementation
 * Free tier: Unlimited for reasonable use
 * No API key required
 */
public class YahooFinanceProvider implements DataSourceProvider {
    private static final Logger log = Logger.getLogger(YahooFinanceProvider.class.getName());
    private static final String BASE_URL = "https://query1.finance.yahoo.com/v8/finance/chart";

    @Override
    public JSONObject fetchDailyData(String symbol, String interval) throws IOException {
        String url = String.format(
            "%s/%s?interval=1d&range=1y",
            BASE_URL, symbol
        );

        log.info("Fetching data from Yahoo Finance for symbol: " + symbol);
        return fetchJson(url);
    }

    @Override
    public String getProviderName() {
        return "Yahoo Finance";
    }

    @Override
    public boolean requiresApiKey() {
        return false;
    }

    private JSONObject fetchJson(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
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

