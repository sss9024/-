#include <ArduinoJson.h>
#include "MPU9250.h"
#include "eeprom_utils.h"
#include <WiFi.h>
#include <PubSubClient.h>

WiFiClient espClient;
PubSubClient client(espClient);
MPU9250 mpu;

const char* ssid = "iptime 95";
const char* password = "18181818";
const char* mqtt_server = "192.168.0.27";

#define MSG_BUFFER_SIZE  (100)
char msg[MSG_BUFFER_SIZE];

float act10012[3][2] = {{0.15f, 0.00f}, {0.05f, 0.50f}, {0.05f, 0.90f}};
float act10013[5][2];

void setup_wifi() {

  delay(10);
  // We start by connecting to a WiFi network
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  randomSeed(micros());

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

int stopck = 0;
void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
  }
  Serial.println();

  if(!strcmp((char*)payload, "stop")) stopck = 1;
  
  if((char)payload[6]=='0')
  {
    snprintf(msg, MSG_BUFFER_SIZE, "{\"success\" : \"1\"}");
    client.publish("s01Out", msg);
  }
  else if((char)payload[6]=='1')
  {
    if((char)payload[7] == '1')
    {
      int ansck = 0;
      float actPitch = 0.0f, actRoll = 0.0f, actPoint = 0.0f;
      while(!ansck)
      {
        if(stopck)
        {
          stopck = 0;
          break;
        }
        mpu.update();
        static uint32_t prev_ms = millis();
        if (millis() > prev_ms + 10)
        {
          print_roll_pitch_yaw();
          static uint32_t act_ms = millis();
          actPitch = 1.00f - abs(act10012[0][0] - mpu.getPitch() / 180) * 10.00f;
          actRoll = (1.00f - abs(act10012[0][1] - mpu.getRoll() / 180) * 10.00f);
          actPoint = (actPitch + actRoll) / 2;
          if(millis() > act_ms + 200)
          {
            snprintf(msg, MSG_BUFFER_SIZE, "{\"actnum\" : \"0\", \"ispass\" : \"0\", \"pitch\" : \"%f\", \"roll\" : \"%f\"}", actPitch, actRoll);
            client.publish("s01Out", msg);
            act_ms = millis();
          }
          if(actPoint >= 0.6f)
          {
            snprintf(msg, MSG_BUFFER_SIZE, "{\"actnum\" : \"0\", \"ispass\" : \"1\", \"pitch\" : \"%f\", \"roll\" : \"%f\"}", actPitch, actRoll);
            client.publish("s01Out", msg);
            ansck = 1;
          }
        }
      }
    }
    else if((char)payload[7] == '2')
    {
      int ansck = 0;
      float actPitch = 0.0f, actRoll = 0.0f, actPoint = 0.0f;
      while(!ansck)
      {
        if(stopck)
        {
          stopck = 0;
          break;
        }
        mpu.update();
        static uint32_t prev_ms = millis();
        if (millis() > prev_ms + 10)
        {
          print_roll_pitch_yaw();
          static uint32_t act_ms = millis();
          actPitch = 1.00f - abs(act10012[1][0] - mpu.getPitch() / 180) * 10.00f;
          actRoll = (1.00f - abs(act10012[1][1] - mpu.getRoll() / 180) * 10.00f);
          actPoint = (actPitch + actRoll) / 2;
          if(millis() > act_ms + 200)
          {
            snprintf(msg, MSG_BUFFER_SIZE, "{\"actnum\" : \"1\", \"ispass\" : \"0\", \"pitch\" : \"%f\", \"roll\" : \"%f\"}", actPitch, actRoll);
            client.publish("s01Out", msg);
            act_ms = millis();
          }
          if(actPoint >= 0.6f)
          {
            snprintf(msg, MSG_BUFFER_SIZE, "{\"actnum\" : \"1\", \"ispass\" : \"1\", \"pitch\" : \"%f\", \"roll\" : \"%f\"}", actPitch, actRoll);
            client.publish("s01Out", msg);
            ansck = 1;
          }
        }
      }
    }
    else if((char)payload[7] == '3')
    {
      int ansck = 0;
      float actPitch = 0.0f, actRoll = 0.0f, actPoint = 0.0f;
      while(!ansck)
      {
        if(stopck)
        {
          stopck = 0;
          break;
        }
        mpu.update();
        static uint32_t prev_ms = millis();
        if (millis() > prev_ms + 10)
        {
          print_roll_pitch_yaw();
          static uint32_t act_ms = millis();
          actPitch = 1.00f - abs(act10012[2][0] - mpu.getPitch() / 180) * 10.00f;
          actRoll = (1.00f - abs(act10012[2][1] - mpu.getRoll() / 180) * 10.00f);
          actPoint = (actPitch + actRoll) / 2;
          if(millis() > act_ms + 200)
          {
            snprintf(msg, MSG_BUFFER_SIZE, "{\"actnum\" : \"2\", \"ispass\" : \"0\", \"pitch\" : \"%f\", \"roll\" : \"%f\"}", actPitch, actRoll);
            client.publish("s01Out", msg);
            act_ms = millis();
          }
          if(actPoint >= 0.6f)
          {
            snprintf(msg, MSG_BUFFER_SIZE, "{\"actnum\" : \"2\", \"ispass\" : \"1\", \"pitch\" : \"%f\", \"roll\" : \"%f\"}", actPitch, actRoll);
            client.publish("s01Out", msg);
            ansck = 1;
          }
        }
      }
    }
  }
  else if((char)payload[6]=='2')
  {
    float avgYaw = 0.0f, avgPitch = 0.0f, avgRoll = 0.0f;
    int ansck = 0, actck[3] = {0};
    float actPitch = 0.0f, actRoll = 0.0f, actPoint[3] = {0};

    while(!ansck)
    {
      if(stopck)
      {
        stopck = 0;
        break;
      }
      mpu.update();
      static uint32_t prev_ms = millis();
      if (millis() > prev_ms + 10)
      {
        print_roll_pitch_yaw();
        if(!actck[0])
        {
          static uint32_t act0_ms = millis();
          actPitch = 1.00f - abs(act10012[0][0] - mpu.getPitch() / 180) * 10.00f;
          actRoll = (1.00f - abs(act10012[0][1] - mpu.getRoll() / 180) * 10.00f);
          actPoint[0] = (actPitch + actRoll) / 2;
          if(millis() > act0_ms + 200)
          {
            snprintf(msg, MSG_BUFFER_SIZE, "{\"actnum\" : \"0\", \"ispass\" : \"0\", \"pitch\" : \"%f\", \"roll\" : \"%f\"}", actPitch, actRoll);
            client.publish("s01Out", msg);
            act0_ms = millis();
          }
          if(actPoint[0] >= 0.6f)
          {
            snprintf(msg, MSG_BUFFER_SIZE, "{\"actnum\" : \"0\", \"ispass\" : \"1\", \"pitch\" : \"%f\", \"roll\" : \"%f\"}", actPitch, actRoll);
            client.publish("s01Out", msg);
            actck[0] = 1;
          }
        }
        else
        {
          if(!actck[1])
          {
            static uint32_t act1_ms = millis();
            actPitch = 1.00f - abs(act10012[1][0] - mpu.getPitch() / 180) * 10.00f;
            actRoll = (1.00f - abs(act10012[1][1] - mpu.getRoll() / 180) * 10.00f);
            actPoint[1] = (actPitch + actRoll) / 2;
            if(millis() > act1_ms + 200)
            {
              snprintf(msg, MSG_BUFFER_SIZE, "{\"actnum\" : \"1\", \"ispass\" : \"0\", \"pitch\" : \"%f\", \"roll\" : \"%f\"}", actPitch, actRoll);
              client.publish("s01Out", msg);
              act1_ms = millis();
            }
            if(actPoint[1] >= 0.6f)
            {
              snprintf(msg, MSG_BUFFER_SIZE, "{\"actnum\" : \"1\", \"ispass\" : \"1\", \"pitch\" : \"%f\", \"roll\" : \"%f\"}", actPitch, actRoll);
              client.publish("s01Out", msg);
              actck[1] = 1;
            }
          }
          else
          {
            if(!actck[2])
            {
              static uint32_t act2_ms = millis();
              actPitch = 1.00f - abs(act10012[2][0] - mpu.getPitch() / 180) * 10.00f;
              actRoll = (1.00f - abs(act10012[2][1] - mpu.getRoll() / 180) * 10.00f);
              actPoint[2] = (actPitch + actRoll) / 2;
              if(millis() > act2_ms + 200)
              {
                snprintf(msg, MSG_BUFFER_SIZE, "{\"actnum\" : \"2\", \"ispass\" : \"0\", \"pitch\" : \"%f\", \"roll\" : \"%f\"}", actPitch, actRoll);
                 client.publish("s01Out", msg);
                act2_ms = millis();
              }
              if(actPoint[2] >= 0.6f)
              {
                snprintf(msg, MSG_BUFFER_SIZE, "{\"actnum\" : \"2\", \"ispass\" : \"1\", \"pitch\" : \"%f\", \"roll\" : \"%f\"}", actPitch, actRoll);
                client.publish("s01Out", msg);
                actck[2] = 1;
                ansck = 1;
              }
            }
          }
        }
        prev_ms = millis();
      }
    }
  }
}

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Create a random client ID
    String clientId = "sensor-left-01";
    // Attempt to connect
    if (client.connect(clientId.c_str())) {
      Serial.println("connected");
      // ... and resubscribe
      client.subscribe("s01In");
      client.subscribe("s011In");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}

