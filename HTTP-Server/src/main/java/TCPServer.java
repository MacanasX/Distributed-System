import databaseclient.CRUD;
import databaseclient.CRUD.Client;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TCPServer implements Runnable {

    private Socket TCPsocket;
    public static final int PORT = 9090;
    /** The host the client connects to. */
    public static final String HOST = "Database";

    TCPServer(Socket mySocket) throws IOException {
    this.TCPsocket = mySocket;


    }

    public void listen() throws IOException, ClassNotFoundException {
        String destination = System.getenv("DESTINATIONTCP");
        String response;
        String [] checkresponse;

      //  InputStream inputStream = this.TCPsocket.getInputStream();
     //  BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        DataInputStream input = new DataInputStream(this.TCPsocket.getInputStream());



        String message = input.readUTF();
        System.out.println("Got a Message from: " + this.TCPsocket.getInetAddress() );
        System.out.println(message);
        HTTPPost header = new HTTPPost();
        response=header.checkHtppMessage(message);

        checkresponse = message.split("\\r?\\n");
       // if(checkresponse[0].equals("HTTP/1.1 200 OK /"))
       // {
            try (TTransport transport = new TSocket(HOST, PORT)){
                transport.open();
                TProtocol protocol = new TBinaryProtocol(transport);
                CRUD.Client client  = new Client(protocol);
                JSONParser parser = new JSONParser();
                System.out.println("Ausgabe von checkresponse: " + checkresponse[checkresponse.length-1]);

                JSONObject json = (JSONObject) parser.parse(checkresponse[checkresponse.length-1]);

                //CRUD.Client client = new CRUD.Client(protocol);

                client.insert(json.get("unit").toString(),json.get("name").toString(),json.get("messageId").toString()
                    ,json.get("value").toString(),json.get("sensor_type").toString(),json.get("timestamp").toString(),
                    json.get("sensorId").toString());


            } catch (TException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        //}
       // PrintStream output = new PrintStream(TCPsocket.getOutputStream());
       // output.println(response);

       Socket dest = new Socket(destination, 53258);

        OutputStream outputStream = dest.getOutputStream();
         //create a data output stream from the output stream so we can send data through it
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        dataOutputStream.writeUTF(response);
       // dataOutputStream.flush();
      //  dataOutputStream.close();

    }


public void run(){

    try {
     // System.out.println("THREAD ERZEUGT!");
        this.listen();
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }

}

}
