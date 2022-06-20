import databaseclient.CRUD;
import databaseclient.CRUD.Client;
import databaseclient.Sensor;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ThriftClient extends  Thread {

  public static final int PORT = 9090;
  /** The host the client connects to. */
  public static final String HOST = "Database";
  public static final String HOST2 = "Database2";


  public void run(){

    try (TTransport transport = new TSocket(HOST, PORT)){
      transport.open();
      TProtocol protocol = new TBinaryProtocol(transport);
      CRUD.Client client  = new CRUD.Client(protocol);
      //CRUD.Client client1 = new Client(protocol);
        try (TTransport transport2 = new TSocket(HOST2, PORT)){
          transport2.open();
          TProtocol protocol2 = new TBinaryProtocol(transport2);
          CRUD.Client client2  = new CRUD.Client(protocol2);


              while(true) {
                //Call RPC for Database
                Sensor temp = HTTPServer.myReceivedMessages.take();
               // client.insert(HTTPServer.myReceivedMessages.take());

                client.insert(temp);
                client2.insert(temp);

                // Test calls for functions
                // client.update(database);
                // System.out.println("Select Methode called : " + client.select(database));
                // System.out.println("Remove called : " +  client.remove(database));
                // System.out.println("Remove called second time  : " +  client.remove(database));

              }

          } catch (TException | InterruptedException e2) {
            e2.printStackTrace();
          }

    } catch (TException e) {
      e.printStackTrace();
    }



  }


}
