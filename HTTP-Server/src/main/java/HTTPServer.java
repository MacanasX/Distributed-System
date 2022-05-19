import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class HTTPServer {

    private ServerSocket server;
    public HTTPServer() throws IOException {

        this.server = new ServerSocket(53257);
    }

    public static void main(String[] args) throws IOException {
        System.out.println("-- Running Server at " + InetAddress.getLocalHost() + "--");
        HTTPServer myserver = new HTTPServer();
        Socket threadSocket;
       // TCPServer myTCPHandler = new TCPServer(new ServerSocket(53257));

  while(true) {

    threadSocket = myserver.server.accept();

      TCPServer myTCPHandler = new TCPServer(threadSocket);
      myTCPHandler.run();

     //myTCPHandler.run();


  }



    }
}
