#include <SimpleZigBeeRadio.h>
#include <SoftwareSerial.h>
#define trigPin 13
#define echoPin 12

SimpleZigBeeRadio xbee = SimpleZigBeeRadio();

SoftwareSerial xbeeSerial(2, 3); // (RX=>DOUT, TX=>DIN Shield DLINE uses 2,3)
SimpleZigBeePacket zbp = SimpleZigBeePacket();

int val = 0;

unsigned long time = 0;
unsigned long last_sent = 0;

void setup() {
    // Start the serial ports ...
    Serial.begin( 9600 );
    pinMode(trigPin, OUTPUT);
    pinMode(echoPin, INPUT);
    // ... and set the serial port for the XBee radio.
    xbee.setSerial( xbeeSerial );
    // Set a non-zero frame id to receive Status and Response packets.
    xbee.setAcknowledgement(true);
    
    uint8_t exFrame[] = { 
      0x10, //frame type
      0x01, //frame id
      0x00,0x00,0x00,0x00,0x00,0x00,0xFF,0xFF, //destination address 64-bit
      0xff,0xfe, // 16-bit address
      0x00,      //broadcast radius
      0x00,      //options
      0xff,0xff   //payload 
    };
    
    zbp.setFrameData(0, exFrame, sizeof(exFrame));
}

void loop() {
    // If data is waiting in the XBee serial port ...
    if( xbee.available() ){
        // ... read the data.
        xbee.read();
        // If a complete message is available, display the contents
       
    }
    
    delay(10);
    int duration, distance;
    digitalWrite(trigPin, LOW);
    delayMicroseconds(2); 
    digitalWrite(trigPin, HIGH);
    delayMicroseconds(10);
    digitalWrite(trigPin, LOW);
    duration = pulseIn(echoPin, HIGH);
    distance = (duration/2) / 29.1;
    //Serial.println(" cm");
    val = distance;
  
    
    time = millis();
    if(time > (last_sent+5000))
    {
      last_sent = time;
      zbp.setFrameData(zbp.getFrameLength()-2, val >> 8 & 0xff);
      zbp.setFrameData(zbp.getFrameLength()-1, val & 0xff);
      Serial.print("\nSend Message: ");
      Serial.print(val);
      
      xbee.send(zbp);
      last_sent = millis();
    }
    delay(20); // Small delay for stability
}

