import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TCPHandler extends Thread {

  private Socket TCPsocket =null;
  public static volatile ArrayList<String> messageBuffer = new ArrayList<>();
   public final static Integer LOCK = 0;
   private SharedBuffer myBuffer = null;
  private BlockingQueue<String> myQ = null;
  private String tcp = "";
  private Logger logger = null;
  private FileHandler filehandler = null;
  TCPHandler(Socket mySocket, BlockingQueue<String> myQ) throws IOException {

        this.TCPsocket = mySocket;
      //  this.myBuffer = myBuffer;
        this.myQ= myQ;
        this.tcp=  System.getenv("DESTINATIONTCP");
        this.logger = Logger.getLogger("MyRTT");
        this.filehandler = new FileHandler("./MyRTT.log");
    }

    public void run() {
      try {
        logger.addHandler(filehandler);
        SimpleFormatter formatter = new SimpleFormatter();
        filehandler.setFormatter(formatter);
        //OutputStream outputStream = TCPsocket.getOutputStream();
       // PrintWriter writer = new PrintWriter(outputStream,true);

      //  OutputStream outputStream = TCPsocket.getOutputStream();
       // DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        HTTPRequest myrequest = new HTTPRequest();
        String TCPmessage = "";
      while (true) {
         // this.TCPsocket = new Socket(tcp,53257);

          // create a data output stream from the output stream so we can send data through it

          TCPmessage =  myQ.take();
         // System.out.println(TCPmessage);

          // if (TCPHandler.messageBuffer.size() != 0) {
        /*  while (!myBuffer.isEmpty()) {  //myBuffer.getBufferSize() > 0

            TCPmessage = TCPmessage + myBuffer.get();
            if (myBuffer.getBufferSize() > 0)
              TCPmessage = TCPmessage + "," + "\n";

          } */

          TCPmessage = myrequest.generateHTTPHeader(TCPmessage);
       //   System.out.println(TCPmessage);
        DataOutputStream output = new DataOutputStream(this.TCPsocket.getOutputStream());
          output.writeUTF(TCPmessage);
        Calendar calendar = Calendar.getInstance();

        // Getting the time in milliseconds.
        long milliSeconds = calendar.getTimeInMillis();
        ;
          logger.info(String.valueOf(milliSeconds));

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
