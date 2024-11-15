package org.sample.CurrencyConvertor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CurrencyConverter {
    // API Key for the ExchangeRate-API (replace with your own)
    private static final String API_KEY = "a34c247dd387affe07b57843";  // You can get it from https://www.exchangerate-api.com/

    // Method to fetch exchange rates from the API
    public static double getExchangeRate(String baseCurrency, String targetCurrency) throws Exception {
        // Build the API URL with the base currency, target currency, and API key
        String urlString = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + baseCurrency;
        URL url = new URL(urlString);
        
        // Open the connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        // Read the response
        Scanner scanner = new Scanner(connection.getInputStream());
        StringBuilder response = new StringBuilder();
        
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        
        // Parse the JSON response to extract the exchange rate for the target currency
        JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
        if (jsonResponse.get("result").getAsString().equals("success")) {
            JsonObject conversionRates = jsonResponse.getAsJsonObject("conversion_rates");
            return conversionRates.get(targetCurrency).getAsDouble();
        } else {
            throw new Exception("Failed to fetch exchange rates");
        }
    }

    // Method to convert the amount from base currency to target currency
    public static double convertCurrency(double amount, double exchangeRate) {
        return amount * exchangeRate;
    }

    // Main method to run the program
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Get user input for base currency, target currency, and amount
        System.out.print("Enter the base currency (e.g., USD, EUR): ");
        String baseCurrency = scanner.next().toUpperCase();  // Convert to uppercase to match the API format

        System.out.print("Enter the target currency (e.g., USD, EUR): ");
        String targetCurrency = scanner.next().toUpperCase();  // Convert to uppercase to match the API format
        
        System.out.print("Enter the amount to convert: ");
        double amount = scanner.nextDouble();
        
        try {
            // Fetch the exchange rate
            double exchangeRate = getExchangeRate(baseCurrency, targetCurrency);
            
            // Convert the amount
            double convertedAmount = convertCurrency(amount, exchangeRate);
            
            // Display the result
            System.out.printf("Converted amount: %.2f %s\n", convertedAmount, targetCurrency);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

