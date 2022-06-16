import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {


  public static void main(String[] args) throws SocketException, UnknownHostException {


  String dest = System.getenv("DESTINATION");
  DatagramSocket Socket = new DatagramSocket(1235);
  InetAddress Address = InetAddress.getByName(dest);

  BlockingQueue<String> sharedQueue = new LinkedBlockingQueue<String>();

  Subscriber subscriber = new Subscriber(sharedQueue);
  UDP udpClient = new UDP(Socket,Address,sharedQueue);
  subscriber.start();
  udpClient.start();


  }

}
