import databaseclient.CRUD.Client;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import databaseclient.CRUD;

public class HTTPServer {

    public static ArrayList<String[]>myReceivedMessages = new ArrayList<>();
    private ServerSocket server;
    public HTTPServer() throws IOException {

        this.server = new ServerSocket(53257);
        this.server.setReuseAddress(true);

    }

    public static void main(String[] args) throws IOException {
        System.out.println("-- Running Server at " + InetAddress.getLocalHost() + "--");
        HTTPServer myserver = new HTTPServer();


      while(true) {


      Socket client = myserver.server.accept();
      TCPServer myTCPHandler = new TCPServer(client);
      new Thread(myTCPHandler).start();



  }



    }
}
