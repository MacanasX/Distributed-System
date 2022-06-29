import databaseclient.CRUD;
import databaseclient.Sensor;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class TransactionCoordinator extends  Thread {

  public static final int PORT = 9090;
  public static final int PORTDB = 4000;
  /**
   * The host the client connects to.
   */
  public static final String HOST = "Database";
  public static final String HOST2 = "Database2";

  private Socket socket;
  private DataInputStream dataInputStream = null;
  private DataOutputStream dataoutputstream = null;
  public static ArrayList<String[]> sensorDataMessages = new ArrayList<>();

  ;

  TransactionCoordinator(String host) {

    CRUD.Client client2 = null;
    CRUD.Client client = null;
   /* this.socket =  null;//new Socket(host, PORTDB);

    DataOutputStream dataoutputstream = new DataOutputStream(socket.getOutputStream());
    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
  */
  }

  public void run() {

    boolean insertedDB1= false;
    boolean insertedDB2= false;
    CRUD.Client client = null;
    CRUD.Client client2 = null;
    Sensor sensorToSend = null;
    while (true) {

      try {
        TTransport transport = new TSocket(HOST, PORT);
        TProtocol protocol = new TBinaryProtocol(transport);
        transport.open();


        TTransport transport2 = new TSocket(HOST2, PORT);

         client = new CRUD.Client(protocol);

        TProtocol protocol2 = new TBinaryProtocol(transport2);
        client2 = new CRUD.Client(protocol2);

        transport2.open();

        //CRUD.Client client1 = new Client(protocol);

        while (true) {
          //Call RPC for Database

          insertedDB1 = false;
          insertedDB2 = false;


          sensorToSend = HTTPServer.myReceivedMessages.take();

          insertedDB1 = client.insert(sensorToSend);
          insertedDB2 = client2.insert(sensorToSend);



          if (insertedDB1 && insertedDB2) {

            client.commit(sensorToSend);

            client2.commit(sensorToSend);

            System.out.println("DB1 and DB2 succesfully commited!");

          }

        }
      } catch(TException | InterruptedException a){
        if (!insertedDB1 && insertedDB2) {

          try {
            client2.abort(sensorToSend);
          } catch (TException e) {
            e.printStackTrace();
          }

          System.out.println("DB2 aborted sensor!");

        }
        if (insertedDB1 && !insertedDB2) {

          try {
            client.abort(sensorToSend);
          } catch (TException e) {
            e.printStackTrace();
          }

          System.out.println("DB1 aborted sensor!");
        }

      }

    }
  }
}





/*
              String preparemessage = "PREPARE;" + sensorToSend.getId() +";" + sensorToSend.getMessageId();
              dataoutputstream.writeUTF(preparemessage);
              Thread.sleep(500);
              if(checkArrayListForMatchingSensor( Integer.toString(sensorToSend.getId()), Integer.toString(sensorToSend.getMessageId())))
                dataoutputstream.writeUTF("COMMIT;"+sensorToSend.getId() + ";" + sensorToSend.getMessageId() );




              Thread.sleep(500);
              //if(checkArrayListForACK(Integer.toString(sensorToSend.getId()),Integer.toString(sensorToSend.getMessageId())))




           // } else {
            //  break;
           }
            // Test calls for functions
            // client.update(database);
            // System.out.println("Select Methode called : " + client.select(database));
            // System.out.println("Remove called : " +  client.remove(database));
            // System.out.println("Remove called second time  : " +  client.remove(database));

         }*/



/*
  public void listenForMessage() {
    new Thread(new Runnable() {
      @Override
      public void run() {

        String msgFromDataBase;

        String [] arrayForMessages ;
        // While there is still a connection with the server, continue to listen for messages on a separate thread.
        while (socket.isConnected()) {
          try {
            // Get the messages sent from other users and print it to the console.
            msgFromDataBase = dataInputStream.readUTF();
            if(msgFromDataBase.contains("READY")){
              System.out.println("Nachrich angekommen: "+ msgFromDataBase);
              arrayForMessages = msgFromDataBase.split(";");
              sensorDataMessages.add(arrayForMessages);

            }
            else if(msgFromDataBase.contains("ACK")){

              arrayForMessages = msgFromDataBase.split(";");
              for(int i = 0 ; i < sensorDataMessages.size() ; i ++){

                if(sensorDataMessages.get(i)[1].equals(arrayForMessages[1]) && sensorDataMessages.get(i)[2].equals(arrayForMessages[2]))
                   sensorDataMessages.get(i)[0] = "ACK";
              }

            }

          } catch (IOException e) {
            // Close everything gracefully.
           // closeEverything(socket, bufferedReader, bufferedWriter);
          }
        }
      }
    }).start();
  }

  private boolean checkArrayListForMatchingSensor(String sensorId, String messageId){


    for(int i = 0 ; i < sensorDataMessages.size() ; i++){

      if(sensorDataMessages.get(i)[1].equals(sensorId) && sensorDataMessages.get(i)[2].equals(messageId))
        return true;

    }
   return false;
  }

  private boolean checkArrayListForACK(String sensorId, String messageId){


    for(int i = 0 ; i < sensorDataMessages.size() ; i++){

      if(sensorDataMessages.get(i)[1].equals(sensorId) && sensorDataMessages.get(i)[2].equals(messageId) && sensorDataMessages.get(i)[0].equals("ACK"))
        return true;

    }
    return false;
  }
} */




