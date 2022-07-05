import java.util.concurrent.BlockingQueue;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Subscriber extends  Thread implements MqttCallback {

  private String broker;
  private BlockingQueue<String> myQ = null;
  private String topic = null;
  private String currentMessage =null;

  Subscriber(BlockingQueue<String> myQ, String sensorId){

    broker = "tcp://mosquitto:1883";
    this.myQ = myQ;
    topic = sensorId;

  }

  public BlockingQueue<String> getMyQ() {
    return myQ;
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
        client.subscribe(topic);
      } catch (MqttException e) {
        e.printStackTrace();
      }

    }


  public String getCurrentMessage() {
    return currentMessage;
  }
  public void clearCurrentMessage(){
    currentMessage = null;
  }

  @Override
  public void connectionLost(Throwable throwable) {
  System.out.println("Connection lost!");
  }

  @Override
  public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
    String arrivedMessage = new String(mqttMessage.getPayload());
   // System.out.println("Message arrived: " + arrivedMessage);
   // myQ.add(arrivedMessage);
    currentMessage = arrivedMessage;

  }

  @Override
  public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
    System.out.println("jo");
  }
}
