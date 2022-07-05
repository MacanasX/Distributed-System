import databaseclient.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

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
    /**
     * The host the client connects to.
     */
    public static final String HOST = "Database";
    // public static BlockingQueue<Integer> myReceivedMessagesAfterCommit = new LinkedBlockingQueue<Integer>();
    // public static ArrayList<Integer> myReceivedMessagesAfterCommit2 = new ArrayList<>();

    TCPServer(Socket mySocket) throws IOException {
        this.TCPsocket = mySocket;


    }

    public void listen() throws IOException, ClassNotFoundException, ParseException, InterruptedException {


        String destination = System.getenv("DESTINATIONTCP");
        String response;
        String[] checkresponse;

        //  InputStream inputStream = this.TCPsocket.getInputStream();
        //  BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        DataInputStream input = new DataInputStream(this.TCPsocket.getInputStream());

        String message = input.readUTF();
        //System.out.println("Got a Message from: " + this.TCPsocket.getInetAddress());
        //System.out.println(message);


        checkresponse = message.split("\\r?\\n");
        // checkresponse = message.split(",");
        Socket dest = new Socket(destination, 53258);

        OutputStream outputStream = dest.getOutputStream();
        //create a data output stream from the output stream so we can send data through it
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        HTTPPost header = new HTTPPost();
        response = header.checkHtppMessage(message);

        //experimentelles...
        // System.out.println("HIER IST checkresponse!!!!!! :" + checkresponse[8]);
        //String splitContent[] = checkresponse[8].split(",");
        //  System.out.println("hier ist die msgID: " + splitContent[2] + " und SensorID: " + splitContent[6]);
        //String msgID = splitContent[2].substring(12, splitContent[2].length());
        //String sensordID = splitContent[6].substring(11, splitContent[6].length() - 1);
        //System.out.println("DAS IST DIE AUSGESCHNITTENE msgID: "+ msgID);
        //System.out.println("DAS IST DIE AUSGESCHNITTENE SensorID: "+ sensordID);
        //String hash = msgID + sensordID;
        //int hashValue = hash.hashCode();
        //System.out.println("DAS IST DER HASHCODE(TCPSERVER) aus " + msgID + " und " + sensordID + " ergibt= " + hashValue);
        //concurrentMap.take | blockingque
        //myReceivedMessagesAfterCommit.poll(1, TimeUnit.SECONDS);
        //this.wait();
        // Thread.sleep(2000);
       /* if(myReceivedMessagesAfterCommit2.contains(hashValue)){
            HTTPPost header = new HTTPPost();
            response = header.checkHtppMessage(message);
            dataOutputStream.writeUTF(response);
            myReceivedMessagesAfterCommit2.remove(hashValue);
        }*/

        dataOutputStream.writeUTF(response);


    }


    public void run() {

        try {
            // System.out.println("THREAD ERZEUGT!");
            this.listen();
        } catch (IOException | ClassNotFoundException | ParseException | InterruptedException e) {
            // e.printStackTrace();
        }

    }

}
