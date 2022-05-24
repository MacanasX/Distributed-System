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




      for (int i = 0; i < sensors.size(); i++) {
        try {

          InetAddress Address = InetAddress.getByName(sensors.get(i));

          isAlive.add(checkSensor(Address, 1235));

        } catch (UnknownHostException | SocketException e) {
          System.out.println(sensors.get(i) + " ist nicht mehr erreichbar!");
          sensors.remove(i);
        }


      }
    try {
      Thread.sleep(4500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }



  private boolean checkSensor(InetAddress Address, int port) throws SocketException {

    boolean isAlive = false;

    // Creates a socket address from a hostname and a port number
    SocketAddress socketAddress = new InetSocketAddress(Address, port);
    DatagramSocket socket = new DatagramSocket(1238);

    // Timeout required - it's in milliseconds
    int timeout = 2000;

   // log("hostName: " + Address + ", port: " + port);

    socket.connect(Address,1235);
   // System.out.println("Sensor " + Address + " is alive!");
    socket.close();
    isAlive = true;

    return isAlive;
  }

  private static void log(String string) {
    System.out.println(string);
  }

  // Simple log utility returns boolean result
  private static void log(boolean isAlive) {
    System.out.println("isAlive result: " + isAlive + "\n");
  }
}
