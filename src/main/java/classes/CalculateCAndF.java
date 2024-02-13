package classes;

public sealed interface CalculateCAndF permits HeatSensor {

    Float celsiusToFahrenheit(Float tempInCelsius);

    Float fahrenheitToCelsius(Float tempInFahrenheit);
}
