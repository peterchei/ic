package com.ic.test;

import com.ic.core.FConfig;
import com.ic.data.provider.DataSourceFactory;
import com.ic.data.provider.DataSourceProvider;
import org.json.JSONObject;

/**
 * Simple test class to verify the data provider pattern works
 * Run this to test if different providers can fetch data successfully
 */
public class DataProviderTest {

    public static void main(String[] args) {
        System.out.println("=== Data Provider Test ===");
        System.out.println("Current Provider: " + FConfig.DATA_SOURCE_PROVIDER);

        try {
            // Create provider based on configuration
            String apiKey = FConfig.getApiKeyForCurrentProvider();
            DataSourceProvider provider = DataSourceFactory.createProvider(
                FConfig.DATA_SOURCE_PROVIDER,
                apiKey
            );

            System.out.println("Provider: " + provider.getProviderName());
            System.out.println("Requires API Key: " + provider.requiresApiKey());

            // Test fetching IBM stock data
            System.out.println("\nFetching IBM stock data...");
            JSONObject data = provider.fetchDailyData("IBM", "1d");

            if (data != null) {
                System.out.println("✓ Success! Data received from " + provider.getProviderName());
                System.out.println("Response keys: " + data.keySet());
            } else {
                System.out.println("✗ Failed to fetch data");
            }

        } catch (Exception e) {
            System.err.println("✗ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

