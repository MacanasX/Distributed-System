import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;
import com.google.gson.*;

public class UDPHandler extends Thread   {

  //Socket udpSocket=null;
  DatagramPacket udpPacket=null;
  private ArrayList<String> messageBuffer;
  private SharedBuffer myBuffer = null;
  private  DatagramPacket packet= null;
  UDPHandler(DatagramPacket packet,SharedBuffer myBuffer){

    this.packet = packet;
    this.myBuffer = myBuffer;

  }


  public void run() {



    String message = new String(packet.getData()).trim();

    myBuffer.put(message);
    //  System.out.println("Got a Message from " + udpPacket.getAddress());
    // System.out.println(
    //     new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(message)));

    }



  }


