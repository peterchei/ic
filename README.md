# IC Project

This is a Java Swing-based charting application for Hong Kong Market data.

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

## Development

- Import as Gradle project in your IDE.
- Main class: `com.ic.app.GUI`

## Gradle Tasks

- `.\gradlew build` - Build the project
- `.\gradlew run` - Run the application directly
- `.\gradlew jar` - Build the JAR file
- `.\gradlew clean` - Clean build directory

## Data Source

The application now uses Alpha Vantage API for stock data instead of the discontinued Yahoo Finance CSV API.
