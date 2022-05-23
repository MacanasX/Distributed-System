import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class HTTPServer {
    private ArrayList<String>myReceivedMessages;
    private ServerSocket server;
    public HTTPServer() throws IOException {

        this.server = new ServerSocket(53257);
        this.server.setReuseAddress(true);
        this.myReceivedMessages = new ArrayList<>();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("-- Running Server at " + InetAddress.getLocalHost() + "--");
        HTTPServer myserver = new HTTPServer();


       // TCPServer myTCPHandler = new TCPServer(new ServerSocket(53257));

  while(true) {

      Socket client = myserver.server.accept();

      TCPServer myTCPHandler = new TCPServer(client,myserver.myReceivedMessages);
      new Thread(myTCPHandler).start();
     // myTCPHandler.run();

     //myTCPHandler.run();


  }



    }
}
