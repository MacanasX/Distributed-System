import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

  public static ArrayList<BlockingQueue<String>> Queues = new ArrayList<>();

  public static void main(String[] args) throws SocketException, UnknownHostException {


    UDPServer udpServer = new UDPServer();


    udpServer.start();










  }

}
