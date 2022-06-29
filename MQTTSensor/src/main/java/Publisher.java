import java.util.Scanner;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Publisher extends Thread {

  private String broker;


  Publisher(){

    broker = "tcp://mosquitto:1883";

  }

public void run() {

  MqttConnectOptions mqttConnectOpts = new MqttConnectOptions();
  mqttConnectOpts.setCleanSession(true);

  MqttClient client = null;
  try {
    client = new MqttClient(broker, MqttClient.generateClientId());
  } catch (MqttException e) {
    e.printStackTrace();
  }
  try {
    client.connect(mqttConnectOpts);
  } catch (MqttException e) {
    e.printStackTrace();
  }

  String name = System.getenv("SENSOR_NAME");
  String type = System.getenv("SENSOR_TYPE");
  String Id = System.getenv("SENSOR_ID");
  int sensID = Integer.parseInt(System.getenv("SENSOR_ID"));



  Sensor sensor1 = new Sensor(sensID,name, type);
  SensorData sensorData = new SensorData(sensor1);

  while(true){

    sensorData.generateData();
    String messageJson =sensorData.serializeToJSON().toString();

    MqttMessage message = new MqttMessage(messageJson.getBytes());

    try {
      client.publish(Id, message);
      Thread.sleep(4000);
    } catch (MqttException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    {

    }

  }
}





}
