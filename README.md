# IC Project

This is a Java Swing-based charting application for Hong Kong Market data. It provides interactive stock charts with various technical analysis indicators.

## Features

- Real-time stock data visualization
- Multiple chart types: Line, Bar, Candlestick
- Technical indicators: SMA, EMA, WMA, RSI, MACD, Bollinger Bands, etc.
- Interactive zooming and panning
- Drawing tools: Lines, Golden Ratio partitions
- Volume charts
- Percentage change charts
- Customizable chart options

## Prerequisites

- Java 21 or higher
- Gradle 8.13 or higher (wrapper included)

## Setup

1. Ensure JAVA_HOME is set to your Java 21+ installation.
2. Get a free API key from [Alpha Vantage](https://www.alphavantage.co/support/#api-key).
3. Create a `config.properties` file in the project root with your API key:
   ```
   api.key=YOUR_API_KEY_HERE
   ```
4. Run `./gradlew build` (on Windows: `gradlew.bat build`)

## Running

Run `./gradlew run` or execute the jar: `java -jar build/libs/IC-1.0-SNAPSHOT.jar`

Upon launch, enter the stock symbol in the text editor to load the corresponding stock prices.

## Development

- Import as Gradle project in your IDE.
- Main class: `com.ic.app.GUI`
- Build with `./gradlew build`
- Run tests with `./gradlew test`

## Gradle Tasks

- `.\gradlew build` - Build the project
- `.\gradlew run` - Run the application directly
- `.\gradlew jar` - Build the JAR file
- `.\gradlew clean` - Clean build directory
- `.\gradlew test` - Run unit tests

## Data Source

The application uses Alpha Vantage API for stock data.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request.
