import java.util.concurrent.BlockingQueue;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Subscriber extends  Thread implements MqttCallback {

  private String broker;
  private BlockingQueue<String> myQ = null;

  Subscriber(BlockingQueue<String> myQ){

    broker = "tcp://mosquitto:1883";
    this.myQ = myQ;
  }

  public void run(){

    MqttClient client = null;
    try {
      client = new MqttClient(broker, MqttClient.generateClientId());
    } catch (MqttException e) {
      e.printStackTrace();
    }
    client.setCallback(this);

    // Connect to the MQTT broker.
    try {
      client.connect();
    } catch (MqttException e) {
      e.printStackTrace();
    }

    // Subscribe to a topic.
    try {
      client.subscribe("sensordata");
    } catch (MqttException e) {
      e.printStackTrace();
    }


  }


  @Override
  public void connectionLost(Throwable throwable) {
  System.out.println("Connection lost!");
  }

  @Override
  public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
    String arrivedMessage = new String(mqttMessage.getPayload());
    System.out.println("Message arrived: " + arrivedMessage);
    myQ.add(arrivedMessage);
  }

  @Override
  public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
    System.out.println("jo");
  }
}
