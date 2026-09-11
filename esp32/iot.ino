#include <WiFi.h>
#include <HTTPClient.h>
#include <DHT.h>
#include <secrets.h>

const char *ssid = WIFI_SSID;
const char *password = WIFI_PASS;
const char* url = API_URL;

const unsigned long interval = 30000;
unsigned long lastRead = 0;

#define DHT_PIN 4
#define DHT_TYPE DHT22

DHT dht(DHT_PIN, DHT_TYPE);


void setup() {
  Serial.begin(115200);

  dht.begin();

  Serial.print("Connecting to wifi");

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected.");
  Serial.println("IP address: " + Wifi.localIP());
}

void loop() {
  if (millis() - lastRead >= interval){
    lastRead = millis();
    float temperature = dht.readTemperature();

    if (isnan(temperature)){
      Serial.println("Reading error.");
      return;
    }
    Serial.println("Temperature: " + temperature + "ºC");
    if (Wifi.status == WL_CONNECTED){
      HTTPClient http;

      http.begin(url);

      http.addHeader("Content-type", "application/json");

      String json = "{";
      json += "\"TruckId\":";
      json += "AAA0A00";
      json += ",";
      json+="\"Temperature\":";
      json+=String(temperature,2);
      json+="}";

      int httpCode = http.POST(json);

      if (httpCode > 0){
        Serial.print("HTTP Status: ");
        Serial.println(httpCode);

        String response = http.getString();

        Serial.print("API response: ");
        Serial.println(resposta);
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
