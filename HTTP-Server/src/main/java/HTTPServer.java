import databaseclient.Sensor;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class HTTPServer {
   public static BlockingQueue<Sensor> myReceivedMessages = new LinkedBlockingQueue<Sensor>();
   // public static Queue<Sensor> myReceivedMessages = new LinkedList<>();
    public static ArrayList<Sensor>myReceivedMessages_List = new ArrayList<>();
    private ServerSocket server;

    public HTTPServer() throws IOException {

        this.server = new ServerSocket(53257);
        this.server.setReuseAddress(true);

    }

    public static void main(String[] args) throws IOException {
        System.out.println("-- Running Server at " + InetAddress.getLocalHost() + "--");
        HTTPServer myserver = new HTTPServer();
      TransactionCoordinator thriftclient = new TransactionCoordinator("Database");
      thriftclient.start();
      //thriftclient.listenForMessage();

      while(true) {


      Socket client = myserver.server.accept();
      TCPServer myTCPHandler = new TCPServer(client);
      new Thread(myTCPHandler).start();

  }

    }
}
