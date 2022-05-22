import java.net.DatagramPacket;
import java.net.Socket;
import java.util.ArrayList;

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
    System.out.println(message);


  }
  public synchronized void writeIntoMessageBuffer(String message){

    messageBuffer.add(message);

  }





  }


