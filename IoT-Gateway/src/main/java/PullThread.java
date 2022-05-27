import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class PullThread implements Runnable {

  private DatagramSocket udpSocket = null;
  private SharedBuffer mybuffer = null;

  public static final AtomicBoolean NeedToExecute = new AtomicBoolean(false);
  private static final int WAIT_DELAY_MS_HACK      = 5000; //ms

  PullThread(DatagramSocket udpSocket, SharedBuffer mybuffer) {
    this.udpSocket = udpSocket;
    this.mybuffer = mybuffer;


  }
  public static final void SendSignalToExecute(){
    synchronized(NeedToExecute){
      NeedToExecute.set(true);
      NeedToExecute.notify();
    }
  }


  public void run() {
    while (true) {
      try {

        PullRequest();
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }

    }
  }


  public void PullRequest() throws IOException, InterruptedException {
    String[] sensoren = {"sensor1", "sensor2", "sensor3", "sensor4"};
    String pullMessage = "PULL";
    Thread.sleep(4000);
    //TimeUnit.SECONDS.sleep(2);
    // Thread.sleep(3500);

    byte[] messageBuffer = pullMessage.getBytes();

      for (int i = 1; i <= checkSensor.sensors.size(); i++) {
        try {
          if (checkSensor.isAlive.get(i - 1)) {
            InetAddress Address = InetAddress.getByName(checkSensor.sensors.get(i - 1));
            DatagramPacket p = new DatagramPacket(messageBuffer, messageBuffer.length, Address,
                1235);
            udpSocket.send(p);
          }
        } catch (ArrayIndexOutOfBoundsException | UnknownHostException e) {
          // System.out.println("Client ist nicht mehr vorhanden!");
          // System.out.print("\033[H\033[2J");
          // System.out.flush();
        }
      }
    }
  }






