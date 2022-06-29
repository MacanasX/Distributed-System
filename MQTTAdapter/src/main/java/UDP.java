import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;

public class UDP extends Thread {

  private DatagramSocket udpSocket;
  private InetAddress inetAddress;
  private byte [] buffer;
  private BlockingQueue<String> myQ = null;
  private Subscriber subscriber = null;

  UDP(DatagramSocket udpSocket, InetAddress inetAddress,BlockingQueue<String> myQ,Subscriber subscriber){

    this.udpSocket = udpSocket;
    this.inetAddress= inetAddress;
    this.myQ = myQ;
    this.subscriber = subscriber;
  }

  public void run() {
    String dest = System.getenv("DESTINATION");

    String message = subscriber.getCurrentMessage();
    subscriber.clearCurrentMessage();
    if(message != null) {
      byte[] buffer = message.getBytes();

      InetAddress Address = null;
      try {
        Address = InetAddress.getByName(dest);
      } catch (UnknownHostException e) {
        e.printStackTrace();
      }
      DatagramPacket p = new DatagramPacket(buffer, buffer.length, Address, 1234);

      try {
        this.udpSocket.send(p);
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }

}
