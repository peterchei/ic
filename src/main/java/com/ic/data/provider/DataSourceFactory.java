package com.ic.data.provider;

import java.util.logging.Logger;

/**
 * Factory class to create data source providers based on configuration
 */
public class DataSourceFactory {
    private static final Logger log = Logger.getLogger(DataSourceFactory.class.getName());

    public enum ProviderType {
        ALPHA_VANTAGE,
        YAHOO_FINANCE,
        TWELVE_DATA,
        FINNHUB
    }

    /**
     * Create a data source provider based on type and API key
     * @param type The provider type
     * @param apiKey API key (can be null for providers that don't require it)
     * @return DataSourceProvider instance
     */
    public static DataSourceProvider createProvider(ProviderType type, String apiKey) {
        log.info("Creating data provider: " + type);

        switch (type) {
            case ALPHA_VANTAGE:
                if (apiKey == null || apiKey.trim().isEmpty()) {
                    log.warning("Alpha Vantage requires an API key. Falling back to Yahoo Finance");
                    return new YahooFinanceProvider();
                }
                return new AlphaVantageProvider(apiKey);

            case YAHOO_FINANCE:
                return new YahooFinanceProvider();

            case TWELVE_DATA:
                log.warning("Twelve Data provider not yet implemented, falling back to Yahoo Finance");
                return new YahooFinanceProvider();

            case FINNHUB:
                log.warning("Finnhub provider not yet implemented, falling back to Yahoo Finance");
                return new YahooFinanceProvider();

            default:
                log.warning("Unknown provider type: " + type + ", using Yahoo Finance as default");
                return new YahooFinanceProvider();
        }
    }

    /**
     * Create a provider from string name (for config file parsing)
     */
    public static DataSourceProvider createProviderFromString(String providerName, String apiKey) {
        try {
            ProviderType type = ProviderType.valueOf(providerName.toUpperCase());
            return createProvider(type, apiKey);
        } catch (IllegalArgumentException e) {
            log.warning("Invalid provider name: " + providerName + ", using Yahoo Finance as default");
            return new YahooFinanceProvider();
        }
    }
}

