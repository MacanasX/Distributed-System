import java.util.ArrayList;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import server.*;
import server.CRUD.Processor;

public class DatabaseServer {
  /** The port the server listens to. */
  public static final int PORT = 9090;
  public static ServerHandler handler;
  public static CRUD.Processor processor;

  public static ArrayList<Sensor> table = new ArrayList<>();


  /**
   * Start a simple Thrift server.
   *
   * @param processor The handler that handles incoming messages.
   */
  public static void startSimpleServer(CRUD.Processor<ServerHandler> processor) {
    try {
      TServerTransport serverTransport = new TServerSocket(PORT);
      TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
      System.out.println("Starting Database...");
      server.serve();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws Exception {
   // handler = new ServerHandler();
    //processor = new Processor<>(handler);
   // startSimpleServer(processor);
    startSimpleServer(new CRUD.Processor<>(new ServerHandler()));
   // startSimpleServer(new Calc.Processor<>(new ServerHandler()));



    }


  }








