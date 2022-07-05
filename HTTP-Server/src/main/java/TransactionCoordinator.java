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

public class TransactionCoordinator extends Thread {

    public static final int PORT = 9090;
    public static final int PORTDB = 4000;
    /**
     * The host the client connects to.
     */
    public static final String HOST = "Database";
    public static final String HOST2 = "Database2";

    //private Socket socket;
    private DataInputStream dataInputStream = null;
    private DataOutputStream dataoutputstream = null;
    // public static ArrayList<String[]> sensorDataMessages = new ArrayList<>();
    // priavete transport

    private boolean insertedDB1 = false;
    private boolean insertedDB2 = false;
    private CRUD.Client client = null;
    private CRUD.Client client2 = null;
    private Sensor sensorToSend = null;
    private TTransport transport = null;
    private TProtocol protocol = null;
    private TTransport transport2 = null;
    private TProtocol protocol2 = null;

    TransactionCoordinator(String host) {


        transport = new TSocket(HOST, PORT);
        protocol = new TBinaryProtocol(transport);
        client = new CRUD.Client(protocol);
        transport2 = new TSocket(HOST2, PORT);
        protocol2 = new TBinaryProtocol(transport2);
        client2 = new CRUD.Client(protocol2);

   /* this.socket =  null;//new Socket(host, PORTDB);

    DataOutputStream dataoutputstream = new DataOutputStream(socket.getOutputStream());
    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
  */
    }

    public void reconectDb() throws InterruptedException, TTransportException {

        Thread.sleep(8000);
        try {
            transport.open();
            transport2.open();
        } catch (TTransportException e) {
            System.out.println("Could not connect to Database... Trying again...");
        }
        for (int i = 0; i < 20; i++) {

            if (!transport2.isOpen()) {
                while (true) {
                    try {
                        transport2.open();
                    } catch (TTransportException e) {
                    }
                    break;
                }
            }
            if (!transport.isOpen()) {
                while (true) {
                    try {
                        transport.open();
                    } catch (TTransportException e) {
                    }
                    break;
                }
            }

            if (transport2.isOpen() && transport.isOpen()) {
                System.out.println("Both Databases are reachable");
                HTTPServer.myReceivedMessages.clear();
                run();
            }
        }
    }


    public void run() {


        while (true) {

            try {

                System.out.print("\nTrying to connect to Database...\n");

                if (!transport.isOpen()) {
                    transport.open();
                    System.out.print("Connection to Database 1 was successful!\n");
                }

                if (!transport2.isOpen()) {
                    transport2.open();
                    System.out.print("Connection to Database 2 was successful!\n");
                }


                while (true) {

                    insertedDB1 = false;
                    insertedDB2 = false;

                    sensorToSend = HTTPServer.myReceivedMessages.take();

                    insertedDB1 = client.insert(sensorToSend);
                    insertedDB2 = client2.insert(sensorToSend);

                    if (insertedDB1 && insertedDB2) {

                        client.commit(sensorToSend);

                        client2.commit(sensorToSend);

                        // hashwert bauen, falls notwendig
                        // String msgID = String.valueOf(sensorToSend.getMessageId());
                        // String sensorID = String.valueOf(sensorToSend.getId());
                        // String hash = msgID + sensorID;
                        // int hashValue = hash.hashCode();
                        // System.out.println("hier wird der HASH AUFGEBAUT (TransactionCoordinator) aus " + msgID + " und " + sensorID + " und das ergibt = " + hashValue);
                        // TCPServer.myReceivedMessagesAfterCommit2.add(hashValue);

                        System.out.println("DB1 and DB2 succesfully commited!");

                    }

                    // }
                }
            } catch (TException | InterruptedException a) {

                if (!insertedDB1 || !insertedDB2) {

                    try {
                        client.abort(sensorToSend);
                        client2.abort(sensorToSend);
                    } catch (TException e) {
                    }

                    System.out.println("Database is currently not available!");

                    try {
                        transport.close();
                        transport2.close();

                        reconectDb();

                    } catch (InterruptedException | TTransportException e) {
                    }
                }

            }//catch

        }//1.while
    }//run
}//class





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




