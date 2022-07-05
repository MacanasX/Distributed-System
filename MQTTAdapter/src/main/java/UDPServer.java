import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class UDPServer extends Thread{

  private DatagramSocket udpSocket;
  private InetAddress inetAddress;
  private byte [] buffer;


  UDPServer() throws SocketException {

    udpSocket = new DatagramSocket(1235);


  }

  public void run() {

    ArrayList<Subscriber> Threadlist = new ArrayList<>();

    BlockingQueue<String> sharedQueue = new LinkedBlockingQueue<String>();
    BlockingQueue<String> sharedQueue2 = new LinkedBlockingQueue<String>();
    BlockingQueue<String> sharedQueue3 = new LinkedBlockingQueue<String>();
    BlockingQueue<String> sharedQueue4 = new LinkedBlockingQueue<String>();

    Main.Queues.add(sharedQueue);
    Main.Queues.add(sharedQueue2);
    Main.Queues.add(sharedQueue3);
    Main.Queues.add(sharedQueue4);

    Subscriber subscriber = new Subscriber( sharedQueue , "5");
    Subscriber subscriber2 = new Subscriber(sharedQueue2, "6");
    Subscriber subscriber3 = new Subscriber(sharedQueue3, "7");
    Subscriber subscriber4 = new Subscriber(sharedQueue4, "8");



    Threadlist.add(subscriber);
    Threadlist.add(subscriber2);
    Threadlist.add(subscriber3);
    Threadlist.add(subscriber4);

    for (Subscriber a: Threadlist
    ) {
      a.start();
    }


    String dest = System.getenv("DESTINATION");
    InetAddress Address = null; //InetAddress.getLocalHost()
    try {
      Address = InetAddress.getByName(dest);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }

    while(true){

      byte[] buf = new byte[256];

      DatagramPacket packet = new DatagramPacket(buf,buf.length);

      try {
        udpSocket.receive(packet);
      } catch (IOException e) {
        e.printStackTrace();
      }

      String message = new String(packet.getData()).trim();

      if(message.equals("PULL")) {

        for(int i = 0 ; i < 4 ; i++ )
        {
          UDP udpThread = new UDP(udpSocket,Address,Main.Queues.get(i),Threadlist.get(i));
          udpThread.start();

        }
      }
    }

  }

}
