#include <WiFi.h>
#include <HTTPClient.h>
#include <OneWire.h>
#include <DallasTemperature.h>
#include "secrets.h" 

const char *ssid = WIFI_SSID;
const char *password = WIFI_PASS;
const char* url = API_URL;

const unsigned long interval = 30000;
unsigned long lastRead = 0;

#define ONE_WIRE_BUS 4
OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);

void setup() {
  Serial.begin(115200);
  
  sensors.begin();

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
    
    sensors.requestTemperatures(); 
    float temperature = sensors.getTempCByIndex(0);

    if (temperature == DEVICE_DISCONNECTED_C) {
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