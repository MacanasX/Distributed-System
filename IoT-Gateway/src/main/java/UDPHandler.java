import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;
import com.google.gson.*;
import java.util.concurrent.BlockingQueue;

public class UDPHandler extends Thread   {

  //Socket udpSocket=null;
  DatagramPacket udpPacket=null;
  private ArrayList<String> messageBuffer;
  private SharedBuffer myBuffer = null;
  private  DatagramPacket packet= null;
  private BlockingQueue<String> myQ = null;
  UDPHandler(DatagramPacket packet, BlockingQueue<String> myQ){

    this.packet = packet;
    this.myBuffer = myBuffer;
    this.myQ= myQ;
  }


  public void run() {



    String message = new String(packet.getData()).trim();

    myQ.add(message);
     // System.out.println("Got a Message from " + packet.getAddress());
    // System.out.println(
    //     new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(message)));

    }



  }


