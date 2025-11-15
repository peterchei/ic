# Data Source Provider Configuration

The IC Stock Chart application now supports multiple data source providers. You can easily switch between them by editing the `config.properties` file.

## Supported Providers

### 1. Yahoo Finance (Default) ⭐ **Recommended**
- **No API key required**
- **Free and unlimited** for reasonable use
- **Most stable** option
- Real-time and historical data

**Configuration:**
```properties
data.source.provider=YAHOO_FINANCE
```

### 2. Alpha Vantage
- Requires free API key
- **Limit**: 25 requests per day (free tier)
- Get free API key at: https://www.alphavantage.co/support/#api-key

**Configuration:**
```properties
data.source.provider=ALPHA_VANTAGE
api.key.alphavantage=YOUR_API_KEY_HERE
```

### 3. Twelve Data (Coming Soon)
- Requires API key
- **Limit**: 800 requests/day (free tier)
- Get API key at: https://twelvedata.com/

### 4. Finnhub (Coming Soon)
- Requires API key
- **Limit**: 60 calls/minute (free tier)
- Get API key at: https://finnhub.io/

## How to Switch Providers

1. Open `config.properties` in the root directory
2. Change the `data.source.provider` value to one of:
   - `YAHOO_FINANCE` (no API key needed)
   - `ALPHA_VANTAGE` (API key required)
   - `TWELVE_DATA` (coming soon)
   - `FINNHUB` (coming soon)

3. If using a provider that requires an API key, add it:
```properties
api.key.alphavantage=YOUR_ALPHA_VANTAGE_KEY
api.key.twelvedata=YOUR_TWELVE_DATA_KEY
api.key.finnhub=YOUR_FINNHUB_KEY
```

4. Restart the application

## Example Configuration

### Using Yahoo Finance (Default - No API Key)
```properties
# Data Source Configuration
data.source.provider=YAHOO_FINANCE
```

### Using Alpha Vantage
```properties
# Data Source Configuration
data.source.provider=ALPHA_VANTAGE

# API Keys
api.key.alphavantage=VC2UUD24FZR4X98C
```

## Troubleshooting

### "No data available" Error
- **Yahoo Finance**: Check if the stock symbol is correct (e.g., use "IBM" for IBM stock)
- **Alpha Vantage**: 
  - Check if you've hit the 25 requests/day limit
  - Verify your API key is correct
  - Wait 24 hours for limit reset or get a new free API key

### Rate Limit Exceeded
- **Alpha Vantage Free Tier**: 25 requests/day
  - Solution: Switch to Yahoo Finance or wait for reset
- **Workaround**: Get multiple free Alpha Vantage API keys and rotate them

### Getting a New Alpha Vantage API Key
1. Go to: https://www.alphavantage.co/support/#api-key
2. Enter your email
3. You'll receive a free API key instantly
4. Update `config.properties` with the new key

## Best Practices

1. **For Development**: Use Yahoo Finance (no limits, no API key needed)
2. **For Production**: Consider a paid plan if you need high-frequency updates
3. **Keep API Keys Secure**: Don't commit `config.properties` with real API keys to version control

## Provider Comparison

| Provider | Free Tier Limit | API Key Required | Stability | Data Quality |
|----------|----------------|------------------|-----------|--------------|
| Yahoo Finance | Unlimited* | No | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ |
| Alpha Vantage | 25 req/day | Yes | ⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ |
| Twelve Data | 800 req/day | Yes | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ |
| Finnhub | 60 req/min | Yes | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ |

*"Reasonable use" - Yahoo Finance doesn't specify hard limits but may throttle excessive requests

## Support

If you encounter issues:
1. Check the logs for detailed error messages
2. Verify your configuration in `config.properties`
3. Try switching to Yahoo Finance as a fallback
4. Ensure you have internet connectivity

