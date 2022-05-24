import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class checkSensor extends Thread {

public static ArrayList<Boolean> isAlive = new ArrayList<>();
public static ArrayList<String> sensors = new ArrayList<>();
  public void run() {


      while(true) {

        for (int i = 0; i < sensors.size(); i++) {
          try {

            InetAddress Address = InetAddress.getByName(sensors.get(i));
            isAlive.add(check(Address, 1235));

          } catch (UnknownHostException | SocketException e) {
            System.out.println(sensors.get(i) + " ist nicht mehr erreichbar!");
            sensors.remove(i);
          }


        }
      }

  }



  private boolean check(InetAddress Address, int port) throws SocketException {

    boolean isAlive = false;

    // Creates a socket address from a hostname and a port number
    SocketAddress socketAddress = new InetSocketAddress(Address, port);
    DatagramSocket socket = new DatagramSocket(1238);
    socket.connect(Address,1235);
    socket.close();
    isAlive = true;
    return isAlive;
  }


}
