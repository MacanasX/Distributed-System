import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TCPHandler extends Thread {

    private Socket TCPsocket = null;
    //public static volatile ArrayList<String> messageBuffer = new ArrayList<>();
//   public final static Integer LOCK = 0;
    private SharedBuffer myBuffer = null;
    private BlockingQueue<String> myQ = null;
    private String tcp = "";
    public static final int TcpHttpPort = 53258;


    TCPHandler(Socket mySocket, BlockingQueue<String> myQ) throws IOException {

        this.TCPsocket = mySocket;
        //  this.myBuffer = myBuffer;
        this.myQ = myQ;
        this.tcp = System.getenv("DESTINATIONTCP");

    }

    public void run() {
        try {

            RttLogger.logger.addHandler(RttLogger.filehandler);
            SimpleFormatter formatter = new SimpleFormatter();
            RttLogger.filehandler.setFormatter(formatter);


            HTTPRequest myrequest = new HTTPRequest();
            TestHTTPcheck request = new TestHTTPcheck();
            int counter = 0;
            String TCPmessage = "";


            while (true) {

                String msg = myQ.take();
                TCPmessage = msg;
                counter ++;
                //   System.out.println("HIER IST DIE MESSAGE AG WANN DIE MESSUNG LOSGEHT! " + TCPmessage + "!");


                DataOutputStream output = new DataOutputStream(this.TCPsocket.getOutputStream());
                // HEADER TEST - nur POST-Anfragen sind erlaubt | erhöht die RTT
                if(counter % 10 == 0){
                    TCPmessage = request.generateFalseHTTPHeaderGet(TCPmessage);
                }
                // Wrong Protocol Test - nur HTTP ist erlaubt
                else if(counter % 15 == 0){
                    TCPmessage = request.generateFalseHTTPHeader2(TCPmessage);//"Banane, Apfel, Kirche, Orange, aber, kein,\n Json,String,test"
                }
                else {
                    TCPmessage = myrequest.generateHTTPHeader(TCPmessage);
                }


                // output.writeUTF(myrequest.generateHTTPHeader("hallo welt"));
                //try{

                output.writeUTF(TCPmessage);
                //output.writeUTF("halloWelt");

                  /*}
                  catch(SocketException e){
                  }*/


                // hier wird ein eindeutiger key aus SensorID und MessageID gebaut für die RTT
                String[] tmpSplit = msg.split(",");
                String msgID = tmpSplit[2];
                String sensID = tmpSplit[6];
                String hash = msgID + sensID;
                RttLogger.start.put(hash.hashCode(), ZonedDateTime.now().toInstant().toEpochMilli());


                output.flush();
                this.TCPsocket.close();
                this.TCPsocket = new Socket(tcp, 53257);


            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
