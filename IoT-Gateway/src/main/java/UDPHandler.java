import java.net.DatagramPacket;
import java.net.Socket;
import java.util.ArrayList;
import com.google.gson.*;

public class UDPHandler implements Runnable {

  //Socket udpSocket=null;
  DatagramPacket udpPacket=null;
  private ArrayList<String> messageBuffer;


  UDPHandler(DatagramPacket packet, ArrayList<String> Buffer){

    //this.udpSocket=socket;
    this.udpPacket=packet;
    this.messageBuffer = Buffer;
  }


  public void run() {

    String message = new String(udpPacket.getData()).trim();
    this.writeIntoMessageBuffer(message);
    System.out.println("Got a Message from " + udpPacket.getAddress());
    System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(message)));
   // this.printMessage(message);



  }
  public synchronized void writeIntoMessageBuffer(String message){

    messageBuffer.add(message);

  }

  public void printMessage(String message){

   Gson gson = new GsonBuilder().setPrettyPrinting().create();
   JsonParser jp = new JsonParser();
   JsonElement je = jp.parse(message);
   String jsonOutput = gson.toJson(je);
    System.out.println(jsonOutput);

  }





  }