void calib() {
    Serial.println("Accel Gyro calibration will start in 3sec.");
    Serial.println("Please leave the device still on the flat plane.");
    mpu.verbose(true);
    delay(3000);
    Serial.println("Start");
    mpu.calibrateAccelGyro();
  
    Serial.println("Mag calibration will start in 3sec.");
    Serial.println("Please Wave device in a figure eight until done.");
    delay(3000);
    Serial.println("Start");
    mpu.calibrateMag();

    printCalibration();
    mpu.verbose(false);

    // save to eeprom
    saveCalibration();

    // load from eeprom
    loadCalibration();
}

void print_roll_pitch_yaw() {
    Serial.print(mpu.getYaw() / 180, 2);
    Serial.print(" ");
    Serial.print(mpu.getPitch() / 180, 2);
    Serial.print(" ");
    Serial.println(mpu.getRoll() / 180, 2);
}

void setup()
{
  Serial.begin(115200);
  Wire.begin();
  setup_wifi();
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
  delay(2000);
  
  if (!EEPROM.begin(EEPROM_SIZE))
  {
    Serial.println("failed to initialise EEPROM"); delay(1000000);
  }
  
  if (!mpu.setup(0x68))
      while (1)
      {
          Serial.println("MPU connection failed. Please check your connection with `connection_check` example.");
          delay(5000);
      }

  if(!isCalibrated()) calib();
  
  setupEEPROM();
  delay(1000);
  mpu.selectFilter(QuatFilterSel::MADGWICK);
  delay(1000);
}

void loop()
{
  if (!client.connected()) {
    reconnect();
  }
  client.loop();
}
