import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class UDPThread implements Runnable {

  DatagramPacket udpPacket=null;
  DatagramSocket Socket=null;
  UDPThread(DatagramPacket packet, DatagramSocket Socket){
    this.udpPacket = packet;
    this.Socket =  Socket;


  }
  public void run(){

    String message = new String(this.udpPacket.getData()).trim();

    if(message.equals("PULL"))
    {

      int sensID = Integer.parseInt(System.getenv("SENSOR_ID"));
      String type = System.getenv("SENSOR_TYPE");
      String name = System.getenv("SENSOR_NAME");




      //Scanner scanner = new Scanner(System.in);
      Sensor sensor1 = new Sensor(sensID,name, type);
      SensorData sensorData = new SensorData(sensor1);


        try {

          sensorData.generateData();
          String message2 =sensorData.serializeToJSON().toString();

          byte [] buffer  = message2.getBytes();

          DatagramPacket p = new DatagramPacket(buffer, buffer.length, this.udpPacket.getAddress(), 1234);

          this.Socket.send(p);
         // TimeUnit.SECONDS.sleep(7);
        }

        catch(IOException e){
          e.printStackTrace();

        }
       // catch (InterruptedException k){
        //  k.printStackTrace();
        //  break;
        }
      }

    }




