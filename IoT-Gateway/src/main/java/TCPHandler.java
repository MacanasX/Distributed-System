import java.io.*;
import java.net.Socket;
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

  private Socket TCPsocket =null;
  public static volatile ArrayList<String> messageBuffer = new ArrayList<>();
   public final static Integer LOCK = 0;
   private SharedBuffer myBuffer = null;
  private BlockingQueue<String> myQ = null;
  private String tcp = "";


  public static long startMilliSeconds;
//  public static long endeMilliSeconds;
  TCPHandler(Socket mySocket, BlockingQueue<String> myQ) throws IOException {

        this.TCPsocket = mySocket;
      //  this.myBuffer = myBuffer;
        this.myQ= myQ;
        this.tcp=  System.getenv("DESTINATIONTCP");
        //this.logger = Logger.getLogger("MyRTT");
       // this.filehandler = new FileHandler("/sharedData/RTT/MyRTT.log");
     //   /file/path/in/container/file /host/local/path/file
    }

    public void run() {
      try {

        RttLogger.logger.addHandler(RttLogger.filehandler);
        SimpleFormatter formatter = new SimpleFormatter();
        RttLogger.filehandler.setFormatter(formatter);

        //OutputStream outputStream = TCPsocket.getOutputStream();
       // PrintWriter writer = new PrintWriter(outputStream,true);

      //  OutputStream outputStream = TCPsocket.getOutputStream();
       // DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        HTTPRequest myrequest = new HTTPRequest();
        String TCPmessage = "";
        int logCounter = 0;

      while (true) {

          String msg = myQ.take();
          TCPmessage = msg;

       //   System.out.println("HIER IST DIE MESSAGE AG WANN DIE MESSUNG LOSGEHT! " + TCPmessage + "!");

          TCPmessage = myrequest.generateHTTPHeader(TCPmessage);
          //System.out.println("HIER IST DIE MESSAGE AG WANN DIE MESSUNG LOSGEHT! " + TCPmessage);
        DataOutputStream output = new DataOutputStream(this.TCPsocket.getOutputStream());
       // output.writeUTF(myrequest.generateHTTPHeader("hallo welt"));
        output.writeUTF(TCPmessage);

        //output.writeUTF("halloWelt");



         // System.out.println("HIER wird die MESSAGE GESPLITTET!-msgID " + msgID + " und senID " + sensID);
        //  System.out.println("die groesse des string: " + sensID.length() );
         // int messageID = Integer.parseInt(String.valueOf(msgID.charAt(10)));
        //  String sensorID = String.valueOf(sensID.charAt(11)); //length = 13 -2
          //int test = Integer.parseInt(sensorID);


         // System.out.println("HIER wird die sendMESSAGE GESPLITTET nach CONVERT!-msgID "  + msgID +  " und senID " + sensID );
         // Calendar calendar = Calendar.getInstance();
          //hashCode(msgID, sensorID);
          //sensorID.hashCode();

         // hash.hashCode();
          //   System.out.println("HIER IST DER GEBAUTE sendHASH AUS " + msgID + " " + sensID + " ist gleich: " + hash.hashCode());
          // hier wird ein eindeutiger key aus SensorID und MessageID gebaut
          String[] tmpSplit = msg.split(",");
          String msgID = tmpSplit[2];
          String sensID = tmpSplit[6];
          String hash = msgID + sensID;
          RttLogger.start.put(hash.hashCode(),ZonedDateTime.now().toInstant().toEpochMilli() );

        // Getting the time in milliseconds.

     //   RttLogger.memory_Start.add(ZonedDateTime.now().toInstant().toEpochMilli());
        // startMilliSeconds = calendar.getTimeInMillis();


        //  RttLogger.logger.log(Level.INFO, "Startzeit " + logCounter+ ": " + startMilliSeconds);
         // System.out.println("test MILLISEKUNDEN: "  + startMilliSeconds);
        //  logCounter++;

          output.flush();
         this.TCPsocket.close();
        this.TCPsocket = new Socket(tcp,53257);
      // output.close();
         // dataOutputStream.writeUTF(TCPmessage);
         // dataOutputStream.flush(); // send the message
         // dataOutputStream.close();
         // this.TCPsocket.close();
      }
        } catch (IOException | InterruptedException e) {
          e.printStackTrace();
        }

}
}
