package com.ic.data.provider;

import org.json.JSONObject;
import java.io.IOException;

/**
 * Interface for different stock data providers
 */
public interface DataSourceProvider {
    /**
     * Fetch daily stock data for a given symbol
     * @param symbol Stock symbol (e.g., "IBM")
     * @param interval Time interval (e.g., "1d")
     * @return JSON response from the provider
     * @throws IOException if network request fails
     */
    JSONObject fetchDailyData(String symbol, String interval) throws IOException;

    /**
     * Get the name of this provider
     */
    String getProviderName();

    /**
     * Check if this provider requires an API key
     */
    boolean requiresApiKey();
}

