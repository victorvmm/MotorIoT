#include <WiFi.h>
#include <HTTPClient.h>
#include <DHT.h>
#include "secrets.h"

const char *ssid = WIFI_SSID;
const char *password = WIFI_PASS;
const char* url = API_URL;

const unsigned long interval = 30000;
unsigned long lastRead = 0;

// Configuração do pino e modelo do sensor
#define DHTPIN 4
#define DHTTYPE DHT22 // O AM2302 equivale ao DHT22

DHT dht(DHTPIN, DHTTYPE);

void setup() {
  Serial.begin(115200);
  
  // Inicializa o sensor AM2302 / DHT22
  dht.begin();

  Serial.print("Connecting to wifi");
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected.");
  Serial.println("IP address: " + WiFi.localIP().toString()); 
}

void loop() {
  if (millis() - lastRead >= interval) {
    lastRead = millis();
    
    // Leitura da temperatura em Celsius
    float temperature = dht.readTemperature();

    if (isnan(temperature)) {
      Serial.println("Reading error.");
      return;
    }
    
    Serial.println("Temperature: " + String(temperature) + "ºC");
    
    if (WiFi.status() == WL_CONNECTED) { 
      HTTPClient http;
      http.begin(url);
      http.addHeader("Content-Type", "application/json");

      String json = "{";
      json += "\"truckId\": \"AAA0A00\", "; 
      json += "\"temperature\": " + String(temperature, 2);
      json += "}";

      int httpCode = http.POST(json);

      if (httpCode > 0) {
        Serial.print("HTTP Status: ");
        Serial.println(httpCode);

        String response = http.getString();
        Serial.print("API response: ");
        Serial.println(response); 
      } else {
        Serial.print("POST error: ");
        Serial.println(http.errorToString(httpCode));
      }
      
      http.end();
    } else {
      Serial.println("Wifi disconnected.");
    }
  }
}