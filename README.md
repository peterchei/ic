# IC Project

This is a Java Swing-based stock charting application with support for multiple data providers.

## Screenshot

![Main Screen](src/main/resources/IC_MainScreen.png)

## Prerequisites

- Java 21 or higher
- Gradle 8.13 or higher (wrapper included)

## Quick Start

### Option 1: Yahoo Finance (Recommended - No API Key Required)
1. Ensure JAVA_HOME is set to your Java 21+ installation
2. The default configuration uses Yahoo Finance (no setup needed)
3. Run `./gradlew build` (on Windows: `gradlew.bat build`)
4. Run `./gradlew run` to start the application

### Option 2: Alpha Vantage (API Key Required)
1. Get a free API key from [Alpha Vantage](https://www.alphavantage.co/support/#api-key)
2. Edit `config.properties` in the project root:
   ```properties
   data.source.provider=ALPHA_VANTAGE
   api.key.alphavantage=YOUR_API_KEY_HERE
   ```
3. Run `./gradlew build` and `./gradlew run`

## Data Source Configuration

The application supports multiple stock data providers. You can easily switch between them:

- **Yahoo Finance** (Default) - No API key, unlimited use ⭐
- **Alpha Vantage** - Free tier: 25 requests/day
- **Twelve Data** - Coming soon
- **Finnhub** - Coming soon

See [DATA_PROVIDERS.md](DATA_PROVIDERS.md) for detailed provider documentation.

### Switching Providers

Edit `config.properties`:
```properties
# Options: YAHOO_FINANCE, ALPHA_VANTAGE, TWELVE_DATA, FINNHUB
data.source.provider=YAHOO_FINANCE

# API Keys (only needed for some providers)
api.key.alphavantage=YOUR_KEY_HERE
```

## Running

**Using Gradle:**
```bash
./gradlew run
```

**Using JAR:**
```bash
java -jar build/libs/ic-1.0-SNAPSHOT.jar
```

## Development

- Import as Gradle project in your IDE
- Main class: `com.ic.app.GUI`
- Test providers: Run `com.ic.test.DataProviderTest`

## Features

- **Multiple Data Providers** - Switch between Yahoo Finance, Alpha Vantage, and more
- **Real-time Stock Charting** - Live price updates
- **Multiple Chart Types** - Candlestick, Line, Bar, Volume
- **Technical Indicators** - RSI, MACD, Bollinger Bands, STC, William %R, OBV
- **Drawing Tools** - Lines, Parallel Lines, Golden Ratio
- **Zoom & Pan** - Interactive chart navigation
- **Symbol Input** - Load any stock by entering its symbol
- **Comparison Mode** - Compare multiple stocks

## Gradle Tasks

- `.\gradlew build` - Build the project
- `.\gradlew run` - Run the application
- `.\gradlew jar` - Build the JAR file
- `.\gradlew clean` - Clean build directory
- `.\gradlew test` - Run tests

## Architecture

The application uses a provider pattern for data sources:

```
┌─────────────────┐
│ RequestCommand  │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│ DataSourceFactory│
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│   Providers     │
├─────────────────┤
│ Yahoo Finance   │
│ Alpha Vantage   │
│ Twelve Data     │
│ Finnhub         │
└─────────────────┘
```

## Troubleshooting

### "No data available" Error
- **Yahoo Finance**: Verify the stock symbol is correct (e.g., "IBM", "AAPL")
- **Alpha Vantage**: Check if you've hit the daily limit (25 requests/day)
- Try switching providers in `config.properties`

### Build Errors
```bash
.\gradlew clean build --refresh-dependencies
```

### API Rate Limit
- Switch to Yahoo Finance (unlimited)
- Get a new Alpha Vantage API key
- Wait 24 hours for limit reset

## Documentation

- [DATA_PROVIDERS.md](DATA_PROVIDERS.md) - Complete provider documentation
- [REFACTORING_SUMMARY.md](REFACTORING_SUMMARY.md) - Technical implementation details

## License

[Add your license here]
