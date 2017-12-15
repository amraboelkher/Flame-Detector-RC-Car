//Motor A
const int motorAPin1  = 5;  // Pin 14 of L293
const int motorAPin2  = 6;  // Pin 10 of L293
//Motor B
const int motorBPin1  = 10; // Pin  7 of L293
const int motorBPin2  = 9;  // Pin  2 of L293
const int delayAmount = 300;

//This will run only one time.
void stop(){
    digitalWrite(motorAPin1, LOW);
    digitalWrite(motorAPin2, LOW);
    digitalWrite(motorBPin1, LOW);
    digitalWrite(motorBPin2, LOW);
}
void move_SE(){
    digitalWrite(motorAPin1, HIGH);
    digitalWrite(motorAPin2, LOW);
    digitalWrite(motorBPin1, LOW);
    digitalWrite(motorBPin2, LOW);
    delay(delayAmount); 
}
void move_NE(){
    digitalWrite(motorAPin1, LOW);
    digitalWrite(motorAPin2, HIGH);
    digitalWrite(motorBPin1, LOW);
    digitalWrite(motorBPin2, LOW);
    delay(delayAmount);
}
void move_SW(){
    digitalWrite(motorAPin1, LOW);
    digitalWrite(motorAPin2, LOW);
    digitalWrite(motorBPin1, LOW);
    digitalWrite(motorBPin2, HIGH);
    delay(delayAmount);   
}
void move_NW(){
    digitalWrite(motorAPin1, LOW);
    digitalWrite(motorAPin2, LOW);
    digitalWrite(motorBPin1, HIGH);
    digitalWrite(motorBPin2, LOW);
    delay(delayAmount); 
}
void move_N(){
    digitalWrite(motorAPin1, LOW);
    digitalWrite(motorAPin2, HIGH);
    digitalWrite(motorBPin1, HIGH);
    digitalWrite(motorBPin2, LOW);
    delay(delayAmount); 
}
void move_S(){
    digitalWrite(motorAPin1, HIGH);
    digitalWrite(motorAPin2, LOW);
    digitalWrite(motorBPin1, LOW);
    digitalWrite(motorBPin2, HIGH);
    delay(delayAmount); 
}
void setup(){

    Serial.begin(9600);

    pinMode(motorAPin1, OUTPUT);
    pinMode(motorAPin2, OUTPUT);
    pinMode(motorBPin1, OUTPUT);
    pinMode(motorBPin2, OUTPUT);
    


    stop();
  
}

String s;

char a;
void loop(){
  if(Serial.available()){

      a = Serial.read();
      Serial.println(a);
      if(a == '3')
        move_N();
      if(a == '4')
        move_S();
      if(a == '5' || a == '2')
        move_NE();
      if(a == '6' || a == '1')
        move_NW();
      if(a == '8')
        move_SE();
      if(a == '7')
        move_SW();
    stop();
   
      
  }
  
}
